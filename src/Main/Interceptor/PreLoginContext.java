package Main.Interceptor;

public class PreLoginContext implements IContextObject{
    @Override
    public String getUserName() {
        return "Unregistered User";
    }
}
