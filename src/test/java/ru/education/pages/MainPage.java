package ru.education.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    // Исправлено: чистый XPath без смешивания с CSS
    private final SelenideElement createAdBtn = $(byXpath("//header//button[contains(text(),'Разместить')] | //button[contains(text(),'Разместить')] | //button[contains(@class,'buttonPrimary')]"));
    private final SelenideElement searchInput = $("input[placeholder*='Я хочу купить'], input[name='name']");
    private final SelenideElement applyFilterBtn = $(byXpath("//button[contains(text(),'Применить')]"));

    public void checkUserIsLoggedIn() {
        $(byXpath("//*[contains(text(), 'Выйти') or contains(text(), 'User')]")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void clickCreateAd() {
        createAdBtn.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void checkAdIsVisible(String title) {
        Selenide.sleep(1500); // Ожидание загрузки данных


        SelenideElement targetElement = $(byXpath("//*[contains(text(), '" + title + "') or contains(., '" + title + "')]"));
        if (targetElement.is(Condition.visible)) {
            return;
        }


        open("/");
        if (searchInput.exists() && searchInput.is(Condition.visible)) {
            searchInput.clear();
            searchInput.sendKeys(title);
            if (applyFilterBtn.exists()) {
                applyFilterBtn.click();
            } else {
                searchInput.pressEnter();
            }
            Selenide.sleep(1000);
        }

        targetElement.shouldBe(Condition.visible, Duration.ofSeconds(12));
    }
}