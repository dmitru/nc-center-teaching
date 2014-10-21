/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.workers.model;

import com.example.workers.model.entities.Worker;
import java.util.List;

/**
 *
 * @author Dmitry
 */
public interface WorkerManager {
    
    List<Worker> getAllWorkers();
    
    void saveWorker(Worker worker);
    
    void saveAllWorkers(List<Worker> workers);
    
    Worker getWorker(Long id);
    
    void deleteWorker(Worker worker);
    
    void deleteWorker(Long id);
    
    void clearAllWorkers();
}
