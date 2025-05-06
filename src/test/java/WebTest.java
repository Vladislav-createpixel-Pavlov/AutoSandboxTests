import io.qameta.allure.Allure;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.GoodsPojo;
import services.FoodService;
import services.RestResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Автотесты - вариант 1
 * 1) Написать автотесты на веб часть портала - меню "Песочница"->"Товары"
 * 2) Проверка что в БД отображаются действия из Web формы меню "Песочница"->"Товары"
 * 3) Проверка что в API отобрадаются действия из Web формы меню "Песочница"->"Товары"
 */
public class WebTest extends BaseTest {
    GoodsPojo food = FoodGenerator.createFood();
    private static FoodService foodService = new FoodService();

    @Test
    @DisplayName("Сброс и добавление товара через Web часть")
    public void aWebTest() throws SQLException {
        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .selectTableElement()
                .clickBtnAdd()
                .fillField("Наименование",food.getName())
                .fillDropDown("Фрукт")
                .fillChechBox("Экзотический")
                .clickSave();

        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");

        ArrayList<String> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(resultSet.getString(2));
            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }
        Allure.addAttachment("Запрос", "application/json", "Select * FROM FOOD");
        Allure.addAttachment("Ответ", "application/json", result.toString());
        Assertions.assertTrue(result.contains(food.getName()),"Товар: "+food.getName()+" не найден в таблице или отсутствует!");

        RestResponse<List<GoodsPojo>> userResponse = foodService.getFoodList();
        userResponse.validate("FoodTemplate.json");
        userResponse.assertCode(200);
        Assertions.assertTrue(userResponse.extract().contains(food.getName()),"Товар: "+food.getName()+" отсутствует в ответе API!");
        Assert.assertTrue("Товар: "+food.getName()+" отсутствует в ответе API!",userResponse.extract().contains(food.getName()));
    }
}
