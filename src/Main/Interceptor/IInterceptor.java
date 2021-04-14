package Main.Interceptor;

public interface IInterceptor {

    /// time stamp functions?
    void onPreLogin(IContextObject context);
    void onPostLogin(IContextObject context);

    void meeeow(IContextObject context);
}
