package org.example;

import org.openqa.selenium.WebDriverException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SearchAndUpload implements IButton{
    private JButton button;
    private JTextField box;
    private boolean visable;
    private final boolean[] connection = {false};

    public SearchAndUpload(String name, boolean visable) {
        button = new JButton(name);
        this.visable = visable;
    }

    public void create(){
        button.setMaximumSize(new Dimension(150, 25));
        button.setBackground(Color.lightGray);
        button.setVisible(visable);

        if (button.getText().equals("Search")){
            box = new JTextField(30);
            box.setMaximumSize(new Dimension(250, 25));
            box.setVisible(true);
        }
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
                    box.setText(box.getText() + " does not have tables or is invalid. Try a new URL");
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

    public void action(RightData rightData, SelectFromSheet selectFromSheet){
        // Zorgt ervoor dat het window voor navigeren in het systeem omhoog komt
        button.addActionListener(new ActionListener(){ //(4)
            @Override //(4)
            public void actionPerformed(ActionEvent evt){ //(4)
                try {
                    rightData.uploadFile();
                    selectFromSheet.getSheets(rightData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
