package com.ohgiraffers.dispatacherserlvet.handler;

public class ViewResolver {

    private final String prefix = "/WEB-INF/";
    private final String suffix = ".jsp";

    public String getView(String viewName){
        return prefix + viewName + suffix;
    }
}
