package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.CommentDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Comment;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class CommentDtoMapper extends PropertyMap<Comment, CommentDto> {
    @Override
    protected void configure() {
        map().setCreator(source.getUser().getUsername());
        map().setCreatorDisplayName(source.getUser().getFullName());
    }
}
