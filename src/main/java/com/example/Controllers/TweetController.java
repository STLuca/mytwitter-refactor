package com.example.Controllers;

import com.example.Config.PrincipalUser;
import com.example.Repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @GetMapping(value = "/home")
    public ModelAndView home(
            Pageable pageable
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        System.out.println("-------------------------------------------------------");
        System.out.println(tweetRepository.getMyFeed(pageable));
        //tweetRepository.getMyFeed(pageable).get
        modelAndView.getModelMap().addAttribute("tweets", tweetRepository.getMyFeed(pageable));
        return modelAndView;
    }

}
