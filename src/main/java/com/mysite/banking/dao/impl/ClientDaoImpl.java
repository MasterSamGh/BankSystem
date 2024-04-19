package com.mysite.banking.dao.impl;

import com.mysite.banking.dao.ClientDao;
import com.mysite.banking.model.Client;
import com.mysite.banking.model.RealClient;
import com.mysite.banking.service.DataBase;
import com.mysite.banking.service.exception.UpdateException;
import com.mysite.banking.service.impl.DataBaseImpl;
import org.hibernate.Session;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    private DataBase dataBase;
    private final static ClientDaoImpl INSTANCE;
    public static ClientDaoImpl getInstance(){
        return INSTANCE;
    }
    static {
        INSTANCE = new ClientDaoImpl();
    }
    private ClientDaoImpl(){
        dataBase = DataBaseImpl.getInstance();
    }
    @Override
    public Integer save(Client client) {
        try (Session session = dataBase.getSession()){
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
            return client.getId();
        }

    }
    @Override
    public Client getByID(Integer id) {
        try (Session session = dataBase.getSession()){
            return session.get(Client.class, id);
        }
    }

    @Override
    public List<Client> getByStatus(Boolean deleted) {
        try (Session session = dataBase.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root);
            if (deleted != null)
                criteriaQuery.where(criteriaBuilder.equal(root.get("deleted"),deleted));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Client> getAll() {
        return getByStatus(null);
    }

    @Override
    public List<Client> getByFamily(String family) {
        try (Session session = dataBase.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<RealClient> root = criteriaQuery.from(RealClient.class);


            Predicate familyPredicate = criteriaBuilder.equal(root.get("family"),family);
            Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"),false);

            Predicate finalPredicate = criteriaBuilder.and(
                    familyPredicate,
                    deletedPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public Client getFirstByEmail(String email) {
        try (Session session = dataBase.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);


            Predicate familyPredicate = criteriaBuilder.equal(root.get("email"),email);
            Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"),false);

            Predicate finalPredicate = criteriaBuilder.and(
                    familyPredicate,
                    deletedPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            TypedQuery<Client> typedQuery = session.createQuery(criteriaQuery);
            typedQuery.setMaxResults(1);
            List<Client> resultList = typedQuery.getResultList();

            return resultList.isEmpty() ? null : resultList.get(0);
        }
    }

    @Override
    public List<Client> getByName(String name) {
        try (Session session = dataBase.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);


            Predicate familyPredicate = criteriaBuilder.equal(root.get("name"),name);
            Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"),false);

            Predicate finalPredicate = criteriaBuilder.and(
                    familyPredicate,
                    deletedPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void delete(Client client) {
        client.setDeleted(true);
        update(client);
    }

    @Override
    public void update(Client client) {
        try (Session session = dataBase.getSession()){
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
        } catch (Exception ex){
            throw new UpdateException("Update exception, Please retry",ex);
        }
    }
}
