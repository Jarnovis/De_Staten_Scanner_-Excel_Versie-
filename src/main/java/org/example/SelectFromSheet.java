package org.example;

import javax.swing.*;

import com.aspose.cells.*;

import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

public class SelectFromSheet extends Component {
    private JComboBox box;
    private String[] sheets = {"Upload excel File first"};

    public void create() {
        box = new JComboBox(sheets);
        box.setMaximumSize(new Dimension(200, 25));
        box.setBackground(Color.LIGHT_GRAY);
        box.setVisible(true);
    }

    public void getSheets(RightData rightData) throws Exception {
        // Opent alle sheets van het excelfile
        File file = rightData.getFile();

        if (file != null) {
            // Verwijderd alle items van de vorige excel file eerst
            box.removeAllItems();

            Workbook workbook = new Workbook(String.valueOf(file));
            WorksheetCollection collection = workbook.getWorksheets();
            ArrayList<String> save = new ArrayList<>();

            // Zorgt ervoor dat alle sheets geen <Object> meer zijn, maar <String>
            for (int sheetNumber = 0; sheetNumber < collection.getCount(); sheetNumber++) {
                Worksheet sheet = collection.get(sheetNumber);
                save.add(sheet.getName());
            }

            String[] sheets = new String[save.size()];

            // Voegt alle Strings toe aan de box
            for (String sheetName : save) {
                box.addItem(sheetName);
            }

            // update de box
            box.revalidate();
            box.repaint();
        }
    }

    public JComboBox getBox() {
        return box;
    }

}
