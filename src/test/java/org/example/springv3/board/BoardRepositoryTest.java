package org.example.springv3.board;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

//JPArEPOSITORY를 상속한 모든 애들은 @DataJPAtest 라고 붙이면 메모리에 다 뜬다.
//
@DataJpaTest
public class BoardRepositoryTest {


    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void mFindByIdWithReply_test(){
        //given
        int id = 4;

        //when
        Board board = boardRepository.mFindByIdWithReply(5).get();
        System.out.println(board);


    }

}
