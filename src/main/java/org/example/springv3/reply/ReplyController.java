package org.example.springv3.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.springv3.core.error.ex.ExceptionApi403;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final HttpSession session;
    private final ReplyService replyService;

    @PostMapping("/api/reply")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO saveDTO) {
        // 2개를 받을꺼니까 requestDTO 만들기 : ) BoardId, comment 를 detail.mustache에서 받는다.
        System.out.println(1);
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println(2);
        ReplyResponse.DTO replyDTO = replyService.댓글쓰기(saveDTO, sessionUser);
        System.out.println(7);
        return ResponseEntity.ok(Resp.ok(replyDTO));
    }

    @DeleteMapping("/api/reply/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        // 1. 인증 체크 (세션)
        // 이거 인터셉터(api)로 뺏으니까 주소에 api만 추가해주면 됨
        
        // 2. 서비스 호출 -> 댓글 삭제
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(id, sessionUser);
        // 3. 응답

        // static에 있는 거니 new 안하고 바로 써도 된다!!!!!!
        return ResponseEntity.ok(Resp.ok(null)); //무조건 resp로 응답해야 한다!!!
        //throw new ExceptionApi403("권한이 없습니다"); // 이거 throw 해봐
    }
}
