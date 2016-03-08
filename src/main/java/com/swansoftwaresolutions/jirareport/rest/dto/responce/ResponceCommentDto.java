package com.swansoftwaresolutions.jirareport.rest.dto.responce;

import java.io.Serializable;

/**
 * @author Vitaliy Holovko
 */

public class ResponceCommentDto implements Serializable{

    Boolean success;
    String  message;

    public ResponceCommentDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
