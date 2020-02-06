package it.contrader.gexin.service.mapper;

import it.contrader.gexin.domain.*;
import it.contrader.gexin.service.dto.TimeZoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TimeZone and its DTO TimeZoneDTO.
 */
@Mapper(componentModel = "spring", uses = {AccessoMapper.class})
public interface TimeZoneMapper extends EntityMapper<TimeZoneDTO, TimeZone> {

    @Mapping(source = "accesso.id", target = "accessoId")
    TimeZoneDTO toDto(TimeZone timeZone);

    @Mapping(source = "accessoId", target = "accesso")
    TimeZone toEntity(TimeZoneDTO timeZoneDTO);

    default TimeZone fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimeZone timeZone = new TimeZone();
        timeZone.setId(id);
        return timeZone;
    }
}
