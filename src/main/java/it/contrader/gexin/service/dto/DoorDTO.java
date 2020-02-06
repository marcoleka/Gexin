package it.contrader.gexin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Door entity.
 */
public class DoorDTO implements Serializable {

    private Long id;

    private String description;

    private String doorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DoorDTO doorDTO = (DoorDTO) o;
        if (doorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), doorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DoorDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", doorName='" + getDoorName() + "'" +
            "}";
    }
}
