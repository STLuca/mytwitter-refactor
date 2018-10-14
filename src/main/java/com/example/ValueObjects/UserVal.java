package com.example.ValueObjects;

import com.example.Entities.User;
import com.example.util.ControllerUrlBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Map;

@Embeddable
@Value
public class UserVal {

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String username;
    private String profilePic;
    private Long tweet_count;
    private Long following_count;
    private Long follower_count;
    private Long like_count;
    private Boolean following;
    private Boolean follower;

    //THESE PROVIDE LINKS FOR THYMELEAF TEMPLATES

    @JsonIgnore
    public Map<String, String> getLinks() {
        return ControllerUrlBuilder.userLinks(username);
    }

}
