/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.workers.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Dmitry
 */
@Entity
public class Worker implements Serializable {

    /* Default constructor is NECESSARY for JPA to work */
    public Worker() {
    }

    public Worker(String name, Worker manager) {
        this.name = name;
        this.manager = manager;
    }

    public Worker(String name) {
        this.name = name;
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @ManyToOne
    private Worker manager;
    
    @OneToMany(mappedBy = "manager", cascade=ALL)
    private List<Worker> subordinates;

    public Worker getManager() {
        return manager;
    }

    public void setManager(Worker manager) {
        this.manager = manager;
    }

    public List<Worker> getSubordinates() {
        if (subordinates == null)
            return new ArrayList<>();
        return subordinates;
    }
    
    public List<Worker> getAllSubordinates() {
        List<Worker> result = new ArrayList<>(getSubordinates());
        subordinates.stream().forEach(sw -> result.addAll(sw.getAllSubordinates()));
        return result;
    }

    public void setSubordinates(List<Worker> subordinates) {
        this.subordinates = subordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Worker)) {
            return false;
        }
        Worker other = (Worker) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.Worker[ id=" + id + " ]";
    }
    
}
