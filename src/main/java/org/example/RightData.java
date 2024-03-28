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
import java.util.Iterator;
import java.util.List;

public class RightData {
    private File file;
    private WorksheetCollection collection;
    private ArrayList<ArrayList> keySources;
    private ArrayList<ArrayList> dataSources;
    private Connector connector;
    private ArrayList<ArrayList<ArrayList<String>>> collected;
    private ArrayList<ArrayList> matches;

    public RightData(Connector connector) {
        this.connector = connector;
    }

    public void createWorkbook() throws Exception {
        Workbook workbook = new Workbook(String.valueOf(file));
        collection = workbook.getWorksheets();
    }

    public ArrayList<ArrayList> readFile(String sheet, String source) {
        ArrayList<ArrayList> gether = new ArrayList<>();
        ArrayList<Object> names = new ArrayList<>();
        ArrayList<int[]> positions = new ArrayList<>();
        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equalsIgnoreCase(sheet)) {

                System.out.println("Worksheet: " + worksheet.getName());

                int rows = worksheet.getCells().getMaxDataRow();
                int cols = worksheet.getCells().getMaxColumn();

                for (int col = 0; col < cols; col++) {
                    String rowName = (String) worksheet.getCells().get(0, col).getValue();
                    if (rowName != null) {
                        if (rowName.equals(source) || source == null) {
                            rows++;

                            if (source != null){
                                for (int row = 0; row < rows; row++) {
                                    names.add(worksheet.getCells().get(row, col).getValue());
                                    positions.add(new int[]{col, row});
                                }
                            }

                            else{
                                for (int row = 0; row < rows; row++) {
                                    names.add(worksheet.getCells().get(row, col).getValue());
                                    positions.add(new int[]{col});
                                }
                            }

                        }
                    }
                }

                gether.add(names);
                gether.add(positions);
            }
        }
        return gether;
    }

    public void uploadFile() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();

        if (file != null) {
            this.file = file;
        }
    }

    public void gatherKeySource(String source, String sheet) {
        System.out.println("KeySource");
        keySources = readFile(sheet, source);

        gatherDataSource(null, sheet);
        getData();
        checkData();
    }

    public void gatherDataSource(String source, String sheet){
        System.out.println("DataSource");
        dataSources = readFile(sheet, source);
    }

    private void getData() {
        System.out.println("getData");
        collected = new ArrayList<>();
        ArrayList<ArrayList<String>> collectedRows = new ArrayList<>();
        ArrayList<String> rowData = new ArrayList<>();

        List<WebElement> data = connector.collect();

        for (WebElement element : connector.getDriver().findElements(By.xpath("//th"))) {
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
        }
    }

    private void checkData() {
        matches = new ArrayList<>();
        boolean found = false;
        int positionsHead = -1;

        for (ArrayList<String> headTable : collected.getFirst()) {
            for (String head : headTable) {
                if (!found) {
                    positionsHead++;
                    if (head.equals(keySources.getFirst().getFirst())) {
                        found = true;
                    }
                }
            }
        }

        ArrayList<int[]> positionsY = keySources.getLast();
        ArrayList<Integer> positionsExcelY = new ArrayList<>();

        for (int[] place : positionsY) {
            positionsExcelY.add(place[1]);
        }

        found = false;
        int positionsDataSource = 0;
        int positionX = 0;
        Object last = null;

        for (ArrayList<String> dataTable : collected.getFirst()) {
            for (String head : dataTable) {
                if (!found) {
                    positionX = 0;
                    positionsDataSource++;
                    for (int i = 0; i < dataSources.getFirst().size(); i++) {
                        if (dataSources.getFirst().get(i) != last){
                            positionX ++;
                            last = dataSources.getFirst().get(i);
                        }

                        Object data = dataSources.getFirst().get(i);
                        if (data != null) {
                            if (head.equalsIgnoreCase(data.toString())) {
                                found = true;
                            }
                        }
                    }
                }
            }
        }

        ArrayList<int[]> positionsX = dataSources.getLast();
        ArrayList<Integer> positionsExcelX = new ArrayList<>();

        for (int[] place : positionsX) {
            positionsExcelX.add(place[0]);
        }

        ArrayList<Object> headSource = new ArrayList<>();
        ArrayList<Object> headData = new ArrayList<>();
        ArrayList<int[]> positioningData = new ArrayList<>();
        for (ArrayList<ArrayList<String>> row : collected) {
            for (ArrayList<String> get : row) {
                boolean match = false;
                if (!get.isEmpty()) {
                    for (int pos = 0; pos < keySources.getFirst().size(); pos++) {
                        if (!match) {
                            if (keySources.getFirst().get(pos).toString().equalsIgnoreCase(get.get(positionsHead))) {
                                int[] data = {positionX, positionsExcelY.get(pos)};
                                headSource.add(keySources.getFirst().get(pos));
                                headData.add(get.get(positionsDataSource));
                                positioningData.add(data);

                                match = true;

                                keySources.getFirst().remove(pos);
                                positionsExcelX.remove(pos);
                                positionsExcelY.remove(pos);
                            }
                        }
                    }
                }
            }
            matches.add(headSource);
            matches.add(headData);
            matches.add(positioningData);
        }


        //System.out.println(matches);
        //System.out.println(keySources.getFirst());
        //System.out.println(dataSources.get(positionsDataSourceInList).get(positionsDataSource));
        ArrayList<int[]> positionsTogether = matches.getLast();
        ArrayList<Integer> positionsLonely = new ArrayList<>();

        for (int[] place : positionsTogether){
            positionsLonely.add(place[0]);
            positionsLonely.add(place[1]);
        }

        System.out.println(matches);
        System.out.println(matches.getFirst());

        for (int i = 0; i < matches.getFirst().size(); i++){
            System.out.println(matches.getFirst().get(i) + " | " + matches.get(1).get(i).toString() + " | " + positionsLonely.get(i * 2) + " | " + positionsLonely.get(i * 2 + 1));
        }

    }


    public File getFile(){
        return this.file;
    }
}
