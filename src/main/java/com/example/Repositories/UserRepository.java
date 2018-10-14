package com.example.Repositories;

import com.example.Views.*;
import com.example.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Override
    @PreAuthorize("isAnonymous() or #user?.id == principal?.id")
    //authentication can equal null and updates wont work since updates require being authenticated through spring security
    User save(@Param("user") User user);

    @Override
    @PreAuthorize("#user?.id == principal?.id")
    void delete(@Param("user") User user);

    @RestResource(exported = false)
    @Query("SELECT u FROM UserPrincipalView u WHERE u.username = :username")
    UserPrincipalView findUserPrincipal(@Param("username") String username);

    @Query("SELECT u FROM UserView u WHERE u.userVal.user.id = :userID AND u.queryingUser = ?#{principal.id}")
    UserView getUser(
            @Param("userID") Long userID
    );

    @Query("SELECT u FROM UserView u WHERE u.userVal.username = :username AND u.queryingUser = ?#{principal.id}")
    UserView getUserByUsername(
            @Param("username") String username
    );

    @Query("SELECT u FROM FollowingView u WHERE u.followedbyuser = :userID AND u.querying_user = ?#{principal.id}")
    Page<FollowingView> getFollowingUsers(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT u FROM FollowingView u WHERE u.followedByUsername = :username AND u.querying_user = ?#{principal.id}")
    Page<FollowingView> getFollowingUsersByUsername(
            @Param("username") String username,
            Pageable pageable
    );

    @Query("SELECT u FROM FollowerView u WHERE u.followinguser = :userID AND u.querying_user = ?#{principal.id}")
    Page<FollowerView> getFollowers(
            @Param("userID") Long userID,
            Pageable pageable
    );

    @Query("SELECT u FROM FollowerView u WHERE u.followingUsername = :username AND u.querying_user = ?#{principal.id}")
    Page<FollowerView> getFollowersByUsername(
            @Param("username") String username,
            Pageable pageable
    );

    @Query("SELECT u FROM TweetLikesView u WHERE u.likedtweet = :tweetID AND u.querying_user = ?#{principal.id}")
    Page<TweetLikesView> getTweetLikes(
            @Param("tweetID") Long tweetID,
            Pageable pageable
    );

    @Query("SELECT u FROM UserView u WHERE u.userVal.username LIKE CONCAT('%',:username,'%') AND u.queryingUser = ?#{principal.id}")
    Page<UserView> searchByUsername(
            @Param("username") String username,
            Pageable pageable
    );

    @Query("SELECT n FROM NotificationView n WHERE n.recieverId = ?#{principal.id} ORDER BY n.notificationTimestamp DESC")
    Page<NotificationView> getMyNotifications(
            Pageable pageable
    );

}
