package it.contrader.gexin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gruppo.
 */
@Entity
@Table(name = "gruppo")
public class Gruppo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome_gruppo")
    private String nomeGruppo;

    @ManyToMany
    @JoinTable(name = "gruppo_door",
               joinColumns = @JoinColumn(name = "gruppos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "doors_id", referencedColumnName = "id"))
    private Set<Door> doors = new HashSet<>();

    @OneToOne(mappedBy = "gruppo")
    @JsonIgnore
    private Accesso accesso;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeGruppo() {
        return nomeGruppo;
    }

    public Gruppo nomeGruppo(String nomeGruppo) {
        this.nomeGruppo = nomeGruppo;
        return this;
    }

    public void setNomeGruppo(String nomeGruppo) {
        this.nomeGruppo = nomeGruppo;
    }

    public Set<Door> getDoors() {
        return doors;
    }

    public Gruppo doors(Set<Door> doors) {
        this.doors = doors;
        return this;
    }

    public Gruppo addDoor(Door door) {
        this.doors.add(door);
        door.getGruppos().add(this);
        return this;
    }

    public Gruppo removeDoor(Door door) {
        this.doors.remove(door);
        door.getGruppos().remove(this);
        return this;
    }

    public void setDoors(Set<Door> doors) {
        this.doors = doors;
    }

    public Accesso getAccesso() {
        return accesso;
    }

    public Gruppo accesso(Accesso accesso) {
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
        Gruppo gruppo = (Gruppo) o;
        if (gruppo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gruppo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gruppo{" +
            "id=" + getId() +
            ", nomeGruppo='" + getNomeGruppo() + "'" +
            "}";
    }
}
