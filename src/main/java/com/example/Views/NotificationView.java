package com.example.Views;

import com.example.Entities.Tweet;
import com.example.util.ControllerUrlBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Map;

@Entity
@Immutable
@Table(name = "notificationsview")
@Value
public class NotificationView {

    @Id
    @JsonIgnore
    private Long id;

    private String username;
    private String picture;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Tweet tweet;

    private Long notificationTimestamp;
    private String notificationType;

    @JsonIgnore
    private Long recieverId;

    @JsonIgnore
    public Map<String, String> getLinks() {
        return ControllerUrlBuilder.notificationLinks(username, tweet.getId());
    }
}
