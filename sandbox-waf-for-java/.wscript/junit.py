#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
from waflib import Task, TaskGen, Utils, Node
from waflib.TaskGen import feature, before, after
from waflib.Configure import conf


JUNIT_RUNNER = 'org.junit.runner.JUnitCore'


@feature('junit')
@after('apply_java', 'use_javac_files')
def make_junit_compile_task(self):
    if not hasattr(self, 'srcdir_test'):
        return
    if not hasattr(self, 'outdir_test'):
        return
    if not hasattr(self, 'sourcepath_test'):
        return
    if not hasattr(self, 'classpath_test'):
        return

    tmp = []
    srcdir_test = getattr(self, 'srcdir_test', '')
    if isinstance(srcdir_test, Node.Node):
        srcdir_test = [srcdir_test]
    for x in Utils.to_list(srcdir_test):
        if isinstance(x, Node.Node):
            y = x
        else:
            y = self.path.find_dir(x)
            if not y:
                self.bld.fatal('Could not find the folder %s from %s' % (x, self.path))
        tmp.append(y)
    self.srcdir_test = tmp

    outdir_test = getattr(self, 'outdir_test')
    if outdir_test:
        if not isinstance(outdir_test, Node.Node):
            outdir_test = self.path.get_bld().make_node(self.outdir_test)
    else:
        outdir_test = self.path.get_bld()
    outdir_test.mkdir()
    self.outdir_test = outdir_test.abspath()

    fold = [isinstance(x, Node.Node) and x or self.path.find_dir(x) for x in self.to_list(self.sourcepath_test)]
    names = os.pathsep.join([x.srcpath() for x in fold])
    self.sourcepath_test = names

    cps = os.pathsep.join(self.to_list(self.classpath_test))
    self.classpath_test = cps + os.pathsep

    junit_compile_task = self.create_task('junit_compile')
    junit_run_task = self.create_task('junit_run')
    try:
        junit_compile_task.set_run_after(self.javac_task)
        junit_run_task.set_run_after(junit_compile_task)
    except:
        pass


class junit_compile(Task.Task):
    color = 'YELLOW'
    vars = []

    def runnable_status(self):
        for t in self.run_after:
            if not t.hasrun:
                return Task.ASK_LATER

        self.srcdir_test = self.generator.srcdir_test
        self.outdir_test = self.generator.outdir_test
        self.sourcepath_test = self.generator.sourcepath_test
        self.classpath_test = self.generator.classpath_test

        n = self.generator.path.find_dir(self.sourcepath_test)
        if not n:
            self.generator.bld.fatal('Could not find the sourcepath_test dir %r' % self.generator.sourcepath_test)
        self.base = n
        self.inputs = n.ant_glob('**/*.java')

        return super(junit_compile, self).runnable_status()

    def run(self):
        cmd = []
        cmd.extend(self.env.JAVAC)
        cmd.append('-classpath')
        cmd.append(
                self.generator.javac_task.env.CLASSPATH
                + os.pathsep
                + self.generator.javac_task.env.OUTDIR
                + os.pathsep
                + self.classpath_test
        )
        cmd.extend(['-d'])
        cmd.append(self.outdir_test)
        cmd.extend(['-sourcepath'])
        cmd.append(self.sourcepath_test)
        cmd.extend([x.path_from(self.generator.bld.bldnode) for x in self.inputs])
        return self.exec_command(cmd)


class junit_run(Task.Task):
    color = 'YELLOW'
    vars = []

    def runnable_status(self):
        for t in self.run_after:
            if not t.hasrun:
                return Task.ASK_LATER

        n = self.generator.path.find_dir(self.generator.sourcepath_test)
        if not n:
            self.generator.bld.fatal('Could not find the sourcepath_test dir %r' % self.generator.sourcepath_test)
        self.base = n
        self.inputs = n.ant_glob('**/*.java')

        return super(junit_run, self).runnable_status()

    def run(self):
        cmd = []
        cmd.extend(self.env.JAVA)
        cmd.append('-classpath')
        cmd.append(
                self.generator.javac_task.env.CLASSPATH
                + os.pathsep
                + self.generator.javac_task.env.OUTDIR
                + os.pathsep
                + self.generator.classpath_test
                + os.pathsep
                + self.generator.outdir_test
        )
        cmd.append(JUNIT_RUNNER)
        cmd.extend([x.path_from(self.base).replace('.java', '').replace(os.sep, '.') for x in self.inputs])
        return self.exec_command(cmd)
