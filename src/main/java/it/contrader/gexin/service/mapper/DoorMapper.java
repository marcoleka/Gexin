package it.contrader.gexin.service.mapper;

import it.contrader.gexin.domain.*;
import it.contrader.gexin.service.dto.DoorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Door and its DTO DoorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DoorMapper extends EntityMapper<DoorDTO, Door> {


    @Mapping(target = "gruppos", ignore = true)
    Door toEntity(DoorDTO doorDTO);

    default Door fromId(Long id) {
        if (id == null) {
            return null;
        }
        Door door = new Door();
        door.setId(id);
        return door;
    }
}
