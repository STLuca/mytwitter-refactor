package com.example.Entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Likes")
@EntityListeners(AuditingEntityListener.class)
public class Like {

    @Id
    @GeneratedValue
    private Long likeID;

    @CreatedDate
    private Long createdDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Tweet tweet;

}
