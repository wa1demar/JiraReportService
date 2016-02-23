package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ReportDataDto implements Serializable {

    public ReportDto report;

    public Long percentPoints;
    public Long percentHours;

    public List<Double> chartLabel;
    public List<Double> chartTarget;
    public List<Double> chartActual;

    public Long showUat;
    public Long closedSprintCount;
}
