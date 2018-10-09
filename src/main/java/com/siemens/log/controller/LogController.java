package com.siemens.log.controller;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(LogController.class);

    @RequestMapping(value = "/level", method = RequestMethod.GET)
    public String updateLogbackLevel(@RequestParam(value = "level") String level,
                                     @RequestParam(value = "packageName", defaultValue = "") String packageName) throws Exception {
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            if (packageName == null || packageName.isEmpty()) {
                loggerContext.getLogger("root").setLevel(ch.qos.logback.classic.Level.toLevel(level));
                log.debug("--------------------------------------change log level of all ");
                log.info("--------------------------------------change log level of all ");
            } else {
                loggerContext.getLogger(packageName).setLevel(ch.qos.logback.classic.Level.valueOf(level));
                log.debug("--------------------------------------change log level of " + packageName);
                log.info("--------------------------------------change log level of " + packageName);
            }
        } catch (Exception e) {
            log.info("--------------------------------------Change log level failed");
        }
        return "success";
    }
}