package demo;
import java.net.MalformedURLException;
public class App {
    public void runTests() throws InterruptedException {
        
        // This is to remove unnecessary warnings from your console
        System.setProperty("java.util.logging.config.file", "logging.properties");

        TestCases tests = new TestCases(); // Initialize test class

        // Call your test cases
         tests.testCase01();
         tests.testCase02();
        tests.testCase03();
        tests.testCase04();

        // End the test session
        tests.endTest();
    }

    public static void main(String[] args) throws InterruptedException {
        new App().runTests();
    }
}
