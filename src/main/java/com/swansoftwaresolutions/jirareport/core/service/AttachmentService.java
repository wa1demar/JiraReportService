package com.swansoftwaresolutions.jirareport.core.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vladimir Martynyuk
 */
public interface AttachmentService {
    InputStream loadFile(HttpServletResponse response, Long id) throws IOException;
}
