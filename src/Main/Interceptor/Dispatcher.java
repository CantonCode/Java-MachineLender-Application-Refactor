package Main.Interceptor;

import java.util.ArrayList;

public class Dispatcher {
    private ArrayList<IInterceptor> interceptors;

    Dispatcher(){
        interceptors = new ArrayList<>();
    }

    void register(IInterceptor interceptor){
        interceptors.add(interceptor);
    }

    void dispatchInterceptor(/*need to pass context*/){
        interceptors.forEach(i -> i.execute(/*need to pass context*/));
    }
}
