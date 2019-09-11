package com.cubic.root.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageApi {
    @GetMapping(value="/")
    public String home()
    {
        return "index";
    }
    @GetMapping(value={"/login.html","login_page.html","login_page.jsp"})
    public String loginPage()
    {
        return "login_page";
    }
}
