package com.swansoftwaresolutions.jirareport.sheduller.job;

import com.swansoftwaresolutions.jirareport.core.service.TaskService;
import org.joda.time.LocalTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class SynchronizeFullJob implements Job {

    private LocalTime localTime = new LocalTime();

    @Autowired
    TaskService taskService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("SynchronizeFullJob started at " + localTime.toString());

        taskService.startFullSynchronization();

        System.out.println("SynchronizeFullJob finished at " + localTime.toString());
    }
}
