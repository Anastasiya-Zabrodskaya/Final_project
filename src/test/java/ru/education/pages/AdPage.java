package ru.education.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Keys;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class AdPage {

    private final SelenideElement titleInput = $("input[name='name'], input[placeholder*='Название']");
    private final SelenideElement priceInput = $("input[name='price'], input[placeholder*='Цена']");
    private final SelenideElement descriptionInput = $("textarea[name='description'], input[name='description']");

    private final SelenideElement submitBtn = $(byXpath("//form//button[@type='submit'] | //button[@type='submit'] | //form//button[contains(text(),'Разместить')] | //button[contains(text(),'Сохранить')] | //button[contains(text(),'Опубликовать')]"));
    private final SelenideElement editBtn = $(byXpath("//button[contains(text(),'Редактировать')] | //button[contains(@class,'edit')] | //*[contains(@class,'editIcon')] | //button[contains(.,'Редактировать')]"));


    private final SelenideElement deleteBtn = $(byXpath("//button[contains(text(),'Удалить')] | //button[contains(@class,'delete')] | //button[contains(@class,'Delete')] | //*[contains(@class,'trash')] | //button[contains(.,'Удалить')]"));

    public void fillTitle(String title) {
        titleInput.shouldBe(visible, Duration.ofSeconds(10));
        titleInput.sendKeys(Keys.CONTROL + "a");
        titleInput.sendKeys(Keys.BACK_SPACE);
        titleInput.setValue(title);
    }

    public void fillPrice(String price) {
        priceInput.shouldBe(visible, Duration.ofSeconds(10));
        priceInput.sendKeys(Keys.CONTROL + "a");
        priceInput.sendKeys(Keys.BACK_SPACE);
        priceInput.setValue(price);
    }

    public void fillDescription(String desc) {
        if (descriptionInput.exists()) {
            descriptionInput.shouldBe(visible);
            descriptionInput.sendKeys(Keys.CONTROL + "a");
            descriptionInput.sendKeys(Keys.BACK_SPACE);
            descriptionInput.setValue(desc);
        }
    }

    public void clickSubmit() {
        if (submitBtn.is(visible)) {
            submitBtn.click();
        } else {
            priceInput.pressEnter();
        }
        Selenide.sleep(2000);
    }

    public void clickEdit() {
        editBtn.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    public void clickDelete() {
        deleteBtn.shouldBe(visible, Duration.ofSeconds(10)).click();


        SelenideElement confirmDelete = $(byXpath("//button[contains(text(),'Да')] | //button[contains(text(),'Подтвердить')] | //button[contains(@class,'confirm')]"));
        if (confirmDelete.exists() && confirmDelete.is(visible)) {
            confirmDelete.click();
        }
        Selenide.sleep(1500);
    }
}