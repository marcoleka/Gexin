package it.contrader.gexin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dipendente.
 */
@Entity
@Table(name = "dipendente")
public class Dipendente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "ruolo")
    private String ruolo;

    @Column(name = "codice_fiscale")
    private String codiceFiscale;

    @Column(name = "reparto")
    private String reparto;

    @Column(name = "numero_scheda")
    private String numeroScheda;

    @Column(name = "scheda_persa")
    private Boolean schedaPersa;

    @OneToOne
    @JoinColumn(unique = true)
    private Accesso accesso;

    @OneToMany(mappedBy = "dipendente")
    private Set<Log> logs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Dipendente name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Dipendente surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRuolo() {
        return ruolo;
    }

    public Dipendente ruolo(String ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Dipendente codiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
        return this;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getReparto() {
        return reparto;
    }

    public Dipendente reparto(String reparto) {
        this.reparto = reparto;
        return this;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public String getNumeroScheda() {
        return numeroScheda;
    }

    public Dipendente numeroScheda(String numeroScheda) {
        this.numeroScheda = numeroScheda;
        return this;
    }

    public void setNumeroScheda(String numeroScheda) {
        this.numeroScheda = numeroScheda;
    }

    public Boolean isSchedaPersa() {
        return schedaPersa;
    }

    public Dipendente schedaPersa(Boolean schedaPersa) {
        this.schedaPersa = schedaPersa;
        return this;
    }

    public void setSchedaPersa(Boolean schedaPersa) {
        this.schedaPersa = schedaPersa;
    }

    public Accesso getAccesso() {
        return accesso;
    }

    public Dipendente accesso(Accesso accesso) {
        this.accesso = accesso;
        return this;
    }

    public void setAccesso(Accesso accesso) {
        this.accesso = accesso;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public Dipendente logs(Set<Log> logs) {
        this.logs = logs;
        return this;
    }

    public Dipendente addLog(Log log) {
        this.logs.add(log);
        log.setDipendente(this);
        return this;
    }

    public Dipendente removeLog(Log log) {
        this.logs.remove(log);
        log.setDipendente(null);
        return this;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
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
        Dipendente dipendente = (Dipendente) o;
        if (dipendente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dipendente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dipendente{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", ruolo='" + getRuolo() + "'" +
            ", codiceFiscale='" + getCodiceFiscale() + "'" +
            ", reparto='" + getReparto() + "'" +
            ", numeroScheda='" + getNumeroScheda() + "'" +
            ", schedaPersa='" + isSchedaPersa() + "'" +
            "}";
    }
}
