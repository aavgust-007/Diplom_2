import api.client.OrderClient;
import api.client.UserClient;
import api.model.Order;
import api.model.User;
import api.util.OrderGenerator;
import api.util.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class CreateOrderTest {
    private Order order;
    private Order order2;
    private Order order3;
    private User user;
    private OrderClient orderClient;
    private UserClient userClient;
    private String accessToken = "";

    @Before
    public void setUp() {
        order = OrderGenerator.getOrderCreate();
        order2 = OrderGenerator.getEmptyOrderCreate();
        order3 = OrderGenerator.getIncorrectCacheOrderCreate();
        orderClient = new OrderClient();
        userClient = new UserClient();
        user = UserGenerator.getUserCreate();
    }
    @After
    public void tearDown() {
        if (!accessToken.isEmpty()) {
            userClient.delete(accessToken);
        }
    }
    @Test
    @DisplayName("Создание заказа авторизованного пользователя")
    public void CreateOrderWithAuthorizationTest() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse response2 = orderClient.create(order, accessToken);
        int statusCode = response2.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_OK, statusCode);
        boolean isCreated = response.extract().path("success");
        assertTrue("failed to create an order with authorization", isCreated);
    }
    @Test
    @DisplayName("Создание заказа неавторизованного пользователя")
    public void createOrderWithoutAuthorizationTest() {
        ValidatableResponse response = orderClient.createOrderWithoutAuthorization(order);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_OK, statusCode);
        boolean isCreated = response.extract().path("success");
        assertTrue("failed to create an order without  authorization", isCreated);
    }
    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void CreateOrderIncorrectCacheTest() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse response2 = orderClient.create(order3, accessToken);
        int statusCode = response2.extract().statusCode();
        assertEquals("failed to create an order with an invalid hash", SC_INTERNAL_SERVER_ERROR, statusCode);
    }
    @Test
    @DisplayName("Создание заказа авторизованного пользователя без ингредиентов")
    public void CreateOrderWithoutIngredientsAndAuthorizationTest() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse response2 = orderClient.createEmptyBody(order2, accessToken);
        int statusCode = response2.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_BAD_REQUEST, statusCode);
        boolean isCreated = response2.extract().path("success");
        assertFalse("managed to create an order without ingredients, but with authorization", isCreated);
    }
    @Test
    @DisplayName("Создание заказа неавторизованного пользователя без ингредиентов")
    public void CreateOrderWithoutIngredientsAndWithoutAuthorizationTest() {
        ValidatableResponse response = orderClient.createOrderWithoutAuthorization(order2);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_BAD_REQUEST, statusCode);
        boolean isCreated = response.extract().path("success");
        assertFalse("it was possible to create an order without ingredients and without authorization", isCreated);
    }
}
