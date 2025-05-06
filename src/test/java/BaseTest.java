import io.qameta.allure.Step;
import org.example.managers.DriverManager;
import org.example.pages.PageManager;
import org.example.managers.InitManager;
import org.example.managers.TestPropManager;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import java.sql.*;

import static org.example.managers.PropConst.BASE_URL;

public class BaseTest
{
    protected PageManager app = PageManager.getPageManager();

    private static final DriverManager driverManager = DriverManager.getDriverManager();

    static Connection connection;
    static Statement statement;

    protected static String QueryBuilder(String operation){
        StringBuilder query = new StringBuilder();
        query.append(operation);
        query.append(";");
        return query.toString();

    }

    @Step
    protected static ResultSet DBSelect(String query) throws SQLException {
        return statement.executeQuery(query);
    }
    @Step
    protected static void DBInsert(String query) throws SQLException {
        try {
            statement.execute(query);
        }
        catch (Exception e)
        {

        };
    }
    @Rule
    public TestListener testListener = new TestListener();

//    @Rule
//    public TestWatcher watchman = new TestWatcher() {
//        @Override
//        protected void failed(Throwable e, Description description) {
//            // Take a screenshot when test fails
//            takeScreenshotAndAttach(description.getMethodName());
//        }
//    };
//
//    private void takeScreenshotAndAttach(String methodName) {
//            // Convert WebDriver instance to TakesScreenshot
//            // Capture screenshot
//            TakesScreenshot tsDriver = (TakesScreenshot) driverManager.getDriver();
//            byte[] screenshot = tsDriver.getScreenshotAs(OutputType.BYTES);
//            // Attach screenshot to Allure report
//            Allure.addAttachment(methodName + "_screenshot", "image/png", Arrays.toString(screenshot), "png");
//        }

    @Step
    @BeforeAll
    public static void beforeAll() throws SQLException {
        System.out.println("beforeAll");
        connection = java.sql.DriverManager.getConnection(
                "jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user",
                "pass"
        );
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        InitManager.initFramework();

    }

    @Step
    @BeforeEach
    public void beforeEach() {
        driverManager.getDriver().get(TestPropManager.getTestPropManager().getProperty(BASE_URL));
    }

    @Step
    @AfterAll
    public static void afterAll() throws SQLException {
        InitManager.quitFramework();
        statement.execute(QueryBuilder("DELETE FROM FOOD WHERE FOOD_ID = (SELECT MAX(FOOD_ID) FROM FOOD)"));
        connection.close();
        System.out.println("afterAll");
    }
}
