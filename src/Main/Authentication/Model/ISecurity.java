package Main.Authentication.Model;

public interface ISecurity {
    String layerOne(String password);
    String layerTwo(String password);
    String layerThree(String password);
}
