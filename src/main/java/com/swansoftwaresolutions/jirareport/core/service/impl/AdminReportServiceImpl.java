package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.AdminReportMapper;
import com.swansoftwaresolutions.jirareport.core.service.AdminReportService;
import com.swansoftwaresolutions.jirareport.domain.repository.AdminReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.adminreport.AdminReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
@Service
public class AdminReportServiceImpl implements AdminReportService{

    @Autowired
    AdminReportRepository adminReportRepository;

    @Autowired
    AdminReportMapper adminReportMapper;

    @Override
    public List<AdminReportDto> findAll() {
        return adminReportMapper.toDtos(adminReportRepository.findAll());
    }


    @Override
    public AdminReportDto findById(Long id) {
        return adminReportMapper.toDto(adminReportRepository.findById(id));
    }

    @Override
    public List<AdminReportDto> findByReportId(Long reportId) {
        return adminReportMapper.toDtos(adminReportRepository.findByReportId(reportId));
    }

    @Override
    public AdminReportDto save(AdminReportDto adminReportDto) {
        return adminReportMapper.toDto(adminReportRepository.add(adminReportMapper.fromDto(adminReportDto)));
    }

    @Override
    public AdminReportDto update(AdminReportDto adminReportDto) throws NoSuchEntityException {
        return adminReportMapper.toDto(adminReportRepository.update(adminReportMapper.fromDto(adminReportDto)));
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException {
        adminReportRepository.delete(id);
    }

    @Override
    public void delete(AdminReportDto adminReportDto) throws NoSuchEntityException {
        adminReportRepository.delete(adminReportMapper.fromDto(adminReportDto));
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
        adminReportRepository.deleteAll();
    }

    @Override
    public void deleteByReportId(Long reportId) throws NoSuchEntityException {
        adminReportRepository.deleteByReportId(reportId);
    }
}
