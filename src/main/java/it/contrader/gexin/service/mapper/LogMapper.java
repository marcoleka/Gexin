package it.contrader.gexin.service.mapper;

import it.contrader.gexin.domain.*;
import it.contrader.gexin.service.dto.LogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Log and its DTO LogDTO.
 */
@Mapper(componentModel = "spring", uses = {DipendenteMapper.class})
public interface LogMapper extends EntityMapper<LogDTO, Log> {

    @Mapping(source = "dipendente.id", target = "dipendenteId")
    LogDTO toDto(Log log);

    @Mapping(source = "dipendenteId", target = "dipendente")
    Log toEntity(LogDTO logDTO);

    default Log fromId(Long id) {
        if (id == null) {
            return null;
        }
        Log log = new Log();
        log.setId(id);
        return log;
    }
}
