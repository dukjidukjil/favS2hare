package com.favshare.user.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile")
public class UserProfileControllerTest {

    @ApiOperation(value="", notes = "")
    @GetMapping
    public ResponseEntity ex1() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
