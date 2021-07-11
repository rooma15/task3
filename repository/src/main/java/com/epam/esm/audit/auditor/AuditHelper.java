package com.epam.esm.audit.auditor;

import com.epam.esm.audit.model.Auditable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuditHelper {

    private final EntityManager entityManager;

    public AuditHelper() {
        String PERSISTENCE_UNIT_NAME = "my-pers";
        EntityManagerFactory emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = emFactoryObj.createEntityManager();
    }

    public void save(Auditable model){
        entityManager.getTransaction().begin();
        entityManager.persist(model);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
