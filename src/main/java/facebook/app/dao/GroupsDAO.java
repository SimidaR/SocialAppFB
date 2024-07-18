package facebook.app.dao;

import facebook.app.entities.Groups;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GroupsDAO {
    private static final String FILE_NAME = "groups.txt";

    public List<Groups> getAllGroups() {
        List<Groups> groupsList = new ArrayList<>();

        try (InputStream is = readFromFile()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(";");
                    if (parts.length < 4) {
                        continue; // Skip lines that do not have enough parts
                    }

                    int groupId = Integer.parseInt(parts[0].trim());
                    String userIds = parts[1].trim(); // User IDs as a single string
                    String groupName = parts[2].trim();
                    String groupDescription = parts[3].trim();

                    Groups group = new Groups(groupId, userIds, groupName, groupDescription);
                    groupsList.add(group);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupsList;
    }

    public void addGroup(Groups group) {
        List<Groups> groupsList = getAllGroups();

        // Find and update the group if it exists, otherwise add as a new group
        boolean groupExists = false;
        for (int i = 0; i < groupsList.size(); i++) {
            if (groupsList.get(i).getGroupId() == group.getGroupId()) {
                groupsList.set(i, group); // Update the existing group
                groupExists = true;
                break;
            }
        }
        if (!groupExists) {
            groupsList.add(group); // Add as a new group
        }
        // Rewrite the file with updated groups list
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(writeToFile()))) {
            for (Groups g : groupsList) {
                writer.write(g.getGroupId() + ";" + g.getUserId() + ";" + g.getGroupName() + ";" + g.getGroupDescription());
                writer.newLine();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Optional<Groups> getGroupById(int groupId) {
        List<Groups> groupsList = getAllGroups();
        return groupsList.stream()
                .filter(group -> group.getGroupId() == groupId)
                .findFirst();
    }

    private InputStream readFromFile() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(GroupsDAO.FILE_NAME);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + GroupsDAO.FILE_NAME);
        }
        return inputStream;
    }

    private File writeToFile() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(GroupsDAO.FILE_NAME)).toURI());
    }
}
