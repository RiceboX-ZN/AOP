package com.parent.aoptest.controller;

import com.parent.aoptest.annotation.AopAnnotation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @AopAnnotation
    @RequestMapping(value = "testAop",method = RequestMethod.POST)
    public ResponseEntity<String> testAop(@RequestParam(value = "username",required = false) String username,
                                        @RequestParam("password") String password) {

        if (username != null) {
            System.out.println(username);
        }
        if (password != null) {
            System.out.println(password);
        }


        return ResponseEntity.ok(password);
    }

}
