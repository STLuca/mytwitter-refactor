package com.example.Controllers;

import com.example.Config.PrincipalUser;
import com.example.Repositories.TweetRepository;
import com.example.Repositories.UserRepository;
import com.example.Views.FeedView;
import com.example.Views.TweetLikesView;
import com.example.Views.TweetTagView;
import com.example.util.PageLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(
            Pageable pageable,
            Model model
    ) {
        Page<FeedView> feed = tweetRepository.getMyFeed(pageable);
        model.addAttribute("tweets", feed);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(feed.getNumber(), feed.getTotalPages()));
        return "home";
    }

    @GetMapping("/tweet/{tweetID}")
    public String tweet(
            @PathVariable Long tweetID,
            Model model
    ) {
        model.addAttribute("tweets", tweetRepository.getTweetTree(tweetID));
        // maybe change list to some sort of tree
        return "tweets/tweet";
    }

    @GetMapping("/tweet/{tweetID}/likes")
    public String tweetLikes(
            @PathVariable Long tweetID,
            Pageable pageable,
            Model model
    ) {
        Page<TweetLikesView> tweets = userRepository.getTweetLikes(tweetID, pageable);
        model.addAttribute("users", tweets);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(tweets.getNumber(), tweets.getTotalPages()));
        return "users/tweetLikes";
    }

    @GetMapping("/tweet/search")
    public String searchForTweetByTag(
            @RequestParam String tag,
            Pageable pageable,
            Model model
    ) {
        Page<TweetTagView> tweets = tweetRepository.getByTag(tag, pageable);
        model.addAttribute("tag", tag);
        model.addAttribute("tweets", tweets);
        model.addAttribute("pageLinks", PageLinkService.getPageLinks(tweets.getNumber(), tweets.getTotalPages()));
        return "tweets/tagSearch";
    }

    /*
    @GetMapping("/user/search")
    public String searchForUser(
        @RequestParam String username,
        Pageable pageable,
        Model model
    ) {
        model.addAttribute("users", userRepository.searchByUsername(username, pageable));
        return "users/userSearch";
    }
     */


}
