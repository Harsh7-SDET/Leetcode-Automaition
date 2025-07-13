package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Enable logging
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // ChromeDriver logs
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Maximize window and implicit wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    public void testCase01() {
        driver.get("https://leetcode.com/");
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("leetcode")) {
            System.out.println("Test Passed: URL contains 'leetcode'");
        } else {
            System.out.println("Test Failed: URL does not contain 'leetcode'");
        }
    }

  public void testCase02() {
    System.out.println("Running testCase02: Verify Problem Set & Question Titles");

    try {
       
        driver.get("https://leetcode.com/");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement problemsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/problemset/']")));
        problemsLink.click();

       
        wait.until(ExpectedConditions.urlContains("problemset"));
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Navigated to Problem Set page: " + currentUrl);

       
        List<WebElement> questionElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.xpath("//div[@class='ellipsis line-clamp-1']")));

        if (questionElements.size() == 0) {
            System.out.println(" Could not load or find question list.");
            return;
        }

        
        List<String> questionNames = new ArrayList<>();
        for (int i =0 ; i < 6 && i < questionElements.size(); i++) {
            questionNames.add(questionElements.get(i).getText().trim());
        }

       
        for (int i = 0; i < questionNames.size(); i++) {
            questionElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='ellipsis line-clamp-1']")));

            WebElement questionLink = questionElements.get(i);
            String expectedName = questionLink.getText().trim();

           
            questionLink.click();
            Thread.sleep(2000);  

            String pageTitle = driver.getTitle().trim();

            String actualTitleOnly = pageTitle.replace(" - LeetCode", "").trim();
            String expectedTitleOnly = expectedName.replaceAll("^\\d+\\.\\s*", "").trim();

            if (actualTitleOnly.equalsIgnoreCase(expectedTitleOnly)) {
                System.out.println(" Verified: " + expectedName + " matches with title -> " + pageTitle);
            } else {
                System.out.println(" Mismatch: Expected -> " + expectedName + ", but got title -> " + pageTitle);
            }

            driver.navigate().back();
            wait.until(ExpectedConditions.urlContains("problemset"));
            Thread.sleep(2000); 
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}



    public void testCase03() {
        System.out.println("Running testCase03: Verify the Two Sum problem");

        driver.get("https://leetcode.com/problemset/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'/problems/')]")));
        List<WebElement> allQuestions = driver.findElements(By.xpath("//a[contains(@href,'/problems/')]"));

        boolean found = false;
        for (WebElement question : allQuestions) {
            String title = question.getText().toLowerCase();
            if (title.contains("two sum")) {
                question.click();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("'Two Sum' question not found in the current list.");
            return;
        }

        wait.until(ExpectedConditions.urlContains("two-sum"));

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("two-sum")) {
            System.out.println("URL contains 'two-sum': " + currentUrl);
        } else {
            System.out.println("URL does not contain 'two-sum'. Actual URL: " + currentUrl);
        }
    }

public void testCase04() {
    System.out.println("Running testCase04: Ensure the submissions tab displays 'Register or Sign In'");

    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://leetcode.com/problems/two-sum/");

        WebElement submissionsTab = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[text()='Submissions' and contains(@class, 'font-normal')]")));
        submissionsTab.click();

        WebElement loginPrompt = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[text()='Register or Log in']")));

        if (loginPrompt.isDisplayed()) {
            System.out.println("Test Passed: 'Register or Log in' message is displayed -> Register or Log in");
        } else {
            System.out.println("Test Failed: Login prompt not visible");
        }

        System.out.println("End Test case: testCase04");

    } catch (Exception e) {
        System.out.println("Test Failed with Exception: " + e.getMessage());
    }
}

//
}











    
   







     
    