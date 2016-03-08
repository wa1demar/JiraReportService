package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.CommentMapper;
import com.swansoftwaresolutions.jirareport.core.repository.CommentRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.CommentService;
import com.swansoftwaresolutions.jirareport.rest.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<CommentDto> findAll() {
        return commentMapper.toDtos(commentRepository.findAll());
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        return commentMapper.toDto(commentRepository.add(commentMapper.fromDto(commentDto)));
    }

    @Override
    public CommentDto update(CommentDto commentDto) throws NoSuchEntityException {
        return commentMapper.toDto(commentRepository.update(commentMapper.fromDto(commentDto)));
    }

    @Override
    public CommentDto findById(long id) throws NoSuchEntityException {
        return commentMapper.toDto(commentRepository.findById(id));
    }

    @Override
    public List<CommentDto> findByReportId(long reportId) throws NoSuchEntityException {
        return commentMapper.toDtos(commentRepository.findByReportId(reportId));
    }

    @Override
    public void delete(long id) throws NoSuchEntityException {
        commentRepository.delete(id);
    }

    @Override
    public void delete(CommentDto commentDto) throws NoSuchEntityException {
        commentRepository.delete(commentMapper.fromDto(commentDto));
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
        commentRepository.deleteAll();
    }
}
