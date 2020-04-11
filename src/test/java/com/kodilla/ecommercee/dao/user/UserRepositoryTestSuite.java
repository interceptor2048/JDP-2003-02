package com.kodilla.ecommercee.dao.user;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.UserRepository;
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
        User user1 = new User(1L, "tomasz-kowlaski", 0, 11L);
        User user2 = new User(2L, "joanna-nowak", 1, 21L);
        User user3 = new User(3L, "michał-wróbel", 2, 31L);

        // When
        userRepository.save(user1);
        Long user1Id = user1.getId();
        userRepository.save(user2);
        Long user2Id = user2.getId();
        userRepository.save(user3);
        Long user3Id = user3.getId();

        // Then
        Assert.assertEquals(1L, user1Id.longValue());
        Assert.assertEquals(2L, user2Id.longValue());
        Assert.assertEquals(3L, user3Id.longValue());

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
        User user1 = new User(1L, "tomasz-kowlaski", 0, 11L);
        User user2 = new User(2L, "joanna-nowak", 1, 21L);
        User user3 = new User(3L, "michał-wróbel", 2, 31L);

        // When
        userRepository.save(user1);
        Long user1Id = user1.getId();
        userRepository.save(user2);
        Long user2Id = user2.getId();
        userRepository.save(user3);
        Long user3Id = user3.getId();




        //Then
        Optional<User> findUser = userRepository.findById(user2Id);
       if( findUser.isPresent()){
        findUser.get().setStatus(2);
        User updateUser = userRepository.save(findUser.get());
        Assert.assertEquals(2, updateUser.getStatus());}

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
