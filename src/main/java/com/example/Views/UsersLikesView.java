package com.example.Views;

import com.example.ValueObjects.TweetVal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "userslikesview")
@Value
public class UsersLikesView {

    @Id
    @JsonIgnore
    private Long id;

    private TweetVal tweetVal;
    private Long liked_date;

    @JsonIgnore
    private Long querying_user;

    @JsonIgnore
    @Column(name = "likedby")
    private Long likedBy;

    @JsonIgnore
    private String likedByUsername;
}
