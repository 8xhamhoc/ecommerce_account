package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TblUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TblUserRepositoryWithBagRelationships {
    Optional<TblUser> fetchBagRelationships(Optional<TblUser> tblUser);

    List<TblUser> fetchBagRelationships(List<TblUser> tblUsers);

    Page<TblUser> fetchBagRelationships(Page<TblUser> tblUsers);
}
