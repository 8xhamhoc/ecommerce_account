package org.quangphan.ecommerce.account.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import org.quangphan.ecommerce.account.domain.AppRole;

/**
 * A DTO for the {@link AppRole} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppRoleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppRoleDTO)) {
            return false;
        }

        AppRoleDTO appRoleDTO = (AppRoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appRoleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppRoleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
