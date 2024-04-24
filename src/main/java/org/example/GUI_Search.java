package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Search implements IGUI{
    private GUI_Upload gui_upload;
    private GUI_Commit gui_commit;
    protected Connector connector;
    private JFrame frame;
    private JTextField textField;
    private JButton searchButton;
    private JButton uploadButton;
    private boolean search = false;
    private RightData rightData;
    private JButton resultButton;
    private boolean testRun;
    private final boolean[] testConnection = {false};

    public GUI_Search() {
    }

    public GUI_Search(GUI_Upload gui_upload, GUI_Commit gui_commit, Connector connector, RightData rightData, boolean testRun){
        this.gui_upload = gui_upload;
        this.gui_commit = gui_commit;
        this.connector = connector;
        this.rightData = rightData;

        searchBar();
        searchButton();
        uploadButton();
        resultButton();

        if (!testRun){
            window();
            placement();
        }
    }

    public void window(){
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
        uploadButton.setText("Upload");
        uploadButton.setPreferredSize(new Dimension(250, 25));
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

    private void resultButton(){
        resultButton = new JButton();
        resultButton.setText("Result");
        resultButton.setPreferredSize(new Dimension(250, 25));
        resultButton.setVisible(true);
        actionResultButton();

    }

    private void actionResultButton(){
        resultButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                setVisible(false);
                gui_commit.setVisible(true);
                (new GUI_Updater()).execute();
            }
        });
    }

    public void placement(){
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel bottem = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        GridBagConstraints bottemConstraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(textField, constraints);

        constraints.gridy ++;
        panel.add(searchButton, constraints);


        bottemConstraints.gridx = 0;
        bottemConstraints.gridy = 0;
        bottemConstraints.anchor = GridBagConstraints.SOUTH;
        bottemConstraints.insets = new Insets(20, 20, 20, 20);
        bottem.add(uploadButton, bottemConstraints);

        bottemConstraints.gridx ++;
        bottem.add(resultButton, bottemConstraints);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(bottem, BorderLayout.SOUTH);

    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private class GUI_Updater extends SwingWorker<String, Object> {
        @Override
        public String doInBackground() {
            if(search && gui_upload.submit){
                System.out.println(-3);
                try{
                   connector.connect(textField.getText());

                    rightData.getData(connector);
                    System.out.println(0);
                    if (gui_upload.website.getComboBox().getSelectedItem().toString().equals("Website Fail")){
                        System.out.println(1);
                        if (!rightData.checkData(gui_upload.keySource.getComboBox().getSelectedItem().toString(), null)){
                            System.out.println(2);
                            if (!testRun){
                                GUI_Error error = new GUI_Error(null, "No Automatic Match Detected", "Auto Match");
                                gui_upload.setVisible(true);
                                setVisible(false);
                                gui_upload.updateMatchFailBoxes();
                                System.out.println(3);
                                gui_upload.setVisibleErrorMatches(true);
                                System.out.println(4);
                            }
                        }
                        else{
                            gui_upload.exelPlacement.getComboBox().removeAllItems();
                            gui_upload.exelPlacement.getComboBox().setVisible(false);
                            gui_upload.setVisibleErrorMatches(false);
                            gui_commit.updateMatchesBox(rightData, gui_upload.keySource.getComboBox(), gui_upload.website.getComboBox());
                            gui_commit.updateNoMatchesBox(rightData);
                            gui_commit.setVisible(true);
                            setVisible(false);
                        }
                    }
                    else{
                        gui_upload.exelPlacement.getComboBox().removeAllItems();
                        gui_upload.exelPlacement.getComboBox().setVisible(false);
                        gui_upload.setVisibleErrorMatches(false);
                        rightData.checkData(gui_upload.keySource.getComboBox().getSelectedItem().toString(), gui_upload.website.getComboBox().getSelectedItem().toString());
                        gui_commit.updateMatchesBox(rightData, gui_upload.keySource.getComboBox(), gui_upload.website.getComboBox());
                        gui_commit.updateNoMatchesBox(rightData);
                        gui_commit.setVisible(true);
                        setVisible(false);
                    }
                    System.out.println(6);
                    testConnection[0] = true;
                    System.out.println(7);

                } catch (Exception e){
                    testConnection[0] = false;
                    if (!testRun){
                        GUI_Error error = new GUI_Error(textField.getText(), "Website does not exist or has no table(s)", "Searching Error");
                    }
                } finally {
                    search = false;
                }
            }
            else if (gui_upload.file == null){
                if (!gui_upload.frame.isVisible() && search && !testRun){
                    GUI_Error error = new GUI_Error(null, "Upload Excel File First", "Upload Error");
                }
            }

            if (!gui_upload.frame.isVisible() && !gui_commit.frame.isVisible()){
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

    // TestOnly Functionaliteiten
    public void setText(String text){
        textField.setText(text);
    }

    public JButton getSearchButton() {
        searchButton();
        return searchButton;
    }

    public void setTestRun(boolean run){
        testRun = run;
        gui_upload.setFile();
    }

    public void setTestConnection() {
        try{
            connector.connect(textField.getText());

            rightData.getData(connector);
            System.out.println(0);
            if (gui_upload.website.getComboBox().getSelectedItem().toString().equals("Website Fail")){
                System.out.println(1);
                if (!rightData.checkData(gui_upload.keySource.getComboBox().getSelectedItem().toString(), null)){
                    System.out.println(2);
                    if (!testRun){
                        GUI_Error error = new GUI_Error(null, "No Automatic Match Detected", "Auto Match");
                        gui_upload.setVisible(true);
                        setVisible(false);
                        gui_upload.updateMatchFailBoxes();
                        System.out.println(3);
                        gui_upload.setVisibleErrorMatches(true);
                        System.out.println(4);
                    }
                }
                else{
                    gui_upload.exelPlacement.getComboBox().removeAllItems();
                    gui_upload.exelPlacement.getComboBox().setVisible(false);
                    gui_upload.setVisibleErrorMatches(false);
                    gui_commit.updateMatchesBox(rightData, gui_upload.keySource.getComboBox(), gui_upload.website.getComboBox());
                    gui_commit.updateNoMatchesBox(rightData);
                    gui_commit.setVisible(true);
                    setVisible(false);
                }
            }
            else{
                gui_upload.exelPlacement.getComboBox().removeAllItems();
                gui_upload.exelPlacement.getComboBox().setVisible(false);
                gui_upload.setVisibleErrorMatches(false);
                rightData.checkData(gui_upload.keySource.getComboBox().getSelectedItem().toString(), gui_upload.website.getComboBox().getSelectedItem().toString());
                gui_commit.updateMatchesBox(rightData, gui_upload.keySource.getComboBox(), gui_upload.website.getComboBox());
                gui_commit.updateNoMatchesBox(rightData);
                gui_commit.setVisible(true);
                setVisible(false);
            }
            System.out.println(6);
            testConnection[0] = true;
            System.out.println(7);

        } catch (Exception e){
            testConnection[0] = false;
            if (!testRun){
                GUI_Error error = new GUI_Error(textField.getText(), "Website does not exist or has no table(s)", "Searching Error");
            }
        } finally {
            search = false;
        }
    }

    public boolean[] getConnection(){
        return testConnection;
    }

}
