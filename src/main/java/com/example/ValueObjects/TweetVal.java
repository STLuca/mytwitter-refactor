package com.example.ValueObjects;

import lombok.Value;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Value
public class TweetVal {

    private Long tweet_id;
    private String message;
    private Long userid;
    private String username;
    private String profilePic;
    private Long tweet_timestamp;
    private Long likecount;
    private Long replycount;
    private Long replyto;
    private Boolean liked;

    @Column(name = "tags", columnDefinition = "text[]")
    @Type(type = "com.vladmihalcea.hibernate.type.array.StringArrayType")
    private String[] tags;

}
