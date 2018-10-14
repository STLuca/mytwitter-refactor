package com.example.Entities;

import com.example.ValueObjects.TweetMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Tweets")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TweetMessage message;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinTable(name = "Replies", joinColumns = @JoinColumn(name = "CHILD_ID"),
            inverseJoinColumns = @JoinColumn(name = "PARENT_ID"))
    private Tweet repliedTo;

    @ElementCollection
    @CollectionTable(
            name = "TAGS",
            joinColumns = @JoinColumn(name = "TWEET_ID"))
    @Column(name = "TAG_TEXT")
    protected Set<String> tags = new HashSet<>();

    @ManyToMany
    private Set<User> mentions = new HashSet<>();

    @CreatedDate
    private Long createdDate;

}
