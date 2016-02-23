package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class CommentDto implements Serializable {

    public Long id;
    public Long reportId;
    public String text;
    public String creator;
    public Date createdDate;
}
