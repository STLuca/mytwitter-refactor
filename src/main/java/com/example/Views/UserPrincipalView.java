package com.example.Views;

import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "userprincipalview")
@Value
public class UserPrincipalView {

    @Id
    private Long id;
    private String username;
    private String password;
    private String profilePic;
    private Long notifications;

}
