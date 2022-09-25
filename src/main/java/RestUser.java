import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RestUser {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    public RequestSpecification getBaseSpec() {

        return new RequestSpecBuilder().setBaseUri(BASE_URL).
                setContentType("application/json").
                build();
    }

    public RequestSpecification getBaseSpecWitnToken(String accessToken) {
        return new RequestSpecBuilder().setBaseUri(BASE_URL).
                setContentType("application/json").
                addHeader("Authorization", accessToken).
                build();

    }
}