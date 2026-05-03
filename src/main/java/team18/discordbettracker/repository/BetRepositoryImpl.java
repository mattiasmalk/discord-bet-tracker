package team18.discordbettracker.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;
import team18.discordbettracker.model.Bet;
import team18.discordbettracker.model.UserId;

import java.util.List;

@Repository
@NullMarked
public class BetRepositoryImpl implements BetRepositoryCustom {

    private static final int HISTORY_LIMIT = 10;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Bet> findLatestByUserId(UserId userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bet> cq = cb.createQuery(Bet.class);
        Root<Bet> bet = cq.from(Bet.class);

        cq.select(bet)
                .where(cb.equal(bet.get("user").get("userId"), userId))
                .orderBy(cb.desc(bet.get("createdAt")));

        TypedQuery<Bet> query = entityManager.createQuery(cq);
        query.setMaxResults(HISTORY_LIMIT);
        return query.getResultList();
    }

    @Override
    public List<Bet> findAllByUserId(UserId userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bet> cq = cb.createQuery(Bet.class);
        Root<Bet> bet = cq.from(Bet.class);

        cq.select(bet)
                .where(cb.equal(bet.get("user").get("userId"), userId))
                .orderBy(cb.desc(bet.get("createdAt")));

        TypedQuery<Bet> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
