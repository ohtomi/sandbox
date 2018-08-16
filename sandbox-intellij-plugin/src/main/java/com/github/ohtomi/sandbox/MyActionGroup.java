package com.github.ohtomi.sandbox;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyActionGroup extends ActionGroup {

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        return new AnAction[]{new MyToolsMenuAction()};
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setIcon(AllIcons.Ide.Pipette);
    }
}
