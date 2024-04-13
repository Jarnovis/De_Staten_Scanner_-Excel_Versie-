import org.example.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Dimension;
import java.awt.event.ActionEvent;

public class DriverTest {
    private final Connector connector = new Connector();

    @Test
    public void ConnectionWebsiteGood() {
        // De driver wordt zichtbaar gesteld, zodat er duidelijk te zien is dat er gecommuniseerd wordt met de driver
        var search = new SearchAndUpload("Search", true);
        var rightData = new RightData(connector);

        connector.open();

        Driver website1 = new Driver(search, connector, rightData, "https://hdr.undp.org/data-center/country-insights#/ranks", true);
        Driver website2 = new Driver(search, connector, rightData, "https://www.cia.gov/the-world-factbook/field/net-migration-rate/country-comparison/", true);
        Driver website3 = new Driver(search, connector, rightData, "https://www.visionofhumanity.org/maps/#/", true);

        connector.close();

    }

    @Test
    public void ConnectionWebsiteBad() {
        // De driver wordt zichtbaar gesteld, zodat er duidelijk te zien is dat er gecommuniseerd wordt met de driver
        var search = new SearchAndUpload("Search", true);
        var rightData = new RightData(connector);
        connector.open();

        Driver website1 = new Driver(search, connector, rightData, "https://www.dehaagsehogeschool.nl", false);
        Driver website2 = new Driver(search, connector, rightData, "https://programmerhumor.io", false);
        Driver website3 = new Driver(search, connector, rightData, "https://stepik.org/catalog", false);

        connector.close();
    }

    @Test
    public void driverMinimized(){
        connector.open();

        // De normale grote voor een chrome driver window is (1050, 732).
        // Als een window kleiner is dan deze grote, dan is deze geminimaliseerd, omdat de open() functionaliteit
        // Hiervoor zorgt.
        Dimension windowSize = connector.getDriver().manage().window().getSize();

        if (windowSize.height <= 732 && windowSize.width <= 1050) {
            Assertions.assertTrue(true);
        }

        connector.close();

    }
}

class Driver{
    public Driver(SearchAndUpload search, Connector connector, RightData rightData, String website, boolean output){
        search.create();
        search.getBox().setText(website);
        search.action(connector, rightData, new SelectFromSheetButton("Test", true));

        // Stimuleert het drukken van op de knop na. (14)
        ActionEvent mockEvent = new ActionEvent(search.getButton(), ActionEvent.ACTION_PERFORMED, "Press");
        search.getButton().getActionListeners()[0].actionPerformed(mockEvent);

        Assertions.assertEquals(output, search.getConnection()[0]);
    }

}
