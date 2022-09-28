package api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersUser {
    private String success;
    private Orders[] orders;
    private int total;
    private int totalToday;
    private String message;
}
