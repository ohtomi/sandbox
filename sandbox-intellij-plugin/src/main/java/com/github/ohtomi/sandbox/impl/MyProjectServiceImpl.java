package com.github.ohtomi.sandbox.impl;

import com.github.ohtomi.sandbox.MyProjectService;
import com.intellij.openapi.project.Project;

public class MyProjectServiceImpl implements MyProjectService {

    public MyProjectServiceImpl(Project project) {
        System.out.println(String.format(
                "implementation: %s, project: %s", MyProjectServiceImpl.class.getCanonicalName(), project.getName()));
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
