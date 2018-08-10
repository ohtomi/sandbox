package com.github.ohtomi.sandbox;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MyToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JComponent parent = toolWindow.getComponent();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Hello World!!");
        panel.add(label);
        parent.add(panel);
    }
}
