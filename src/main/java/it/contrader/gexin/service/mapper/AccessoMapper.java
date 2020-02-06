package it.contrader.gexin.service.mapper;

import it.contrader.gexin.domain.*;
import it.contrader.gexin.service.dto.AccessoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Accesso and its DTO AccessoDTO.
 */
@Mapper(componentModel = "spring", uses = {GruppoMapper.class})
public interface AccessoMapper extends EntityMapper<AccessoDTO, Accesso> {

    @Mapping(source = "gruppo.id", target = "gruppoId")
    AccessoDTO toDto(Accesso accesso);

    @Mapping(source = "gruppoId", target = "gruppo")
    @Mapping(target = "timeZones", ignore = true)
    @Mapping(target = "dipendente", ignore = true)
    Accesso toEntity(AccessoDTO accessoDTO);

    default Accesso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Accesso accesso = new Accesso();
        accesso.setId(id);
        return accesso;
    }
}
