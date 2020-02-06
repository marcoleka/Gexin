package it.contrader.gexin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import it.contrader.gexin.domain.enumeration.Day;

/**
 * A TimeZone.
 */
@Entity
@Table(name = "time_zone")
public class TimeZone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "da_1")
    private ZonedDateTime da1;

    @Column(name = "da_2")
    private ZonedDateTime da2;

    @Column(name = "da_3")
    private ZonedDateTime da3;

    @Column(name = "da_4")
    private ZonedDateTime da4;

    @Column(name = "a_1")
    private ZonedDateTime a1;

    @Column(name = "a_2")
    private ZonedDateTime a2;

    @Column(name = "a_3")
    private ZonedDateTime a3;

    @Column(name = "a_4")
    private ZonedDateTime a4;

    @Enumerated(EnumType.STRING)
    @Column(name = "giorno")
    private Day giorno;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("timeZones")
    private Accesso accesso;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDa1() {
        return da1;
    }

    public TimeZone da1(ZonedDateTime da1) {
        this.da1 = da1;
        return this;
    }

    public void setDa1(ZonedDateTime da1) {
        this.da1 = da1;
    }

    public ZonedDateTime getDa2() {
        return da2;
    }

    public TimeZone da2(ZonedDateTime da2) {
        this.da2 = da2;
        return this;
    }

    public void setDa2(ZonedDateTime da2) {
        this.da2 = da2;
    }

    public ZonedDateTime getDa3() {
        return da3;
    }

    public TimeZone da3(ZonedDateTime da3) {
        this.da3 = da3;
        return this;
    }

    public void setDa3(ZonedDateTime da3) {
        this.da3 = da3;
    }

    public ZonedDateTime getDa4() {
        return da4;
    }

    public TimeZone da4(ZonedDateTime da4) {
        this.da4 = da4;
        return this;
    }

    public void setDa4(ZonedDateTime da4) {
        this.da4 = da4;
    }

    public ZonedDateTime geta1() {
        return a1;
    }

    public TimeZone a1(ZonedDateTime a1) {
        this.a1 = a1;
        return this;
    }

    public void seta1(ZonedDateTime a1) {
        this.a1 = a1;
    }

    public ZonedDateTime geta2() {
        return a2;
    }

    public TimeZone a2(ZonedDateTime a2) {
        this.a2 = a2;
        return this;
    }

    public void seta2(ZonedDateTime a2) {
        this.a2 = a2;
    }

    public ZonedDateTime geta3() {
        return a3;
    }

    public TimeZone a3(ZonedDateTime a3) {
        this.a3 = a3;
        return this;
    }

    public void seta3(ZonedDateTime a3) {
        this.a3 = a3;
    }

    public ZonedDateTime geta4() {
        return a4;
    }

    public TimeZone a4(ZonedDateTime a4) {
        this.a4 = a4;
        return this;
    }

    public void seta4(ZonedDateTime a4) {
        this.a4 = a4;
    }

    public Day getGiorno() {
        return giorno;
    }

    public TimeZone giorno(Day giorno) {
        this.giorno = giorno;
        return this;
    }

    public void setGiorno(Day giorno) {
        this.giorno = giorno;
    }

    public String getDescription() {
        return description;
    }

    public TimeZone description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Accesso getAccesso() {
        return accesso;
    }

    public TimeZone accesso(Accesso accesso) {
        this.accesso = accesso;
        return this;
    }

    public void setAccesso(Accesso accesso) {
        this.accesso = accesso;
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
        TimeZone timeZone = (TimeZone) o;
        if (timeZone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeZone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeZone{" +
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
            "}";
    }
}
