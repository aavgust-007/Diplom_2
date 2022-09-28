package api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotWithEmail {
    private String name;
    private String password;
}
