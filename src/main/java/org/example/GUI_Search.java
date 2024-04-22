package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI_Search {
    private GUI_Upload gui_upload;
    private GUI_Commit gui_commit;
    private Connector connector;
    private JFrame frame = new JFrame();
    private JTextField textField = new JTextField();
    private JButton searchButton = new JButton();
    private JButton uploadButton = new JButton();

    public GUI_Search() {

    }

    public GUI_Search(GUI_Upload gui_upload, GUI_Commit gui_commit, Connector connector){
        this.gui_upload = gui_upload;
        this.gui_commit = gui_commit;
        this.connector = connector;

        searchBar();
        searchButton();
        uploadButton();
        placement();
        window();
    }

    protected void window(){
        frame.setName("De Staten Scanner (Excel)");
        frame.setSize(new Dimension(500, 400));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

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
    }

    private void searchBar(){
        textField.setPreferredSize(new Dimension(250, 25));
        textField.setEditable(true);
        textField.setVisible(true);
        textField.setText("Give an URL-Link");
        actionSearchBar();
    }

    public void actionSearchBar(){
        // Checkt of er actie wordt ondernomen rondom de box
        textField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            // Als er in de box geklikt wordt, wordt de eerdere text weg gehaald
            @Override
            public void mousePressed(MouseEvent e) {
                textField.setText("");
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

    private void searchButton(){
        searchButton.setText("Search");
        searchButton.setSize(new Dimension(250, 25));
        searchButton.setVisible(true);
        actionSearchButton();
    }

    private void actionSearchButton(){
        searchButton.addActionListener(new ActionListener(){ //(4)
            @Override //(4)
            public void actionPerformed(ActionEvent evt){//(4)
                try{
                    connector.connect(textField.getText());
                    connector.collect();

                } catch (Exception e){
                    GUI_Error error = new GUI_Error(textField.getText(), "Website does not exist or has no table(s)");
                }
            }
        });
    }

    private void uploadButton(){
        uploadButton.setText("Upload File");
        uploadButton.setSize(new Dimension(250, 25));
        uploadButton.setVisible(true);
        actionUploadButton();

    }

    private void actionUploadButton(){
        uploadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                gui_upload.setVisible(true);
            }
        });
    }

    protected void placement(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(textField, constraints);

        constraints.gridy ++;
        panel.add(searchButton, constraints);

        for (int i = 0; i <= 22; i++){
            constraints.gridy ++;
            panel.add(Box.createVerticalGlue(), constraints);
        }

        constraints.gridy ++;
        panel.add(uploadButton, constraints);

        frame.add(panel, BorderLayout.NORTH);

    }


}
