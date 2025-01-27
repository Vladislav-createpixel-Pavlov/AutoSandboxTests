import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.Arrays;

public class MyExecution implements AfterTestExecutionCallback
{
    TakesScreenshot tsDriver;
    byte[] screenshot = tsDriver.getScreenshotAs(OutputType.BYTES);

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (extensionContext.getExecutionException().isPresent()) Allure.addAttachment("_screenshot", "image/png", Arrays.toString(screenshot), "png");

    }
}
