package com.mickc0.gtac.service;

import com.mickc0.gtac.model.CompletionDate;
import com.mickc0.gtac.repository.CompletionDateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompletionDateServiceImpl implements CompletionDateService {
    private final CompletionDateRepository completionDateRepository;
    @Override
    public List<CompletionDate> findAll() {
        return completionDateRepository.findAll();
    }

    @Override
    public void save(CompletionDate completionDate) {
        completionDateRepository.save(completionDate);
    }
}
