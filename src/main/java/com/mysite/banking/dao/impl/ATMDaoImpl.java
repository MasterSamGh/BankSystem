package com.mysite.banking.dao.impl;

import com.mysite.banking.dao.ATMDao;
import com.mysite.banking.model.ATM;
import com.mysite.banking.model.Account;
import com.mysite.banking.service.DataBase;
import com.mysite.banking.service.exception.UpdateException;
import com.mysite.banking.service.impl.DataBaseImpl;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ATMDaoImpl implements ATMDao {
    private DataBase dataBase;
    private final static ATMDaoImpl INSTANCE;
    public static ATMDaoImpl getInstance(){
        return INSTANCE;
    }
    static {
        INSTANCE = new ATMDaoImpl();
    }
    private ATMDaoImpl(){
        dataBase = DataBaseImpl.getInstance();
    }
    @Override
    public List<ATM> getAll() {
        try (Session session = dataBase.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ATM> criteriaQuery = criteriaBuilder.createQuery(ATM.class);
            Root<ATM> root = criteriaQuery.from(ATM.class);
            criteriaQuery.select(root);
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("denomination")));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public Long save(ATM atm) {
        try (Session session = dataBase.getSession()){
            session.beginTransaction();
            session.save(atm);
            session.getTransaction().commit();
            return atm.getId();
        }
    }

    @Override
    public void update(ATM atm) {
        try (Session session = dataBase.getSession()){
            session.beginTransaction();
            session.update(atm);
            session.getTransaction().commit();
        } catch (Exception ex){
            throw new UpdateException("Update exception, Please ",ex);
        }
    }

    @Override
    public List<ATM> getByDenomination(int denomination) {
        try (Session session = dataBase.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ATM> criteriaQuery = criteriaBuilder.createQuery(ATM.class);
            Root<ATM> root = criteriaQuery.from(ATM.class);
            Predicate denominationPredicate = criteriaBuilder.equal(root.get("denomination"),denomination);
            Predicate finalPredicate = criteriaBuilder.and(
                    denominationPredicate
            );
            criteriaQuery.select(root).where(finalPredicate);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
