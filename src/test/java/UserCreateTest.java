import api.client.UserClient;
import api.model.User;
import api.model.UserNotWithEmail;
import api.util.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class UserCreateTest {
    private User user;
    private UserNotWithEmail userNotWithEmail;
    private UserClient userClient;
    private String accessToken = "";

    @Before
    public void setUp() {
        user = UserGenerator.getUserCreate();
        userNotWithEmail = UserGenerator.getUserNotWithEmailCreate();
        userClient = new UserClient();
    }
    @After
    public void tearDown() {
        if (!accessToken.isEmpty()) {
            userClient.delete(accessToken);
        }
    }
    @Test
    @DisplayName("Создание уникального пользователя")
    public void userCanBeCreatedTest() {
        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_OK, statusCode);
        boolean isCreated = response.extract().path("success");
        assertTrue("user is not created", isCreated);
        accessToken = response.extract().path("accessToken");
        assertNotEquals("accessToken is null"
                , "", accessToken);
    }
    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void errorWhenCreatingDublicateUserTest() {
        ValidatableResponse response = userClient.create(user);
        ValidatableResponse response2 = userClient.create(user);
        int statusCode = response2.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_FORBIDDEN, statusCode);
        boolean isCreated = response2.extract().path("success");
        assertFalse("duplicate user created ", isCreated);
        accessToken = response.extract().path("accessToken");
    }
    @Test
    @DisplayName("Создание пользователя без обязательных полей")
    public void userNotWithEmail() {
        ValidatableResponse response = userClient.createNotWithEmail(userNotWithEmail);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_FORBIDDEN, statusCode);
        boolean isCreated = response.extract().path("success");
        assertFalse("created a user without an email", isCreated);
    }
}
