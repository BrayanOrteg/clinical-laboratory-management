package model;

import java.util.*;

public class Patient implements Comparable<Patient>{

   private String name;
   private Calendar BDate;
   private Integer priority;
   private AggravationEnum aggravation;
   private  long id;
   private  String causeOfAdmission;

   private  StatusPatientEnum statusPatient;

   public Patient(String name, Calendar BDate,String causeOfAdmission , Integer priority, AggravationEnum aggravation, StatusPatientEnum statusPatient, long id) {
      this.name = name;
      this.BDate = BDate;
      this.priority = priority;
      this.aggravation = aggravation;
      this.causeOfAdmission=causeOfAdmission;
      this.statusPatient=statusPatient;
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public Integer getPriority() {
      return priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   @Override
   public int compareTo(Patient toCompare) {
      return priority-toCompare.getPriority();
   }

   public String getCauseOfAdmission() { return causeOfAdmission; }

   public void setCauseOfAdmission(String causeOfAdmission) { this.causeOfAdmission = causeOfAdmission; }

   public StatusPatientEnum getStatusPatient() { return statusPatient; }

   public void setStatusPatient(StatusPatientEnum statusPatient) { this.statusPatient = statusPatient; }


}



