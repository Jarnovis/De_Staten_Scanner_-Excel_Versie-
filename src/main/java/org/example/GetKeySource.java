package org.example;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GetKeySource extends SelectFromSheet {
    private JComboBox box;
    private String[] sheets = {"Upload excel File first"};

    private  Object boxInhoud;
    public void create(){
        box = new JComboBox(sheets);
        box.setMaximumSize(new Dimension(200, 25));
        box.setPreferredSize(new Dimension(200, 25));
        box.setBackground(Color.LIGHT_GRAY);
        box.setVisible(true);
    }
    public void getKey(RightData rightData, SelectFromSheet selectFromSheet) throws Exception{
        // Opent alle sheets van het excelfile
        File file = rightData.getFile();

        if (file != null) {
            // Verwijderd alle items van de vorige excel file eerst van de box
            box.removeAllItems();

            Workbook workbook = new Workbook(String.valueOf(file));
            WorksheetCollection collection = workbook.getWorksheets();
            ArrayList<String> save = new ArrayList<>();


            for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
                Worksheet worksheet = collection.get(worksheetIndex);
                if (worksheet.getName().equalsIgnoreCase((String) selectFromSheet.getBox().getSelectedItem())) {
                    int cols = worksheet.getCells().getMaxColumn();

                    for (int j = 0; j < cols; j++) {
                        if (worksheet.getCells().get(0, j).getValue() != null){
                            box.addItem(worksheet.getCells().get(0, j).getValue());
                        }
                    }
                }
            }
            // update de box
            box.revalidate();
            box.repaint();
        }
    }

    public JComboBox getBox(){
        return box;
    }
}
