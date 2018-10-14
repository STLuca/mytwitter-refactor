package com.example.util;

import java.util.HashMap;
import java.util.Map;

public class ControllerUrlBuilder {

    public static String userUrl(String username){
        return "/user/" + username + "/tweets";
    }

    private static String userFollowing(String username) {
        return "/user/" + username + "/following";
    }

    private static String userFollowers(String username) {
        return "/user/" + username + "/followers";
    }

    private static String userLikes(String username) {
        return "/user/" + username + "/likes";
    }

    private static String tweetUrl(Long tweetId) {
        return "/tweet/" + tweetId;
    }

    private static String tweetLikes(Long tweetId) {
        return "/tweet/" + tweetId + "/likes";
    }

    public static String searchTag(String tag) {
        return "/tweet/search?tag=" + tag;
    }

    public static Map<String, String> tweetLinks(Long tweetId, String username) {
        Map<String, String> linkMap = new HashMap<>();
        linkMap.put("tweet", tweetUrl(tweetId));
        linkMap.put("likes", tweetLikes(tweetId));
        linkMap.put("user", userUrl(username));
        return linkMap;
    }

    public static Map<String, String> userLinks(String username) {
        Map<String, String> linkMap = new HashMap<>();
        linkMap.put("user", userUrl(username));
        linkMap.put("following", userFollowing(username));
        linkMap.put("followers", userFollowers(username));
        linkMap.put("likes", userLikes(username));
        return linkMap;
    }

    public static Map<String, String> notificationLinks(String username, Long tweetId) {
        Map<String, String> linkMap = new HashMap<>();
        linkMap.put("tweet", tweetUrl(tweetId));
        linkMap.put("user", userUrl(username));
        return linkMap;
    }


}
