package ru.yandex.test.appmanager;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.test.model.Monitor;

import java.io.File;
import java.util.ArrayList;

public class FilterHelper {
    private int screenshotCount=0;
    private WebDriver driver;

    public FilterHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoad() {
        WebDriverWait waiter = new WebDriverWait(driver, 30, 100);
        waiter.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class='preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja']"), 0));
        waiter.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja']"), 0));
    }

    public void makeScreenshot() throws Exception{

        Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS, 500, true).withName("Result" + ++screenshotCount).save();
/*
        File screenshot = ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.FILE);

        String path = "./target/screenshots/" + screenshot.getName();
        FileUtils.copyFile(screenshot, new File(path));*/
    }

    public void rateByCoast() throws InterruptedException {
        driver.findElement(By.xpath("//a[.='по цене']")).click();
        this.waitForLoad();
    }

    public void setManufacturer(String manufacturer) {
        driver.findElement(By.xpath("//fieldset[child::legend[.='Производитель']]//span[.='" + manufacturer + "']")).click();
    }

    public void setColor(String color) {
        driver.findElement(By.xpath("//fieldset[child::legend[.='Цвет']]//div[child::span[.='Цвет " + color.toLowerCase() + "']]")).click();
    }

    public void setMinPrice(String price) {
        driver.findElement(By.xpath("//*[@id='glpricefrom']")).clear();
        driver.findElement(By.xpath("//*[@id='glpricefrom']")).sendKeys(price);
    }

    public void setMaxPrice(String price) {
        driver.findElement(By.xpath("//*[@id='glpriceto']")).clear();
        driver.findElement(By.xpath("//*[@id='glpriceto']")).sendKeys(price);
    }

    public void setScreenSize(String size) {
        driver.findElement(By.xpath("//fieldset[child::legend[.='Размер экрана']]//span[.='" + size + "']")).click();
    }

    public void setResolution(String resolution) {

        driver.findElement(By.xpath("//fieldset[./legend[text()='Макс. разрешение']]//span[text()='" + resolution + "']")).click();
    }


    public void setMonitorParameters(ArrayList<Monitor> monitors) throws Exception{

        for (Monitor monitor : monitors) {
            this.resetParameters();
            this.setParametersFromClass(monitor);
            this.waitForLoad();
            this.makeScreenshot();
        }
    }

    public void resetParameters() {
        driver.findElement(By.xpath("//span[text()='Все фильтры']")).click();
        driver.findElement(By.xpath("//button[./span[contains(text(),'Сбросить фильтр')]]")).click();
        driver.findElement(By.xpath("//a[./span[text()='Показать подходящие']]")).click();

    }

    public void setParametersFromClass(Object object) {
        if (object instanceof Monitor) {
            if (((Monitor) object).getPriceFrom() != null) {
                this.setMinPrice(((Monitor) object).getPriceFrom().toString());
            }

            if (((Monitor) object).getPriceTo() != null) {
                this.setMaxPrice(((Monitor) object).getPriceTo().toString());
            }

            if (((Monitor) object).getManufactorer() != null) {
                this.setManufacturer(((Monitor) object).getManufactorer());
            }

            if (((Monitor) object).getResolution() != null) {
                this.setResolution(((Monitor) object).getResolution());
            }

            if (((Monitor) object).getScreenSize() != null) {
                this.setScreenSize(((Monitor) object).getScreenSize());
            }

            if (((Monitor) object).getHdmi() != null) {
                this.setHdmi();
            }

            if (((Monitor) object).getAspectRatio() != null) {
                this.setAspectRatio(((Monitor) object).getAspectRatio());
            }

           /* if ((Monitor) object).getMatrixType() != null) {
    this.serMatrixType(((Monitor) object).getMatrixType());
            }*/
        }
    }

    private void serMatrixType(String matrixType) {

    }

    private void setAspectRatio(String aspectRatio) {
        driver.findElement(By.xpath("//fieldset[child::legend[.='Соотношение сторон']]//span[.='" + aspectRatio + "']")).click();
    }

    private void setHdmi() {
        driver.findElement(By.xpath("//fieldset[child::legend[.='Вход HDMI']]//span[.='Вход HDMI']")).click();
    }

    public void clearScreenshots() throws Exception{
        FileUtils.deleteDirectory(new File("screenshots"));
    }
}
