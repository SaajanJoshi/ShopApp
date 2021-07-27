package com.store.storeapp.controller;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("")
    public HttpEntity home(){
        JSONObject obj = new JSONObject();
        obj.put("Application","shop");
        obj.put("Version","v1.0");
        return new ResponseEntity(obj, HttpStatus.OK);
    }
}
