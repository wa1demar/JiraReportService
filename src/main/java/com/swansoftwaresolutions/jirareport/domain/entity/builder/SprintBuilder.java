package com.swansoftwaresolutions.jirareport.domain.entity.builder;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class SprintBuilder {

    private Sprint sprint;

    public SprintBuilder() {
        this.sprint = new Sprint();
    }

    public SprintBuilder id(Long id) {
        this.sprint.setId(id);
        return this;
    }

    public SprintBuilder name(String name) {
        this.sprint.setName(name);
        return this;
    }

    public SprintBuilder state(String state) {
        this.sprint.setState(state);
        return this;
    }

    public SprintBuilder startDate(Date startDate) {
        this.sprint.setStartDate(startDate);
        return this;
    }

    public SprintBuilder endDate(Date endDate) {
        this.sprint.setEndDate(endDate);
        return this;
    }

    public SprintBuilder type(int type) {
        this.sprint.setType(type);
        return this;
    }

    public SprintBuilder showUAT(boolean showUAT) {
        this.sprint.setShowUAT(showUAT);
        return this;
    }

    public SprintBuilder notCountTarget(boolean notCountTarget) {
        this.sprint.setNotCountTarget(notCountTarget);
        return this;
    }

    public SprintBuilder jiraSprint(JiraSprint jiraSprint) {
        this.sprint.setJiraSprint(jiraSprint);
        return this;
    }

    public Sprint build() {
        return this.sprint;
    }
}
