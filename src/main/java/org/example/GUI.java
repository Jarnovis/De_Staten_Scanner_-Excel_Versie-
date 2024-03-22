package org.example;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

public class GUI {
    private final JFrame frame;
    private final Connector connector;
    private final RightData rightData;
    private JTextField box;

    public GUI(Connector connector, RightData rightData){
        this.frame = new JFrame("De Staten Scanner (Excel Versie)");
        this.connector = connector;
        this.rightData = rightData;
        window();
    }

    private void window(){
        UploadButton uploadButton = new UploadButton(getFrame());
        SearchButton searchButton = new SearchButton(getFrame());
        // Compenenten eerst toevoegen, voordat frame gemaakt wordt
        uploadButton.create();
        searchButton.create();
        searchBar();

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

        // Alle acties laten uitvoeren
        frame.addWindowListener(Listener);
        uploadButton.action(rightData);
        searchButton.action(box, connector);
    }

    public void searchBar(){
        // SearchBar implementatie
        JPanel boxPosition = new JPanel();
        this.box = new JTextField(30);
        box.setPreferredSize(new Dimension(250, 25));
        box.setVisible(true);
        boxPosition.add(box);
        frame.getContentPane().add(boxPosition, BorderLayout.NORTH);
        final String[] input ={"https://www.google.com"};

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

        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input[0] = box.getText();
                System.out.println(box.getText());
            }
        });
    }
    public JFrame getFrame(){
        return this.frame;
    }
}
