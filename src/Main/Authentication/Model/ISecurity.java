package Main.Authentication.Model;

public interface ISecurity {
    public String layerOne(String password);
    public String layerTwo(String password);
    public String layerThree(String password);
}
