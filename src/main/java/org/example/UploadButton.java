package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UploadButton implements IButton {
    private JButton button = new JButton("Upload Files");

    public void create(){
        this.button.setMaximumSize(new Dimension(150, 25));
        this.button.setBackground(Color.lightGray);
        this.button.setVisible(true);
    }

    public void action(RightData rightData, SelectFromSheet selectFromSheetButton){
        button.addActionListener(new ActionListener(){ //(4)
            @Override //(4)
            public void actionPerformed(ActionEvent evt){ //(4)
                try {
                    rightData.uploadFile();
                    selectFromSheetButton.getSheets(rightData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public JButton getButton(){
        return button;
    }
}