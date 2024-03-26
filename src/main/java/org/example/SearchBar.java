package org.example;

import org.example.SearchButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SearchBar {
    private JTextField box;

    public void create(){
        // org.example.SearchBar implementatie
        this.box = new JTextField(30);
        box.setMaximumSize(new Dimension(250, 25));
        box.setVisible(true);

    }

    public void action(){
        final String[] input ={"https://www.google.com"};

        // Checkt of er actie wordt ondernomen rondom de box
        box.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            // Als er in de box geklikt wordt, wordt de eerdere text weg gehaald
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("WAJHDWA");
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

        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input[0] = box.getText();
                System.out.println(box.getText());
            }
        });
    }

    public JTextField getBox(){
        return this.box;
    }

}
