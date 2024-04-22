package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GUI_Upload extends GUI_Search{
    private File file;
    private JFrame frame = new JFrame();
    private JButton uploadButton = new JButton();
    private SelectComboBox sheets = new SelectComboBox(new String[] {"Upload Excel File First"});
    private SelectComboBox keySource = new SelectComboBox(new String[] {"Select Needed Sheet First"});
    private Object lastSheet = sheets.getComboBox().getSelectedItem();

    public GUI_Upload() {
        super();
        uploadButton();
        placement();
        window();
    }

    @Override
    protected void window(){
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
                    keySource.updateComboBoxKeySource(file, sheets);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                updaterGUI();
            }
        });
    }

    @Override
    protected void placement(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(uploadButton, constraints);

        constraints.gridy ++;
        panel.add(sheets.getComboBox(), constraints);

        constraints.gridx ++;
        panel.add(keySource.getComboBox(), constraints);

        frame.add(panel, BorderLayout.CENTER);
    }


    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }

    public void updaterGUI() { //(9)
        (new GUI_Upload.GUI_Updater()).execute();
    }

    // Zorgt ervoor dat de code binnen GUI_Reworked geupdate wordt
    class GUI_Updater extends SwingWorker<String, Object>{ //(9)
        @Override
        public String doInBackground() throws Exception {
            if (lastSheet != sheets.getComboBox().getSelectedItem()) {
                keySource.updateComboBoxKeySource(file, sheets);
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
}