package com.swansoftwaresolutions.jirareport.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class JiraBoardsDto implements Serializable {

    private List<JiraBoardDto> boards;

    public JiraBoardsDto() {
    }

    public JiraBoardsDto(List<JiraBoardDto> boards) {
        this.boards = boards;
    }

    public List<JiraBoardDto> getBoards() {
        return boards;
    }

    public void setBoards(List<JiraBoardDto> boards) {
        this.boards = boards;
    }
}
