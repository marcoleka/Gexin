package it.contrader.gexin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Door.
 */
@Entity
@Table(name = "door")
public class Door implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "door_name")
    private String doorName;

    @ManyToMany(mappedBy = "doors")
    @JsonIgnore
    private Set<Gruppo> gruppos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Door description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoorName() {
        return doorName;
    }

    public Door doorName(String doorName) {
        this.doorName = doorName;
        return this;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public Set<Gruppo> getGruppos() {
        return gruppos;
    }

    public Door gruppos(Set<Gruppo> gruppos) {
        this.gruppos = gruppos;
        return this;
    }

    public Door addGruppo(Gruppo gruppo) {
        this.gruppos.add(gruppo);
        gruppo.getDoors().add(this);
        return this;
    }

    public Door removeGruppo(Gruppo gruppo) {
        this.gruppos.remove(gruppo);
        gruppo.getDoors().remove(this);
        return this;
    }

    public void setGruppos(Set<Gruppo> gruppos) {
        this.gruppos = gruppos;
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
        Door door = (Door) o;
        if (door.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), door.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Door{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", doorName='" + getDoorName() + "'" +
            "}";
    }
}
