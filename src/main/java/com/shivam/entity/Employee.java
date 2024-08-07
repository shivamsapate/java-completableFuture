package  com.shivam.entity;

import lombok.Data;

@Data
public class Employee {
    private String gender;
    private String lastName;
    private long id;
    private String firstName;
    private String email;
    private boolean newJoiner;
    private boolean learningPending;
}
