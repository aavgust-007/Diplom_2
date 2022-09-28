import api.client.UserClient;
import api.model.User;
import api.model.UserCredentials;
import api.model.UserNotWithEmail;
import api.util.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;

public class UserLoginTest {
    private User user;
    private UserNotWithEmail userNotWithEmail;
    private UserClient userClient;
    private String accessToken = "";
    private UserCredentials userCredentials;

    @Before
    public void setUp() {
        user = UserGenerator.getUserCreate();
        userCredentials = UserGenerator.getUserLogin();
        userNotWithEmail = UserGenerator.getUserNotWithEmailCreate();
        userClient = new UserClient();
    }
    @After
    public void tearDown() {
        if (!accessToken.isEmpty()) {userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void userLoginTest() {
        userClient.create(user);
        ValidatableResponse response = userClient.login(userCredentials);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_OK, statusCode);
        boolean actual = response.extract().path("success");
        assertTrue("user is not login", actual);
        accessToken = response.extract().path("accessToken");
        assertNotEquals("accessToken is null"
                , "", accessToken);
    }
    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void AuthorizationErrorUnderNonexistentUserTest() {
        ValidatableResponse response = userClient.login(userCredentials);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_UNAUTHORIZED, statusCode);
        boolean isCreated = response.extract().path("success");
        assertFalse("the user was created by mistake", isCreated);
    }
}
