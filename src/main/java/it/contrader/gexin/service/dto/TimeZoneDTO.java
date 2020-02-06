package it.contrader.gexin.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import it.contrader.gexin.domain.enumeration.Day;

/**
 * A DTO for the TimeZone entity.
 */
public class TimeZoneDTO implements Serializable {

    private Long id;

    private ZonedDateTime da1;

    private ZonedDateTime da2;

    private ZonedDateTime da3;

    private ZonedDateTime da4;

    private ZonedDateTime a1;

    private ZonedDateTime a2;

    private ZonedDateTime a3;

    private ZonedDateTime a4;

    private Day giorno;

    private String description;

    private Long accessoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDa1() {
        return da1;
    }

    public void setDa1(ZonedDateTime da1) {
        this.da1 = da1;
    }

    public ZonedDateTime getDa2() {
        return da2;
    }

    public void setDa2(ZonedDateTime da2) {
        this.da2 = da2;
    }

    public ZonedDateTime getDa3() {
        return da3;
    }

    public void setDa3(ZonedDateTime da3) {
        this.da3 = da3;
    }

    public ZonedDateTime getDa4() {
        return da4;
    }

    public void setDa4(ZonedDateTime da4) {
        this.da4 = da4;
    }

    public ZonedDateTime geta1() {
        return a1;
    }

    public void seta1(ZonedDateTime a1) {
        this.a1 = a1;
    }

    public ZonedDateTime geta2() {
        return a2;
    }

    public void seta2(ZonedDateTime a2) {
        this.a2 = a2;
    }

    public ZonedDateTime geta3() {
        return a3;
    }

    public void seta3(ZonedDateTime a3) {
        this.a3 = a3;
    }

    public ZonedDateTime geta4() {
        return a4;
    }

    public void seta4(ZonedDateTime a4) {
        this.a4 = a4;
    }

    public Day getGiorno() {
        return giorno;
    }

    public void setGiorno(Day giorno) {
        this.giorno = giorno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccessoId() {
        return accessoId;
    }

    public void setAccessoId(Long accessoId) {
        this.accessoId = accessoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimeZoneDTO timeZoneDTO = (TimeZoneDTO) o;
        if (timeZoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeZoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeZoneDTO{" +
            "id=" + getId() +
            ", da1='" + getDa1() + "'" +
            ", da2='" + getDa2() + "'" +
            ", da3='" + getDa3() + "'" +
            ", da4='" + getDa4() + "'" +
            ", a1='" + geta1() + "'" +
            ", a2='" + geta2() + "'" +
            ", a3='" + geta3() + "'" +
            ", a4='" + geta4() + "'" +
            ", giorno='" + getGiorno() + "'" +
            ", description='" + getDescription() + "'" +
            ", accesso=" + getAccessoId() +
            "}";
    }
}
