package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.PointDto;
import com.swansoftwaresolutions.jirareport.core.dto.ReportResponceDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.mapper.PointMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.mappings.NewReportDtoModelMap;
import com.swansoftwaresolutions.jirareport.core.mapper.mappings.UpdatedReportDtoModelMap;
import com.swansoftwaresolutions.jirareport.domain.entity.Point;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
@Component
public class PointMapperImpl implements PointMapper {

    ModelMapper modelMapper;

    @Override
    public PointDto toDto(Point report) {
        return modelMapper.map(report, PointDto.class);
    }

    @Override
    public List<PointDto> toDtos(List<Point> reportList) {
        Type targetistType = new TypeToken<List<PointDto>>(){}.getType();
        return modelMapper.map(reportList, targetistType);
    }

    @Override
    public Point fromDto(PointDto reportDto) {
        return modelMapper.map(reportDto, Point.class);
    }

    @Override
    public List<Point> fromDtos(List<PointDto> reportDtos) {
        Type targetistType = new TypeToken<List<Point>>(){}.getType();
        return modelMapper.map(reportDtos, targetistType);
    }

}
