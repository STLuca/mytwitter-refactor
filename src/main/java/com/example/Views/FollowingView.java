package com.example.Views;

import com.example.ValueObjects.UserVal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "followingview")
@Value
public class FollowingView {

    @Id
    @JsonIgnore
    private Long id;

    private UserVal userVal;
    private Long followed_date;

    @JsonIgnore
    private Long querying_user;
    @JsonIgnore
    private Long followedbyuser;
    @JsonIgnore
    private String followedByUsername;


}