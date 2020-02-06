package it.contrader.gexin.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Gruppo entity.
 */
public class GruppoDTO implements Serializable {

    private Long id;

    private String nomeGruppo;

    private Set<DoorDTO> doors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeGruppo() {
        return nomeGruppo;
    }

    public void setNomeGruppo(String nomeGruppo) {
        this.nomeGruppo = nomeGruppo;
    }

    public Set<DoorDTO> getDoors() {
        return doors;
    }

    public void setDoors(Set<DoorDTO> doors) {
        this.doors = doors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GruppoDTO gruppoDTO = (GruppoDTO) o;
        if (gruppoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gruppoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GruppoDTO{" +
            "id=" + getId() +
            ", nomeGruppo='" + getNomeGruppo() + "'" +
            "}";
    }
}
