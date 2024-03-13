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
    private String URL;
    private List<WebElement> Data;
    private boolean closed;

    public Connector(){
        WebDriverManager.chromedriver().setup(); //(1)
        System.out.print("AWDWADADAW");
    }

    public void open(){
        this.driver = new ChromeDriver();
        this.driver.manage().window().minimize();
        this.closed = false;
    }

    public void connect(String URL_link){
        try{ //(5)
            this.driver.getTitle(); //(5)
        } catch(WebDriverException e){ //(5)
            this.driver.quit(); //(5)
            open(); //(5)
        }

        this.driver.get(URL_link);
        collect();
    }

    private List<WebElement> collect(){
        this.Data = driver.findElements(By.tagName("table"));
        return Data;
    }

    public void close(){
        this.driver.close();
        this.closed = true;
    }
}

