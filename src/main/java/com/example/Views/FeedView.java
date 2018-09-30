package com.example.Views;

import com.example.ValueObjects.TweetVal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
@Table(name = "feedview")
@Value
public class FeedView {

    @Id
    @JsonIgnore
    private Long id;

    private TweetVal tweetVal;

    @JsonIgnore
    private Long querying_user;

    @JsonIgnore
    private Long followed_by;

}