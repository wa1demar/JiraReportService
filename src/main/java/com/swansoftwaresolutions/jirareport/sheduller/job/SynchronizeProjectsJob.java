package com.swansoftwaresolutions.jirareport.sheduller.job;

import com.swansoftwaresolutions.jirareport.core.service.TaskService;
import org.joda.time.LocalTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Vladimir Martynyuk
 */
public class SynchronizeProjectsJob implements Job {

    private LocalTime localTime = new LocalTime();

    @Autowired
    TaskService taskService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("SynchronizeProjectsJob started at " + localTime.toString());

        taskService.start("projects");

        System.out.println("SynchronizeProjectsJob finished at " + localTime.toString());
    }
}
