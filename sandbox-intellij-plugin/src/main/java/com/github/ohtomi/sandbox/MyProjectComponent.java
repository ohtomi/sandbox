package com.github.ohtomi.sandbox;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.ui.Messages;

public class MyProjectComponent implements ProjectComponent {

    @Override
    public void initComponent() {
        System.out.println("initComponent()");
    }

    @Override
    public void projectOpened() {
        int answer = Messages.showYesNoCancelDialog("select button", "yes/no/cancel", null);
        switch (answer) {
            case Messages.YES:
                Notifications.Bus.notify(
                        new Notification("com.github.ohtomi.sandbox.sandbox-intellij-plugin", "yes/no/cancel", "yes !!!",
                                NotificationType.INFORMATION));
                break;
            case Messages.NO:
                Notifications.Bus.notify(
                        new Notification("com.github.ohtomi.sandbox.sandbox-intellij-plugin", "yes/no/cancel", "no ...",
                                NotificationType.WARNING));
                break;
            case Messages.CANCEL:
                Notifications.Bus.notify(
                        new Notification("com.github.ohtomi.sandbox.sandbox-intellij-plugin", "yes/no/cancel", "cancel ???",
                                NotificationType.ERROR));
                break;
        }
    }
}
