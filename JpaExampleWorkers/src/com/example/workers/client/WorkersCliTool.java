/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.workers.client;

import com.example.workers.model.WorkerManager;
import com.example.workers.model.WorkerManagerImpl;
import com.example.workers.model.entities.Worker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dmitry
 */
public class WorkersCliTool {
    
    private static final Logger logger = LoggerFactory.getLogger(WorkersCliTool.class);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {       
        System.out.println("Hi! Usage:\n"
                + "ls - list all workers\n"
                + "add <name> [<manager id>] - add new worker\n"
                + "rm <id> - delete worker and all it subworkers\n"
                + "clear - remove all workers\n"
                + "exit - quit the program"
                + "Have fun!");
        
        WorkerManager workerManager = new WorkerManagerImpl();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print(">>> ");
            String command = br.readLine();
            String[] parts = command.split("\\s+");
            String cmd = parts[0];
            try {
                switch (cmd) {
                    case "ls":
                        listWorkers(workerManager);
                        break;
                    case "add":
                        String name = parts[1];
                        if (parts.length > 2) {
                            Long managerId = Long.parseLong(parts[2]);
                            addWorker(workerManager, name, managerId);
                        } else {
                            addWorker(workerManager, name, null);
                        }
                        break;
                    case "clear":
                        clearWorkers(workerManager);
                        break;
                    case "rm":
                        Long id = Long.parseLong(parts[1]);
                        removeWorker(workerManager, id);
                        break;
                    case "exit":
                    case "quit":
                        return;
                }
            } catch (Exception e) {
                logger.error("Error: ", e);
            }
        }
    }

    private static void listWorkers(WorkerManager workerManager) {
        List<Worker> topManagers = workerManager.getAllWorkers();
        topManagers = workerManager.getAllWorkers().stream()
                        .filter(worker -> {
                            return worker.getManager() == null;
                        }).collect(Collectors.toList());
        for (Worker topManager : topManagers) {
            listWorkerHierarchy(topManager, "");
        }
    }

    private static void addWorker(WorkerManager workerManager, String name, Long managerId) {
        if (managerId != null) {
            Worker manager = workerManager.getWorker(managerId);
            if (manager == null) {
                System.err.println("Error: no worker with id " + managerId);
                return;
            }
            workerManager.saveWorker(new Worker(name, manager));
        } else {
            workerManager.saveWorker(new Worker(name));
        }
    }

    private static void clearWorkers(WorkerManager workerManager) {
        workerManager.clearAllWorkers();
    }

    private static void removeWorker(WorkerManager workerManager, Long id) {
        workerManager.deleteWorker(id);
    }

    private static void listWorkerHierarchy(Worker worker, String indentation) {
        System.out.print(indentation);
        System.out.println("(" + worker.getId() + ") " + worker.getName());
        for (Worker subworker : worker.getSubordinates()) {
            listWorkerHierarchy(subworker, indentation + "--");
        }
    }
    
}
