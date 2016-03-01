package com.swansoftwaresolutions.jirareport.sheduller.job;

import org.joda.time.LocalTime;
import org.quartz.*;

/**
 * @author Vladimir Martynyuk
 */

public class LoadProjectsJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalTime localTime = new LocalTime();

        System.out.println("JOB RUNNING - " + localTime.toString());
    }
}