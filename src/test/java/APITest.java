import io.qameta.allure.Allure;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import pojos.GoodsPojo;
import services.FoodService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Автотесты - вариант 3
 * 1) Написать автотесты на API часть портала - "Товары" и сброс
 * 3) Проверка что в БД - отображаются действия проделанные в API
 * 2) Проверка что в веб части портала - меню "Песочница"->"Товары" - отображаются действия проделанные в API
 */
public class APITest extends BaseTest{
    private static FoodService foodService = new FoodService();
    // лучше не делать static - до выполнения подгружается в память каждый раз при рекомпиляции
    static GoodsPojo food = FoodGenerator.createFood();
    @Test
    @DisplayName("Сброс и добавление товара через API и проверка на Web и в БД")
    public void aApiTest() throws SQLException {

        Response usersResponse = foodService.createFood(food);
        Assertions.assertEquals(200,usersResponse.getStatusCode());
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .checkOpenSanboxPage()
                .selectTableElement()
                .AssertTableElement(food.getName());

        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");
        ArrayList<String> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(resultSet.getString(2));

            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }
        Allure.addAttachment("Ответ", "application/json", String.valueOf(result));

        Assertions.assertTrue(result.contains(food.getName()),"Товар: "+food.getName()+" не найден в таблице или отсутствует!");


    }

}

