package org.example;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import org.checkerframework.checker.units.qual.A;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RightData {
    private File file;
    private WorksheetCollection collection;
    private ArrayList<ArrayList> keySources;

    public void createWorkbook() throws Exception {
        Workbook workbook = new Workbook(String.valueOf(file));
        collection = workbook.getWorksheets();
    }

    public void readFile(String sheet){
        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equalsIgnoreCase(sheet)) {

                System.out.println("Worksheet: " + worksheet.getName());

                int rows = worksheet.getCells().getMaxDataRow();
                int cols = worksheet.getCells().getMaxColumn();

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        //System.out.print(worksheet.getCells().get(i, j).getValue() + " | ");
                    }
                    //System.out.println(" ");
                }
            }
        }
    }

    public void uploadFile() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();

        if (file != null){
            this.file = file;
        }
    }

    public void gatherKeySource(String keySource, String sheet){
        keySources = new ArrayList<>();
        ArrayList<Object> names = new ArrayList<>();
        ArrayList<int[]> position = new ArrayList<>();
        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equalsIgnoreCase(sheet)) {
                int rows = worksheet.getCells().getMaxDataRow();
                int cols = worksheet.getCells().getMaxColumn();


                for (int col = 0; col < cols; col++){
                    String rowName = (String) worksheet.getCells().get(0, col).getValue();
                    if (rowName != null){
                        if (rowName.equals(keySource)){
                            rows ++;
                            for (int row = 0; row < rows; row++){
                                names.add(worksheet.getCells().get(row, col).getValue());
                                position.add(new int[] {col, row});
                            }
                        }
                    }
                }

                keySources.add(names);
                keySources.add(position);

                for (ArrayList<Object> list : keySources){
                    for (Object data : list){
                        if (data instanceof Object){
                            System.out.println((Object) data);
                        }
                        else if (data instanceof int[]){
                            int [] intArr = (int[]) data;
                            System.out.println(intArr);
                            }

                        }
                    }
                }
            }
        }


    public File getFile(){
        return this.file;
    }
}
