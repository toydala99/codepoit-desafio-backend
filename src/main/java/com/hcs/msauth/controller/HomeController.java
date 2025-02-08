package com.hcs.msauth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping(value = "/")
@RestController
public class HomeController {

    @CrossOrigin
    @GetMapping()
    public String home(){
        return "<p>Benvindo a EcoTrack</p> <a href='https://toydala99.github.io/kanal.codepoint.desafio/'>FrontEnd Teste<a/>";
    }
}
