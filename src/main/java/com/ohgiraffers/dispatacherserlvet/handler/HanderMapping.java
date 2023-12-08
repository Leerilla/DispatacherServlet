package com.ohgiraffers.dispatacherserlvet.handler;

import com.ohgiraffers.dispatacherserlvet.contorller.Controller;
import com.ohgiraffers.dispatacherserlvet.contorller.MainController;
import com.ohgiraffers.dispatacherserlvet.contorller.TestController;

import java.util.HashMap;
import java.util.Map;

public class HanderMapping {

    private Map<String, Controller> mappings = new HashMap<>();

    public HanderMapping() {
        mappings.put("/main", new MainController());
        mappings.put("/test", new TestController());
    }

    public Controller getController(String uri){
        return mappings.get(uri);
    }
}
