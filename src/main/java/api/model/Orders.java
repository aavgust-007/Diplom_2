package api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private String _id;
    private String[] ingredients;
    private String status;
    private String name;
    private int number;
    private Date createdAt;
    private Date updatedAt;
}
