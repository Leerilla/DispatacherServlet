package com.ohgiraffers.dispatacherserlvet;

import com.ohgiraffers.dispatacherserlvet.contorller.Controller;
import com.ohgiraffers.dispatacherserlvet.handler.HandlerMapping;
import com.ohgiraffers.dispatacherserlvet.handler.ViewResolver;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@WebServlet(value = "/")
public class DispatcherServlet extends HttpServlet {

    private HandlerMapping handerMapping;
    private ViewResolver viewResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // Spring에서는 ApplicationContext에서 타입과 일치하는 빈을 주입하는 방식으로 작성되어 있다. 참고
        handerMapping = new HandlerMapping();
        viewResolver = new ViewResolver();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        String method = httpRequest.getMethod();

        // 요청 메서드에 따른 처리 분기
        if ("GET".equals(method)) {
            doGet(httpRequest,httpResponse);

        } else if ("POST".equals(method)) {
            doPost(httpRequest, httpResponse);

        } else if ("PUT".equals(method)) {
            doPut(httpRequest, httpResponse);

        } else if ("DELETE".equals(method)) {
            doDelete(httpRequest,httpResponse);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // 요청 리소스에서 루트만 분리함
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // 컨트롤러를 찾아줌
        /* handler Mapping */
        Controller controller = handerMapping.getController(path);


        /* view resolver */
        // controller가 존재하는 경우
        if(controller != null){
            //컨트롤러 수행 이후 view를 반환함
            String page = controller.handlerRequest(request,response);

            // view의 경로를 획득함
            Map<String,String> root = viewResolver.getView(page);

            // redirect 요청시
            if(root.containsKey("redirect")){
                response.sendRedirect(root.get("redirect"));
            }else{ // 일반 요청시
                // webapp에 파일이 존재하는지 확인
                ServletContext servletContext = request.getServletContext();
                URL resource = servletContext.getResource(root.get("forward"));

                // 뷰가 없으면 에러
                if(resource == null){
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

                request.getRequestDispatcher(root.get("forward")).forward(request,response);
            }

        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
