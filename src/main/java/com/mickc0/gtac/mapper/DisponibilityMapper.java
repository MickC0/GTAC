package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.DisponibilityDTO;
import com.mickc0.gtac.model.Disponibility;

public class DisponibilityMapper {

    public DisponibilityDTO mapToDto(Disponibility disponibility){
        DisponibilityDTO disponibilityDTO = new DisponibilityDTO();
        disponibilityDTO.setUuid(disponibility.getUuid());
        disponibilityDTO.setDay(disponibility.getDay());
        disponibilityDTO.setStartingHour(disponibility.getStartingHour());
        disponibilityDTO.setEndingHour(disponibility.getEndingHour());
        return disponibilityDTO;
    }

    public Disponibility mapToEntity(DisponibilityDTO disponibilityDTO){
        Disponibility disponibility = new Disponibility();
        disponibility.setUuid(disponibilityDTO.getUuid());
        disponibility.setDay(disponibilityDTO.getDay());
        disponibility.setStartingHour(disponibilityDTO.getStartingHour());
        disponibility.setEndingHour(disponibilityDTO.getEndingHour());
        return disponibility;
    }
}
