package Main.Interceptor;

public class TimeLoggingInterceptor implements IInterceptor {

    //// get post - pre and print. #time for functions.
    private long start;

    @Override
    public void onProgramStart(IContextObject context) {
        System.out.println("Application has started, hello from time logging interceptor.");
    }

    @Override
    public void onPreLogin(IContextObject context) {
        start = System.currentTimeMillis();
    }

    @Override
    public void onLoginAttempt(IContextObject context) {

    }

    @Override
    public void onPostLogin(IContextObject context) {
        System.out.print("Time taken to Log In: ");
        System.out.println(System.currentTimeMillis() - start);
        start = 0;
    }

    @Override
    public void onPreCatalog(IContextObject context) {
        start = System.currentTimeMillis();
    }

    @Override
    public void onPostCatalog(IContextObject context) {
        System.out.printf("User: %s ", context.getUserName());
        System.out.print("Time taken Viewing Catalog: ");
        System.out.println(System.currentTimeMillis() - start);
        start = 0;
    }

    @Override
    public void onPreInventory(IContextObject context) {
        start = System.currentTimeMillis();
    }

    @Override
    public void onPostInventory(IContextObject context) {
        System.out.printf("User: %s ", context.getUserName());
        System.out.print("Time taken Viewing Inventory: ");
        System.out.println(System.currentTimeMillis() - start);
        start = 0;
    }

    @Override
    public void onPreBorrow(IContextObject context) {
        start = System.currentTimeMillis();
    }

    @Override
    public void onPostBorrow(IContextObject context) {
        System.out.printf("User: %s ", context.getUserName());
        System.out.print("Time taken in Borrowing Screen: ");
        System.out.println(System.currentTimeMillis() - start);
        start = 0;
    }

    @Override
    public void onProgramExit(IContextObject context) {

    }


}
