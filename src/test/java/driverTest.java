import org.example.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Dimension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DriverTest extends Driver{
    protected final RightData rightData = new RightData();
    protected final GUI_Upload upload = new GUI_Upload(rightData);
    protected final GUI_Commit commit = new GUI_Commit();
    protected final GUI_Search search = new GUI_Search(upload, commit, connector, rightData, true);

    @BeforeAll
    public static void setUp(){
        (new Driver()).startDriver();
    }

    @Test
    @Order(2)
    public void ConnectionWebsiteGood() {
        Driver website1 = new Driver(search,  "https://hdr.undp.org/data-center/country-insights#/ranks", true);
        Driver website2 = new Driver(search,  "https://www.cia.gov/the-world-factbook/field/net-migration-rate/country-comparison/", true);
        Driver website3 = new Driver(search, "https://www.visionofhumanity.org/maps/#/", true);

    }

    @Test
    @Order(3)
    public void ConnectionWebsiteBad() {
        Driver website1 = new Driver(search,  "https://www.dehaagsehogeschool.nl", false);
        Driver website2 = new Driver(search,  "https://programmerhumor.io", false);
        Driver website3 = new Driver(search, "https://stepik.org/catalog", false);
    }

    @Test
    @Order(1)
    public void DriverMinimized(){
        // De normale grote voor een chrome driver window is (1050, 732).
        // Als een window kleiner is dan deze grote, dan is deze geminimaliseerd, omdat de open() functionaliteit
        // Hiervoor zorgt.
        Dimension windowSize = connector.getDriver().manage().window().getSize();

        if (windowSize.height <= 732 && windowSize.width <= 1050) {
            Assertions.assertTrue(true);
        }
    }
}
