package ru.education.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.education.models.UserDto;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    public Response registerUser(UserDto user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/signup");
    }
}
