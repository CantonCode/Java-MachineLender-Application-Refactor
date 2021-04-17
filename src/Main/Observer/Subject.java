package Main.Observer;

public interface Subject {
    //this method will register a new observer
    void register(Observer o);
    //this  method will remove an observer
    void unregister(Observer o);
    //this method will notify obsers of any changes
    void notifyObserver();
}
