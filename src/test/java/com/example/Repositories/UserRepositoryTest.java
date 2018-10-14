package com.example.Repositories;

import com.example.Application;
import com.example.Entities.Follow;
import com.example.Entities.User;
import com.example.ValueObjects.UserVal;
import com.example.testConfig.TestUserDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestUserDetailsService.class, EvaluationContextExtension.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    FollowRepository followRepository;

    User bob;
    User sally;

    @Before
    public void createTestData() {
        bob = userRepository.save(new User(new Long(10), "bob", "password", "picture", new Long(0)));
        sally = userRepository.save(new User(new Long(20), "sally", "password2", "picture2", new Long(0)));

        Follow bobToSally = new Follow(new Long(30), bob, sally);
        followRepository.save(bobToSally);

    }

    @Test
    public void testStarts() {

    }

    @Test
    @WithUserDetails("bob")
    public void testGetUser() {
        UserVal expected = new UserVal(bob, bob.getUsername(), bob.getProfilePic(), new Long(0), new Long(1), new Long(0), new Long(0), false, false);
        UserVal actual = userRepository.getUser(bob.getId()).getUserVal();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFollowingQuery(){

    }

}