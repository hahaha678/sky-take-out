//package com.sky.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class MyTask {
//
//    private boolean test = true;
//
//    @Scheduled(cron = "* * * * * ? ")
//    public void executeTask(){
//        if(test){
//            log.info("-----定时任务执行0:{}",new Date());
//            test = false;
//        }else {
//            log.info("*****定时任务执行1:{}",new Date());
//            test = true;
//        }
//    }
//}
