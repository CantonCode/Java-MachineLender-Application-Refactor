package sample.Authentication.Model;

abstract class Security implements ISecurity{
    @Override
    public String layerOne(String password) {
        return null;
    }

    @Override
    public String layerTwo(String password) {
        return null;
    }
}
