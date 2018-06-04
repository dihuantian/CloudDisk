package com.cloud.disk.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/20
 * Time: 23:58
 */
@Controller
public class MainSiteErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @GetMapping(value = ERROR_PATH)
    public String handleError() {
        return "404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
