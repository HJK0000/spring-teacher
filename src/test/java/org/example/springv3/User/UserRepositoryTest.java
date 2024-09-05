package org.example.springv3.User;

import org.example.springv3.user.User;
import org.example.springv3.user.UserQueryRepository;
import org.example.springv3.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({UserQueryRepository.class})
public class UserRepositoryTest {

    // test는 생성자 주입안된다. 그래서 다 autowired 걸어야 한다.
    @Autowired
    private UserRepository userRepository;


}
