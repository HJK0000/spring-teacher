package org.example.springv3.reply;

import lombok.RequiredArgsConstructor;
import org.example.springv3.core.error.ex.ExceptionApi403;
import org.example.springv3.core.error.ex.ExceptionApi404;
import org.example.springv3.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true) // 트랜젝션 고립성때문에 생성해줌
@RequiredArgsConstructor
@Service // Component 스캔을 위해 IoC에 띄우기
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public void 댓글삭제(int id, User sessionUser){
        // 삭제하기 전에 조회먼저. 없으면 throw
        Reply replyPS = replyRepository.findById(id).orElseThrow(
                () -> new ExceptionApi404("댓글을 찾을 수 없습니다.")
        );

        // 권한체크
        // boardId랑 UserId는 reply가 처음 select할때 가져왔으니까 여기서 또 select 안한다.
        // 만약 username이 필요한 경우에는 mFindById를 만들어서 조인해서 사용하면 select 한번만 치니까 속도에서 개선이 된다.
        if(replyPS.getUser().getId() != sessionUser.getId()){
            throw new ExceptionApi403("댓글 삭제 권한이 없습니다.");
        }
        //
        replyRepository.deleteById(id);
}
}
