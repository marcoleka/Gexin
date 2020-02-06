package it.contrader.gexin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Log.
 */
@Entity
@Table(name = "log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "error")
    private String error;

    @Column(name = "evento")
    private String evento;

    @ManyToOne
    @JsonIgnoreProperties("logs")
    private Dipendente dipendente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Log date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getError() {
        return error;
    }

    public Log error(String error) {
        this.error = error;
        return this;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEvento() {
        return evento;
    }

    public Log evento(String evento) {
        this.evento = evento;
        return this;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public Log dipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
        return this;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Log log = (Log) o;
        if (log.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), log.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Log{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", error='" + getError() + "'" +
            ", evento='" + getEvento() + "'" +
            "}";
    }
}
