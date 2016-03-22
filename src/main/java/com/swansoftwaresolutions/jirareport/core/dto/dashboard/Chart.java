package com.swansoftwaresolutions.jirareport.core.dto.dashboard;

/**
 * @author Vitaliy Holovko
 */
public class Chart {
    private String[] label;
    private int[] target;
    private int[] actual;

    public Chart() {
    }

    public Chart(String[] label, int[] target, int[] actual) {
        this.label = label;
        this.target = target;
        this.actual = actual;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public int[] getTarget() {
        return target;
    }

    public void setTarget(int[] target) {
        this.target = target;
    }

    public int[] getActual() {
        return actual;
    }

    public void setActual(int[] actual) {
        this.actual = actual;
    }
}
