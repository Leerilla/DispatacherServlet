package com.ohgiraffers.dispatacherserlvet.contorller;

import com.ohgiraffers.dispatacherserlvet.contorller.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestController implements Controller {

    @Override
    public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("message","hello controller");

        return "test";
    }
}
