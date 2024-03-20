package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TblRole.
 */
@Entity
@Table(name = "tbl_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TblRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roles" }, allowSetters = true)
    private Set<TblUser> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TblRole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public TblRole name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TblUser> getUsers() {
        return this.users;
    }

    public void setUsers(Set<TblUser> tblUsers) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeRole(this));
        }
        if (tblUsers != null) {
            tblUsers.forEach(i -> i.addRole(this));
        }
        this.users = tblUsers;
    }

    public TblRole users(Set<TblUser> tblUsers) {
        this.setUsers(tblUsers);
        return this;
    }

    public TblRole addUser(TblUser tblUser) {
        this.users.add(tblUser);
        tblUser.getRoles().add(this);
        return this;
    }

    public TblRole removeUser(TblUser tblUser) {
        this.users.remove(tblUser);
        tblUser.getRoles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TblRole)) {
            return false;
        }
        return id != null && id.equals(((TblRole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TblRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
