/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.contact.contactmanager.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Course")
public class Course {
   
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    
    private String coursename;
    
    private String fee;

    public Course(int id, String coursename, String fee) {
        this.id = id;
        this.coursename = coursename;
        this.fee = fee;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", coursename=" + coursename + ", fee=" + fee + '}';
    }
    
    
    
}
