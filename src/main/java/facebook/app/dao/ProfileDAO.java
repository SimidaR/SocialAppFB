package facebook.app.dao;

import facebook.app.entities.Profile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO {
    private final List<Profile> profileList = new ArrayList<>();
    private final File file = new File(FILE_NAME);
    private static final String FILE_NAME = "src/main/resources/profile.txt";

    public List<Profile> readProfile() {
        if (!file.exists()) {
            System.err.println("File not found: " + FILE_NAME);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                int id = Integer.parseInt(userData[0].trim());
                String name = userData[1].trim();
                String email = userData[2].trim();
                int age = Integer.parseInt(userData[3].trim());
                String location = userData[4].trim();

                Profile profile = new Profile(id, name, email, age, location);
                profileList.add(profile);
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return profileList;
    }

    public void writeProfile(int id, String name, String email, int age, String location) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(id + ";" + name + ";" + email + ";" + age + ";" + location);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
