package com.mickc0.gtac.service;

import com.mickc0.gtac.model.CompletionDate;

import java.util.List;


public interface CompletionDateService {
    List<CompletionDate> findAll();
    void save(CompletionDate completionDate);
}
