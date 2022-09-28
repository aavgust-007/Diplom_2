package api.client;

import api.model.User;
import api.model.UserCredentials;
import api.model.UserNotWithEmail;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends api.client.RestUser {
    private static final String USER_PATH_REG = "/api/auth/register";
    private static final String USER_PATH_LOGIN = "/api/auth/login ";
    private static final String USER_UPDATE_DELETE = "api/auth/user";
    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .when()
                .post(USER_PATH_REG)
                .then();
    }
    @Step("Создание пользователя без email")
    public ValidatableResponse createNotWithEmail(UserNotWithEmail user) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .when()
                .post(USER_PATH_REG)
                .then();
    }
    @Step("Логин пользователя")
    public ValidatableResponse login(UserCredentials user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH_LOGIN)
                .then();
    }
    @Step("Удаление пользователя")
    public void delete(String accessToken) {
        given()
                .spec(getBaseSpecWitnToken(accessToken))
                .delete(USER_UPDATE_DELETE)
                .then();
    }
    @Step("Изменение данных пользователя")
    public ValidatableResponse patch(String accessToken) {
        return given()
                .spec(getBaseSpecWitnToken(accessToken))
                .patch(USER_UPDATE_DELETE)
                .then();
    }
}
