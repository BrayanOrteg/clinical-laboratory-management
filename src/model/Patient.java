package model;

import java.util.*;

public class Patient implements Comparable<Patient>{

   private String name;
   private Calendar BDate;
   private Integer priority;
   private StateEnum admissionCause;
   private AggravationEnum aggravation;
   private  Integer id;
   private  String causeOfAdmission;

   public Patient(String name, Calendar BDate,String causeOfAdmission , Integer priority, StateEnum admissionCause, AggravationEnum aggravation, Integer id) {
      this.name = name;
      this.BDate = BDate;
      this.priority = priority;
      this.admissionCause = admissionCause;
      this.aggravation = aggravation;
      this.causeOfAdmission=causeOfAdmission;
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

   public String getCauseOfAdmission() { return causeOfAdmission; }

   public void setCauseOfAdmission(String causeOfAdmission) { this.causeOfAdmission = causeOfAdmission; }
}



