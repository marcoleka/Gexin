package it.contrader.gexin.service.mapper;

import it.contrader.gexin.domain.*;
import it.contrader.gexin.service.dto.DipendenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dipendente and its DTO DipendenteDTO.
 */
@Mapper(componentModel = "spring", uses = {AccessoMapper.class})
public interface DipendenteMapper extends EntityMapper<DipendenteDTO, Dipendente> {

    @Mapping(source = "accesso.id", target = "accessoId")
    DipendenteDTO toDto(Dipendente dipendente);

    @Mapping(source = "accessoId", target = "accesso")
    @Mapping(target = "logs", ignore = true)
    Dipendente toEntity(DipendenteDTO dipendenteDTO);

    default Dipendente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dipendente dipendente = new Dipendente();
        dipendente.setId(id);
        return dipendente;
    }
}
