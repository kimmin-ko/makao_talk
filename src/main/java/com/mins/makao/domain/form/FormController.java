package com.mins.makao.domain.form;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping("/favicon.ico")
    public void favicon() {
    }

    @GetMapping("/home")
    public String homeForm() {
        return "/home";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/login";
    }

    @GetMapping("/chat")
    public String chatForm() {
        return "/chat";
    }

    @GetMapping("/chatRoom")
    public String chatRoomForm() {
        return "/chatRoom";
    }

    @GetMapping("/find")
    public String findForm() {
        return "/find";
    }

    @GetMapping("/more")
    public String moreForm() {
        return "/more";
    }

    @GetMapping("/setting")
    public String settingForm() {
        return "/setting";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "/join";
    }

}