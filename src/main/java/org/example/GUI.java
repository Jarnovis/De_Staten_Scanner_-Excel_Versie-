package org.example;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    private final JFrame frame;
    private final Connector connector;
    private final RightData rightData;
    private JPanel excelFilePanel = new JPanel(new GridBagLayout());
    private JPanel searchPanel = new JPanel(new BorderLayout());
    private JPanel headPanel = new JPanel();
    private UploadButton uploadButton;
    private SearchButton searchButton;
    private SelectFromSheet selectFromSheet;
    private SelectFromSheetButton selectFromSheetButton;
    private SearchBar searchBar;
    private GetKeySource getKeySource;
    private SelectFromSheetButton selectKeySourceButton;

    public GUI(Connector connector, RightData rightData) throws Exception {
        this.frame = new JFrame("De Staten Scanner (Excel Versie)");
        this.connector = connector;
        this.rightData = rightData;

        uploadButton = new UploadButton();
        selectFromSheet = new SelectFromSheet();
        selectFromSheetButton = new SelectFromSheetButton("Select Key Sheet");
        getKeySource = new GetKeySource();
        selectKeySourceButton = new SelectFromSheetButton("Select Key Source");

        searchButton = new SearchButton("Search");
        searchBar = new SearchBar();
        // Compenenten eerst toevoegen, voordat frame gemaakt wordt
        // Creëren componenten voor zoeken
        window();
        actions();
    }

    private void window() throws Exception {
        // creëeren componenten voor excelFiles
        selectFromSheet.create();
        selectFromSheetButton.create();
        uploadButton.create();
        getKeySource.create();
        searchBar.create();

        positionPanels();

        // Window creëren
        this.frame.pack(); //(2)
        this.frame.setSize(new Dimension(600, 800));
        this.frame.setVisible(true); //(2)
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void positionPanels(){
        // Positioneren panels (8)
        excelFilePanel.setLayout(new GridBagLayout());
        searchPanel.setLayout(new GridBagLayout());
        headPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcExcel = new GridBagConstraints();
        GridBagConstraints gbcSearch = new GridBagConstraints();
        GridBagConstraints gbcHead = new GridBagConstraints();
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


        frame.add(headPanel);

    }

    private void actions(){
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
        uploadButton.action(rightData, selectFromSheet);
        searchButton.action(searchBar.getBox(), connector);
        selectFromSheetButton.action(selectFromSheet, rightData, getKeySource, selectKeySourceButton);
        selectKeySourceButton.action(rightData, getKeySource);

    }



    public JFrame getFrame(){
        return this.frame;
    }
}
