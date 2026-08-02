package ru.education.utils;

import ru.education.models.UserDto;

public class DataGenerator {

    public static UserDto getRandomUser() {
        String randomEmail = "test_user_" + System.currentTimeMillis() + "@gmail.com";
        String randomPassword = "Password123!";
        return new UserDto(randomEmail, randomPassword);
    }
}
