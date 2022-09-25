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
            userClient.delete(user, accessToken);
        }
    }

    @Test
    public void CreateOrderWithAuthorizationTest() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        orderClient.create(order, accessToken);
        OrdersUser response2 = ordersUserClient.getOrdersWithAutorization(accessToken);
        Orders[] orders = response2.getOrders();
        boolean actual = orders[0].getName().isEmpty();
        System.out.println(orders[0].getName());
        assertFalse("there is no order with authorization", actual);

    }

    @Test
    public void CreateOrderWithouthAuthorizationTest() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        orderClient.create(order, accessToken);
        OrdersUser response2 = ordersUserClient.getOrdersWithoutAutorization();
        assertEquals("Incorrect message", "You should be authorised", response2.getMessage());

    }
}
