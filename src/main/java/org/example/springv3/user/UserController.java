package org.example.springv3.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springv3.core.util.Resp;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final HttpSession session;
    private final UserService userService;

    // http://localhost:8080/user/samecheck?username=hello
    @GetMapping("/user/samecheck")
    public ResponseEntity<?> sameCheck(@RequestParam("username") String username) {
        boolean isSameUsername = userService.유저네임중복되었니(username); // isSameUsername? 유저네임중복체크 이런식으로 매서드 이름짓지 말기
        // true는 중복된거, false는 중복되지 않은거 // 팀원들이랑 정해야함
        return ResponseEntity.ok(Resp.ok(isSameUsername, isSameUsername ? "중복되었어요" : "중복되지않았어요")); // front가 true받으면 중복된거, false받으면 중복안된거
    }


    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO loginDTO, Errors errors) {
        User sessionUser = userService.로그인(loginDTO);
        System.out.println("12312323123213" + sessionUser.getUsername());
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO joinDTO, Errors errors) {
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }
}
