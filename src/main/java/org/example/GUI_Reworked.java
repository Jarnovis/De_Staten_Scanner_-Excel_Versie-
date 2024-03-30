package org.example;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Reworked extends JFrame {
    private final Connector connector;
    private final RightData rightData;
    private JPanel excelFilePanel = new JPanel(new GridBagLayout());
    private JPanel searchPanel = new JPanel(new BorderLayout());
    private JPanel headPanel = new JPanel();
    private UploadButton uploadButton;
    private SearchButton searchButton;
    private SelectFromSheet selectFromSheet;
    private GetKeySource selectFromSheetFail;
    private GetKeySource selectFromWebsiteFail;
    private SelectFromSheetButton selectFromSheetButton;
    private SelectFromSheetButton selectFromSheetButtonFail;
    private SearchBar searchBar;
    private GetKeySource getKeySource;
    private SelectFromSheetButton selectKeySourceButton;
    private GridBagConstraints gbcExcel;
    private GridBagConstraints gbcSearch;
    private GridBagConstraints gbcHead;
    private boolean showFailFirstTime = true;
    private int loop = 0;
    private TextString failBoxText;

    public GUI_Reworked(Connector connector, RightData rightData) throws Exception {
        super("De Staten Scanner (Excel Versie)");
        this.connector = connector;
        this.rightData = rightData;

        uploadButton = new UploadButton();
        selectFromSheet = new SelectFromSheet();
        selectFromSheetButton = new SelectFromSheetButton("Select Key Sheet");
        getKeySource = new GetKeySource();
        selectKeySourceButton = new SelectFromSheetButton("Select Key Source");
        selectFromSheetFail = new GetKeySource();
        selectFromSheetButtonFail = new SelectFromSheetButton("Select Colom");
        failBoxText = new TextString();
        selectFromWebsiteFail = new GetKeySource();

        searchButton = new SearchButton("Search");
        searchBar = new SearchBar();
        // Compenenten eerst toevoegen, voordat frame gemaakt wordt
        // Creëren componenten voor zoeken
        window();
        actions();
    }

    private void window() throws Exception {
        // creëeren componenten voor excelFiles
        selectFromSheet.create(new String[] {"Upload excel File first"}, true);
        selectFromSheetButton.create(true);
        selectFromSheetFail.create(new String[] {"Failed to find collum"}, false);
        selectFromSheetButtonFail.create(false);
        failBoxText.create("No matches found", false);
        selectFromWebsiteFail.create(new String[] {"Failed to find collum"}, false);


        uploadButton.create(true);
        getKeySource.create(new String[] {"Upload excel file first"}, true);
        searchBar.create();

        positionPanels();

        // Window creëren
        setSize(new Dimension(600, 800));
        setVisible(true); //(2)
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void positionPanels(){
        // Positioneren panels (8)
        excelFilePanel.setLayout(new GridBagLayout());
        searchPanel.setLayout(new GridBagLayout());
        headPanel.setLayout(new GridBagLayout());
        gbcExcel = new GridBagConstraints();
        gbcSearch = new GridBagConstraints();
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
        excelFilePanel.add(selectFromSheetFail.getBox(), gbcExcel);

        gbcExcel.gridx ++;
        excelFilePanel.add(selectFromSheetButtonFail.getButton(), gbcExcel);

        gbcExcel.gridy ++;
        gbcExcel.gridx --;
        excelFilePanel.add(selectFromWebsiteFail.getBox(), gbcExcel);

        gbcSearch.gridx = 0;
        gbcSearch.gridy = 0;
        gbcSearch.weighty = 1;
        gbcSearch.weightx = 1;
        gbcSearch.insets = new Insets(10, 10, 10, 10);

        searchPanel.add(searchBar.getBox(), gbcSearch);
        gbcSearch.gridy ++;
        searchPanel.add(searchButton.getButton(), gbcSearch);

        gbcHead.anchor = GridBagConstraints.FIRST_LINE_START;
        headPanel.add(searchPanel, gbcHead);

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
        searchButton.action(searchBar.getBox(), connector, rightData);
        selectFromSheetButtonFail.action(rightData, selectFromSheetFail);

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

            System.out.println(showFailFirstTime + " | " + selectFromSheetButton.getThrough());

            if (!selectKeySourceButton.getThrough()){
                if (showFailFirstTime){
                    selectFromSheetFail.getKey(rightData, selectFromSheet);
                    selectFromWebsiteFail.getKey(rightData);
                    showFailFirstTime = false;
                }

                selectFromSheetFail.visible(true);
                selectFromSheetButtonFail.visible(true);
                failBoxText.visible(true);
                selectFromWebsiteFail.visible(true);
            }

            else{
                selectFromSheetFail.visible(false);
                selectFromSheetButtonFail.visible(false);
                failBoxText.visible(false);
                selectFromWebsiteFail.visible(false);
                showFailFirstTime = true;
            }

            return "refresh";
        }

        @Override
        protected void done(){ //(9)
            updaterGUI();

            if (loop == 0){
                selectFromSheetButton.action(selectFromSheet, rightData, getKeySource, selectKeySourceButton);
                selectKeySourceButton.action(rightData, getKeySource);
            }

            loop ++;
            if (loop == 35){
                loop = 0;
            }

        }

    }
}