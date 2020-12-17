package sample.Observer;

public interface Subject {
    //this method will register a new observer
    public void register(Observer o);
    //this  method will remove an observer
    public void unregister(Observer o);
    //this method will notify obsers of any changes
    public void notifyObserver();
}
