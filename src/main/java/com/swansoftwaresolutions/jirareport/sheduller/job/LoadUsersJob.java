package com.swansoftwaresolutions.jirareport.sheduller.job;

import com.swansoftwaresolutions.jirareport.sheduller.rest.client.UsersRestClient;
import org.joda.time.LocalTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Vitaliy Holovko
 */
public class LoadUsersJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalTime localTime = new LocalTime();

        System.out.println("USER JOB - " + localTime.toString());

        UsersRestClient client = new UsersRestClient();
        client.getComments();
    }
}
