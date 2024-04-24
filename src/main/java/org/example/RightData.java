package org.example;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RightData {
    private File file;
    private WorksheetCollection collection;
    private ArrayList<ArrayList> keySources;
    private ArrayList<Object> noMatches;
    private ArrayList<ArrayList> dataSources;
    private ArrayList<ArrayList<ArrayList<String>>> collected;
    private ArrayList<ArrayList> firstRow;
    private ArrayList<ArrayList> matches;
    private String keySource;
    private static List<WebElement> DATA;
    private  boolean passFound = true;

    private void createWorkbook(){
        // Zorgt ervoor dat er een nieuwe Workbookinstantie gecreëerd wordt, zodat er andere soorten files gebruikt kunnen worden
        try{
            Workbook workbook = new Workbook(String.valueOf(file));
            collection = workbook.getWorksheets();
        }catch(Exception e){}
    }

    public ArrayList<ArrayList> readFile(String sheet, String source){
        ArrayList<ArrayList> gether = new ArrayList<>();
        ArrayList<Object> names = new ArrayList<>();
        ArrayList<int[]> positions = new ArrayList<>();
        createWorkbook();

        // Stopt alle gegevens met posities in een gether
        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equalsIgnoreCase(sheet)) {
                int rows = worksheet.getCells().getMaxDataRow();
                int cols = worksheet.getCells().getMaxColumn();

                for (int col = 0; col < cols; col++) {
                    String rowName = (String) worksheet.getCells().get(0, col).getValue();
                    if (rowName != null) {
                        if (rowName.equals(source) || source == null) {
                            rows++;

                            if (source != null){
                                // Gegevens en posities van de hoofdzoekterm wordt geselecteerd
                                for (int row = 0; row < rows; row++) {
                                    if (!(worksheet.getCells().get(row, col).getValue() == null)){
                                        names.add(worksheet.getCells().get(row, col).getValue());
                                        positions.add(new int[]{col, row});
                                    }
                                }
                            }
                            // Alle zoektermen met posities worden geselecteerd
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

    public void gatherKeySource(String source, String sheet) {
        keySource = source;
        keySources = readFile(sheet, keySource);
        firstRow = readFile(sheet, null);

        gatherDataSource(null, sheet);
    }

    public void gatherDataSource(String source, String sheet) {
        dataSources = readFile(sheet, source);
    }

    public void getData(Connector connector) {
        collected = new ArrayList<>();
        ArrayList<ArrayList<String>> collectedRows = new ArrayList<>();
        ArrayList<String> rowData = new ArrayList<>();

        DATA = connector.collect();

        // Verzameld de zoektermen van de gevonden gegevens van de website uit
        for (WebElement element : connector.getDriver().findElements(By.xpath("//th"))) {
            rowData.add(element.getText());
        }

        collectedRows.add(rowData);
        collected.add(collectedRows);

        // Splits de verzamelde data van de gevonden gegevens van de website
        for (WebElement table : DATA) {
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            collectedRows = new ArrayList<>();
            // Mochten er meerdere soorten rijen zijn (meerdere tabellen op de website) wordt eerst elke soort rij afgesplitst
            for (WebElement row : rows) {
                // In een specifieke rij worden alle cellen gesplits
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
        matches = new ArrayList<>();
        boolean found = false;
        int positionsHead = 1;

        // Detecteren van automatische matches
        if (neededCollomWebsite == null){
            for (ArrayList<String> headTable : collected.getFirst()) {
                for (String head : headTable) {
                    System.out.println(head);
                    if (!found) {
                        positionsHead++;
                        for (int i = 0; i< firstRow.getFirst().size(); i++){
                            System.out.println(firstRow.getFirst().size());
                            if (neededCollom == null){
                                if (head.equals(firstRow.getFirst().get(i)) && !head.equals(keySource)) {
                                    neededCollom = firstRow.getFirst().get(i).toString();
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
            System.out.println(found);
        }

        // Kijken waar in het excel bestand de match zit
        if (found || neededCollomWebsite != null){
            passFound = true;

            found = false;
            int positionsDataSource = -1;
            int positionX = 0;

            for (ArrayList<String> dataTable : collected.getFirst()) {
                for (String head : dataTable) {
                    if (!found) {
                        positionX = 0;
                        positionsDataSource++;
                        for (int i = 0; i < dataSources.getFirst().size(); i++) {
                            Object data = dataSources.getFirst().get(i);
                            if (data != null) {
                                if (head.equalsIgnoreCase(neededCollomWebsite) && neededCollom.equals(data.toString())) {
                                    found = true;
                                    break;
                                }
                            }
                            positionX ++;
                        }
                    }
                }
            }

            // Matches aan elkaar koppelen met excel locaties
            if (found){
                ArrayList<int[]> positionsY = keySources.getLast();
                ArrayList<Integer> positionsExcelY = new ArrayList<>();

                for (int[] place : positionsY) {
                    positionsExcelY.add(place[1]);
                }

                ArrayList<Object> headSource = new ArrayList<>();
                ArrayList<Object> headData = new ArrayList<>();
                ArrayList<int[]> positioningData = new ArrayList<>();
                for (ArrayList<ArrayList<String>> rows : collected) {
                    for (ArrayList<String> row : rows) {
                        boolean match = false;
                        if (!row.isEmpty()) {
                            for (int pos = 0; pos < keySources.getFirst().size(); pos++) {
                                if (!match) {
                                    if (keySources.getFirst().get(pos).toString().equalsIgnoreCase(row.get(positionsHead))) {
                                        int[] data = {positionX, positionsExcelY.get(pos)};
                                        headSource.add(keySources.getFirst().get(pos));
                                        headData.add(row.get(positionsDataSource));
                                        positioningData.add(data);
                                        match = true;
                                        System.out.println("A NEW MATCH IS FOUND");
                                    }
                                }
                            }
                        }
                    }

                    matches.add(headSource);
                    matches.add(headData);
                    matches.add(positioningData);

                    noMatches = new ArrayList<>();

                    for (Object name : keySources.getFirst()){
                        boolean matching = false;
                        for (Object match : matches.getFirst()){
                            if (name == match){
                                matching = true;
                            }
                        }

                        if (!matching){
                            noMatches.add(name);
                        }
                    }
                }
                return passFound;
            }
            else{
                passFound = false;
                return passFound;
            }

        }

        passFound = false;
        return passFound;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getCollected(){
        return collected;
    }

    public ArrayList<ArrayList> getMatches(){
        return matches;
    }

    public ArrayList<Object> getNoMatches() {
        return noMatches;
    }

    public void setFile(File file){
        this.file = file;
    }

    public File getFile(){
        return file;
    }

}
