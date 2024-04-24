package org.example;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SelectComboBox{
    private JComboBox comboBox = new JComboBox();
    private String[] choices;
    private WorksheetCollection collection;

    public SelectComboBox(String[] choices){
        this.choices = choices;
        comboBox();
    }

    private void comboBox(){
        comboBox.setMaximumSize(new Dimension(180, 25));
        comboBox.setPreferredSize(new Dimension(180, 25));
        comboBox.addItem(choices[0]);

        if (choices[0].equals("Excel Fail") || choices[0].equals("Website Fail")){
            comboBox.setVisible(false);
        }
        else{
            comboBox.setVisible(true);
        }
    }

    public void updateComboBoxSheets(File file) throws Exception {
        comboBox.removeAllItems();
        Workbook workbook = new Workbook(String.valueOf(file));
        collection = workbook.getWorksheets();

        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            comboBox.addItem(worksheet.getName());
        }
    }

    public void updateComboBoxKeySource(SelectComboBox comobBoxSheets){
        comboBox.removeAllItems();
        collection = comobBoxSheets.collection;
        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equals(comobBoxSheets.getComboBox().getSelectedItem().toString())){
                int cols = worksheet.getCells().getMaxColumn();

                for (int i = 0; i < cols; i++){
                    if (worksheet.getCells().get(0, i).getValue() != null){
                        comboBox.addItem(worksheet.getCells().get(0, i).getValue());
                    }
                }
            }
        }
    }

    public void updateComboBoxExcelPlacement(SelectComboBox comobBoxSheets, SelectComboBox comobBoxKeySource){
        comboBox.setVisible(true);
        comboBox.removeAllItems();
        collection = comobBoxSheets.collection;
        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equals(comobBoxSheets.getComboBox().getSelectedItem().toString())){
                int cols = worksheet.getCells().getMaxColumn();

                for (int i = 0; i < cols; i++){
                    if (worksheet.getCells().get(0, i).getValue() != null && worksheet.getCells().get(0, i).getValue() != comobBoxKeySource.getComboBox().getSelectedItem().toString()){
                        comboBox.addItem(worksheet.getCells().get(0, i).getValue());
                    }
                }
            }
        }
    }

    public void updateComboBoxWebsite(SelectComboBox comobBoxKeySource, RightData rightData){
        comboBox.setVisible(true);
        comboBox.removeAllItems();

        ArrayList<ArrayList<ArrayList<String>>> collected = rightData.getCollected();
        for (ArrayList<String> list : collected.getFirst()){
            for (String item : list){
                if (!item.equals(comobBoxKeySource.getComboBox().getSelectedItem().toString())){
                    comboBox.addItem(item);
                }
            }
        }
    }

    public JComboBox getComboBox() {
        return comboBox;
    }
}
