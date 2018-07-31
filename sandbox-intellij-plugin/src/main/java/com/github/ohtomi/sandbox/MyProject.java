package com.github.ohtomi.sandbox;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface MyProject {
    static MyProject getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, MyProject.class);
    }

    String getMessage();
    String getTitle();
}
