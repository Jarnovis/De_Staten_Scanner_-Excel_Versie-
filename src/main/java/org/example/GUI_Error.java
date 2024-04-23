package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Error extends GUI_Search{
    private JFrame frame = new JFrame();
    private JButton button = new JButton();
    private JLabel label = new JLabel();
    private String error;
    private String message;
    private String kindError;

    public GUI_Error(String error, String message, String kindError){
        super();
        this.error = "Error <br> " + error;
        this.message = message;
        this.kindError = kindError;
        acceptButton();
        errorDisplay();
        placement();
        window();
    }

    @Override
    protected void window(){
        frame.setName("Error Found");
        frame.setSize(new Dimension(300, 300));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
    }

    private void errorDisplay(){
        if (kindError.equals("Searching Error")){
            error = kindError + "\n" +  error + "\ndoes not exists or has no table(s)";
        }
        else if(kindError.equals("Upload Error")){
            error = kindError + "<br>" + message;
        }
        else if(kindError.equals("Auto Match")){
            error = kindError + "<br>" + message;
        }
        String errorSized = "";
        int size = 0;
        int lines = 0;

        for (char c : error.toCharArray()) {
            if (size == 40) {
                errorSized += "<br>";
                size = 0;
                lines ++;
            }

            if (lines >= 10){
                errorSized += message;
                break;
            }

            errorSized += c;
            size++;
        }

        label.setText("<html>" + errorSized + "</html>");
        label.setSize(new Dimension(100, 50));
        label.setVisible(true);
    }

    private void acceptButton(){
        button.setSize(new Dimension(100, 50));
        button.setVisible(true);
        button.setText("Accept");
        actionAcceptButton();

    }

    private void actionAcceptButton(){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    @Override
    protected void placement(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(label, constraints);

        constraints.gridy = 10;
        panel.add(button, constraints);

        frame.add(panel, BorderLayout.CENTER);
    }

}
