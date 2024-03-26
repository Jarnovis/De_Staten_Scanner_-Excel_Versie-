package org.example;

import org.openqa.selenium.WebDriverException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchButton extends UploadButton implements IButton{
    private static JButton button;

    public SearchButton(String name){
        button = new JButton(name);
    }

    @Override
    public void create(){
        button.setMaximumSize(new Dimension(150, 25));
        button.setBackground(Color.lightGray);
        button.setVisible(true);
    }

    public void action(JTextField input, Connector connector){
        // Probeert om naar de website te gaan. Als de website niet bestaat / URL-link is ongeldig komt er een error melding
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connector.connect(input.getText());
                } catch(WebDriverException exception){
                    input.setText(input.getText() + " is not found.\nPlease enter a valid URL-Link");
                }
            }
        });
    }

    public JButton getButton(){
        return button;
    }
}
