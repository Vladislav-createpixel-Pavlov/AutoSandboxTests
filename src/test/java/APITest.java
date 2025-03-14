import io.qameta.allure.Allure;
import io.restassured.response.Response;

import org.example.data.Food;
import org.example.data.FoodGenerator;
import org.example.pages.MainPage;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.MethodSorters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Автотесты - вариант 3
 * 1) Написать автотесты на API часть портала - "Товары" и сброс
 * 3) Проверка что в БД - отображаются действия проделанные в API
 * 2) Проверка что в веб части портала - меню "Песочница"->"Товары" - отображаются действия проделанные в API
 */
public class APITest extends BaseTest{
    // лучше не делать static - до выполнения подгружается в память каждый раз при рекомпиляции
    static Food food = FoodGenerator.getRandomFood();
    static public Stream<Arguments> GenerateFood()
    {
        return Stream.of(
                arguments(food));

    }
    @DisplayName("Сброс и добавление товара через API и проверка на Web и в БД")
    @ParameterizedTest
    @MethodSource("GenerateFood")
    public void aApiTest() throws SQLException {
        //
        ApiRequest request = RequestFactory.createRequest("POST","http://localhost:8080/",food);
        Response response = request.sendRequest();
        Assertions.assertEquals(200,response.getStatusCode());
        Allure.addAttachment("Запрос", "application/json", request.toString());
//        ApiRequest request = RequestFactory.createRequest("GET","http://localhost:8080/api/food",food);
//        Response response = request.sendRequest();
//        Assert.assertEquals(200,response.getStatusCode());
//        Assert.assertTrue(response.getBody().jsonPath().getString("name").contains("Помидор"));

//    @org.junit.jupiter.api.Test
//    @DisplayName("Проверка что в веб части портала - меню \"Песочница\"->\"Товары\" - отображаются действия проделанные в API")
//    public void bWebTest() throws InterruptedException, SQLException {
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .checkOpenSanboxPage()
                .selectTableElement()
                .AssertTableElement(food.name);
   // }
//    @org.junit.jupiter.api.Test
//    @DisplayName("Проверка что в БД - отображаются действия проделанные в API")
//    public void cBDTest() throws InterruptedException, SQLException {
        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");
        ArrayList<String> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(resultSet.getString(2));

            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }
        Allure.addAttachment("Ответ", "application/json", String.valueOf(result));

        Assertions.assertTrue(result.contains(food.name),"Товар: "+food.name+" не найден в таблице или отсутствует!");


    }

}

