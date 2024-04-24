package org.example;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import deletedClasses.IButton;

public class Writer implements IButton {
    private Workbook workbook;
    private WorksheetCollection collection;
    public Writer(RightData rightData) throws Exception {
        workbook = new Workbook(String.valueOf(rightData.getFile()));
        collection = workbook.getWorksheets();
    }

    public void createSheet(String name, String sheet, String headSource) throws Exception {
        // Maakt een 1 op 1 kopie van de hoofdsheet
        Worksheet worksheet = collection.add(name);
        for (int i = 0; i < collection.getCount(); i++){
            if (sheet.equals(collection.get(i).getName())){
                worksheet.copy(collection.get(i)); //(11)
            }

            if (collection.get(i).getName().equals("Evaluation Warning")){
                collection.removeAt(collection.get(i).getName());
            }
        }

        try{
            worksheet = emptyingWorksheet(worksheet, "Country");
        } catch (Exception e) {
            System.out.println(e);
        }

        workbook.save(workbook.getFileName());
    }

    private Worksheet emptyingWorksheet(Worksheet worksheet, String headSource){
        // Verwijderd alles in de sheet, behalve de zoektermen en de gegevens van de hoofdzoekterm
        int rows = worksheet.getCells().getMaxDataRow()+1;
        int cols = worksheet.getCells().getMaxColumn();

        for (int col = 0; col < cols; col++){
            String rowName = (String) worksheet.getCells().get(0, col).getValue();
            System.out.println(rowName);
            if (rowName != null){
                if (!rowName.equals(headSource)){
                    for (int row = 0; row < rows; row++){
                        if (row > 0){
                            // Formule in cell wordt opgehaald, cell wordt volledig gewist, formule wordt terug geplaatst
                            String formula = worksheet.getCells().get(row, col).getFormula();
                            worksheet.getCells().get(row, col).setValue(null);
                            worksheet.getCells().get(row, col).setFormula(formula);
                        }
                    }
                }
            }
        }

        return worksheet;
    }

    public void write(){

    }

    public void create(){

    }
}
