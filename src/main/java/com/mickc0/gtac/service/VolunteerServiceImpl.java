package com.mickc0.gtac.service;

import com.mickc0.gtac.model.Volunteer;
import com.mickc0.gtac.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerServiceImpl implements VolunteerService{
    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }
}
