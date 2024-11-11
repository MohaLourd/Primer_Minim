package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserManagerTest {

    private UserManager um;

    @Before
    public void setUp() {
        um = UserManagerImpl.getInstance();
        ((UserManagerImpl) um).clear(); // Reiniciar la instancia
        um.addUser("1", "John", "Doe", "john.doe@example.com", "1990-01-01");
        um.addUser("2", "Jane", "Smith", "jane.smith@example.com", "1992-02-02");

        um.addPointOfInterest("10", "20", ElementType.DOOR);
        um.addPointOfInterest("30", "40", ElementType.TREE);
    }

    @Test
    public void testAddUser() {
        User user = um.addUser("3", "Alice", "Johnson", "alice.johnson@example.com", "1995-03-03");
        Assert.assertNotNull(user);
        Assert.assertEquals("Alice", user.getName());
    }

    @Test
    public void testGetUser() {
        User user = um.getUser("1");
        Assert.assertNotNull(user);
        Assert.assertEquals("John", user.getName());
    }

    @Test
    public void testGetUserPointsOfInterest() {
        um.registerUserAtPoint("1", "10", "20");
        um.registerUserAtPoint("1", "30", "40");
        List<PointOfInterest> points = um.getUserPointsOfInterest("1");
        Assert.assertEquals(2, points.size());
    }

    @Test
    public void testGetUsersByPointOfInterest() {
        um.registerUserAtPoint("1", "10", "20");
        um.registerUserAtPoint("2", "10", "20");
        List<User> users = um.getUsersByPointOfInterest("10", "20");
        Assert.assertEquals(2, users.size());
    }


}