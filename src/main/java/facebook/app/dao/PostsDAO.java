package facebook.app.dao;

import facebook.app.entities.Posts;
import facebook.app.services.PostServiceInterface;
import facebook.app.entities.User;

import java.io.*;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PostsDAO implements PostServiceInterface {
    private static final String DATABASE_FILE_PATH = "posts.txt";
    public PostsDAO() {
    }

    public List<Posts> getAllPosts(User user) {
        List<Posts> posts = new ArrayList<>();

        // Check if user is null
        if (user == null || user.getUserId() < 1) {
            System.out.println("There's no user.");
            return posts; // Return an empty list or throw an exception as needed
        }

        try (InputStream inputStream = readFromFile();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] postData = line.split(";");
                if (postData.length >= 3 && Objects.equals(postData[0], String.valueOf(user.getUserId()))) {
                    int userId = Integer.parseInt(postData[0]);
                    String timePosted = postData[1];
                    String content = postData[2];
                    Posts post = new Posts(userId, timePosted, content);
                    posts.add(post);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as needed
        }
        return posts;
    }

    public Posts getLatestPost(User user) {
        List<Posts> latestPosts = getAllPosts(user);
        if (latestPosts.isEmpty()) {
            return null;
        } else {
            return latestPosts.get(latestPosts.size() - 1);
        }
    }

    public List<Posts> getRecentPosts(User user, int posts) {
        List<Posts> latestPostsFromUser = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_FILE_PATH)) {
            assert inputStream != null;
            try (InputStreamReader streamReader = new InputStreamReader(inputStream);
                 BufferedReader reader = new BufferedReader(streamReader)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] postData = line.split(";");
                    if (String.valueOf(user.getUserId()).equals(postData[0])) {
                        Posts appPost = new Posts((int) user.getUserId(), postData[1], postData[2]);
                        latestPostsFromUser.add(appPost);
                    }
                }
                latestPostsFromUser.sort(Comparator.comparing(Posts::getTimePosted).reversed());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latestPostsFromUser.stream().limit(posts).collect(Collectors.toList());
    }

    public void createPost(Posts post) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(writeToFile(), true))) {
            String line = String.format("%d;%s;%s", post.getUser(), post.getTimePosted(), post.getContent());
            writer.write(line);
            writer.newLine();
            writer.flush();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace(); // Or handle the exception as needed
        }
    }

    public List<Posts> getPostsFromAnotherUser(int userId) {
        List<Posts> userPosts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(readFromFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] postData = line.split(";");
                if (Integer.parseInt(postData[0].trim()) == userId) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String postDate = String.valueOf(sdf.parse(postData[1].trim()));
                        Posts appPost = new Posts(userId, postDate, postData[2]);
                        userPosts.add(appPost);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userPosts;
    }

    private InputStream readFromFile() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_FILE_PATH);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + DATABASE_FILE_PATH);
        }
        return inputStream;
    }

    private File writeToFile() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(DATABASE_FILE_PATH)).toURI());
    }
}
