package jzt.erp.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestBootController {

    //http://localhost:8080/bbb
    @RequestMapping("bbb")
    public String bbb() {

        return "bbbccc";
    }
}
