package api.client;

import api.model.OrdersUser;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class OrdersUserClient extends RestUser {
    private static final String ORDER_GET_PATH = "/api/orders";
    @Step("Получение заказа с авторизацией")
    public OrdersUser getOrdersWithAutorization(String token) {
        return given().spec(getBaseSpecWitnToken(token)).
                get(ORDER_GET_PATH).body().
                as(OrdersUser.class);
    }
    @Step("Получение заказа без авторизации")
    public OrdersUser getOrdersWithoutAutorization() {
        return given().spec(getBaseSpec()).
                get(ORDER_GET_PATH).body().
                as(OrdersUser.class);
    }
}
