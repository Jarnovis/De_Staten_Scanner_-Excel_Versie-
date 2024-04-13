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
    static List<WebElement> Data;
    private String URL;

    public void setup(){
        WebDriverManager.chromedriver().setup(); //(1)
    }

    public void open(){
        this.driver = new ChromeDriver();
        this.driver.manage().window().setSize(new Dimension(200, 200));
        this.driver.manage().window().minimize();
    }

    public void connect(String URL_link){
        URL = URL_link;
        try{ //(5)
            driver.get(URL); //(5)
        } catch(WebDriverException e){ //(5)
        }
    }

    public List<WebElement> collect(){
        // Geeft de applicatie 25 miliseconden om te zoeken naar het table element in de code
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(200));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        Data = driver.findElements(By.tagName("table"));
        if (Data.isEmpty()){
            close();
            setup();
            connect(URL);
            collect();
        }

        return Data;
    }

    public void close(){
        driver.quit();
    }

    public WebDriver getDriver(){
        return driver;
    }
}

