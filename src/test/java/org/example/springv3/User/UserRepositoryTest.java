package org.example.springv3.User;

import org.example.springv3.user.User;
import org.example.springv3.user.UserQueryRepository;
import org.example.springv3.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@Import({UserQueryRepository.class})
public class UserRepositoryTest {

    // test는 생성자 주입안된다. 그래서 다 autowired 걸어야 한다.
    @Autowired
    private UserRepository userRepository;


    @Test
    public void go(){
        User user = null;
        Optional<User> op = Optional.ofNullable(user);

//        User userPS = op.get();
//        System.out.println(userPS.getId()); // 1

//        if(op.isPresent()){
//            User userPS = op.get();
//        }else{
//            throw new RuntimeException("존재하지 않아요");
//        } // 2

//        User u = User.builder().id(1).username("ssar").build();
//        User u2 = op.orElse(u);
//        System.out.println(u2.getUsername()); // 3

        User u = op.orElseThrow(() -> new RuntimeException("fdsafdsafdsa")); // 4

    }

}
