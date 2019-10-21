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
    @GetMapping(value={"/login"})
    public String loginPage()
    {
        return "login";
    }
    @GetMapping(value={"/upload_plug"})
    public String uploadplug()
    {
        return "upload_plug";
    }
}
