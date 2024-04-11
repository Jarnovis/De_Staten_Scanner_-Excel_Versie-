import org.example.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.awt.event.ActionEvent;

public class ConnectieTest {
    @Test
    public void ConnectionWebsiteGood() {
        // Classes aanmaken
        var search = new Search("Search", true);
        var connector = new Connector();
        var rightData = new RightData(connector);

        connector.open();

        search.create();
        search.getBox().setText("https://www.cia.gov/the-world-factbook/field/net-migration-rate/country-comparison/");
        search.action(connector, rightData, new SelectFromSheetButton("Test", true));

        // Stimuleert het drukken van op de knop na. (14)
        ActionEvent mockEvent = new ActionEvent(search.getButton(), ActionEvent.ACTION_PERFORMED, "Press");
        search.getButton().getActionListeners()[0].actionPerformed(mockEvent);

        Assertions.assertEquals(true, search.getConnection()[0]);


        connector.close();
    }

    @Test
    public void ConnectionWebsiteBad() {
        // Classes aanmaken
        var search = new Search("Search", true);
        var connector = new Connector();
        var rightData = new RightData(connector);

        connector.open();

        search.create();
        search.getBox().setText("Geen geldige URL");
        search.action(connector, rightData, new SelectFromSheetButton("Test", true));
        ActionEvent mockEvent = new ActionEvent(search.getButton(), ActionEvent.ACTION_PERFORMED, "");
        search.getButton().getActionListeners()[0].actionPerformed(mockEvent);

        Assertions.assertEquals(false, search.getConnection()[0]);

        connector.close();
    }
}
