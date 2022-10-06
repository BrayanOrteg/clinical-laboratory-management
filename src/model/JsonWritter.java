package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonWritter {

    public void Write(Patient[] toSave){

        Gson gson = new Gson();

        String json = gson.toJson(toSave);

        return true;
    }

    public static void main(String[] args) {
        Gson gson = new Gson();

        ArrayList<Patient> people = new ArrayList<>();



        String json = gson.toJson(people);
        System.out.print(json);

        try {
            FileOutputStream fos = new FileOutputStream(new File("people.txt"));
            fos.write( json.getBytes(StandardCharsets.UTF_8) );
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
