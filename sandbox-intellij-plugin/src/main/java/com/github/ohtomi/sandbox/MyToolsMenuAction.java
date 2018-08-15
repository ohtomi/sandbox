package com.github.ohtomi.sandbox;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class MyToolsMenuAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showInfoMessage("Hello World!", "Hello");
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setText("show my popup");
        e.getPresentation().setDescription("my popup action");
        e.getPresentation().setIcon(AllIcons.Ide.HectorOff);
    }
}
