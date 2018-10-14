package com.example.Views;

import com.example.ValueObjects.TweetVal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "tweettagview")
@Value
public class TweetTagView {

    @Id
    @JsonIgnore
    private Long id;

    private TweetVal tweetVal;

    @JsonIgnore
    private Long querying_user;

    @JsonIgnore
    private String tag;


}
