package org.example.springv3.board;

import lombok.RequiredArgsConstructor;
import org.example.springv3.core.error.ex.Exception400;
import org.example.springv3.core.error.ex.Exception403;
import org.example.springv3.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.example.springv3.core.error.ex.Exception404;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    public BoardResponse.PageDTO 게시글목록보기(String title, int page) {

        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        if(title == null){
            Page<Board> boardList = boardRepository.findAll(pageable); // findAll안에 pageable을 넣을 수 있게 되어있음
            // pageable 객체가order by b.id desc 도 해줘서 레파지
            return new BoardResponse.PageDTO(boardList);
        }else{
            Page<Board> boardList = boardRepository.mFindAll(title, pageable); // 동적쿼리 아님
            return new BoardResponse.PageDTO(boardList);
        }

    }



//    public List<Board> 게시글목록보기() {
//        //Pageable pg = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
//        Sort sort = Sort.by(Sort.Direction.DESC, "id"); // 거꾸로 정렬
//        List<Board> boardList = boardRepository.findAll(sort);
//        return boardList;
//    }


    @Transactional
    public void 게시글삭제하기(Integer id, User sessionUser) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new Exception404("게시글을 찾을 수 없습니다"));

        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("작성자가 아닙니다.");
        }

        boardRepository.deleteById(id);
    }



    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO saveDTO, User sessionUser) {

        Board boardEntity = saveDTO.toEntity(sessionUser);
        boardRepository.save(boardEntity);
    }

    public Board 게시글수정화면(int id, User sessionUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new Exception404("게시글을 찾을 수 없습니다"));

        // 아래 코드 보다 위의 코드를 쓰는게 더 깔끔하다. 두줄만에 끝나는 코드!
/*        Optional<Board> boardOP = boardRepository.findById(1);
        if(boardOP.isEmpty()){
            throw new Exception404("게시글을 찾을 수 없습니다");
        }

        Board board = boardOP.get();*/

        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글 수정 권한이 없습니다.");
        }
        return board;
    }

    public BoardResponse.DTO 게시글수정화면V2(int id, User sessionUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new Exception404("게시글을 찾을 수 없습니다"));

        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글 수정 권한이 없습니다.");
        }
        return new BoardResponse.DTO(board);
    }

    @Transactional
    public void 게시글수정(int id, BoardRequest.UpdateDTO updateDTO, User sessionUser) {
        // 1. 게시글 조회 (없으면 404)
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new Exception404("게시글을 찾을 수 없습니다"));

        // 2. 권한체크
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }
        // 3. 게시글 수정
        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());

    }

    
    public BoardResponse.DetailDTO 게시글상세보기(User sessionUser, Integer boardId){
        Board boardPS = boardRepository.mFindByIdWithReply(boardId)
                .orElseThrow(() -> new Exception404("게시글이 없습니다."));

        return new BoardResponse.DetailDTO(boardPS,sessionUser);
    }

    public Board 게시글상세보기V3(User sessionUser, Integer boardId){
        Board boardPS = boardRepository.mFindByIdWithReply(boardId)
                .orElseThrow(() -> new Exception404("게시글이 없습니다."));
        return boardPS;
    }
}
