package hello.login.web.session;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.stream;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request) {
        Cookie cookie = findCookie(request);

        if (cookie == null) {
            return null;
        }
        return sessionStore.get(cookie.getValue());
    }

    public void expireSession(HttpServletRequest request,HttpServletResponse response) {
        Cookie cookie = findCookie(request);
        if (cookie != null) {
            sessionStore.remove(cookie.getValue());
        }
        cookie = new Cookie(SESSION_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private Cookie findCookie(HttpServletRequest request){
        if(request.getCookies() == null){
            return null;
        }

        return stream(request.getCookies())
                .filter((c) -> c.getName().equals(SESSION_COOKIE_NAME))
                .findAny()
                .orElse(null);
    }

}
