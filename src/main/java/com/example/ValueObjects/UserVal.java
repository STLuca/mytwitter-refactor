package com.example.ValueObjects;

import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class UserVal {

    private Long user_id;
    private String username;
    private String profilePic;
    private Long tweet_count;
    private Long following_count;
    private Long follower_count;
    private Boolean following;
    private Boolean follower;


}
