package com.ohgiraffers.dispatacherserlvet;

import com.ohgiraffers.dispatacherserlvet.contorller.Controller;
import com.ohgiraffers.dispatacherserlvet.handler.HanderMapping;
import com.ohgiraffers.dispatacherserlvet.handler.ViewResolver;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/")
public class DispatcherServlet extends HttpServlet {

    private HanderMapping handerMapping;
    private ViewResolver viewResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 리소스 공급
        handerMapping = new HanderMapping();
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
        String path = request.getRequestURI().substring(request.getContextPath().length());
        // 컨트롤러를 찾아줌
        Controller controller = handerMapping.getController(path);

        if(controller != null){
            //컨트롤러 수행 이후 view를 반환함
            String page = controller.handlerRequest(request,response);

            // 뷰가 있는지 확인함
            String root = viewResolver.getView(page);

            // 뷰가 없으면 에러
            if(root.equals("")|| root == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            request.getRequestDispatcher(root).forward(request,response);
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
