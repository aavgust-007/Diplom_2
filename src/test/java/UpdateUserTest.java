import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;

public class UpdateUserTest {

    private User user;
    private UserClient userClient;
    private String accessToken = "";

    private UserCredentials userCredentials;

    @Before
    public void setUp() {
        user = UserGenerator.getUserCreate();
        userCredentials = UserGenerator.getUserLogin();
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        if (!accessToken.isEmpty()) {
            userClient.delete(user, accessToken);
        }
    }

    @Test
    public void userUpdateTest() {
        ValidatableResponse response2 = userClient.create(user);
        accessToken = response2.extract().path("accessToken");
        ValidatableResponse response = userClient.patch(userCredentials, accessToken);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_OK, statusCode);
        boolean isCreated = response.extract().path("success");
        assertTrue("user is not created", isCreated);
    }

    @Test
    public void userUpdateTestUnauthorized() {
        ValidatableResponse response = userClient.patch(userCredentials, accessToken);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_UNAUTHORIZED, statusCode);
        boolean isCreated = response.extract().path("success");
        assertFalse("user is created", isCreated);
        String message = response.extract().path("message");
        assertEquals("Incorrect message", message, "You should be authorised");
    }
}
