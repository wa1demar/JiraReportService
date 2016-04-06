package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.rest.service.DueDateImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class IssueImporterServiceImpl implements DueDateImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Override
    public void importDueDateFromJira() {

    }
}
