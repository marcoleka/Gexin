package it.contrader.gexin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Dipendente entity.
 */
public class DipendenteDTO implements Serializable {

    private Long id;

    private String name;

    private String surname;

    private String ruolo;

    private String codiceFiscale;

    private String reparto;

    private String numeroScheda;

    private Boolean schedaPersa;

    private Long accessoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public String getNumeroScheda() {
        return numeroScheda;
    }

    public void setNumeroScheda(String numeroScheda) {
        this.numeroScheda = numeroScheda;
    }

    public Boolean isSchedaPersa() {
        return schedaPersa;
    }

    public void setSchedaPersa(Boolean schedaPersa) {
        this.schedaPersa = schedaPersa;
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

        DipendenteDTO dipendenteDTO = (DipendenteDTO) o;
        if (dipendenteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dipendenteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DipendenteDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", ruolo='" + getRuolo() + "'" +
            ", codiceFiscale='" + getCodiceFiscale() + "'" +
            ", reparto='" + getReparto() + "'" +
            ", numeroScheda='" + getNumeroScheda() + "'" +
            ", schedaPersa='" + isSchedaPersa() + "'" +
            ", accesso=" + getAccessoId() +
            "}";
    }
}
