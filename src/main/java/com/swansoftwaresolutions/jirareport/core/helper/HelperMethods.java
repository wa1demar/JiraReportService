package com.swansoftwaresolutions.jirareport.core.helper;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_issue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.dashboard.Chart;
import com.swansoftwaresolutions.jirareport.core.dto.dashboard.SprintProjectReportDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.IssueDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraIssueDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Vitaliy Holovko
 */


public class HelperMethods {

    public boolean isWeekend(Date date1) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);

        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public boolean isCurrentDay(String text) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Date date = format.parse(text);
            return date.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }



    public  boolean isSameDate(Date date, Date anotherDate) {
        if(date==null && anotherDate==null){
            return true;
        }
        else if(date==null || anotherDate==null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTime(anotherDate);
        anotherCalendar.set(Calendar.HOUR_OF_DAY, 0);
        anotherCalendar.set(Calendar.MINUTE, 0);
        anotherCalendar.set(Calendar.SECOND, 0);
        anotherCalendar.set(Calendar.MILLISECOND, 0);
        anotherCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar.compareTo(anotherCalendar) == 0;
    }

    public Long isNull(Long hours) {
        if (hours == null){
            return 0L;
        }
        return hours;
    }

    public int isNullDoubleToInt(Double targetHours) {
        if (targetHours!=null){
            return targetHours.intValue();
        }
        return 0;
    }

    public float isNullFloat(float target) {
        Float object = target;
        if (object !=null){
            return target;
        }
        return 0;
    }

    public Chart generateReportChart(List<SprintProjectReportDto> sprints) {
        Chart chart = new Chart();

        String date = "0,";

        float targetPoints = 0;
        for (SprintProjectReportDto sprintProjectReportDto : sprints){
            targetPoints+=sprintProjectReportDto.getTargetPoints();
        }

        int[] actual = new int[sprints.size()+1];
        double[] target = new double[sprints.size()+1];

        List<Integer> list = new ArrayList<>();
        list.add((int) targetPoints);

        actual[0] = (int) targetPoints;
        target[0] = (int) targetPoints;

        int i = 1;
        int j = 1;
        for (SprintProjectReportDto sprint : sprints) {
            if (!sprint.isNotCountTarget()){
                actual[j] = actual[j - 1] - (int) sprint.getActualPoints();
                list.add(actual[j - 1] - (int) sprint.getActualPoints());
                j++;
            }
            target[i] = (targetPoints - targetPoints / (sprints.size()) * i);
            date = date+i+",";
            i++;
        }
        String[] dateArray = date.split(",");

        chart.setLabel(dateArray);
        int[] array = new int[list.size()];
        for (int k = 0; k < list.size(); k++) array[k] = list.get(k);

        chart.setActual(array);
        chart.setTarget(target);

        return chart;
    }

    public Chart getChatData(List<IssuesByDayDto> issuesByDayList, float targetPoint) {
        Chart chart = new Chart();

        HelperMethods helperMethods = new HelperMethods();

        String date = "0,";
        for (IssuesByDayDto issuesByDayDto : issuesByDayList) {
            date += issuesByDayDto.getDate() + ",";
        }

        String[] dateArray = date.split(",");
        chart.setLabel(dateArray);
        int[] actual = new int[dateArray.length];
        double[] target = new double[dateArray.length];

        actual[0] = (int) targetPoint;
        target[0] = (int) targetPoint;

        List<Integer> ii = new ArrayList<>();
        ii.add(actual[0]);


        for (int i = 1; i < dateArray.length; i++) {
            actual[i] =actual[i-1];

            if (helperMethods.isCurrentDay(dateArray[i])) {
                for (IssuesByDayDto issuesDto : issuesByDayList) {
                    if (issuesDto.getDate().equals(dateArray[i])) {
                        for (SprintIssueDto sprintIssueDto : issuesDto.getIssues()) {
                            actual[i] = actual[i] - sprintIssueDto.getPoint();
                        }
                    }
                }

                    ii.add(actual[i]);
            } else {
                ii.add(actual[i]);
            }

            target[i] = (targetPoint-targetPoint/(dateArray.length-1)*i);
        }

        int[] array = new int[ii.size()];
        for (int i = 0; i < ii.size(); i++) array[i] = ii.get(i);

        chart.setActual(array);
        chart.setTarget(target);

        return chart;
    }


    public JiraIssueDto convertIssueDtoToJiraIssueDto(IssueDto issueDto, long boardId) {
        HelperMethods helperMethods = new HelperMethods();

        JiraIssueDto jiraIssueDto = new JiraIssueDto();

        jiraIssueDto.setKey(issueDto.key);
        jiraIssueDto.setProjectId(issueDto.fields.getProject().getId());
        jiraIssueDto.setProjectKey(issueDto.fields.getProject().getKey());
        jiraIssueDto.setIssueTypeId(issueDto.fields.getIssuetype().id);
        jiraIssueDto.setIssueTypeName(issueDto.fields.getIssuetype().name);
        jiraIssueDto.setIssueTypeSubTask(issueDto.fields.getIssuetype().subtask);
        jiraIssueDto.setTimeSpent(helperMethods.isNull(issueDto.fields.getTimespent()));
        if (issueDto.fields.getResolution() != null) {
            jiraIssueDto.setResolutionId(issueDto.fields.getResolution().id);
            jiraIssueDto.setResolutionName(issueDto.fields.getResolution().name);
        } else {
            jiraIssueDto.setResolutionId(0);
            jiraIssueDto.setResolutionName("");
        }
        jiraIssueDto.setCreated(issueDto.fields.getCreated());
        jiraIssueDto.setUpdated(issueDto.fields.getUpdated());
        jiraIssueDto.setAssignedKey(issueDto.fields.getAssignee().key);
        jiraIssueDto.setAssignedName(issueDto.fields.getAssignee().name);
        jiraIssueDto.setAssignedFullName(issueDto.fields.getAssignee().displayName);
        jiraIssueDto.setCreatorKey(issueDto.fields.getCreator().key);
        jiraIssueDto.setCreatorName(issueDto.fields.getCreator().name);
        jiraIssueDto.setCreatorFullName(issueDto.fields.getCreator().displayName);
        jiraIssueDto.setStatusId(issueDto.fields.getStatus().id);
        jiraIssueDto.setStatusName(issueDto.fields.getStatus().name);
        jiraIssueDto.setDueDate(issueDto.fields.getDuedate());
        jiraIssueDto.setPoints(issueDto.fields.getCustomfield_10102());
        jiraIssueDto.setBoardId(boardId);
        jiraIssueDto.setDescription(issueDto.fields.getDescription());
        jiraIssueDto.setSummary(issueDto.fields.getSummary());
        return jiraIssueDto;
    }
}
