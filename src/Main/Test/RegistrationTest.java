package Main.Test;

import Main.Authentication.Logic.RegisterController;
import Main.Authentication.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RegistrationTest {
    private final RegisterController regTest = new RegisterController();
    private final String validPassword = "Tester123";
    private final String validEmail = "tester@email.com";
    private final String validUsername = "tester01";
    private final String invalidPassword = "test12";
    private final String invalidEmail = "tester @test,,com";



    @Test()
    public void validationTestTrue(){
        try {
            boolean actual = regTest.validate("tester", validUsername, validPassword, validEmail);
            assertTrue(actual);
        }catch (Exception ignored) {

        }
    }

    @Test()
    public void validationTestInvalidPassword(){
        try {
            boolean actual = regTest.validate("tester", validUsername, invalidPassword, validEmail);
            assertFalse(actual);
        }catch (Exception ignored) {

        }
    }

    @Test()
    public void validationTestInvalidEmail(){
        try {
            boolean actual = regTest.validate("tester", validUsername, validPassword, invalidEmail);

            assertFalse(actual);
        }catch (Exception ignored) {

        }
    }

    @Test()
    public void testBuilderCorrectlyCreatingCustomer() {
        CustomerBuilder customerBuilder = new CustomerBuilder().setId("1").setName(validUsername).setPassword(validPassword);

        customerBuilder.setType(AccountType.CUSTOMER);
        User user = customerBuilder.createCustomer();

        AccountType userType = user.getType();

        assertEquals(AccountType.CUSTOMER,userType);
    }


}

