package edu.upc.dsa;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.ElementType;
import java.util.List;


public interface UserManager {
    User addUser(String id, String name, String surname, String email, String birthDate);
    User getUser(String id);
    List<User> findAll();
    void deleteUser(String id);
    User updateUser(User user);
    List<User> findAllSorted();
    User getUserById(String id);
    PointOfInterest addPointOfInterest(String x, String y, ElementType type);
    void registerUserAtPoint(String userId, String x, String y);
    List<PointOfInterest> getUserPointsOfInterest(String userId);
    List<User> getUsersByPointOfInterest(String x, String y);
    List<PointOfInterest> getPointsOfInterestByType(ElementType type);
    void clear();
}