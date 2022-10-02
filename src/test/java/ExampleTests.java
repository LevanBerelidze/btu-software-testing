import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExampleTests {
    @Test
    public void firstTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/");
        driver.manage().window().maximize();

        WebElement button = driver.findElement(By.xpath("//ul/li/a"));
        button.click();

        WebElement text = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3"));
        Assert.assertTrue(text.getText().equals("A/B Test Control") ||
            text.getText().equals("A/B Test Variation 1"));

        // Navigate back to the main page
        driver.navigate().back();

        // Click on dropdown
        WebElement dropdownAnchor = driver.findElement(By.xpath("//ul/li/a[@href=\"/dropdown\"]"));
        dropdownAnchor.click();

        text = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3"));
        Assert.assertEquals(text.getText(), "Dropdown List");

        // Select 'Option 1'
        WebElement dropdownWebElement = driver.findElement(By.id("dropdown"));
        Select dropdown = new Select(dropdownWebElement);
        dropdown.selectByVisibleText("Option 1");

        WebElement option1WebElement = driver.findElement(By.xpath("//select/option[text() = 'Option 1']"));
        Assert.assertEquals(option1WebElement.getAttribute("selected"), "true");

        driver.close();
    }
}
