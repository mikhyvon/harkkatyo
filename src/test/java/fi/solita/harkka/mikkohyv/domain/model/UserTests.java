package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TimeService timeService;

    @Test
    public void userName_UsernameIsCorrect_EqualsTrue(){
        UserId userId = userRepository.generateId();
        User newUser = new User(userId, "UusiKayttaja", "Admin", "salasana");
        userRepository.store(newUser);

        User fetchedUser = userRepository.findById(userId);
        assertNotNull(fetchedUser);
        assertEquals(fetchedUser.name(), "UusiKayttaja");
    }

    @Test
    public void userPassword_PasswordWasChanged_True(){
        UserId userId = userRepository.generateId();
        User newUser = new User(userId, "UusiKayttaja", "Admin", "salasana");
        userRepository.store(newUser);

        User fetchedUser = userRepository.findById(userId);
        assertTrue("Password was changed", fetchedUser.updatePassword("salasana", "uusiSalasana"));
    }

    @Test
    public void user_ListAllUsers_True(){
        UserId userId = userRepository.generateId();
        User newUser = new User(userId, "UusiKayttaja", "Admin", "salasana");
        userRepository.store(newUser);

        UserId userId2 = userRepository.generateId();
        User newUser2 = new User(userId2, "UudempiKayttaja", "User", "salasana2");
        userRepository.store(newUser2);

        assertTrue(userRepository.listAll().size() == 2);
    }

    @Test
    public void userLogin_CheckUsernameAndPasswordAndReturnId_EqualsTrue(){
        UserId userId = userRepository.generateId();
        User newUser = new User(userId, "UusiKayttaja", "Admin", "salasana");
        userRepository.store(newUser);

        UserId userId2 = userRepository.generateId();
        User newUser2 = new User(userId2, "UudempiKayttaja", "User", "salasana2");
        userRepository.store(newUser2);

        UserId fetchedUserId = userRepository.checkUsernameAndPassword("UudempiKayttaja", "salasana2");
        assertNotNull(fetchedUserId);
        assertEquals(userId2, fetchedUserId);
    }


}
