package org.example;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        comboBox.setVisible(true);
        comboBox.addItem(choices[0]);
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
        if (!(new RightData()).checkData(comobBoxKeySource.getComboBox().getSelectedItem().toString(), null)){
            comboBox.setVisible(false);
        }
        else{
            comboBox.setVisible(true);
            comboBox.removeAllItems();
            collection = comobBoxSheets.collection;
            for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
                Worksheet worksheet = collection.get(worksheetIndex);
                if (worksheet.getName().equals(comobBoxSheets.getComboBox().getSelectedItem().toString())){
                    int cols = worksheet.getCells().getMaxColumn();

                    for(int i = 0; i < cols; i++){
                        if (worksheet.getCells().get(0, i).getValue() != null || worksheet.getCells().get(1, i).getValue() != comobBoxKeySource.getComboBox().getSelectedItem().toString()){
                            comboBox.addItem(worksheet.getCells().get(0, i).getValue());
                        }
                    }
                }
            }
        }
    }

    public void updateComboBoxWebsite(SelectComboBox comobBoxKeySource, RightData rightData, Connector connector){
        if (rightData.checkData(comobBoxKeySource.getComboBox().getSelectedItem().toString(), null)){
            comboBox.setVisible(false);
        }
        else{
            comboBox.setVisible(true);
            comboBox.removeAllItems();

            rightData.getData(connector);
            for (Object source : rightData.getDATA()){
                if (!source.toString().equals(comobBoxKeySource.getComboBox().getSelectedItem().toString())){
                    comboBox.addItem(source.toString());
                }
            }
        }
    }

    public JComboBox getComboBox() {
        return comboBox;
    }
}
