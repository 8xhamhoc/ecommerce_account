package org.quangphan.ecommerce.account.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.quangphan.ecommerce.account.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AppUserRepositoryWithBagRelationshipsImpl implements AppUserRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AppUser> fetchBagRelationships(Optional<AppUser> appUser) {
        return appUser.map(this::fetchRoles);
    }

    @Override
    public Page<AppUser> fetchBagRelationships(Page<AppUser> appUsers) {
        return new PageImpl<>(fetchBagRelationships(appUsers.getContent()), appUsers.getPageable(), appUsers.getTotalElements());
    }

    @Override
    public List<AppUser> fetchBagRelationships(List<AppUser> appUsers) {
        return Optional.of(appUsers).map(this::fetchRoles).orElse(Collections.emptyList());
    }

    AppUser fetchRoles(AppUser result) {
        return entityManager
            .createQuery("select appUser from AppUser appUser left join fetch appUser.roles where appUser is :appUser", AppUser.class)
            .setParameter("appUser", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<AppUser> fetchRoles(List<AppUser> appUsers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, appUsers.size()).forEach(index -> order.put(appUsers.get(index).getId(), index));
        List<AppUser> result = entityManager
            .createQuery(
                "select distinct appUser from AppUser appUser left join fetch appUser.roles where appUser in :appUsers",
                AppUser.class
            )
            .setParameter("appUsers", appUsers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
