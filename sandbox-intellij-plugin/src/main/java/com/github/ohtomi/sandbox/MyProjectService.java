package com.github.ohtomi.sandbox;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface MyProjectService {

    static MyProjectService getInstance(@NotNull Project project) {
        System.out.println(String.format(
                "interface: %s, project: %s", MyProjectService.class.getCanonicalName(), project.getName()));
        return ServiceManager.getService(project, MyProjectService.class);
    }

    String getMessage();

    String getTitle();
}
