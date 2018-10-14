package com.example.Entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Follows")
@EntityListeners(AuditingEntityListener.class)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long followID;

    @CreatedDate
    private Long createdDate;

    @ManyToOne
    private User follower;

    @ManyToOne
    private User followee;

    public Follow(Long followID, User follower, User followee) {
        this.followID = followID;
        this.follower = follower;
        this.followee = followee;
    }
}
