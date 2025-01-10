import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.example.*;
import org.example.DriverManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.*;

import static io.restassured.RestAssured.given;
import static org.example.PropConst.BASE_URL;

public class BaseTest
{
    protected PageManager app = PageManager.getPageManager();

    private static final DriverManager driverManager = DriverManager.getDriverManager();

    static Faker faker = new Faker();
    static String cookie = null;

    static Connection connection;
    static Statement statement;
    protected static String QueryBuilder(String operation){
        StringBuilder query = new StringBuilder();
        query.append(operation);
        query.append(";");
        return query.toString();

    }
    @Step
    protected static void Insert(String URI,String name,String type,Boolean exotic) {
        Food food = FoodGenerator.getRandomFood();
        Response putResponse = given()
                .baseUri(URI)
                .header("Content-type", "application/json")
                .header("accept", "*/*")
                .and()
                .body("{\"name\": " + "\"" + name + "\"" + ", \"type\": " + "\"" + type + "\"" + ", \"exotic\": " + exotic + "}")
                .when()
                .log()
                .all()
                .post("api/food")
                .then()
                .log().all()
                .extract().response();
        cookie = putResponse.cookie("JSESSIONID");
    }
    protected static ResultSet DBSelect(String query) throws SQLException {

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        }
        catch (Exception e)
        {

        };
        return statement.executeQuery(query);
    }
    protected static void DBInsert(String query) throws SQLException {

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.execute(query);
        }
        catch (Exception e)
        {

        };
    }
    @Step
    protected static ResponseBody Select(String URI){
        ResponseBody getResponse = given()
                .baseUri(URI)
                .header("accept", "*/*")
                //.cookie("JSESSIONID",cookie)
                .when()
                .log().all()
                .get("api/food")
                .then()
                .statusCode(200)
                .assertThat()
                .extract().response();

        return getResponse;
    }

    @Step
    @BeforeAll
    public static void beforeAll() throws SQLException, IOException {
        connection = java.sql.DriverManager.getConnection(
                "jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user",
                "pass"
        );
        System.out.println("beforeAll");
//         Runtime.getRuntime().exec("cd C:\\Working Project");
//        Runtime.getRuntime().exec("java -jar qualit-sandbox.jar");

        InitManager.initFramework();

    }

    @Step
    @BeforeClass
    public static void beforeEach() throws SQLException {
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
