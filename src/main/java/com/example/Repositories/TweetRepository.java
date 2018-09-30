package com.example.Repositories;

import com.example.Entities.Tweet;
import com.example.Views.FeedView;
import com.example.Views.TweetView;
import com.example.Views.UsersLikesView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RepositoryRestResource
public interface TweetRepository extends PagingAndSortingRepository<Tweet, Long> {

    @Override
    @PreAuthorize("#tweet?.user?.id == principal?.id")
    Tweet save(@Param("tweet") Tweet tweet);

    @Override
    @PreAuthorize("#tweet?.user?.id == principal?.id")
    void delete(@Param("tweet") Tweet tweet);

    @Query("SELECT t FROM TweetView t WHERE t.tweetVal.tweet_id = :tweetID AND t.querying_user = ?#{principal.id}")
    TweetView getTweet(
            @Param("tweetID") Long tweetID
    );

    @Query("SELECT t FROM TweetView t WHERE t.tweetVal.userid = :userID AND t.querying_user = ?#{principal.id}")
    Page<TweetView> getUsersTweets(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT t FROM UsersLikesView t WHERE t.likedBy = :userID AND t.querying_user = ?#{principal.id}")
    Page<UsersLikesView> getUsersLikes(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT t FROM FeedView t WHERE t.followed_by = :userID AND t.querying_user = ?#{principal.id}")
    Page<FeedView> getFeed(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT t FROM FeedView t WHERE t.followed_by = ?#{principal.id} AND t.querying_user = ?#{principal.id}")
    Page<FeedView> getMyFeed(
            Pageable pageable
    );

}
