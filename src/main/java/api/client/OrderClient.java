package api.client;
import api.model.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends api.client.RestUser {
    private static final String ORDER_PATH = "/api/orders";

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuthorization(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    @Step("Создание заказа с авторизации")
    public ValidatableResponse create(Order order, String token) {
        return given()
                .spec(getBaseSpecWitnToken(token))
                .and()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    @Step("Создание заказа с пустым телом")
    public ValidatableResponse createEmptyBody(Order order, String token) {
        return given()
                .spec(getBaseSpecWitnToken(token))
                .and()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
}
