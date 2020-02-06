package it.contrader.gexin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Accesso entity.
 */
public class AccessoDTO implements Serializable {

    private Long id;

    private Long gruppoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGruppoId() {
        return gruppoId;
    }

    public void setGruppoId(Long gruppoId) {
        this.gruppoId = gruppoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccessoDTO accessoDTO = (AccessoDTO) o;
        if (accessoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accessoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccessoDTO{" +
            "id=" + getId() +
            ", gruppo=" + getGruppoId() +
            "}";
    }
}
