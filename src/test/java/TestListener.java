import io.qameta.allure.Attachment;
import org.example.DriverManager;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestListener implements TestRule
{
    WebDriver driver = DriverManager.getDriverManager().getDriver();
    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    captureScreenshot();
                    throw t;
                }
            }

            @Attachment
            private byte[] captureScreenshot() {
                    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            }
        };
    }
}

