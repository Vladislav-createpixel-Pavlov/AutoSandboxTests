import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.checkerframework.checker.index.qual.LowerBoundBottom;
import org.example.Food;
import org.example.FoodGenerator;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Автотесты - вариант 1
 * 1) Написать автотесты на веб часть портала - меню "Песочница"->"Товары"
 * 2) Проверка что в БД отображаются действия из Web формы меню "Песочница"->"Товары"
 * 3) Проверка что в API отобрадаются действия из Web формы меню "Песочница"->"Товары"
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebTest extends BaseTest {
    Food food = FoodGenerator.getRandomFood();

    @org.junit.jupiter.api.Test
    @DisplayName("Сброс и добавление товара через Web часть")
    public void aWebTest() throws InterruptedException, SQLException {
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .selectTableElement()
                .clickBtnAdd()
                .fillField("Наименование",food.name)
                .fillDropDown(String.valueOf(food.type))
                .fillChechBox(food.exotic)
                .clickSave();
    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка что в БД отображаются действия из Web формы меню \"Песочница\"->\"Товары\"")
    public void bBDTestAssert() throws InterruptedException, SQLException {
        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");
        Allure.addAttachment("Запрос", "application/json", "Select * FROM FOOD");
        Allure.addAttachment("Ответ", "application/json", String.valueOf(resultSet));
        ArrayList<String> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(resultSet.getString(2));
            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }
        Assertions.assertTrue(result.contains(food.name),"Товар: "+food.name+" не найден в таблице или отсутствует!");
    }
    @org.junit.jupiter.api.Test
    @DisplayName("Проверка что в API отобрадаются действия из Web формы меню \"Песочница\"->\"Товары\"")
    public void cApiTestAssert() throws InterruptedException, SQLException {
        ApiRequest request = RequestFactory.createRequest("GET","http://localhost:8080/api/food",food);
        Response response = request.sendRequest();
        Allure.addAttachment("Ответ", "application/json",response.body().prettyPrint());
        Allure.addAttachment("Запрос", "application/json", request.toString());
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertTrue("Товар: "+food.name+" отсутствует в ответе API!",response.getBody().jsonPath().getString("name").contains(food.name));
    }


}
