package com.example.Views;

import com.example.ValueObjects.UserVal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "followerview")
@Value
public class FollowerView {

    @Id
    @JsonIgnore
    private Long id;

    private UserVal userVal;
    private Long followed_date;

    @JsonIgnore
    private Long querying_user;
    @JsonIgnore
    private Long followinguser;

}
