package org.example;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePage {

    @FindBy(xpath = "//li[starts-with(@class,'nav-item')]")
    private List<WebElement> listMenu;

    @FindBy(xpath = "//a[@class='dropdown-item']")
    private List<WebElement> listSubMenu;
    @Step
    public MainPage selectPointOfMenu(String nameBaseMenu) {
        for (WebElement menuItem : listMenu) {
            if (menuItem.getText().trim().equalsIgnoreCase(nameBaseMenu)) {
                waitUtilElementToBeClickable(menuItem).click();
                return this;
            }
        }
        Assert.fail("Меню '" + nameBaseMenu + "' не было найдено на главной странице!");
        return this;
    }
    @Step
    public SandboxPage selectSubMenu(String nameSubMenu) {
        for (WebElement menuItem : listSubMenu) {
            if (menuItem.getText().equalsIgnoreCase(nameSubMenu)) {
                waitUtilElementToBeClickable(menuItem).click();
                return pageManager.getSandboxPage().checkOpenSanboxPage();
            }
        }
        Assert.fail("Подменю '" + nameSubMenu + "' не было найдено на главной странице!");
        return pageManager.getSandboxPage();
    }
}
