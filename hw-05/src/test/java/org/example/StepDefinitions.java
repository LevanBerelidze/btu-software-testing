package org.example;

import io.cucumber.java.After;
import io.cucumber.java.en.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {
    private final WebDriver driver;

    public StepDefinitions() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Given("user is on log in page")
    public void navigateToLogInPage() {
        driver.get("https://demoqa.com/login");
        driver.manage().window().maximize();
    }

    @When("enters credentials")
    public void enterUserCredentials() {
        WebElement userNameInput = driver.findElement(By.id("userName"));
        userNameInput.sendKeys("test123");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Automation@123");
    }

    @When("clicks log in button")
    public void clickLogInButton() {
        WebElement logInButton = driver.findElement(By.id("login"));
        scrollToElement(logInButton);
        logInButton.click();
    }

    @Then("log out button appears")
    public void waitForLogOutButton() {
        waitUntilClickable(By.xpath("//*[text() = 'Log out']"));
    }

    @Given("user is logged in")
    public void logIn() {
        navigateToLogInPage();
        enterUserCredentials();
        clickLogInButton();
        waitForLogOutButton();
    }

    @When("presses bookstore button")
    public void pressBookstoreButton() {
        WebElement bookstoreButton = driver.findElement(By.id("gotoStore"));
        scrollToElement(bookstoreButton);
        bookstoreButton.click();
    }

    @Then("{int} books are shown")
    public void checkBookCount(int bookCount) throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> bookAnchors = driver.findElements(By.xpath("//div[@class='action-buttons']/span/a"));
        assertEquals(bookAnchors.size(), bookCount);
    }

    @Given("user is on the bookstore page")
    public void goToBookstorePage() {
        logIn();
        pressBookstoreButton();
    }

    @When("presses the list item with id {string}")
    public void pressItem(String id) {
        waitUntilClickable(By.id(id));
        WebElement bookElement = driver.findElement(By.id(id));
        bookElement.click();
    }

    @Then("book title is {string}")
    public void checkTitle(String title) {
        WebElement bookTitle = driver.findElement(By.xpath("(//label[@id='userName-value'])[3]"));
        assertEquals(title, bookTitle.getText());
    }

    @Given("user is on book, with id {string}, details page")
    public void goToDetailsPage(String bookId) {
        goToBookstorePage();
        pressItem(bookId);
    }

    @When("user presses add to collection button")
    public void pressAddToCollectionButton() {
        WebElement button = driver.findElement(By.xpath("(//*[@id='addNewRecordButton'])[2]"));
        scrollToElement(button);
        button.click();
    }

    @Then("the following message is displayed: {string}")
    public void checkMessage(String expectedAlertText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());

        String alertText = driver.switchTo().alert().getText();
        assertEquals(expectedAlertText, alertText);
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void waitUntilClickable(By locator) {
        ExpectedCondition<WebElement> finishCondition = ExpectedConditions.elementToBeClickable(locator);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(finishCondition);
    }
}
