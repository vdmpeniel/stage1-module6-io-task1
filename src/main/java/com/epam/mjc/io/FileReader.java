package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



public class FileReader {

    private String readFile(File file){
        StringBuilder content = new StringBuilder();
        try(FileInputStream fileInputStream = new FileInputStream(file)){
            int character;
            while((character = fileInputStream.read()) != -1 ){
                content.append((char) character);
            }

        } catch(IOException ioe){
            return ioe.getMessage();
        }
        return content.toString();
    }

    private void populateField(Profile profile, String field, String value){
        try {
            switch (field) {
                case "Name":
                    profile.setName(value);
                    break;
                case "Age":
                    profile.setAge(Integer.valueOf(value));
                    break;
                case "Email":
                    profile.setEmail(value);
                    break;
                case "Phone":
                    profile.setPhone(Long.valueOf(value));
                    break;
                default:
                    throw new IllegalArgumentException("Field " + field + "is not allowed.");
            }
        } catch(IllegalArgumentException iae){
            iae.printStackTrace();
        }
    }

    private String[] fields = {"Name", "Age", "Email", "Phone"};
    private Profile parseContent(String content){
        String[] lines = content.split("\r?\n");

        Profile profile = new Profile();
        for(String str : lines){
            for(String field : fields){
                if(str.contains(field)){
                    populateField(profile, field, str.replace(field + ": ", ""));
                }
            }
        }
        return profile;
    }

    public Profile getDataFromFile(File file) {
        return parseContent(readFile(file));
    }

}
