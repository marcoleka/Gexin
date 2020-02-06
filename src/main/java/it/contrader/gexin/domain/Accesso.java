package it.contrader.gexin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Accesso.
 */
@Entity
@Table(name = "accesso")
public class Accesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Gruppo gruppo;

    @OneToMany(mappedBy = "accesso")
    private Set<TimeZone> timeZones = new HashSet<>();

    @OneToOne(mappedBy = "accesso")
    @JsonIgnore
    private Dipendente dipendente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gruppo getGruppo() {
        return gruppo;
    }

    public Accesso gruppo(Gruppo gruppo) {
        this.gruppo = gruppo;
        return this;
    }

    public void setGruppo(Gruppo gruppo) {
        this.gruppo = gruppo;
    }

    public Set<TimeZone> getTimeZones() {
        return timeZones;
    }

    public Accesso timeZones(Set<TimeZone> timeZones) {
        this.timeZones = timeZones;
        return this;
    }

    public Accesso addTimeZone(TimeZone timeZone) {
        this.timeZones.add(timeZone);
        timeZone.setAccesso(this);
        return this;
    }

    public Accesso removeTimeZone(TimeZone timeZone) {
        this.timeZones.remove(timeZone);
        timeZone.setAccesso(null);
        return this;
    }

    public void setTimeZones(Set<TimeZone> timeZones) {
        this.timeZones = timeZones;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public Accesso dipendente(Dipendente dipendente) {
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
        Accesso accesso = (Accesso) o;
        if (accesso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accesso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Accesso{" +
            "id=" + getId() +
            "}";
    }
}
