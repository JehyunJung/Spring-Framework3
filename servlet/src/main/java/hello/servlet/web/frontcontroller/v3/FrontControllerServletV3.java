package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap=new HashMap<>();

      public FrontControllerServletV3() {
          controllerMap.put("/front-controller/v3/members/new-form", new
                  MemberFormControllerV3());
          controllerMap.put("/front-controller/v3/members/save", new
                  MemberSaveControllerV3());
          controllerMap.put("/front-controller/v3/members", new
                  MemberListControllerV3());
      }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("FrontControllerServletV3.service");
        
        String requestUrl=request.getRequestURI();
        System.out.println("requestUrl = " + requestUrl);

        ControllerV3 controller=controllerMap.get(requestUrl);

        if(controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> paramMap=createParamMap(request,response);

        ModelView modelView=controller.process(paramMap);
        MyView myView=viewResolver(modelView.getViewName());

        myView.render(modelView.getModel(),request,response);

    }

    private Map<String,String> createParamMap(HttpServletRequest request,HttpServletResponse response){
        Map<String, String> paramMap=new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(
                paramName-> paramMap.put(paramName,request.getParameter(paramName))
        );
        return paramMap;
    }
    private MyView viewResolver(String viewName){
          return new MyView("/WEB-INF/views/"+viewName+".jsp");
    }


}
