package org.example;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import javax.swing.*;
import java.io.*;

public class RightData {
    private File file;

    public void readFile() throws Exception {
        Workbook workbook = new Workbook(String.valueOf(file));
        WorksheetCollection collection = workbook.getWorksheets();

        for (int worksheetIndex = 0; worksheetIndex < collection.getCount(); worksheetIndex++) {
            Worksheet worksheet = collection.get(worksheetIndex);
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

    public File uploadFile() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null);
        File file = fileChooser.getSelectedFile();
        this.file = file;
        readFile();
        return file;
    }
}
