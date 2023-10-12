package com.mickc0.gtac.service;

import com.mickc0.gtac.model.CompletionDate;
import com.mickc0.gtac.repository.CompletionDateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletionDateServiceImpl implements CompletionDateService {
    private final CompletionDateRepository completionDateRepository;

    public CompletionDateServiceImpl(CompletionDateRepository completionDateRepository) {
        this.completionDateRepository = completionDateRepository;
    }

    @Override
    public List<CompletionDate> findAll() {
        return completionDateRepository.findAll();
    }

    @Override
    public void save(CompletionDate completionDate) {
        completionDateRepository.save(completionDate);
    }
}
