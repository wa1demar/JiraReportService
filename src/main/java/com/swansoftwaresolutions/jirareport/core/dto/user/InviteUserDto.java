package com.swansoftwaresolutions.jirareport.core.dto.user;

/**
 * @author Vladimir Martynyuk
 */
public class InviteUserDto {

//    @Unique(value = User.class, property = "login")
    private String inviteParam;

    public String getInviteParam() {
        return inviteParam;
    }

    public void setInviteParam(String inviteParam) {
        this.inviteParam = inviteParam;
    }
}
