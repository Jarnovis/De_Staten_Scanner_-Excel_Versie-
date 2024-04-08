package org.example;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI_Reworked extends JFrame {
    private final Connector connector;
    private final RightData rightData;
    private JPanel excelFilePanel = new JPanel(new GridBagLayout());
    private JPanel searchPanel = new JPanel(new BorderLayout());
    private JPanel headPanel = new JPanel();
    private UploadButton uploadButton;
    private Search search;
    private SelectFromSheet selectFromSheet;
    private GetKeySource selectFromSheetFail;
    private GetKeySource selectFromWebsiteFail;
    private SelectFromSheetButton selectFromSheetButton;
    private SelectFromSheetButton selectFromSheetButtonFail;
    private GetKeySource getKeySource;
    private SelectFromSheetButton selectKeySourceButton;
    private GridBagConstraints gbcExcel;
    private GridBagConstraints gbcSearch;
    private GridBagConstraints gbcHead;
    private boolean showFailFirstTime = true;
    private int loop = 0;
    private TextString failBoxText;
    private TextString failExcelCollomText;
    private TextString failWebsiteCollomText;
    private ScrollField matchesField;
    private JPanel fieldPanel = new JPanel();
    private GridBagConstraints gbcField;
    private TextString infoMatches;
    private TextString notFound;
    private ScrollField noMatchesField;
    private ArrayList<IButton> buttons = new ArrayList<>();
    private ArrayList<IComboBox> comboBoxes = new ArrayList<>();

    public GUI_Reworked(Connector connector, RightData rightData) throws Exception {
        super("De Staten Scanner (Excel Versie)");
        this.connector = connector;
        this.rightData = rightData;

        buttons.add(uploadButton = new UploadButton(true));
        comboBoxes.add(selectFromSheet = new SelectFromSheet(new String[] {"Upload excel File first"}, true));
        selectFromSheetButton = new SelectFromSheetButton("Select Key Sheet", true);
        comboBoxes.add(getKeySource = new GetKeySource(new String[] {"Select Key Sheet First"}, true));
        buttons.add(selectKeySourceButton = new SelectFromSheetButton("Select Key Source", true));
        comboBoxes.add(selectFromSheetFail = new GetKeySource(new String[] {"Failed To Tind Tollum"}, false));
        buttons.add(selectFromSheetButtonFail = new SelectFromSheetButton("Commit Collums", false));
        failBoxText = new TextString();
        comboBoxes.add(selectFromWebsiteFail = new GetKeySource(new String[] {"Failed To Find Collum"}, false));
        failExcelCollomText = new TextString();
        failWebsiteCollomText = new TextString();
        matchesField = new ScrollField(new int[] {10, 25});
        infoMatches = new TextString();
        noMatchesField = new ScrollField(new int[] {10, 25});
        notFound = new TextString();

        search = new Search("Search", true);
        // Compenenten eerst toevoegen, voordat frame gemaakt wordt
        // Creëren componenten voor zoeken
        window();
        actions();
    }

    private void window() throws Exception {
        // creëeren componenten voor excelFiles
        for (IButton button : buttons){
            button.create();
        }

        for (IComboBox comboBox : comboBoxes){
            comboBox.create();
        }

        failBoxText.create("No matches found", false, new int[] {200, 15});
        failExcelCollomText.create("Excel Collom:", false, null);
        failWebsiteCollomText.create("Website Collom: ", false, null);
        infoMatches.create("Matches will be shown here", true, new int[] {200, 25});
        notFound.create("No matches", true, new int[] {75, 25});
        search.create();

        positionPanels();

        // Window creëren
        setSize(Toolkit.getDefaultToolkit().getScreenSize()); //(12)
        setVisible(true); //(2)
    }

    private void positionPanels(){
        // Positioneren panels (8)
        excelFilePanel.setLayout(new GridBagLayout());
        searchPanel.setLayout(new GridBagLayout());
        fieldPanel.setLayout(new GridBagLayout());
        headPanel.setLayout(new GridBagLayout());

        gbcExcel = new GridBagConstraints();
        gbcSearch = new GridBagConstraints();
        gbcField = new GridBagConstraints();
        gbcHead = new GridBagConstraints();

        gbcHead.gridx = 0;
        gbcHead.gridy = 0;
        gbcHead.weighty = 1;
        gbcHead.weightx = 1;
        gbcHead.anchor = GridBagConstraints.WEST;

        gbcExcel.gridx = 0;
        gbcExcel.gridy = 0;
        gbcExcel.weightx = 1;
        gbcExcel.weighty = 1;
        gbcExcel.insets = new Insets(10, 10, 10, 10);

        excelFilePanel.add(selectFromSheet.getBox(), gbcExcel);

        gbcExcel.gridx ++;
        excelFilePanel.add(selectFromSheetButton.getButton(), gbcExcel);

        gbcExcel.gridy ++;
        gbcExcel.gridx --;
        excelFilePanel.add(uploadButton.getButton(), gbcExcel);

        gbcExcel.gridy ++;
        excelFilePanel.add(getKeySource.getBox(), gbcExcel);
        headPanel.add(excelFilePanel, gbcHead);

        gbcExcel.gridx ++;
        excelFilePanel.add(selectKeySourceButton.getButton(), gbcExcel);

        gbcExcel.gridy ++;
        gbcExcel.gridx --;
        excelFilePanel.add(failBoxText.getText(), gbcExcel);

        gbcExcel.gridy ++;
        excelFilePanel.add(failExcelCollomText.getText(), gbcExcel);

        gbcExcel.gridx ++;
        excelFilePanel.add(selectFromSheetFail.getBox(), gbcExcel);

        gbcExcel.gridy ++;
        gbcExcel.gridx --;
        excelFilePanel.add(failWebsiteCollomText.getText(), gbcExcel);

        gbcExcel.gridx ++;
        excelFilePanel.add(selectFromWebsiteFail.getBox(), gbcExcel);

        gbcExcel.gridx ++;
        excelFilePanel.add(selectFromSheetButtonFail.getButton(), gbcExcel);

        gbcSearch.gridx = 0;
        gbcSearch.gridy = 0;
        gbcSearch.weighty = 1;
        gbcSearch.weightx = 1;
        gbcSearch.insets = new Insets(10, 10, 10, 10);

        searchPanel.add(search.getBox(), gbcSearch);
        gbcSearch.gridy ++;
        searchPanel.add(search.getButton(), gbcSearch);

        gbcHead.anchor = GridBagConstraints.FIRST_LINE_START;
        headPanel.add(searchPanel, gbcHead);

        gbcField.gridx = 0;
        gbcField.gridy = 0;
        gbcField.weighty = 1;
        gbcField.weightx = 1;
        gbcField.insets = new Insets(0, 2, 0, 20);

        fieldPanel.add(infoMatches.getText(), gbcField);

        gbcField.gridx ++;
        fieldPanel.add(notFound.getText(), gbcField);

        gbcField.gridy ++;
        gbcField.gridx --;
        fieldPanel.add(matchesField.getScroll(), gbcField);

        gbcField.gridx ++;
        fieldPanel.add(noMatchesField.getScroll(), gbcField);

        gbcHead.anchor = GridBagConstraints.EAST;
        headPanel.add(fieldPanel, gbcHead);

        add(headPanel);
    }

    public void actions() throws Exception {
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
        addWindowListener(Listener);
        uploadButton.action(rightData, selectFromSheet, updaterGUI());
        search.action(connector, rightData, selectKeySourceButton);

    }

    public boolean updaterGUI() { //(9)
        (new GUI_Updater()).execute();
        return true;
    }

    public SelectFromSheetButton getSelectFromSheetButton(){ //(9)
        return selectFromSheetButton;
    }

    class GUI_Updater extends SwingWorker<String, Object>{ //(9)
        @Override
        public String doInBackground() throws Exception {
            try{
                Thread.currentThread().sleep(500);
            } catch (InterruptedException ignore){}

            if (!selectKeySourceButton.getThrough()){
                if (showFailFirstTime){
                    selectFromSheetFail.getKey(rightData, selectFromSheet, getKeySource);
                    selectFromWebsiteFail.getKey(rightData, getKeySource);
                    showFailFirstTime = false;
                }

                selectFromSheetFail.visible(true);
                selectFromSheetButtonFail.visible(true);
                failBoxText.visible(true);
                selectFromWebsiteFail.visible(true);
                failExcelCollomText.visible(true);
                failWebsiteCollomText.visible(true);
            }

            else{
                selectFromSheetFail.visible(false);
                selectFromSheetButtonFail.visible(false);
                failBoxText.visible(false);
                selectFromWebsiteFail.visible(false);
                failWebsiteCollomText.visible(false);
                failExcelCollomText.visible(false);
                showFailFirstTime = true;
            }

            if (selectFromSheetButtonFail.getPressed()[0]){
                infoMatches.setText(rightData.getMatches());
                infoMatches.visible(true);
            }

            return "refresh";
        }

        @Override
        protected void done(){ //(9)
            updaterGUI();

            if (loop == 0){
                selectFromSheetButton.action(selectFromSheet, rightData, getKeySource, selectKeySourceButton);
                selectKeySourceButton.action(rightData, getKeySource);
                selectFromSheetButtonFail.action(rightData, selectFromSheetFail, selectFromWebsiteFail, matchesField, noMatchesField);
            }

            loop ++;
            if (loop == 37){
                loop = 0;
            }
            search.action();

        }

    }
}