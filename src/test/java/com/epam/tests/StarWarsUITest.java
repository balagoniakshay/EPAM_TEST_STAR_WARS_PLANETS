package com.epam.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

public class StarWarsUITest {

    WebDriver driver;
    String baseurl;

    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browser){

        switch(browser) {
            case "chrome" :
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox" :
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default :
                Reporter.log("No browser chosen", true);
                break;
        }
        baseurl = "https://www.starwars.com/";
        driver.manage().window().maximize();
        if(driver !=null) {
            driver.get(baseurl);
        }
        else
            Assert.fail("driver object is null");
    }

    @Test(dataProvider = "Search TestData")
    public void homePageSearchTest(String sSearchTestData){
        WebElement iconSearch = driver.findElement(By.id("nav-search-icon"));
        Assert.assertTrue(iconSearch.isDisplayed(),"Search icon should be displayed on Star Wars Homepage");
        iconSearch.click();
        WebElement inputSearch = driver.findElement(By.id("nav-search-input"));
        inputSearch.clear();
        inputSearch.sendKeys(sSearchTestData);
        inputSearch.sendKeys(Keys.ENTER);
        List<WebElement> txtTitles = driver.findElements(By.xpath("//div[@class='title-wrap']//h3"));
        Assert.assertTrue(txtTitles.get(0).getText().contains(sSearchTestData), "Title should contain: " + sSearchTestData);
    }

    @Test
    public void videoTest(){
        driver.findElement(By.xpath("//a[@href='/video']")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.starwars.com/video", "User should be navigated Videos Page");
        WebElement txtFeaturedVideos = driver.findElement(By.className("module_title"));
        Assert.assertEquals(txtFeaturedVideos.getText(), "THE STAR WARS SHOW //", "Videos should have Header");
        WebElement txtSearch = driver.findElement(By.xpath("//div[@class='bound']//input[@name='q']"));
        Assert.assertEquals(txtSearch.getAttribute("placeholder"), "Search Videos", "Videos page should have search");
        txtSearch.clear();
        txtSearch.sendKeys("WARS");
        driver.findElement(By.className("search-button")).click();
        List<WebElement> txtTitles = driver.findElements(By.xpath("//div[@class='title-wrap']//h3"));
        Assert.assertTrue(txtTitles.get(0).getText().contains("WARS"),"Title should contain: " + "WARS");
    }

    @Test
    public void filmTest() {
        driver.findElement(By.xpath("//a[@href='/films']")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.starwars.com/films", "User should be navigated to Films page");
        driver.findElement(By.className("category-container")).click();
        WebElement filmSelectorLabel = driver.findElement(By.xpath("//div[@id='film-selector']//label"));
        Assert.assertTrue(filmSelectorLabel.getText().contains(" STAR WARS FILM SELECTOR "), "Films should have label Star Wars Film Selector");
        driver.findElement(By.xpath("//a[contains(text(),'SOLO')]")).click();
        Assert.assertTrue(driver.findElement(By.className("purchase_product")).isDisplayed(), "User should be going to page to Purchase Product");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @DataProvider(name="Search TestData")
    public Object[][] dataProviderInvalidCredentials(){
        return new Object[][] {{"PLANET"},{"VEHICLE"}};
    }

}
