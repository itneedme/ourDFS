package com.cq.controller;

import com.cq.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dataNode")
public class DataNodeController {

    @Autowired
    private DataService dataService;

    @RequestMapping(path = "/move", method = RequestMethod.GET)
    public String getMovePage() {
        return "/file/moveFile";
    }

}
