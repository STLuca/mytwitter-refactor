package com.example.Controllers;

import com.example.Repositories.TweetRepository;
import com.example.Repositories.UserRepository;
import com.example.Views.*;
import com.example.util.PageLink;
import com.example.util.PageLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @GetMapping("/user/{username}/tweets")
    public String user(
            @PathVariable String username,
            Pageable pageable,
            Model model
    ) {
        Page<TweetView> tweets = tweetRepository.getUsersTweetsByUsername(username, pageable);
        model.addAttribute("user", userRepository.getUserByUsername(username));
        model.addAttribute("tweets", tweets);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(tweets.getNumber(), tweets.getTotalPages()));
        return "users/user";
    }

    @GetMapping("/user/{username}/likes")
    public String userLikes(
            @PathVariable String username,
            Pageable pageable,
            Model model
    ) {
        Page<UsersLikesView> tweets = tweetRepository.getUsersLikesByUsername(username, pageable);
        model.addAttribute("tweets", tweets);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(tweets.getNumber(), tweets.getTotalPages()));
        return "tweets/userLikes";
    }

    @GetMapping("/user/search")
    public String searchForUser(
        @RequestParam String username,
        Pageable pageable,
        Model model
    ) {
        Page<UserView> users = userRepository.searchByUsername(username, pageable);
        model.addAttribute("username", username);
        model.addAttribute("users", users);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(users.getNumber(), users.getTotalPages()));
        return "users/userSearch";
    }

    @GetMapping("/user/{username}/following")
    public String userFollowing(
            @PathVariable String username,
            Pageable pageable,
            Model model
    ) {
        Page<FollowingView> users = userRepository.getFollowingUsersByUsername(username, pageable);
        model.addAttribute("users", users);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(users.getNumber(), users.getTotalPages()));
        return "users/following";
    }

    @GetMapping("/user/{username}/followers")
    public String userFollowers(
            @PathVariable String username,
            Pageable pageable,
            Model model
    ) {
        Page<FollowerView> users = userRepository.getFollowersByUsername(username, pageable);
        model.addAttribute("users", users);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(users.getNumber(), users.getTotalPages()));
        return "users/followers";
    }

    @GetMapping("/notifications")
    public String notifications(
            Pageable pageable,
            Model model
    ) {
        Page<NotificationView> notifications = userRepository.getMyNotifications(pageable);
        model.addAttribute("notifications", notifications);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(notifications.getNumber(), notifications.getTotalPages()));
        return "users/notifications";
    }
}
