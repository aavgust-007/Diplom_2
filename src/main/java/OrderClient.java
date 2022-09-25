import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestUser {
    private static final String ORDER_PATH = "/api/orders";
    private static final String GET_ORDERS = "/api/ingredients";

    public ValidatableResponse createOrderWithoutAuthorization(Order order) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    public ValidatableResponse create(Order order, String token) {
        return given()
                .spec(getBaseSpecWitnToken(token))
                .and()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    public ValidatableResponse createEmptyBody(Order order, String token) {
        return given()
                .spec(getBaseSpecWitnToken(token))
                .and()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    public OrdersUser getIngredients() {
        return given()
                .spec(getBaseSpec())
                .get(GET_ORDERS).body().as(OrdersUser.class)
              ;
    }
}
