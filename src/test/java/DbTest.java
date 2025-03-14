import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.example.data.Food;
import org.example.data.FoodGenerator;
import org.example.pages.MainPage;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Автотесты - вариант 2
 * 1) Написать автотесты на БД - CRUD опреции с товарами
 * 2) Проверка что в веб части портала - меню "Песочница"->"Товары" - отображаются действия проделанные в БД
 * 3) Проверка что в API отобрадаются действия проделанные в БД
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DbTest extends BaseTest
{
    Food food = FoodGenerator.getRandomFood();
    @Test
    @DisplayName("CRUD опереции с товарами через БД")
    public void aBDTestInsert() throws SQLException {
        System.out.printf("Тестовые параметры: %nНазвание:"+ food.name+"%nТип:"+food.type+"%nЭкзотический:"+food.exotic+" %n");
        BaseTest.DBInsert("INSERT INTO FOOD(FOOD_NAME,FOOD_TYPE,FOOD_EXOTIC) VALUES ('"+food.name+"','VEGETABLE',0)");
        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");
        Allure.addAttachment("Запрос", "application/json","INSERT INTO FOOD(FOOD_NAME,FOOD_TYPE,FOOD_EXOTIC) VALUES ('"+food.name+"','VEGETABLE',0)");
        while(resultSet.next()){
            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }

//    @org.junit.jupiter.api.Test
//    @DisplayName("Проверка что в веб части портала - меню \"Песочница\"->\"Товары\" - отображаются действия проделанные в БД")
//    public void bWebTest() {
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .checkOpenSanboxPage()
                .selectTableElement()
                .AssertTableElement(food.name);

//    @org.junit.jupiter.api.Test
//    @DisplayName("Проверка что в API отобрадаются действия из Web формы меню \"Песочница\"->\"Товары\"")
//    public void cApiTestAssert() {
        ApiRequest request = RequestFactory.createRequest("GET","http://localhost:8080/api/food",food);
        Response response = request.sendRequest();
        Allure.addAttachment("Ответ", "application/json", response.body().prettyPrint());
        Allure.addAttachment("Запрос", "application/json", "curl -X GET \"http://localhost:8080/api/food\" -H  \"accept: */*\"");
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getString("name").contains(food.name),"Товар: "+food.name+" отсутствует в ответе API!");
    }
}
