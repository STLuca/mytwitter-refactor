package com.example.Repositories;

import com.example.Entities.Tweet;
import com.example.Views.*;
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
    @PreAuthorize("#tweet.html?.user?.id == principal?.id")
    Tweet save(@Param("tweet.html") Tweet tweet);

    @Override
    @PreAuthorize("#tweet.html?.user?.id == principal?.id")
    void delete(@Param("tweet.html") Tweet tweet);

    @Query("SELECT t FROM TweetView t WHERE t.tweetVal.tweet.id = :tweetID AND t.querying_user = ?#{principal.id}")
    TweetView getTweet(
            @Param("tweetID") Long tweetID
    );

    @Query("SELECT t FROM TweetView t WHERE t.tweetVal.user.id = :userID AND t.querying_user = ?#{principal.id}")
    Page<TweetView> getUsersTweets(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT t FROM TweetView t WHERE t.tweetVal.username = :username AND t.querying_user = ?#{principal.id}")
    Page<TweetView> getUsersTweetsByUsername(
            @Param("username") String username,
            Pageable pageable
    );

    @Query("SELECT t FROM UsersLikesView t WHERE t.likedBy = :userID AND t.querying_user = ?#{principal.id}")
    Page<UsersLikesView> getUsersLikes(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT t FROM UsersLikesView t WHERE t.likedByUsername = :username AND t.querying_user = ?#{principal.id}")
    Page<UsersLikesView> getUsersLikesByUsername(
            @Param("username") String username,
            Pageable pageable
    );

    @Query("SELECT t FROM FeedView t WHERE t.followed_by = :userID AND t.querying_user = ?#{principal.id}")
    Page<FeedView> getFeed(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT t FROM FeedView t WHERE t.followed_by = ?#{principal.id} AND t.querying_user = ?#{principal.id} ORDER BY t.tweetVal.tweet_timestamp DESC")
    Page<FeedView> getMyFeed(
            Pageable pageable
    );

    @Query("SELECT t FROM TweetTree t WHERE t.treeof = :tweetID AND t.querying_user = ?#{principal.id} ORDER BY t.id")
    List<TweetTree> getTweetTree(
            @Param("tweetID") Long tweetID
    );

    @Query("SELECT t FROM TweetTagView t WHERE t.tag = :tag AND t.querying_user = ?#{principal.id}")
    Page<TweetTagView> getByTag(
            @Param("tag") String tag,
            Pageable pageable
    );

}
