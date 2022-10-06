package model;

import java.util.*;

public class Patient implements Comparable<Patient>{

   private String name;
   private Calendar BDate;
   private Integer priority;
   private CauseOfAdmissionEnum admissionCause;
   private  AgravationEnum agravation;
   private  Integer id;

   public Patient(String name, Calendar BDate, Integer priority, CauseOfAdmissionEnum admissionCause, AgravationEnum agravation, Integer id) {
      this.name = name;
      this.BDate = BDate;
      this.priority = priority;
      this.admissionCause = admissionCause;
      this.agravation = agravation;
      this.id = id;
   }

   public Integer getPriority() {
      return priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   @Override
   public int compareTo(Patient toCompare) {
      return priority-toCompare.getPriority();
   }
}



