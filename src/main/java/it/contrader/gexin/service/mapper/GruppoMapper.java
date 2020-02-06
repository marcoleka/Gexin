package it.contrader.gexin.service.mapper;

import it.contrader.gexin.domain.*;
import it.contrader.gexin.service.dto.GruppoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gruppo and its DTO GruppoDTO.
 */
@Mapper(componentModel = "spring", uses = {DoorMapper.class})
public interface GruppoMapper extends EntityMapper<GruppoDTO, Gruppo> {


    @Mapping(target = "accesso", ignore = true)
    Gruppo toEntity(GruppoDTO gruppoDTO);

    default Gruppo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gruppo gruppo = new Gruppo();
        gruppo.setId(id);
        return gruppo;
    }
}
