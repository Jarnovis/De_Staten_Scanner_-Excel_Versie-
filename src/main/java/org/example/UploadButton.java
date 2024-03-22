package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UploadButton implements IButton {
    private JButton button = new JButton("Upload Files");
    private JFrame frame;
    private JPanel position = new JPanel();

    public UploadButton(JFrame getFrame){
        this.frame = getFrame;
    }
    public void create(){
        this.button.setPreferredSize(new Dimension(150, 25));
        this.button.setBackground(Color.lightGray);
        this.button.setVisible(true);
        this.position.add(button);
        this.frame.getContentPane().add(position, BorderLayout.SOUTH);
        System.out.println("Created");
    }

    public void action(RightData rightData){
        button.addActionListener(new ActionListener(){ //(4)
            @Override //(4)
            public void actionPerformed(ActionEvent evt){ //(4)
                try {
                    rightData.uploadFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}