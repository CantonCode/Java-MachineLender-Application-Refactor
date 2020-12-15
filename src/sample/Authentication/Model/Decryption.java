package sample.Authentication.Model;

/*
    Class for decrypting passwords encrypted using the Encryption class.
 */
public class Decryption extends Security{
    @Override
    public String layerOne(String password) {
        //every second letter is flipped with the one before it
        char tempChar;
        StringBuilder sb = new StringBuilder(password);

        if (password.length()%2 == 1) {
            tempChar = sb.charAt(0);
            sb.setCharAt(0, sb.charAt(sb.length() - 1));
            sb.setCharAt(sb.length() - 1, tempChar);
        }

        for (int i = 0; i < sb.length() - 1; i+=2) {
            tempChar = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(i+1));
            sb.setCharAt(i+1, tempChar);
        }
        password = sb.toString();

        //then the last and the first is switched
        return password;
    }

    @Override
    public String layerTwo(String password) {
        char tempChar;
        StringBuilder sb = new StringBuilder(password);

        sb.reverse();

        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, (char) (sb.charAt(i)-2));
        }

        password = sb.toString();
        return password;
    }
}
