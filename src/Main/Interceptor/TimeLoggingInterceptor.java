package Main.Interceptor;

public class TimeLoggingInterceptor implements IInterceptor {

    //// get post - pre and print. #time for functions.
    private long start;

    @Override
    public void onProgramStart(IContextObject context) {

    }

    @Override
    public void onPreLogin(IContextObject context) {
        start = System.currentTimeMillis();
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
        System.out.print("Time taken Viewing Catalog: ");
        System.out.println(System.currentTimeMillis() - start);
        start = 0;
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
