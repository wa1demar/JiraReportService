package com.swansoftwaresolutions.jirareport.core.dto.jira_issue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class DueDatesDto {
    private int page;
    private int totalPages;
    private int totalItems;
    private int itemsPerPage = 10;

    List<DueDateDto> dates = new ArrayList<>();


    public List<DueDateDto> getDates() {
        return dates;
    }

    public void setDates(List<DueDateDto> dates) {
        this.dates = dates;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
}
