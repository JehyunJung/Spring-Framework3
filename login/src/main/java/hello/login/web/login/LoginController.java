package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.websocket.Session;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j

public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    /*@PostMapping("/login")
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
    }*/
    /*@PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member member=loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}", member);

        if(member == null){
            bindingResult.reject("loginError", "로그인이 실패하였습니다.");
            return "login/loginForm";
        }
        sessionManager.createSession(member, response);

        return "redirect:/";
    }*/
    /*@PostMapping("/login}")
    public String loginV3(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member member=loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}", member);

        if(member == null){
            bindingResult.reject("loginError", "로그인이 실패하였습니다.");
            return "login/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return "redirect:/";
    }*/
    @PostMapping("/login")
    public String loginV4(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request, @RequestParam(defaultValue="/") String redirectURL) {
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member member=loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}", member);

        if(member == null){
            bindingResult.reject("loginError", "로그인이 실패하였습니다.");
            return "login/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        log.info("Redirect URL: {}", redirectURL);
        return "redirect:"+redirectURL;
    }

    /*@PostMapping("logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }*/
    /*@PostMapping("logout")
    public String logoutV2(HttpServletRequest request,HttpServletResponse response){
        sessionManager.expireSession(request,response);
        return "redirect:/";
    }*/
    @PostMapping("logout")
    public String logoutV3(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response,String cookieName){
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
