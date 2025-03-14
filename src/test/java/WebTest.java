import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.example.data.Food;
import org.example.data.FoodGenerator;
import org.example.pages.MainPage;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runners.MethodSorters;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Автотесты - вариант 1
 * 1) Написать автотесты на веб часть портала - меню "Песочница"->"Товары"
 * 2) Проверка что в БД отображаются действия из Web формы меню "Песочница"->"Товары"
 * 3) Проверка что в API отобрадаются действия из Web формы меню "Песочница"->"Товары"
 */
public class WebTest extends BaseTest {
    Food food = FoodGenerator.getRandomFood();

    @org.junit.jupiter.api.Test
    @DisplayName("Сброс и добавление товара через Web часть")
    public void aWebTest() throws SQLException {
       // app.getPage(MainPage.class)
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .selectTableElement()
                .clickBtnAdd()
                .fillField("Наименование",food.name)
                .fillDropDown(String.valueOf(food.type))
                .fillChechBox(food.exotic)
                .clickSave();

//    @org.junit.jupiter.api.Test
//    @DisplayName("Проверка что в БД отображаются действия из Web формы меню \"Песочница\"->\"Товары\"")
//    public void bBDTestAssert() throws SQLException {
        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");
        Allure.addAttachment("Запрос", "application/json", "Select * FROM FOOD");
        Allure.addAttachment("Ответ", "application/json", String.valueOf(resultSet));
        ArrayList<String> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(resultSet.getString(2));
            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }
        Assertions.assertTrue(result.contains(food.name),"Товар: "+food.name+" не найден в таблице или отсутствует!");

//    @org.junit.jupiter.api.Test
//    @DisplayName("Проверка что в API отобрадаются действия из Web формы меню \"Песочница\"->\"Товары\"")
//    public void cApiTestAssert() {
        ApiRequest request = RequestFactory.createRequest("GET","http://localhost:8080/api/food",food);
        Response response = request.sendRequest();
        Allure.addAttachment("Ответ", "application/json",response.body().prettyPrint());
        Allure.addAttachment("Запрос", "application/json", "curl -X GET \"http://localhost:8080/api/food\" -H  \"accept: */*\"");
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertTrue("Товар: "+food.name+" отсутствует в ответе API!",response.getBody().jsonPath().getString("name").contains(food.name));
    }
}
