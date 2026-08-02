package ru.education.config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;

public class TestConfig {

    public static void setup() {

        Configuration.baseUrl = "https://qa-desk.education-services.ru";
        RestAssured.baseURI = "https://qa-desk.education-services.ru";


        Configuration.browser = "chrome";
        Configuration.timeout = 8000;
        Configuration.headless = false; // Поставь true, если не нужно открывать окно браузера
    }
}
