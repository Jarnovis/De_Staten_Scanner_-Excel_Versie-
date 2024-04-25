import org.example.*;
import org.junit.jupiter.api.Assertions;

public class Driver {
    protected static Connector connector = new Connector();
    protected Driver(){
    }

    protected Driver(GUI_Search search, String website, boolean output){
        System.out.println(website);
        search.setText(website);
        Assertions.assertEquals(output, search.setTestConnection());
    }

    protected void startDriver(){
        connector.open();
    }

    protected void stopDriver(){
        connector.close();
    }
}