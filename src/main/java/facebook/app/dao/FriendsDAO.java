package facebook.app.dao;

import facebook.app.entities.Friends;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class FriendsDAO {
    private static final String FILE_NAME = "friends.txt";

    public List<Friends> getAllFriends() {
        List<Friends> friendsList = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + FILE_NAME);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    Friends friend = new Friends(
                            Integer.parseInt(parts[0].trim()),
                            Integer.parseInt(parts[1].trim()),
                            parts[2].trim());
                    friendsList.add(friend);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return friendsList;
    }

    public void addFriend(Friends friend) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getResourceFile(), true))) {
            writer.write(friend.getUserId() + ";" + friend.getFriendId() + ";" + friend.getFriendNameID());
            writer.newLine();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void removeFriend(int userId, int friendId) {
        List<Friends> friendsList = getAllFriends();
        List<Friends> updatedFriendsList = friendsList.stream()
                .filter(friend -> !(friend.getUserId() == userId && friend.getFriendId() == friendId) &&
                        !(friend.getUserId() == friendId && friend.getFriendId() == userId))
                .collect(Collectors.toList());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getResourceFile(), false))) {
            for (Friends friend : updatedFriendsList) {
                writer.write(friend.getUserId() + ";" + friend.getFriendId() + ";" + friend.getFriendNameID());
                writer.newLine();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private File getResourceFile() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(FILE_NAME)).toURI());
    }
}
