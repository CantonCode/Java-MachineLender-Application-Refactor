package Main.Interceptor;

public class PostLoginContext implements IContextObject {
    private String currentUser;

    public PostLoginContext(){
        this("Current User");
    }

    public PostLoginContext(String currentUser){
        this.currentUser = currentUser;
    }

    @Override
    public String getUserName() {
        return currentUser;
    }
}
