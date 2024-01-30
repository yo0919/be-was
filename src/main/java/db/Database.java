package db;

import model.User;
import model.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private static List<Post> posts = new ArrayList<>();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public static void addPost(Post post) {
        posts.add(post);
    }

    public static List<Post> getAllPosts() {
        return posts;
    }
}
