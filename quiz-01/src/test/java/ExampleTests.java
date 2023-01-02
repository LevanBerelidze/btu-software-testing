import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ExampleTests {
    @Test
    public void firstTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/login");
        driver.manage().window().maximize();

        // Login
        WebElement userNameInput = driver.findElement(By.id("userName"));
        userNameInput.sendKeys("test123");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Automation@123");

        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();

        ExpectedCondition<WebElement> finishCondition = ExpectedConditions.elementToBeClickable(By.xpath("//*[text() = 'Log out']"));
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(finishCondition);

        // Go to bookstore
        WebElement bookStoreListItem = driver.findElement(By.id("item-2"));
        bookStoreListItem.click();

        List<WebElement> bookAnchors = driver.findElements(By.xpath("//div[@class='action-buttons']/span/a"));
        Assert.assertEquals(bookAnchors.size(), 8);

        // Book details
        WebElement bookElement = driver.findElement(By.id("see-book-Git Pocket Guide"));
        bookElement.click();

        WebElement bookTitle = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[2]/div[2]/div[2]/label"));
        Assert.assertEquals(bookTitle.getText(), "Git Pocket Guide");

        // Add to your collection
        WebElement goBackButton = driver.findElement(By.xpath("//*[text() = 'Add To Your Collection']"));
        goBackButton.click();

        // Back to book store
        WebElement addToCollectionButton = driver.findElement(By.xpath("//*[text() = 'Back To Book Store']"));
        addToCollectionButton.click();

        // Log out
        WebElement logoutButton = driver.findElement(By.xpath("//*[text() = 'Log out']"));
        logoutButton.click();

        WebElement welcomeH2 = driver.findElement(By.xpath("//*[@id=\"userForm\"]/div[1]/h2"));
        Assert.assertEquals(welcomeH2.getText(), "Welcome,");

        WebElement welcomeH5 = driver.findElement(By.xpath("//*[@id=\"userForm\"]/div[1]/h5"));
        Assert.assertEquals(welcomeH5.getText(), "Login in Book Store");

        driver.quit();
    }
}
