/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.workers.model;

import com.example.workers.model.entities.Worker;
import com.example.workers.util.PersistenceUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dmitry
 */
public class WorkerManagerTest {
    
    private static WorkerManager workerManager = null;
    
    public WorkerManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        workerManager = new WorkerManagerImpl();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        workerManager.clearAllWorkers();
    }
    
    @After
    public void tearDown() {
        workerManager.clearAllWorkers();
    }

    /**
     * Test of getAllWorkers method, of class WorkerManager.
     */
    @Test
    public void testSaveAndGetAllWorkers() {        
        Worker w1 = new Worker("Bob");
        Worker w2 = new Worker("Alice");
        workerManager.saveWorker(w1);
        workerManager.saveWorker(w2);
        
        List<Worker> result = workerManager.getAllWorkers();
        assertFalse(result.isEmpty());
        
        List<Worker> expected = Arrays.asList(new Worker[] {w1, w2});
        assertEquals(new HashSet<>(result), new HashSet<>(expected));
    }
    
    @Test
    public void testDeleteWorkers() {
        Worker w1 = new Worker("Bob");
        Worker w2 = new Worker("Alice");
        workerManager.saveWorker(w1);
        workerManager.saveWorker(w2);
        
        List<Worker> result = workerManager.getAllWorkers();
        assertFalse(result.isEmpty());
        
        List<Worker> expected = Arrays.asList(new Worker[] {w1, w2});
        assertEquals(new HashSet<>(result), new HashSet<>(expected));
        
        workerManager.deleteWorker(w2);
        
        result = workerManager.getAllWorkers();
        expected = Arrays.asList(new Worker[] {w1});
        assertEquals(new HashSet<>(result), new HashSet<>(expected));
        
        workerManager.deleteWorker(w1);
        
        result = workerManager.getAllWorkers();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetWorkerManager() {
        Worker manager = new Worker("Manager");
        Worker worker1 = new Worker("Worker 1", manager);
        Worker worker2 = new Worker("Worker 2", manager);
        Worker lowestWorker = new Worker("Lowest Worker", worker1);
        
        workerManager.saveAllWorkers(Arrays.asList(manager, worker1, worker2, lowestWorker));
        
        EntityManager em = PersistenceUtil.getEntityManager();
        
        List<Worker> result = manager.getSubordinates();
        List<Worker> expected = Arrays.asList(new Worker[] {worker1, worker2});
        assertEquals(new HashSet<>(expected), new HashSet<>(result));
        
        result = manager.getAllSubordinates();
        expected = Arrays.asList(new Worker[] {lowestWorker, worker1, worker2});
        assertEquals(new HashSet<>(expected), new HashSet<>(result));
        
        assertEquals(worker1, lowestWorker.getManager());
        assertEquals(manager, lowestWorker.getManager().getManager());
    }
}
