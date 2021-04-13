package Main.Test;

import org.junit.jupiter.api.Test;
import Main.Home.Model.Crane;

import static org.junit.jupiter.api.Assertions.*;

/*
    CraneRentTest conducts some tests to check if the calcRent method functions correctly
    commented out due to JUnit not being installed on everyone's IDEs
*/
class CraneRentTest {
    private final String idTest = "testCrane";
    private Crane craneTest = new Crane(idTest, idTest, 20);

    /*
        Tests for a case of when rent is long
     */
   @Test
   void calcRentTest() {
       double price = craneTest.calcRent(30);
       assertEquals(420, price);
    }

    /*
        Tests for a case of when rent is short
     */

    @Test
    void calcRentTest1(){
        double price1 = craneTest.calcRent(2);
        assertEquals(40,price1);
    }
}
