package org.example;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SandboxPage extends BasePage
{
    @FindBy(xpath = "//h5[not(contains(@class,'modal-title'))]")
    private WebElement title;

    @FindBy(xpath = "//button[text() ='Добавить']")
    private WebElement btmAdd;

    @FindBy(xpath = "//li[starts-with(@class,'nav-item')]")
    private List<WebElement> listMenu;

    @FindBy(xpath = "//table[@class=\"table\"]//tr//td[1]")
    private List<WebElement> elementTable;

    @FindBy(xpath = "//a[@class='dropdown-item']")
    private List<WebElement> listSubMenu;
    @Step
    public SandboxPage selectPointOfMenu(String nameBaseMenu) {
        for (WebElement menuItem : listMenu) {
            if (menuItem.getText().trim().equalsIgnoreCase(nameBaseMenu)) {
                waitUtilElementToBeClickable(menuItem).click();
                return this;
            }
        }
        Assert.fail("Меню '" + nameBaseMenu + "' не было найдено на главной странице!");
        return this;
    }
//    @Step
//    public SandboxPage selectTableElement() {
//        for (WebElement menuItem : elementTable) {
//            System.out.println(menuItem.getText());
//        }
//        return this;
//    }

    @Step
    public AddPage clickBtnAdd() {
        waitUtilElementToBeClickable(btmAdd).click();
        return pageManager.getAddPage();
    }
    @Step
    public SandboxPage checkOpenSanboxPage() {
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Список товаров", title.getText());
        return pageManager.getSandboxPage();
    }
    @Step
    public SandboxPage selectTableElement() {
        for (WebElement menuItem : elementTable) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(menuItem.getText());
        }
        return pageManager.getSandboxPage();
    }
    @Step
    public void AssertTableElement(String assert_element) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (WebElement menuItem : elementTable) {

            arrayList.add(menuItem.getText());
        }
        Assert.assertTrue(arrayList.contains(assert_element));
    }
}
