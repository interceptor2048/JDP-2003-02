package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUsers() {

        //Given
        User user1 = User.builder().id(null).username("tomasz-kowlaski").status("blocked").userKey(11L).build();
        User user2 = User.builder().id(null).username("joanna-nowak").status("unblocked").userKey(11L).build();
        User user3 = User.builder().id(null).username("michał-wróbel").status("unblocked").userKey(11L).build();

        // When
        userRepository.save(user1);
        Long user1Id = user1.getId();
        userRepository.save(user2);
        Long user2Id = user2.getId();
        userRepository.save(user3);
        Long user3Id = user3.getId();

        // Then
        Optional<User> actualUser1 = userRepository.findById(user1Id);
        Assert.assertTrue(actualUser1.isPresent());
        Optional<User> actualUser2 = userRepository.findById(user2Id);
        Assert.assertTrue(actualUser2.isPresent());
        Optional<User> actualUser3 = userRepository.findById(user3Id);
        Assert.assertTrue(actualUser3.isPresent());

        //CleanUp
        try {
            userRepository.deleteById(user1Id);
            userRepository.deleteById(user2Id);
            userRepository.deleteById(user3Id);
        } catch (Exception e) {
            //do nothing
        }
    }

    @Test
    public void testUpdateStatusOfUser() {
        //Given
        User user1 = User.builder().id(null).username("tomasz-kowlaski").status("blocked").userKey(11L).build();
        User user2 = User.builder().id(null).username("joanna-nowak").status("unblocked").userKey(11L).build();
        User user3 = User.builder().id(null).username("michał-wróbel").status("unblocked").userKey(11L).build();

        // When
        userRepository.save(user1);
        Long user1Id = user1.getId();
        userRepository.save(user2);
        Long user2Id = user2.getId();
        userRepository.save(user3);
        Long user3Id = user3.getId();


        //Then
        Optional<User> findUser = userRepository.findById(user2Id);
        if (findUser.isPresent()) {
            findUser.get().setStatus("blocked");
            User updateUser = userRepository.save(findUser.get());
            Assert.assertEquals("blocked", updateUser.getStatus());
        }

        //CleanUp
        try {
            userRepository.deleteById(user1Id);
            userRepository.deleteById(user2Id);
            userRepository.deleteById(user3Id);
        } catch (Exception e) {
            //do nothing
        }
    }
}
