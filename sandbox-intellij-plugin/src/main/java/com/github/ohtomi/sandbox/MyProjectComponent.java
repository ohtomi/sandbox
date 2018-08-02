package com.github.ohtomi.sandbox;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.ui.Messages;

public class MyProjectComponent implements ProjectComponent {

    @Override
    public void projectOpened() {
        int answer = Messages.showYesNoCancelDialog("select button", "yes/no/cancel", null);
        switch (answer) {
            case Messages.YES:
                Messages.showInfoMessage("yes !!!", "yes/no/cancel");
                break;
            case Messages.NO:
                Messages.showInfoMessage("no ...", "yes/no/cancel");
                break;
            case Messages.CANCEL:
                Messages.showInfoMessage("cancel ???", "yes/no/cancel");
                break;
        }
    }
}
