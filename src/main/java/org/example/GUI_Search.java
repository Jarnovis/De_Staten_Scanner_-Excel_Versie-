package org.example;

import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GUI_Search {
    private GUI_Upload gui_upload;
    private GUI_Commit gui_commit;
    protected Connector connector;
    protected JFrame frame;
    protected JTextField textField;
    protected JButton searchButton;
    protected JButton uploadButton;
    private boolean search = false;
    private RightData rightData;

    public GUI_Search() {

    }

    public GUI_Search(GUI_Upload gui_upload, GUI_Commit gui_commit, Connector connector, RightData rightData){
        this.gui_upload = gui_upload;
        this.gui_commit = gui_commit;
        this.connector = connector;
        this.rightData = rightData;

        window();
        searchBar();
        searchButton();
        uploadButton();
        placement();
    }

    protected void window(){
        frame = new JFrame();
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
        textField = new JTextField();
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
        searchButton = new JButton();
        searchButton.setText("Search");
        searchButton.setSize(new Dimension(250, 25));
        searchButton.setVisible(true);
        actionSearchButton();
    }

    private void actionSearchButton(){
        searchButton.addActionListener(new ActionListener(){ //(4)
            @Override //(4)
            public void actionPerformed(ActionEvent evt){//(4)
                search = true;
                (new GUI_Updater()).execute();
            }
        });
    }

    private void uploadButton(){
        uploadButton = new JButton();
        uploadButton.setText("Upload File");
        uploadButton.setSize(new Dimension(250, 25));
        uploadButton.setVisible(true);
        actionUploadButton();

    }

    private void actionUploadButton(){
        uploadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                setVisible(false);
                gui_upload.setVisible(true);
                (new GUI_Updater()).execute();

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

    protected void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    protected Connector getConnector(){
        return connector;
    }




    private class GUI_Updater extends SwingWorker<String, Object> {
        @Override
        public String doInBackground() throws InterruptedException {
            if(search && gui_upload.submit){
                System.out.println(search);
                System.out.println(gui_upload.submit);

                try{
                    connector.connect(textField.getText());
                    connector.collect();
                    rightData.getData(connector);
                    if (gui_upload.website.getComboBox().getSelectedItem().toString() == null){
                        rightData.checkData(gui_upload.keySource.getComboBox().getSelectedItem().toString(), null);
                    }
                    else{
                        rightData.checkData(gui_upload.keySource.getComboBox().getSelectedItem().toString(), gui_upload.website.getComboBox().getSelectedItem().toString());
                    }


                } catch (Exception e){
                    GUI_Error error = new GUI_Error(textField.getText(), "Website does not exist or has no table(s)", "Searching Error");
                    wait(1);
                } finally {
                    search = false;
                }

                (new GUI_Updater()).execute();
            }
            //else{
                //if (!gui_upload.frame.isVisible()){
                    //GUI_Error error = new GUI_Error(null, "Upload Excel File First", "Upload Error");
                    //wait(1);
                //}
            //}

            if (!gui_upload.frame.isVisible()){
                setVisible(true);
            }
            else{
                (new GUI_Updater()).execute();
            }

            search = false;
            return null;
        }

        @Override
        protected void done() {

        }
    }

}
