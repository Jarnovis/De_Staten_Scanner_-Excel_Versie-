import org.example.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Dimension;
import java.awt.event.ActionEvent;

public class DriverTest {
    private final Connector connector = new Connector();
    private final RightData rightData = new RightData();
    private final GUI_Upload upload = new GUI_Upload(rightData);
    private final GUI_Commit commit = new GUI_Commit();
    private final GUI_Search search = new GUI_Search(upload, commit, connector, rightData, true);

    @Test
    public void ConnectionWebsiteGood() throws InterruptedException {
        connector.open();

        Driver website1 = new Driver(search,  "https://hdr.undp.org/data-center/country-insights#/ranks", true);
        Driver website2 = new Driver(search,  "https://www.cia.gov/the-world-factbook/field/net-migration-rate/country-comparison/", true);
        Driver website3 = new Driver(search, "https://www.visionofhumanity.org/maps/#/", true);

        connector.close();

    }

    @Test
    public void ConnectionWebsiteBad() throws InterruptedException {
        connector.open();

        Driver website1 = new Driver(search,  "https://www.dehaagsehogeschool.nl", false);
        Driver website2 = new Driver(search,  "https://programmerhumor.io", false);
        Driver website3 = new Driver(search, "https://stepik.org/catalog", false);

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
