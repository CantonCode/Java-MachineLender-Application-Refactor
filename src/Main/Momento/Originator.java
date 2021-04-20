package Main.Momento;

public class Originator<T> {
    private T state;

    public void setState(T state){
        this.state = state;
    }

    // create a new momento to hokd the current state
    public Momento<T> storeState(){
        return new ConcreteMomento<T>(state);
    }

    // retrive State from momento
    public T retrieveState(Momento<T> m){
        this.state = m.getState();
        return this.state;
    }

}
