package org.example;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

public class GUI {
    private final JFrame frame;
    private Connector connector;
    private RightData rightData;

    public GUI(Connector connector, RightData rightData){
        this.frame = new JFrame("Scanner");
        this.connector = connector;
        this.rightData = rightData;
        window();
    }

    private void window(){
        this.frame.pack(); //(2)
        this.frame.setSize(new Dimension(400, 600));
        this.frame.setVisible(true); //(2)

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WindowListener Listener = new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    connector.close();
                }

                finally {
                    System.exit(0);
                }

            }
        };

        frame.addWindowListener(Listener);
        uploadButton();

    }

    private void uploadButton(){
        JButton button = new JButton("Upload Files");
        JPanel bottom = new JPanel();
        button.setPreferredSize(new Dimension(150, 25));
        button.setBackground(Color.lightGray);


        button.setVisible(true);
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

        bottom.add(button);
        this.frame.add(bottom, BorderLayout.SOUTH);
    }

}
