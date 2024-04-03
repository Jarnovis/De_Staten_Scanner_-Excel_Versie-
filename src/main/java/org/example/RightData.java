package org.example;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RightData {
    private File file;
    private WorksheetCollection collection;
    private ArrayList<ArrayList> keySources;
    private ArrayList<ArrayList> dataSources;
    private Connector connector;
    private ArrayList<ArrayList<ArrayList<String>>> collected;
    private ArrayList<ArrayList> firstRow;
    private ArrayList<ArrayList> matches;
    private ArrayList<Integer> positionsLonely;
    private String keySource;
    private boolean passFound;

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
                                names.add(worksheet.getCells().get(0, col).getValue());
                                positions.add(new int[]{col});
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

    public boolean gatherKeySource(String source, String sheet) {
        keySource = source;
        keySources = readFile(sheet, keySource);
        firstRow = readFile(sheet, null);

        gatherDataSource(null, sheet);
        return checkData(null, null);
    }

    public void gatherDataSource(String source, String sheet){
        dataSources = readFile(sheet, source);
    }

    public void getData() {
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

    public boolean checkData(String neededCollom, String neededCollomWebsite) {
        System.out.println("Check in");
        matches = new ArrayList<>();
        boolean found = false;
        int positionsHead = 1;

        if (neededCollomWebsite == null){
            for (ArrayList<String> headTable : collected.getFirst()) {
                for (String head : headTable) {
                    if (!found) {
                        positionsHead++;
                        for (int i = 0; i< firstRow.getFirst().size(); i++){
                            if (neededCollom == null){
                                if (head.equals(firstRow.getFirst().get(i)) && !head.equals(keySource)) {
                                    found = true;
                                }
                            }

                            else{
                                if (head.equals(neededCollom)) {
                                    found = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(found + " | " + neededCollomWebsite);

        if (found || neededCollomWebsite != null){
            passFound = true;
            ArrayList<int[]> positionsY = keySources.getLast();
            ArrayList<Integer> positionsExcelY = new ArrayList<>();

            for (int[] place : positionsY) {
                positionsExcelY.add(place[1]);
            }

            found = false;
            int positionsDataSource = -1;
            int positionX = 0;

            for (ArrayList<String> dataTable : collected.getFirst()) {
                for (String head : dataTable) {
                    if (!found) {
                        positionX = 0;
                        positionsDataSource++;
                        for (int i = 0; i < dataSources.getFirst().size(); i++) {
                            positionX ++;

                            Object data = dataSources.getFirst().get(i);
                            if (data != null) {
                                if ((head.equalsIgnoreCase(data.toString()) || (head.equals(neededCollomWebsite) && neededCollom.equals(data.toString()))) && !head.equals(keySource)) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(1);

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
                                    int[] data = {positionX+2, positionsExcelY.get(pos)};
                                    headSource.add(keySources.getFirst().get(pos));
                                    headData.add(get.get(positionsDataSource));
                                    positioningData.add(data);

                                    match = true;
                                }
                            }
                        }
                    }
                }
                matches.add(headSource);
                matches.add(headData);
                matches.add(positioningData);
            }

            System.out.println(2);

            ArrayList<int[]> positionsTogether = matches.getLast();
            positionsLonely = new ArrayList<>();

            for (int[] place : positionsTogether){
                positionsLonely.add(place[0]);
                positionsLonely.add(place[1]);
            }

            System.out.println(matches);
            System.out.println(matches.getFirst());

            for (int i = 0; i < matches.getFirst().size(); i++){
                System.out.println(matches.getFirst().get(i) + " | " + matches.get(1).get(i).toString() + " | " + positionsLonely.get(i * 2) + " | " + positionsLonely.get(i * 2 + 1));
            }

            System.out.println("Done");

            return passFound;

        }

        passFound = false;
        return passFound;
    }


    public File getFile(){
        return this.file;
    }
    public ArrayList<ArrayList<ArrayList<String>>> getCollected(){
        return collected;
    }

    public ArrayList<ArrayList> getMatches(){
        System.out.println(matches);
        return matches;
    }

    public ArrayList<Integer> getPositions(){
        return positionsLonely;
    }
}
