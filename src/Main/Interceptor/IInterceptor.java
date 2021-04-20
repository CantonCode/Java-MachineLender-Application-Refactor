package Main.Interceptor;

public interface IInterceptor {

    void onProgramStart(IContextObject context);
    void onPreLogin(IContextObject context);
    void onLoginAttempt(IContextObject context);
    void onPostLogin(IContextObject context);
    void onPreCatalog(IContextObject context);
    void onPostCatalog(IContextObject context);
    void onPreInventory(IContextObject context);
    void onPostInventory(IContextObject context);
    void onPreBorrow(IContextObject context);
    void onPostBorrow(IContextObject context);
    void onProgramExit(IContextObject context);
}
