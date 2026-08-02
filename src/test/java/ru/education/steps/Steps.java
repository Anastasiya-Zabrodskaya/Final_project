package ru.education.steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Selectors;
import io.cucumber.java.Before;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import ru.education.api.UserApiClient;
import ru.education.config.TestConfig;
import ru.education.context.TestContext;
import ru.education.models.UserDto;
import ru.education.pages.AdPage;
import ru.education.pages.AuthPage;
import ru.education.pages.MainPage;
import ru.education.utils.DataGenerator;

public class Steps {

    private final TestContext context;
    private final UserApiClient userApiClient = new UserApiClient();
    private final AuthPage authPage = new AuthPage();
    private final MainPage mainPage = new MainPage();
    private final AdPage adPage = new AdPage();

    private String createdAdTitle;

    public Steps(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        TestConfig.setup();
    }

    @Дано("В системе зарегистрирован пользователь через API")
    public void registerUserViaApi() {
        UserDto randomUser = DataGenerator.getRandomUser();
        Response response = userApiClient.registerUser(randomUser);

        if (response.getStatusCode() != 201) {
            throw new RuntimeException("Не удалось зарегистрировать пользователя через API. Статус: " + response.getStatusCode());
        }

        context.setCurrentUser(randomUser);
    }

    @Когда("Пользователь открывает страницу регистрации")
    public void openRegistrationPage() {
        authPage.openRegistrationPage();
    }

    @И("Вводит случайный email и пароль для регистрации")
    public void fillRandomRegistrationData() {
        UserDto newUser = DataGenerator.getRandomUser();
        context.setCurrentUser(newUser);

        authPage.fillEmail(newUser.getEmail());
        authPage.fillPassword(newUser.getPassword());
        authPage.fillSubmitPassword(newUser.getSubmitPassword());
    }

    @И("Нажимает кнопку отправки формы")
    public void clickSubmitForm() {
        authPage.clickSubmit();
    }

    @Тогда("Пользователь успешно авторизован в системе")
    public void checkUserLoggedIn() {
        mainPage.checkUserIsLoggedIn();
    }

    @И("Вводит email ранее зарегистрированного пользователя")
    public void fillExistingEmail() {
        authPage.fillEmail(context.getCurrentUser().getEmail());
    }

    @И("Вводит пароль для регистрации")
    public void fillPasswordForRegistration() {
        authPage.fillPassword(context.getCurrentUser().getPassword());
        authPage.fillSubmitPassword(context.getCurrentUser().getPassword());
    }

    @Тогда("я вижу сообщение об ошибке с текстом {string}")
    public void checkErrorMessage(String text) {
        authPage.checkErrorMessageContains("Ошибка");
    }

    @Тогда("Отображается ошибка о том, что пользователь уже существует")
    public void checkUserAlreadyExistsError() {
        authPage.checkErrorMessageContains("Ошибка");
    }

    @Когда("Пользователь открывает страницу входа")
    public void openLoginPage() {
        authPage.openLoginPage();
    }

    @И("Вводит email и пароль зарегистрированного пользователя")
    public void fillLoginData() {
        authPage.fillEmail(context.getCurrentUser().getEmail());
        authPage.fillPassword(context.getCurrentUser().getPassword());
    }

    @И("Пользователь авторизован в системе")
    public void authorizeUserUI() {
        authPage.openLoginPage();
        authPage.fillEmail(context.getCurrentUser().getEmail());
        authPage.fillPassword(context.getCurrentUser().getPassword());
        authPage.clickSubmit();
        mainPage.checkUserIsLoggedIn();
    }

    @Когда("Пользователь переходит к созданию объявления")
    public void goToCreateAd() {
        mainPage.clickCreateAd();
    }

    @И("Заполняет форму объявления случайными данными")
    public void fillAdData() {
        createdAdTitle = "Товар " + System.currentTimeMillis();
        adPage.fillTitle(createdAdTitle);
        adPage.fillPrice("1500");
        adPage.fillDescription("Описание тестового товара");
    }

    @И("Нажимает кнопку публикации")
    public void submitAd() {
        adPage.clickSubmit();
    }

    @Тогда("Объявление успешно отображается на странице")
    public void checkAdCreated() {
        mainPage.checkAdIsVisible(createdAdTitle);
    }

    @И("Пользователь создал объявление")
    public void createAdStep() {
        mainPage.clickCreateAd();
        createdAdTitle = "Ред " + System.currentTimeMillis();
        adPage.fillTitle(createdAdTitle);
        adPage.fillPrice("3000");
        adPage.fillDescription("Описание товара");
        adPage.clickSubmit();
        mainPage.checkAdIsVisible(createdAdTitle);
    }

    @Когда("Пользователь редактирует название своего объявления")
    public void editAdTitle() {
        Selenide.$(Selectors.byXpath("//*[contains(text(),'" + createdAdTitle + "')]/ancestor::a | //*[contains(text(),'" + createdAdTitle + "')]")).click();
        Selenide.sleep(1000);

        adPage.clickEdit();
        createdAdTitle = "Обновлен " + System.currentTimeMillis();
        adPage.fillTitle(createdAdTitle);
        adPage.clickSubmit();
    }

    @Тогда("Обновленное название объявления отображается на странице")
    public void checkUpdatedTitle() {
        mainPage.checkAdIsVisible(createdAdTitle);
    }

    @Когда("Пользователь удаляет свое объявление")
    public void deleteAd() {

        Selenide.$(Selectors.byXpath("//*[contains(text(),'" + createdAdTitle + "')]/ancestor::a | //*[contains(text(),'" + createdAdTitle + "')]")).click();
        Selenide.sleep(1000);
        adPage.clickDelete();
    }

    @Тогда("Объявление больше не отображается на странице")
    public void checkAdDeleted() {
        Selenide.open("/");
        Selenide.sleep(1500);

        Selenide.$(Selectors.byXpath("//*[contains(text(),'" + createdAdTitle + "')]")).shouldNotBe(com.codeborne.selenide.Condition.visible);
    }
}