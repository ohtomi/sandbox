package com.github.ohtomi.sandbox;

import com.intellij.openapi.fileEditor.impl.EditorTabColorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class MyEditorTabColorProvider implements EditorTabColorProvider {

    @Nullable
    @Override
    public Color getEditorTabColor(@NotNull Project project, @NotNull VirtualFile file) {
        System.out.println("getEditorTabColor(@NotNull Project project, @NotNull VirtualFile file)");
        switch (file.getFileType().getDefaultExtension()) {
            case "txt":
                return JBColor.GREEN;
            case "xml":
                return JBColor.BLUE;
            case "java":
                return JBColor.YELLOW;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Color getProjectViewColor(@NotNull Project project, @NotNull VirtualFile file) {
        System.out.println("getProjectViewColor(@NotNull Project project, @NotNull VirtualFile file)");
        switch (file.getFileType().getDefaultExtension()) {
            case "txt":
                return JBColor.GREEN;
            case "xml":
                return JBColor.BLUE;
            case "java":
                return JBColor.YELLOW;
            default:
                return null;
        }
    }
}
