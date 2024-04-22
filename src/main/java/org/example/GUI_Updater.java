package org.example;

import javax.swing.*;

public abstract class GUI_Updater extends SwingWorker<String, Object> {//(9)
    @Override
    public String doInBackground() {
        return null;
    }

    @Override
    protected void done(){ //(9)
    }
}

