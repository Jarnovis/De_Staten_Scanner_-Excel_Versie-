package org.example;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchButton extends UploadButton implements IButton{
    private static JButton button = new JButton("Search");
    private JFrame frame;
    private JPanel position = new JPanel();

    public SearchButton(JFrame getFrame){
        super(getFrame);
        this.frame = getFrame;
    }


    @Override
    public void create(){
        button.setPreferredSize(new Dimension(150, 25));
        button.setBackground(Color.lightGray);
        button.setVisible(true);
        this.position.add(button);
        this.frame.getContentPane().add(position, BorderLayout.CENTER);
    }


    public void action(JTextField input, Connector connector){
        // Probeert om naar de website te gaan. Als de website niet bestaat / URL-link is ongeldig komt er een error melding
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connector.connect(input.getText());
                } catch(WebDriverException exception){
                    input.setText(input.getText() + " is not found.\nPlease enter a correct URL-Link");
                }
            }
        });
    }
}
