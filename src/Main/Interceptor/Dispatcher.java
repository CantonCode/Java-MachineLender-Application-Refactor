package Main.Interceptor;

import java.util.ArrayList;

public class Dispatcher implements IDispatcher, IInterceptor {
    private ArrayList<IInterceptor> interceptors;

    public Dispatcher(){
        interceptors = new ArrayList<>();
    }

    @Override
    public void registerInterceptor(IInterceptor interceptor){
        interceptors.add(interceptor);
    }

    @Override
    public void onProgramStart(IContextObject context) {
        interceptors.forEach(i -> i.onProgramStart(context));
    }

    @Override
    public void onPreLogin(IContextObject context) {
        interceptors.forEach(i -> i.onPreLogin(context));
    }

    @Override
    public void onLoginAttempt(IContextObject context) {
        interceptors.forEach(i -> i.onLoginAttempt(context));
    }

    @Override
    public void onPostLogin(IContextObject context) {
        interceptors.forEach(i -> i.onPostLogin(context));
    }

    @Override
    public void onPreCatalog(IContextObject context) {
        interceptors.forEach(i -> i.onPreCatalog(context));
    }

    @Override
    public void onPostCatalog(IContextObject context) {
        interceptors.forEach(i -> i.onPostCatalog(context));
    }

    @Override
    public void onPreInventory(IContextObject context) {
        interceptors.forEach(i -> i.onPreInventory(context));
    }

    @Override
    public void onPostInventory(IContextObject context) {
        interceptors.forEach(i -> i.onPostInventory(context));
    }

    @Override
    public void onPreBorrow(IContextObject context) {
        interceptors.forEach(i -> i.onPreBorrow(context));
    }

    @Override
    public void onPostBorrow(IContextObject context) {
        interceptors.forEach(i -> i.onPostBorrow(context));
    }

    @Override
    public void onProgramExit(IContextObject context) {
        interceptors.forEach(i -> i.onProgramExit(context));
    }

}
