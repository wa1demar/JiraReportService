package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.ImportedBardsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraBoardMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraBoardRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.rest.service.BoardImporterService;
import com.swansoftwaresolutions.jirareport.core.dto.ImportedJiraBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class BoardImporterServiceImpl implements BoardImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    JiraBoardRepository jiraBoardRepository;

    @Autowired
    JiraBoardMapper jiraBoardMapper;


    @Override
    public void importBoardsFromJira() {

        List<JiraProject> projects = projectRepository.findAll();

        for (JiraProject project : projects) {
            ImportedBardsDto importedBardsDto = restClient.loadAllBoardsByProjectKey(project.getKey());

            List<ImportedJiraBoardDto> bordDtos = importedBardsDto.getValues();
            if (bordDtos.size() > 0) {
                jiraBoardRepository.add(jiraBoardMapper.fromImportedDtos(bordDtos), project.getKey());
            }
        }


    }
}
