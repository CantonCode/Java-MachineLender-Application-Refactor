package Main.Interceptor;

public interface IInterceptor {

    /// time stamp functions?
    void onProgramStart(IContextObject context);

    void onPreLogin(IContextObject context);
    void onPostLogin(IContextObject context);


    void onPreCatalog(IContextObject context);
    void onPostCatalog(IContextObject context);

    void onPreInventory(IContextObject context);
    void onPostInventory(IContextObject context);

    void onPreBorrow(IContextObject context);
    void onPostBorrow(IContextObject context);

    void onProgramExit(IContextObject context);
}
