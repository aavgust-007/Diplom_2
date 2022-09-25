import static io.restassured.RestAssured.given;

public class OrdersUserClient extends RestUser {
    private static final String ORDER_GET_PATH = "/api/orders";

    public OrdersUser getOrdersWithAutorization(String token) {
        return given()
                .spec(getBaseSpecWitnToken(token))
                .get(ORDER_GET_PATH).body().as(OrdersUser.class)
                ;
    }

    public OrdersUser getOrdersWithoutAutorization() {
        return given()
                .spec(getBaseSpec())
                .get(ORDER_GET_PATH).body().as(OrdersUser.class)
                ;
    }
}
