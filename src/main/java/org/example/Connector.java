package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager; //(1)
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;




import java.util.List;

public class Connector {
    private WebDriver driver;
    static List<WebElement> Data;
    private String URL;
    private int loop = 0;

    public Connector(){
        WebDriverManager.chromedriver().setup(); //(1)
    }

    public void open(){
        this.driver = new ChromeDriver();
        this.driver.manage().window().setSize(new Dimension(1, 1));
        this.driver.manage().window().minimize();
    }

    public void connect(String URL_link){
        URL = URL_link;
        try{ //(5)
            this.driver.getTitle(); //(5)
        } catch(WebDriverException e){ //(5)
            this.driver.quit(); //(5)
            open(); //(5)
        }

        this.driver.get(URL);
    }

    public List<WebElement> collect(){
        loop++;

        this.Data = driver.findElements(By.tagName("table"));
        if (this.Data.isEmpty() && loop <= 4 ){
            connect(URL);
            collect();
        }
        return Data;
    }

    public void close(){
        this.driver.close();
    }

    public WebDriver getDriver(){
        return driver;
    }
}

