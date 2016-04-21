package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Attachment;

/**
 * @author Vladimir Martynyuk
 */
public interface AttachmentRepository {
    Attachment save(Attachment attachment);
}
