package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


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
    public void userName_UsernameIsCorrect_True(){
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
    public void userLogin_CheckIfUsernameAndPasswordExists_True(){
        UserId userId = userRepository.generateId();
        User newUser = new User(userId, "UusiKayttaja", "Admin", "salasana");
        userRepository.store(newUser);
        //TODO No assertation yet, needs infra service
    }

}
