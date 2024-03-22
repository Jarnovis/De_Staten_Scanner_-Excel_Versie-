package org.example;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import javax.swing.*;
import java.io.*;

import java.util.Scanner;

public class RightData {
    private File file;
    Scanner scanner = new Scanner(System.in);

    public void readFile() throws Exception {
        Workbook workbook = new Workbook(String.valueOf(file));

        WorksheetCollection collection = workbook.getWorksheets();
        System.out.println("Geef de naam van de benodigde werksheet (1 sheet)");
        String sheet = scanner.nextLine();

        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
            if (worksheet.getName().equalsIgnoreCase(sheet)) {

                System.out.println("Worksheet: " + worksheet.getName());

                int rows = worksheet.getCells().getMaxDataRow();
                int cols = worksheet.getCells().getMaxColumn();

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        System.out.print(worksheet.getCells().get(i, j).getValue() + " | ");
                    }
                    System.out.println(" ");
                }
            }
        }
    }

    public void uploadFile() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();
        this.file = file;
        readFile();
    }
}
