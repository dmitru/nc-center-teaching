/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.workers.util;

import java.util.concurrent.Callable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dmitry
 */
public class PersistenceUtil {
    private static final String PERSISTENCE_UNIT_NAME = "JpaExampleWorkersPU";
    private static EntityManagerFactory factory = null;
    private static EntityManager entityManager = null;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
}
