import io.qameta.allure.Allure;
import io.restassured.response.Response;

import org.example.Food;
import org.example.FoodGenerator;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runners.MethodSorters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Автотесты - вариант 3
 * 1) Написать автотесты на API часть портала - "Товары" и сброс
 * 3) Проверка что в БД - отображаются действия проделанные в API
 * 2) Проверка что в веб части портала - меню "Песочница"->"Товары" - отображаются действия проделанные в API
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APITest extends BaseTest{
    Food food = FoodGenerator.getRandomFood();
    @Test
    @DisplayName("Сброс и добавление товара через API")
    public void aApiTest() throws SQLException {

        ApiRequest request = RequestFactory.createRequest("POST","http://localhost:8080/",food);
        Response response = request.sendRequest();
        Assert.assertEquals(200,response.getStatusCode());
//        ApiRequest request = RequestFactory.createRequest("GET","http://localhost:8080/api/food",food);
//        Response response = request.sendRequest();
//        Assert.assertEquals(200,response.getStatusCode());
//        Assert.assertTrue(response.getBody().jsonPath().getString("name").contains("Помидор"));
    }
    @Test
    @DisplayName("Проверка что в веб части портала - меню \"Песочница\"->\"Товары\" - отображаются действия проделанные в API")
    public void bWebTest() throws InterruptedException, SQLException {
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .checkOpenSanboxPage()
                .selectTableElement()
                .AssertTableElement(food.name);
    }
    @Test
    @DisplayName("Проверка что в БД - отображаются действия проделанные в API")
    public void cBDTest() throws InterruptedException, SQLException {
        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");
        Allure.addAttachment("Результат", "application/json", String.valueOf(resultSet));
        ArrayList<String> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(resultSet.getString(2));

            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }
        Assertions.assertTrue(result.contains(food.name));


    }

}

