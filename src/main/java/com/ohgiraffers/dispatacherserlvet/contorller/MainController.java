package com.ohgiraffers.dispatacherserlvet.contorller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class MainController implements Controller{

    @Override
    public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("message", "main message");
        return "main";
    }
}
