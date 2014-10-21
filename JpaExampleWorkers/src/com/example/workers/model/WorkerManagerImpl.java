/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.workers.model;

import com.example.workers.model.entities.Worker;
import com.example.workers.util.PersistenceUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Dmitry
 */
public class WorkerManagerImpl implements WorkerManager {
    
    Logger logger = LoggerFactory.getLogger(WorkerManagerImpl.class);
    
    @Override
    public List<Worker> getAllWorkers() {
        logger.debug("getAllWorkers() is called");
        
        EntityManager em = PersistenceUtil.getEntityManager();
        Query q = em.createQuery("select w from Worker w");
        List<Worker> result = q.getResultList();
        return result;
    }

    @Override
    public void saveWorker(Worker worker) {
        logger.debug("saveWorker() is called for worker: " + worker);
        
        // Creating transactions manually with EntityManager
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(worker);
        em.getTransaction().commit();
        em.refresh(worker);
        em.clear();
    }

    @Override
    public void saveAllWorkers(List<Worker> workers) {
        logger.debug("saveWorker() is called");
        
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        workers.stream().forEach(worker -> em.persist(worker));
        em.getTransaction().commit();
        workers.stream().forEach(worker -> em.refresh(worker));
        em.clear();
    }
    
    @Override
    public Worker getWorker(Long id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        return em.find(Worker.class, id);
    }

    @Override
    public void clearAllWorkers() {
        logger.debug("clearAllWorkers() is called");
        
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        getAllWorkers().forEach(worker -> em.remove(worker));
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void deleteWorker(Worker worker) {
        logger.debug("deleteWorker() is called for worker: " + worker);
        
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        em.remove(worker);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public void deleteWorker(Long id) {
        deleteWorker(getWorker(id));
    }
}
