package org.example;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GetKeySource extends SelectFromSheet implements IComboBox{
    private JComboBox box;
    private String[] sources;
    private boolean visible;

    public GetKeySource(String[] sources, boolean visable){
        super(sources, visable);
        this.sources = sources;
        this.visible = visable;
    }

    @Override
    public void create(){
        box = new JComboBox(sources);
        box.setMaximumSize(new Dimension(200, 25));
        box.setPreferredSize(new Dimension(200, 25));
        box.setBackground(Color.LIGHT_GRAY);
        box.setVisible(visible);
    }

    public void getKey(RightData rightData, SelectFromSheet selectFromSheet, GetKeySource keySource) throws Exception {
        // Opent alle sheets van het excelfile
        File file = rightData.getFile();

        if (file != null) {
            // Verwijderd alle items van de vorige excel file eerst van de box
            box.removeAllItems();

            Workbook workbook = new Workbook(String.valueOf(file));
            WorksheetCollection collection = workbook.getWorksheets();

            for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
                Worksheet worksheet = collection.get(worksheetIndex);
                if (worksheet.getName().equalsIgnoreCase((String) selectFromSheet.getBox().getSelectedItem())) {
                    int cols = worksheet.getCells().getMaxColumn();

                    for (int j = 0; j < cols; j++) {
                        if (worksheet.getCells().get(0, j).getValue() != null) {
                            if (keySource != null) {
                                if (!(worksheet.getCells().get(0, j).getValue().toString().equalsIgnoreCase((String) keySource.getBox().getSelectedItem()))) {
                                    box.addItem(worksheet.getCells().get(0, j).getValue());
                                }
                            }
                            else {
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
    }

    public void getKey(RightData rightData, GetKeySource keySource){
        ArrayList<ArrayList<ArrayList<String>>> collected = rightData.getCollected();

        box.removeAllItems();

        for (ArrayList<String> row : collected.getFirst()){
            for (String collum : row){
                if (!(collum.equalsIgnoreCase((String) keySource.getBox().getSelectedItem()))) {
                    box.addItem(collum);
                }
            }
        }

        box.revalidate();
        box.repaint();
    }


    @Override
    public void visible (boolean visability){
        box.setVisible(visability);
    }

    public JComboBox getBox(){
        return box;
    }
}
