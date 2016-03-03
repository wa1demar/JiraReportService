package com.swansoftwaresolutions.jirareport.sheduller.dto;

import java.util.Date;

/**
 * @author Vitaliy Holovko
 */

public class SprintDto {
    public int id;
    public String state;
    public String name;
    public Date startDate;
    public Date endDate;
    public Date completeDate;
    public int originBoardId;
}
