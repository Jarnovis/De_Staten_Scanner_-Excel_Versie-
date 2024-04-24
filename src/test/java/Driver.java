import org.example.GUI_Search;
import org.junit.jupiter.api.Assertions;

class Driver{
    public Driver(GUI_Search search, String website, boolean output){
        System.out.println(website);
        search.setText(website);
        search.setTestRun(true);
        //search.action(connector, rightData, new SelectFromSheetButton("Test", true));

        // Stimuleert het drukken van op de knop na. (14)
        //ActionEvent mockEvent = new ActionEvent(search.getSearchButton(), ActionEvent.ACTION_PERFORMED, "Press");
        //search.getSearchButton().getActionListeners()[0].actionPerformed(mockEvent);
        search.setTestConnection();

        Assertions.assertEquals(output, search.getConnection()[0]);
    }

}
