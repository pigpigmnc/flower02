package com.zsc.flower.utils.alarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class Schedule {


    @Autowired
    private RestTemplate restTemplate;

//    @Scheduled(fixedRate = 2000000)
    @Scheduled(cron = "0 0 14 * * ?")
    public void checkIsAlarm() throws IOException {

    }
}
