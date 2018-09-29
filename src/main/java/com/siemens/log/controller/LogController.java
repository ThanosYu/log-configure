package com.siemens.log.controller;

import ch.qos.logback.classic.LoggerContext;
import com.siemens.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Thanos Yu
 * @date 9/28/2018 1:56 PM
 */
@RestController
@RequestMapping("/v1/logConfigure")
public class LogController {
    @Autowired
    private LogService logService;

    private Logger log = LoggerFactory.getLogger(LogController.class);

    @RequestMapping(value = "/level",method=RequestMethod.GET)
    public String updateLogbackLevel(@RequestParam(value = "level") String level,
                                     @RequestParam(value = "packageName", defaultValue = "") String packageName) throws Exception {
        System.out.println("--------------------------------------level: " + level);
        System.out.println("--------------------------------------packageName: " + packageName);

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (packageName == null || packageName.isEmpty()) {
            System.out.println("--------------------all");
            loggerContext.getLogger("root").setLevel(ch.qos.logback.classic.Level.toLevel(level));
        } else {
            loggerContext.getLogger(packageName).setLevel(ch.qos.logback.classic.Level.valueOf(level));
            System.out.println("--------------------part");
        }

        log.debug("--------------------------------------debug: ");
        log.info("--------------------------------------info: ");
        log.error("--------------------------------------error: ");
        logService.pringLog();
        return "success";
    }
}