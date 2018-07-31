package com.github.ohtomi.sandbox.impl;

import com.github.ohtomi.sandbox.MyProject;
import com.intellij.notification.EventLog;
import com.intellij.openapi.project.Project;

public class MyProjectImpl implements MyProject {
    public MyProjectImpl(Project project) {
        // TODO
    }

    @Override
    public String getMessage() {
        return "hello, my project.";
    }

    @Override
    public String getTitle() {
        return "project service";
    }
}
