import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.example.Food;
import org.example.FoodGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Автотесты - вариант 2
 * 1) Написать автотесты на БД - CRUD опреции с товарами
 * 2) Проверка что в веб части портала - меню "Песочница"->"Товары" - отображаются действия проделанные в БД
 * 3) Проверка что в API отобрадаются действия проделанные в БД
 */
public class DbTest extends BaseTest
{
    Food food = FoodGenerator.getRandomFood();
    @Test
    @DisplayName("CRUD опреции с товарами через БД")
    public void BDTestInsert() throws InterruptedException, SQLException {
        System.out.printf("Тестовые параметры: %nНазвание:"+ food.name+"%nТип:"+food.type+"%nЭкзотический:"+food.exotic+" %n");
        BaseTest.statement.executeUpdate("INSERT INTO FOOD(FOOD_NAME,FOOD_TYPE,FOOD_EXOTIC) VALUES ('"+food.name+"','VEGETABLE',0)");
        ResultSet resultSet = BaseTest.statement.executeQuery("Select * FROM FOOD");
    }
    @Test
    @DisplayName("Проверка что в веб части портала - меню \"Песочница\"->\"Товары\" - отображаются действия проделанные в БД")
    public void WebTest() throws InterruptedException, SQLException {
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .checkOpenSanboxPage()
                .selectTableElement()
                .AssertTableElement(food.name);
    }
    @Test
    @DisplayName("Проверка что в API отобрадаются действия из Web формы меню \"Песочница\"->\"Товары\"")
    public void ApiTestAssert() throws InterruptedException, SQLException {
        ApiRequest request = RequestFactory.createRequest("GET","http://localhost:8080/api/food",food);
        Response response = request.sendRequest();
        Allure.addAttachment("Результат", "application/json", response.body().prettyPrint());
        Allure.addAttachment("Результат", "application/json", request.toString());
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertTrue(response.getBody().jsonPath().getString("name").contains(food.name));

    }
}
