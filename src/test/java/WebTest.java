import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.checkerframework.checker.index.qual.LowerBoundBottom;
import org.example.Food;
import org.example.FoodGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * Автотесты - вариант 1
 * 1) Написать автотесты на веб часть портала - меню "Песочница"->"Товары"
 * 2) Проверка что в БД отображаются действия из Web формы меню "Песочница"->"Товары"
 * 3) Проверка что в API отобрадаются действия из Web формы меню "Песочница"->"Товары"
 */

public class WebTest extends BaseTest {
    Food food = FoodGenerator.getRandomFood();

    @Test
    @DisplayName("Сброс и добавление товара через Web часть")
    public void WebTest() throws InterruptedException, SQLException {
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .checkOpenSanboxPage()
                .selectTableElement()
                .clickBtnAdd()
                .fillField("Наименование",food.name)
                .fillDropDown(String.valueOf(food.type))
                .fillChechBox(food.exotic)
                .clickSave();
    }
    @Test
    @DisplayName("Проверка что в БД отображаются действия из Web формы меню \"Песочница\"->\"Товары\"")
    public void BDTestAssert() throws InterruptedException, SQLException {
        System.out.printf("Тестовые параметры: %nНазвание:"+ food.name+"%nТип:"+food.type+"%nЭкзотический:"+food.exotic+" %n");
        ResultSet resultSet = statement.executeQuery(("Select * FROM FOOD"));
        System.out.println();
    }
    @Test
    @DisplayName("Проверка что в API отобрадаются действия из Web формы меню \"Песочница\"->\"Товары\"")
    public void ApiTestAssert() throws InterruptedException, SQLException {
        ApiRequest request = RequestFactory.createRequest("GET","http://localhost:8080/api/food",food);
        Response response = request.sendRequest();
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertTrue(response.getBody().jsonPath().getString("name").contains(food.name));
        Allure.addAttachment("Результат", "application/json", String.valueOf(response));
        Allure.addAttachment("Результат", "application/json", request.toString());
    }


}
