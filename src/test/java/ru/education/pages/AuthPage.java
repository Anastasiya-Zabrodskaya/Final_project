package ru.education.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath; // ИСПРАВЛЕНО: Добавили импорт для byXpath
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthPage {


    private final SelenideElement emailInput = $("input[name='email']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitPasswordInput = $("input[name='submitPassword']");
    private final SelenideElement submitButton = $("button[type='submit']");


    private final SelenideElement errorMessage = $(byXpath("//*[text()='Ошибка']"));

    public void openRegistrationPage() {
        open("/registration");
    }

    public void openLoginPage() {
        open("/login");
    }

    public void fillEmail(String email) {
        emailInput.shouldBe(visible).setValue(email);
    }

    public void fillPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
    }

    public void fillSubmitPassword(String password) {
        if (submitPasswordInput.exists()) {
            submitPasswordInput.setValue(password);
        }
    }

    public void clickSubmit() {
        submitButton.shouldBe(visible).click();
    }

    public void checkErrorMessageContains(String expectedText) {
        errorMessage.shouldBe(visible).shouldHave(text(expectedText));
    }
}