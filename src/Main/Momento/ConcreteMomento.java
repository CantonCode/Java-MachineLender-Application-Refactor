package Main.Momento;

public class ConcreteMomento<T> implements Momento {
    private T state;
    public ConcreteMomento(T state){
        this.state = state;
    }
    @Override
    public T getState() {
        return state;
    }
}
