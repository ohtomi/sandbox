package com.github.ohtomi.sandbox;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class MyPopupAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        MyProjectService service = ServiceManager.getService(project, MyProjectService.class);
        String message = service.getMessage();
        String title = service.getTitle();
        Messages.showInfoMessage(project, message, title);
    }
}
