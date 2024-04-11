package org.example;

import org.openqa.selenium.WebDriverException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Search extends UploadButton implements IButton{
    private static JButton button;
    private JTextField box;
    private boolean visable;
    private final boolean[] connection = {false};

    public Search(String name, boolean visable){
        super(visable);
        button = new JButton(name);
        this.visable = visable;
    }

    @Override
    public void create(){
        button.setMaximumSize(new Dimension(150, 25));
        button.setBackground(Color.lightGray);
        button.setVisible(visable);

        box = new JTextField(30);
        box.setMaximumSize(new Dimension(250, 25));
        box.setVisible(true);
    }

    public void action(Connector connector, RightData rightData, SelectFromSheetButton selectFromKeySourceButton){
        // Probeert om naar de website te gaan. Als de website niet bestaat / URL-link is ongeldig komt er een error melding
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectFromKeySourceButton.setThrough(true);
                    connector.connect(box.getText());
                    rightData.getData();
                    connection[0] = true;

                } catch(WebDriverException exception){
                    box.setText(box.getText() + " is not found.\nPlease enter a valid URL-Link");
                    connection[0] = false;
                }
            }
        });
    }

    public void action(){

        // Checkt of er actie wordt ondernomen rondom de box
        box.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            // Als er in de box geklikt wordt, wordt de eerdere text weg gehaald
            @Override
            public void mousePressed(MouseEvent e) {
                box.setText("");
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public JButton getButton(){
        return button;
    }
    public JTextField getBox(){
        return this.box;
    }
    public boolean[] getConnection(){
        return connection;
    }
}
