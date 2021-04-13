package Main.Interceptor;

import java.util.ArrayList;

public class Dispatcher implements IDispatcher, IInterceptor {
    private ArrayList<IInterceptor> interceptors;

    public Dispatcher(){
        interceptors = new ArrayList<>();
    }

    @Override
    public void register(IInterceptor interceptor){
        interceptors.add(interceptor);
    }

    @Override
    public void onPreLogin(IContextObject context) {
        interceptors.forEach(i -> i.onPreLogin(context));
    }

    @Override
    public void onPostLogin(IContextObject context) {
        interceptors.forEach(i -> i.onPostLogin(context));
    }

    @Override
    public void meeeow(IContextObject context) {
        interceptors.forEach(i -> i.meeeow(context));
    }
}
