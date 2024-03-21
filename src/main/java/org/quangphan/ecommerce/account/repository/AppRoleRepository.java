package org.quangphan.ecommerce.account.repository;

import org.quangphan.ecommerce.account.domain.AppRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {}
