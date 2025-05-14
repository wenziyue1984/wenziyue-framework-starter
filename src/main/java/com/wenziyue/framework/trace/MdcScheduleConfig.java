//package com.wenziyue.framework.trace;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//
///**
// * @author wenziyue
// */
//@Configuration
//public class MdcScheduleConfig implements SchedulingConfigurer {
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar registrar) {
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
//        registrar.setScheduler(scheduler);
//    }
//}
