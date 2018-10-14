package com.example.ValueObjects;

import com.example.Entities.Tweet;
import com.example.Entities.User;
import com.example.util.ControllerUrlBuilder;
import com.example.util.Mention;
import com.example.util.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Embeddable
@Value
public class TweetVal {

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Tweet tweet;

    private String message;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

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

    @Column(name = "mentions", columnDefinition = "text[]")
    @Type(type = "com.vladmihalcea.hibernate.type.array.StringArrayType")
    private String[] mentions;

    //THESE PROVIDE LINKS FOR THYMELEAF TEMPLATES

    @JsonIgnore
    public Map<String, String> getLinks() {
        return ControllerUrlBuilder.tweetLinks(tweet.getId(), username);
    }

    @JsonIgnore
    public List<Mention> getMentionsList(){

        if (mentions == null) {
            return new ArrayList<>();
        }

        return Arrays.stream(mentions)
                .map(mention -> new Mention(mention))
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Tag> getTagsList(){

        if (tags == null) {
            return new ArrayList<>();
        }

        return Arrays.stream(tags)
                .map(tag -> new Tag(tag))
                .collect(Collectors.toList());
    }
}
