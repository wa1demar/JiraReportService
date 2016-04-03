package com.swansoftwaresolutions.jirareport.domain.model;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class Paged {

    private int page;
    private int total;
    private List list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
