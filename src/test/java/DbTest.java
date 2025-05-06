import io.qameta.allure.Allure;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import pojos.GoodsPojo;
import services.FoodService;
import services.RestResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Автотесты - вариант 2
 * 1) Написать автотесты на БД - CRUD опреции с товарами
 * 2) Проверка что в веб части портала - меню "Песочница"->"Товары" - отображаются действия проделанные в БД
 * 3) Проверка что в API отобрадаются действия проделанные в БД
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DbTest extends BaseTest
{
    private static FoodService foodService = new FoodService();

    GoodsPojo food = FoodGenerator.createFood();
    @Test
    @DisplayName("CRUD опереции с товарами через БД")
    public void aBDTestInsert() throws SQLException {
        System.out.printf("Тестовые параметры: %nНазвание:"+ food.getName()+"%nТип:"+food.getName()+"%nЭкзотический:"+food.getExotic()+" %n");
        BaseTest.DBInsert("INSERT INTO FOOD(FOOD_NAME,FOOD_TYPE,FOOD_EXOTIC) VALUES ('"+food.getName()+"','VEGETABLE',0)");
        ResultSet resultSet = BaseTest.DBSelect("Select * FROM FOOD");
        Allure.addAttachment("Запрос", "application/json","INSERT INTO FOOD(FOOD_NAME,FOOD_TYPE,FOOD_EXOTIC) VALUES ('"+food.getName()+"','VEGETABLE',0)");
        while(resultSet.next()){
            System.out.println("|"+resultSet.getString(1)+"|"+resultSet.getString(2)+"|"+resultSet.getString(3)+"|");
        }

        app.getMainPage()
                .selectPointOfMenu("Песочница")
                .selectSubMenu("Товары")
                .checkOpenSanboxPage()
                .selectTableElement()
                .AssertTableElement(food.getName());

        RestResponse<List<GoodsPojo>> userResponse = foodService.getFoodList();
        userResponse.assertCode(200);
        userResponse.validate("FoodTemplate.json");
        Assertions.assertTrue(userResponse.extract().contains(food.getName()),"Товар: "+food.getName()+" отсутствует в ответе API!");
    }
}
