package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TblUser;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TblUserRepositoryWithBagRelationshipsImpl implements TblUserRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TblUser> fetchBagRelationships(Optional<TblUser> tblUser) {
        return tblUser.map(this::fetchRoles);
    }

    @Override
    public Page<TblUser> fetchBagRelationships(Page<TblUser> tblUsers) {
        return new PageImpl<>(fetchBagRelationships(tblUsers.getContent()), tblUsers.getPageable(), tblUsers.getTotalElements());
    }

    @Override
    public List<TblUser> fetchBagRelationships(List<TblUser> tblUsers) {
        return Optional.of(tblUsers).map(this::fetchRoles).orElse(Collections.emptyList());
    }

    TblUser fetchRoles(TblUser result) {
        return entityManager
            .createQuery("select tblUser from TblUser tblUser left join fetch tblUser.roles where tblUser is :tblUser", TblUser.class)
            .setParameter("tblUser", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<TblUser> fetchRoles(List<TblUser> tblUsers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tblUsers.size()).forEach(index -> order.put(tblUsers.get(index).getId(), index));
        List<TblUser> result = entityManager
            .createQuery(
                "select distinct tblUser from TblUser tblUser left join fetch tblUser.roles where tblUser in :tblUsers",
                TblUser.class
            )
            .setParameter("tblUsers", tblUsers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
