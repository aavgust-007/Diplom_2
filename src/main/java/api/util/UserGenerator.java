package api.util;

import api.model.User;
import api.model.UserCredentials;
import api.model.UserNotWithEmail;

public class UserGenerator {
    public static User getUserCreate() {
        return new User("hfgjfgjh", "pasghrfytsfwrd", "q1Vfdrffdkgyeisd@mil.ru");
    }
    public static UserNotWithEmail getUserNotWithEmailCreate() {
        return new UserNotWithEmail("teffst", "pafssword");
    }
    public static UserCredentials getUserLogin() {
        return new UserCredentials("q1Vfdrffdkgyeisd@mil.ru", "pasghrfytsfwrd");
    }
}
