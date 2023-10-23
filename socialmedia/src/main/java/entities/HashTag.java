package entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
public class HashTag {

    @Id
    @GeneratedValue
    private Long id;

    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

}
