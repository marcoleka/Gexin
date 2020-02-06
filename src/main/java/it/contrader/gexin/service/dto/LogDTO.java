package it.contrader.gexin.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Log entity.
 */
public class LogDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String error;

    private String evento;

    private Long dipendenteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Long getDipendenteId() {
        return dipendenteId;
    }

    public void setDipendenteId(Long dipendenteId) {
        this.dipendenteId = dipendenteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogDTO logDTO = (LogDTO) o;
        if (logDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", error='" + getError() + "'" +
            ", evento='" + getEvento() + "'" +
            ", dipendente=" + getDipendenteId() +
            "}";
    }
}
