package com.example.util;

import lombok.Value;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import java.util.List;

@Value
public class PageLink {

    int pageNumber;
    String link;
    boolean active;


}
