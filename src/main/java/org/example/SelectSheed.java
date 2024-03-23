package org.example;

import javax.swing.*;

import com.aspose.cells.*;

import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class SelectSheed {
    private JComboBox box;
    private JPanel position;
    private JFrame frame;
    private String[] sheets = {"Upload excel File first"};
    public SelectSheed(JPanel position, JFrame frame){
        this.position = position;
        this.frame = frame;
    }

    public void create(){
        box = new JComboBox(sheets);
        box.setPreferredSize(new Dimension(200, 25));
        box.setBackground(Color.LIGHT_GRAY);
        box.setVisible(true);
        position.add(box);
        frame.getContentPane().add(position, BorderLayout.WEST);
    }

    public void getSheets(RightData rightData) throws Exception{
        // Verwijderd alle items van de vorige excel file eerst
        box.removeAllItems();

        // Opent alle sheets van het excelfile
        File file = rightData.getFile();
        Workbook workbook = new Workbook(String.valueOf(file));
        WorksheetCollection collection = workbook.getWorksheets();
        ArrayList<String> save = new ArrayList<>();

        // Zorgt ervoor dat alle sheets geen <Object> meer zijn, maar <String>
        for (int sheetNumber = 0; sheetNumber < collection.getCount(); sheetNumber++){
            Worksheet sheet = collection.get(sheetNumber);
            save.add(sheet.getName());
        }

        String[] sheets = new String[save.size()];

        // Voegt alle Strings toe aan de box
        for (String sheetName : save){
            box.addItem(sheetName);
        }

        // update de box
        box.revalidate();
        box.repaint();
    }

    public void action(RightData rightData){
        // Kijkt of er een actie plaats vindt in de box
        box.addActionListener(new ActionListener(){
            @Override //(4)
            public void actionPerformed(ActionEvent evt){ //(4)
                // Slaat de read file aan met maar 1 sheet
                try {
                    rightData.readFile((String) box.getSelectedItem());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        System.out.println(box.getName());
    }

}
