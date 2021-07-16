package com.epam.esm.audit.auditor;

import com.epam.esm.audit.model.Auditable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuditHelper {

    private final String PERSISTENCE_UNIT_NAME = "my-pers";

    private final EntityManager entityManager;

    public AuditHelper() {
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
