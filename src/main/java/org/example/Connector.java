package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager; //(1)
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Connector {
    private WebDriver driver;
    static List<WebElement> DATA;
    private String url;

    public void setup(){
        // Zorgt ervoor dat chromedriver bestuurt kan worden
        WebDriverManager.chromedriver().setup(); //(1)
    }

    public void open(){
        // Opent en minimaliseerd de driver
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(200, 200));
        driver.manage().window().minimize();
    }

    public void connect(String URL_link){
        // Probeert om een connectie met een website aan te gaan
        // Als de URL niet gevonden is crashed de applicatie niet
        url = URL_link;
        try{ //(5)
            driver.get(url); //(5)
        } catch(WebDriverException e){ //(5)
        }
    }

    public List<WebElement> collect(){
        // Geeft de applicatie 1000 miliseconden om te zoeken naar het table element in de code
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        // Verzameld alle HTML-gegevens binnen de <table>
        DATA = driver.findElements(By.tagName("table"));
        if (DATA.isEmpty()){
            close();
            setup();
            connect(url);
            collect();
        }

        return DATA;
    }

    public void close(){
        driver.quit();
    }

    public WebDriver getDriver(){
        return driver;
    }
}

