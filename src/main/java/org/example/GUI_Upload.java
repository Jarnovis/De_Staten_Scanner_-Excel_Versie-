package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;

public class GUI_Upload extends GUI_Search implements IGUI {
    protected File file;
    protected JFrame frame;
    private JButton uploadButton;
    private JButton submitButton;
    protected SelectComboBox sheets = new SelectComboBox(new String[] {"Upload Excel File First"});
    protected SelectComboBox keySource = new SelectComboBox(new String[] {"Select Needed Sheet First"});
    protected SelectComboBox exelPlacement = new SelectComboBox(new String[] {"Excel Fail"});
    protected SelectComboBox website = new SelectComboBox(new String[] {"Website Fail"});
    private Object lastSheet = sheets.getComboBox().getSelectedItem();
    private RightData rightData;
    protected boolean submit = false;
    private TextString sheet = new TextString("Sheet", true, new int[] {100, 15});
    private TextString keySourceText = new TextString("Key Source", true, new int[] {100, 15});
    private TextString coupleSourceText = new TextString("Couple Source", false, new int[] {100, 15});
    private TextString websiteText = new TextString("Website", false, new int[] {100, 15});


    public GUI_Upload(RightData rightData) {
        super();
        this.rightData = rightData;

        window();
        uploadButton();
        submitButton();
        placement();

    }

    @Override
    public void window(){
        frame = new JFrame();

        frame.setName("De Staten Scanner (Excel)");
        frame.setSize(new Dimension(500, 400));
        frame.setVisible(false);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        WindowListener Listener = new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                setVisible(false);
            }
        };

        frame.addWindowListener(Listener);
    }

    private void uploadButton(){
        uploadButton = new JButton();
        uploadButton.setText("Upload");
        uploadButton.setSize(new Dimension(250, 25));
        uploadButton.setVisible(true);
        actionUploadButton();
    }

    private void actionUploadButton(){
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // kunnen uploaden van een excel bestand
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files (DO NOT CHANGE THIS FILTER)", "xls", "xlsx")); //(13)
                fileChooser.showSaveDialog(null);
                File newFile = fileChooser.getSelectedFile();

                if (newFile != null) {
                    file = newFile;
                }

                try {
                    sheets.updateComboBoxSheets(file);
                    keySource.updateComboBoxKeySource(sheets);
                } catch (Exception ex) {
                }

                updaterGUI();
            }
        });
    }

    private void submitButton(){
        submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.setSize(new Dimension(250, 25));
        submitButton.setVisible(true);

        actionSubmitButton();
    }

    private void actionSubmitButton(){
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit = true;
                rightData.setFile(file);
                rightData.gatherKeySource((String) keySource.getComboBox().getSelectedItem(), (String) sheets.getComboBox().getSelectedItem());
                setVisible(false);
            }
        });
    }

    protected void updateMatchFailBoxes(){
        exelPlacement.updateComboBoxExcelPlacement(sheets, keySource);
        website.updateComboBoxWebsite(keySource, rightData);
    }

    protected void setVisibleErrorMatches(boolean visible){
        coupleSourceText.getText().setVisible(visible);
        websiteText.getText().setVisible(visible);
    }

    @Override
    public void placement(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(sheet.getText(), constraints);

        constraints.gridx ++;
        panel.add(keySourceText.getText(), constraints);

        constraints.gridy ++;
        constraints.gridx --;
        panel.add(sheets.getComboBox(), constraints);

        constraints.gridx ++;
        panel.add(keySource.getComboBox(), constraints);

        constraints.gridy ++;
        constraints.gridx --;
        panel.add(coupleSourceText.getText(), constraints);

        constraints.gridx ++;
        panel.add(websiteText.getText(), constraints);

        constraints.gridy ++;
        constraints.gridx --;
        panel.add(exelPlacement.getComboBox(), constraints);

        constraints.gridx ++;
        panel.add(website.getComboBox(), constraints);

        constraints.gridy ++;
        constraints.gridx --;
        panel.add(uploadButton, constraints);

        constraints.gridx ++;
        panel.add(submitButton, constraints);

        frame.add(panel, BorderLayout.CENTER);
    }


    @Override
    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }

    public void updaterGUI() { //(9)
        (new GUI_Updater()).execute();
    }

    // Zorgt ervoor dat de code binnen GUI_Reworked geupdate wordt
    private class GUI_Updater extends SwingWorker<String, Object>{ //(9)
        @Override
        public String doInBackground(){
            if (lastSheet != sheets.getComboBox().getSelectedItem()) {
                keySource.updateComboBoxKeySource(sheets);
                lastSheet = sheets.getComboBox().getSelectedItem();
            }
            return null;
        }

        @Override
        protected void done(){ //(9)
            if (frame.isVisible()) {
                updaterGUI();
            }
        }
    }

    // TestOnly functionaliteiten
    public void setFile(){
        file = new File("UML- en tetxtfiles/Index_Failed_States_PWS.xlsx");
        submit = true;

    }
}