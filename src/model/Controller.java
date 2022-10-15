package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Controller {

    Patient [] Hashpatients;
    Patient [] Priotiypatients;

    public Controller() {
    }

    public Patient RegisterEntry(){

        return Priotiypatients[0];
    }

    public Patient RegisterEgress(){

        return  Hashpatients[0];

    }

    public void WriteJson(ArrayList<Patient> toSave){

        Gson gson = new Gson();

        String json = gson.toJson(toSave);

        System.out.print(json);

        try {
            FileOutputStream fos = new FileOutputStream(new File("dataBase\\patients.txt"));
            fos.write( json.getBytes(StandardCharsets.UTF_8) );
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadJson() {
        try {
            File file = new File("dataBase\\patients.txt");
            System.out.println("\n\nExiste: "+file.exists() + file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;
            if((line=reader.readLine())!=null){
                json= line;
            }
            fis.close();
            System.out.println(json);

            Gson gson = new Gson();
            Patient[] patienstFromJson = gson.fromJson(json, Patient[].class);

            ArrayList<Patient> people = new ArrayList<>();
            for(Patient p : patienstFromJson){
                people.add(p);
                System.out.println(p.getPriority() + " " + p.getCauseOfAdmission());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
