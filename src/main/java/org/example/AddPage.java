package org.example;

import io.qameta.allure.Step;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import java.util.List;

public class AddPage extends BasePage
{
    @FindBy(xpath = "//h5[not(contains(@class,'modal-title'))]")
    private WebElement title;

    @FindBy(xpath = "//tr")
    private List<WebElement> listTable;
    @FindBy(xpath = "//table[@class='table']//tr//td[1]")
    private List<WebElement> elementTable;

    @FindBy(xpath = "//select[@id='type']")
    private WebElement Type;

    @FindBy(xpath = "//option")
    private List<WebElement> listType;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement addName;

    @FindBy(xpath = "//button[@id='save']")
    private WebElement save;

    @FindBy(xpath = "//input[@id='exotic']")
    private WebElement CheckboxExotic;

    @Step
    public AddPage fillField(String nameField, String value) {
        WebElement element = null;
        switch (nameField) {
            case "Наименование":
                fillInputField(addName, value);
                element = addName;
                break;
            default:
                Assert.fail("Поле с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Добавление товара'");

        }
        Assert.assertEquals("Поле '" + nameField + "' было заполнено некорректно",
                value, element.getAttribute("value"));
        return this;
    }
    @Step
    public AddPage fillChechBox(String name) {
        WebElement element = null;
        switch (name) {
            case "Экзотический":
                CheckboxExotic.click();
                element = CheckboxExotic;
                break;
            default:
                Assert.fail("Чекбокс '" + name + "' не был отмечен");

        }
        return this;
    }
    @Step
    public AddPage fillDropDown(String nameField) {
        WebElement element = null;
        Type.click();
        for (WebElement menuItem : listType) {
            if (menuItem.getText().trim().equalsIgnoreCase(nameField)) {
                waitUtilElementToBeClickable(menuItem).click();
                return this;
            }
        }
        Assert.fail("Элемент '" + nameField + "' выпадающего списка не был найден на странице!");
        return this;
    }
    @Step
    public AddPage checkOpenAddPage() {
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Добавление товара", title.getText());
        return this;
    }
    @Step
    public SandboxPage clickSave() {
        waitUtilElementToBeClickable(save).click();
        return pageManager.getSandboxPage();
    }



}
