package com.hcs.msauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/")
@RestController
public class HomeController {

    @GetMapping()
    public String home(){
        return "<p>Benvindo a EcoTrack</p> <a href='https://toydala99.github.io/kanal.codepoint.desafio/'>FrontEnd Teste<a/>";
    }
}
