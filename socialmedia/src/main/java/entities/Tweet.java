package entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    private int author;

    private Timestamp posted;

    private boolean deleted;

    private String content;

    private Integer replyTo;

    private Integer repostOf;




}
