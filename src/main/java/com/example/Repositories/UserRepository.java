package com.example.Repositories;

import com.example.Views.FollowerView;
import com.example.Views.FollowingView;
import com.example.Views.TweetLikesView;
import com.example.Views.UserView;
import com.example.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Override
        //@PreAuthorize("#user?.auth == authentication?.name")
    User save(@Param("user") User user);

    @Override
    @PreAuthorize("#user?.id == principal?.id")
    void delete(@Param("user") User user);

    User findByUsername(String username);

    @Query("SELECT u FROM UserView u WHERE u.user_id = :userID AND u.queryingUser = ?#{principal.id}")
    UserView getUser(
            @Param("userID") Long userID
    );

    @Query("SELECT u FROM FollowingView u WHERE u.followedbyuser = :userID AND u.querying_user = ?#{principal.id}")
    Page<FollowingView> getFollowingUsers(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT u FROM FollowerView u WHERE u.followinguser = :userID AND u.querying_user = ?#{principal.id}")
    Page<FollowerView> getFollowers(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT u FROM TweetLikesView u WHERE u.likedtweet = :tweetID AND u.querying_user = ?#{principal.id}")
    Page<TweetLikesView> getTweetLikes(
            @Param("tweetID") Long tweetID,
            Pageable pageable
    );

}
