package Main.Interceptor;

public class LoginAttemptsInterceptor implements IInterceptor{
    private int attempts;

    @Override
    public void onProgramStart(IContextObject context) {
        attempts = 0;
    }

    @Override
    public void onPreLogin(IContextObject context) {
        attempts = attempts + 1;
        System.out.printf("%d Login attempts.",attempts);


        // (if attempts > x)

    }

    @Override
    public void onPostLogin(IContextObject context) {
        System.out.printf("%d Login attempts.",attempts);
    }

    @Override
    public void onPreCatalog(IContextObject context) {

    }

    @Override
    public void onPostCatalog(IContextObject context) {

    }

    @Override
    public void onPreInventory(IContextObject context) {

    }

    @Override
    public void onPostInventory(IContextObject context) {

    }

    @Override
    public void onPreBorrow(IContextObject context) {

    }

    @Override
    public void onPostBorrow(IContextObject context) {

    }

    @Override
    public void onProgramExit(IContextObject context) {

    }


}
