import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Task2Tests {
    @Test
    public void progressBarTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/progress-bar");
        driver.manage().window().maximize();

        WebElement startButton = driver.findElement(By.id("startStopButton"));
        startButton.click();

        WebElement progressBar = driver.findElement(By.id("progressBar"));
        ExpectedCondition<Boolean> finishCondition = ExpectedConditions.textToBePresentInElement(progressBar, "100%");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(finishCondition);
        System.out.println("100%");

        driver.quit();
    }

    @Test
    public void dropdownCheckBoxRadioButtonTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        driver.manage().window().maximize();

        // Choose programming language from dropdown and check that it was selected
        Select languageDropdown = new Select(driver.findElement(By.id("dropdowm-menu-1")));
        languageDropdown.selectByValue("c#");
        WebElement option1WebElement = driver.findElement(By.xpath("//*[@id=\"dropdowm-menu-1\"]/option[@value=\"c#\"]"));
        Assert.assertEquals(option1WebElement.getAttribute("selected"), "true");

        // Click on all unselected checkboxes
        List<WebElement> checkBoxLabels = driver.findElements(By.xpath("//*[@id=\"checkboxes\"]/label"));
        for (WebElement label : checkBoxLabels) {
            WebElement checkBox = label.findElement(By.tagName("input"));
            if (!checkBox.isSelected()) {
                label.click();
            }
        }

        // Click on 'Purple' radio button
        WebElement purpleRadioButton = driver.findElement(By.xpath("//*[@id=\"radio-buttons\"]/input[@value=\"purple\"]"));
        purpleRadioButton.click();

        // In 'Selected & Disabled' section check that 'Orange' option in dropdown is disabled
        WebElement orangeOption = driver.findElement(By.xpath("//*[@id=\"fruit-selects\"]/option[@value=\"orange\"]"));
        Assert.assertFalse(orangeOption.isEnabled());

        driver.quit();
    }
}
