package edu.upc.dsa;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.ElementType;
import org.apache.log4j.Logger;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class UserManagerImpl implements UserManager {
    private static UserManager instance;
    protected List<User> users;
    protected List<PointOfInterest> pointsOfInterest;
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        this.users = new LinkedList<>();
        this.pointsOfInterest = new LinkedList<>();
    }

    public static UserManager getInstance() {
        if (instance == null) instance = new UserManagerImpl();
        return instance;
    }

    public User addUser(String id, String name, String surname, String email, String birthDate) {
        User user = new User(id, name, surname, email, birthDate);
        this.users.add(user);
        logger.info("new User added: " + user);
        return user;
    }

    public User getUser(String id) {
        for (User u : this.users) {
            if (u.getId().equals(id)) {
                logger.info("getUser(" + id + "): " + u);
                return u;
            }
        }
        logger.warn("User not found: " + id);
        return null;
    }

    public List<User> findAll() {
        return this.users;
    }

    public void deleteUser(String id) {
        User u = this.getUser(id);
        if (u != null) {
            this.users.remove(u);
            logger.info("User deleted: " + u);
        } else {
            logger.warn("User not found: " + id);
        }
    }

    public User updateUser(User user) {
        User u = this.getUser(user.getId());
        if (u != null) {
            u.setName(user.getName());
            u.setSurname(user.getSurname());
            u.setEmail(user.getEmail());
            u.setBirthDate(user.getBirthDate());
            logger.info("User updated: " + u);
            return u;
        } else {
            logger.warn("User not found: " + user.getId());
            return null;
        }
    }

    public List<User> findAllSorted() {
        List<User> sortedUsers = new LinkedList<>(this.users);
        Collections.sort(sortedUsers, Comparator.comparing(User::getSurname).thenComparing(User::getName));
        logger.info("Users sorted");
        return sortedUsers;
    }

    public User getUserById(String id) {
        for (User u : this.users) {
            if (u.getId().equals(id)) {
                logger.info("getUserById(" + id + "): " + u);
                return u;
            }
        }
        logger.warn("User not found: " + id);
        return null;
    }

    public PointOfInterest addPointOfInterest(String x, String y, ElementType type) {
        PointOfInterest poi = new PointOfInterest(Integer.parseInt(x), Integer.parseInt(y), type);
        this.pointsOfInterest.add(poi);
        logger.info("new PointOfInterest added: " + poi);
        return poi;
    }

    public void registerUserAtPoint(String userId, String x, String y) {
        User user = this.getUser(userId);
        if (user == null) {
            logger.warn("User not found: " + userId);
            throw new IllegalArgumentException("User not found");
        }

        PointOfInterest poi = null;
        for (PointOfInterest p : this.pointsOfInterest) {
            if (String.valueOf(p.getX()).equals(x) && String.valueOf(p.getY()).equals(y)) {
                poi = p;
                break;
            }
        }

        if (poi == null) {
            logger.warn("PointOfInterest not found at coordinates: (" + x + ", " + y + ")");
            throw new IllegalArgumentException("PointOfInterest not found");
        }

        user.getPointsOfInterest().add(poi);
        logger.info("User " + userId + " registered at PointOfInterest: (" + x + ", " + y + ")");
    }

    public List<PointOfInterest> getUserPointsOfInterest(String userId) {
        User user = this.getUser(userId);
        if (user == null) {
            logger.warn("User not found: " + userId);
            throw new IllegalArgumentException("User not found");
        }
        return user.getPointsOfInterest();
    }

    public List<User> getUsersByPointOfInterest(String x, String y) {
        final PointOfInterest poi = this.pointsOfInterest.stream()
                .filter(p -> String.valueOf(p.getX()).equals(x) && String.valueOf(p.getY()).equals(y))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("PointOfInterest not found at coordinates: (" + x + ", " + y + ")");
                    return new IllegalArgumentException("PointOfInterest not found");
                });

        return this.users.stream()
                .filter(user -> user.getPointsOfInterest().contains(poi))
                .collect(Collectors.toList());
    }

    public List<PointOfInterest> getPointsOfInterestByType(ElementType type) {
        return this.pointsOfInterest.stream()
                .filter(p -> p.getType() == type)
                .collect(Collectors.toList());
    }

    public void clear() {
        this.users.clear();
        this.pointsOfInterest.clear();
    }
}