package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TblRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TblRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TblRoleRepository extends JpaRepository<TblRole, Long> {}
