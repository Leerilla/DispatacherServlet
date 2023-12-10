package com.ohgiraffers.dispatacherserlvet.handler;

import java.util.HashMap;
import java.util.Map;

public class ViewResolver {

    private final String prefix = "/WEB-INF/";
    private final String suffix = ".jsp";

    public Map<String,String> getView(String viewName){

        Map<String, String> view = new HashMap<>();
        if(viewName.matches(".*redirect:.*")){
            view.put("redirect", viewName.substring("redirect:".length()));
        }else{
            view.put("forward", prefix+viewName+suffix);
        }

        return view;
    }
}
