package org.example;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RightData {
    private File file;
    private WorksheetCollection collection;
    private ArrayList<ArrayList> keySources;
    private Connector connector;

    public RightData(Connector connector) {
        this.connector = connector;
    }

    public void createWorkbook() throws Exception {
        Workbook workbook = new Workbook(String.valueOf(file));
        collection = workbook.getWorksheets();
    }

    public void readFile(String sheet) {
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

        if (file != null) {
            this.file = file;
        }
    }

    public void gatherKeySource(String keySource, String sheet) {
        keySources = new ArrayList<>();
        ArrayList<Object> names = new ArrayList<>();
        ArrayList<int[]> position = new ArrayList<>();
        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equalsIgnoreCase(sheet)) {
                int rows = worksheet.getCells().getMaxDataRow();
                int cols = worksheet.getCells().getMaxColumn();


                for (int col = 0; col < cols; col++) {
                    String rowName = (String) worksheet.getCells().get(0, col).getValue();
                    if (rowName != null) {
                        if (rowName.equals(keySource)) {
                            rows++;
                            for (int row = 0; row < rows; row++) {
                                names.add(worksheet.getCells().get(row, col).getValue());
                                position.add(new int[]{col, row});
                            }
                        }
                    }
                }

                keySources.add(names);
                keySources.add(position);

                checkData();

                //for (ArrayList<Object> list : keySources) {
                    //for (Object data : list) {
                        //if (data instanceof Object) {
                            //System.out.println((Object) data);
                        //} else if (data instanceof int[]) {
                            //int[] intArr = (int[]) data;
                            //System.out.println(intArr);
                        //}

                    //}
                //}
            }
        }
    }

    private void checkData(){
        ArrayList<ArrayList> collected = new ArrayList<>();
        ArrayList<ArrayList<String>> collectedRows = new ArrayList<>();
        ArrayList<String> rowData = new ArrayList<>();

        List <WebElement> data = connector.collect();

        for (WebElement element : connector.getDriver().findElements(By.xpath("//th"))){
            rowData.add(element.getText());
        }

        collectedRows.add(rowData);
        collected.add(collectedRows);

        for (WebElement table : data) {
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            collectedRows = new ArrayList<>();
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                rowData = new ArrayList<>();
                for (WebElement cell : cells) {
                    rowData.add(cell.getText());
                }

                collectedRows.add(rowData);
            }
            collected.add(collectedRows);

            int pos = 0;
            for (ArrayList<String> check : collected){
                if (check == null){
                    System.out.println(check + " c");
                    collected.remove(pos);
                }
                pos++;
            }
       }

       for (ArrayList<ArrayList<String>> row: collected){
           System.out.println("in");
           for (ArrayList<String> get : row){
               System.out.println(get + "c");
           }
       }

       //System.out.println(collected.getFirst().getFirst());
       //System.out.println(collected.getLast().getFirst());

       //System.out.println("Done");
    }


    public File getFile(){
        return this.file;
    }
}
