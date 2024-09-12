package org.example.springv3.board;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

//JPArEPOSITORY를 상속한 모든 애들은 @DataJPAtest 라고 붙이면 메모리에 다 뜬다.
//
@DataJpaTest
public class BoardRepositoryTest {


    @Autowired
    private BoardRepository boardRepository;

    // toString test
    @Test
    public void mFindAllV2_test() throws JsonProcessingException {
        // given
        String title = "제목";

        // when
        Pageable pageable = PageRequest.of(0, 3);
        Page<Board> boardPG = boardRepository.mFindAll(title, pageable);

        // eye
        System.out.println(boardPG.getContent());
    }

    @Test
    public void mFindAll_test() throws JsonProcessingException {
        // given
        String title = "제목";

        // when
        Pageable pageable = PageRequest.of(0, 3);
        Page<Board> boardPG = boardRepository.mFindAll(title, pageable);

        // 안에 어떻게 생겼는지 궁금할 때 뿌려보는 가장 좋은 방법은 json으로 뿌려보는 거
        // eye
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(boardPG);
        //om.readValue(responseBody, Board.class); // 바꾸고 싶은 객체를 뒤에 넣어준다. -> json을 객체로 바꿔주면 확인할 수 있다. 검색해보기
        System.out.println(responseBody);
    }


//    @Test
//public void mFindAll_test(){
//        //given
//        String title = "제";
//
//        //when
//        List<Board> boardList = boardRepository.mFindAll(title);
//
//        //eye
//        System.out.println(boardList.size());
//        System.out.println(boardList.get(0).getTitle());
//        System.out.println(boardList.get(1).getTitle());
//        System.out.println(boardList.get(2).getTitle());
//        System.out.println(boardList.get(3).getTitle());
//        System.out.println(boardList.get(4).getTitle());
//    }


    @Test
    public void mFindByIdWithReply_test(){
        //given
        int id = 4;

        //when
        Board board = boardRepository.mFindByIdWithReply(5).get();
        System.out.println(board);


    }

}
