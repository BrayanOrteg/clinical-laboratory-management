package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonWritter {

    public void Write(ArrayList<PatientNode> toSave){

        Gson gson = new Gson();

        String json = gson.toJson(toSave);

        System.out.print(json);

        try {
            FileOutputStream fos = new FileOutputStream(new File("patients.txt"));
            fos.write( json.getBytes(StandardCharsets.UTF_8) );
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
