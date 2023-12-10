package com.ohgiraffers.dispatacherserlvet.handler;

import com.ohgiraffers.dispatacherserlvet.contorller.Controller;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.lang.reflect.InvocationTargetException;

public class HandlerMapping {

    public Controller getController(String uri){

        Object controller= null;
        String type = null;

        if(uri.equals("/test")){
            type = "TestController";
        } else if (uri.equals("/main")) {
            type="MainController";
        } else if (uri.equals("/redirect")) {
            type="RedirectController";
        }

        try(ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.ohgiraffers.dispatacherserlvet").scan()){
            ClassInfoList classInfos = scanResult.getClassesImplementing("com.ohgiraffers.dispatacherserlvet.contorller.Controller");
            for (Class<?> implClass:classInfos.loadClasses()) {
                if(implClass.getName().equals("com.ohgiraffers.dispatacherserlvet.contorller."+type)){
                    controller = implClass.getDeclaredConstructor().newInstance();
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return (Controller) controller;
    }

}
