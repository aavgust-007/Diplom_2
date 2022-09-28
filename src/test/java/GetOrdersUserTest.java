import api.client.OrderClient;
import api.client.OrdersUserClient;
import api.client.UserClient;
import api.model.Order;
import api.model.Orders;
import api.model.OrdersUser;
import api.model.User;
import api.util.OrderGenerator;
import api.util.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GetOrdersUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken = "";
    private OrderClient orderClient;
    private Order order;
    private OrdersUserClient ordersUserClient;

    @Before
    public void setUp() {
        order = OrderGenerator.getOrderCreate();
        orderClient = new OrderClient();
        userClient = new UserClient();
        user = UserGenerator.getUserCreate();
        ordersUserClient = new OrdersUserClient();
    }
    @After
    public void tearDown() {
        if (!accessToken.isEmpty()) {
            userClient.delete(accessToken);
        }
    }
    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void GetOrderWithAuthorizationTest() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        orderClient.create(order, accessToken);
        OrdersUser response2 = ordersUserClient.getOrdersWithAutorization(accessToken);
        Orders[] orders = response2.getOrders();
        boolean actual = orders[0].getName().isEmpty();
        assertFalse("there is no order with authorization", actual);
    }
    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void GetOrderWithouthAuthorizationTest() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        orderClient.create(order, accessToken);
        OrdersUser response2 = ordersUserClient.getOrdersWithoutAutorization();
        assertEquals("Incorrect message", "You should be authorised", response2.getMessage());
    }
}
