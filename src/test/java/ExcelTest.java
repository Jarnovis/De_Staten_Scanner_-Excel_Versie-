import org.example.*;

/*import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;

public class ExcelTest {
    @Test
    public void checkZoekTermen() {
        var rightData = new RightData(new Connector());
        UploadFile uploadFile = new UploadFile(rightData);
        String[] expected = {"Economie", "Punten", "Country", "Totaal Score", "GDP", "Score BBP per inwoner",
                "Unemployment", "Score Werkeloosheid", "national debt", "Score Staatsschuld als percentage van het BBP",
        "inflation", "Score Inflatie", "Gini coefficient", "Score Gini-coëfficiënt", "Totaal Economie", "Safety",
        "Score Veiligheid", "Freedom", "Score Vrijheid", "Welfare", "Score Welzijn", "migration", "Score Migratie",
        "Population growth", "Score Bevolkingsgroei", "Totaal Sociaal", "Human rights", "Score Mensenrechten",
        "separation of powers", "Score Trias Politica", "Democratie", "Score Democratie", "Corruption", "Score Corruptie"};
        ArrayList<String> actual = new ArrayList<>();

        ArrayList<ArrayList> data = rightData.readFile("Index 2021", null);

        for (int i = 0; i < data.getFirst().size(); i++){
            actual.add(data.getFirst().get(i).toString());
        }
        Assertions.assertArrayEquals(expected, actual.toArray());
    }

    @Test
    public void checkHoofdZoekter(){
        var rightData = new RightData(new Connector());
        UploadFile uploadFile = new UploadFile(rightData);

        String[] expected = {"Country", "Denmark", "Norway", "Switzerland", "Ireland", "Netherlands", "Sweden", "Luxembourg",
                "New Zealand", "Finland", "Belgium", "Germany", "Australia", "Canada", "Iceland", "Malta",
                "United Kingdom", "South Korea", "Austria", "Slovenia", "Czech Republic", "Andorra", "Estonia", "Portugal"};
        ArrayList<String> actual = new ArrayList<>();
        ArrayList<ArrayList> data = rightData.readFile("Index 2021", "Country");

        for (int i = 0; i < 24; i++){
            actual.add(data.getFirst().get(i).toString());
        }
        Assertions.assertArrayEquals(expected, actual.toArray());

    }
}

class UploadFile{
    public UploadFile(RightData rightData){
        rightData.setFile("UML- en tetxtfiles/Index_Failed_States_PWS.xlsx");
    }
}
 */