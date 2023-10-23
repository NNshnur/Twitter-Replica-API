package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private Timestamp joined;

    private boolean deleted;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;






}
