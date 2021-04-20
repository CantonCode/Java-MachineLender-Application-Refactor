package Main.Interceptor;

public interface IInterceptor {

    void onProgramStart(IContextObject context); //
    void onPreLogin(IContextObject context); //
    void onLoginAttempt(IContextObject context); //
    void onPostLogin(IContextObject context); //
    void onPreCatalog(IContextObject context); // when catalog button has been selected.
    void onPostCatalog(IContextObject context); // when back button has been selected from catalog screen.
    void onPreInventory(IContextObject context); // when user presses a button to view borrowed machines.
    void onPostInventory(IContextObject context); // when the data for borrowed machines has been loaded into memory.
    void onPreBorrow(IContextObject context); // when a user selects the borrow machine button.
    void onPostBorrow(IContextObject context); //
    void onProgramExit(IContextObject context);
}
