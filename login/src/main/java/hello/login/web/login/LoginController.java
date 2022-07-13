package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j

public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member member=loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}", member);

        if(member == null){
            bindingResult.reject("loginError", "로그인이 실패하였습니다.");
            return "login/loginForm";
        }
        Cookie cookie = new Cookie("memberId",String.valueOf(member.getId()));
        response.addCookie(cookie);

        return "redirect:/";
    }

    @PostMapping("logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response,String cookieName){
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
