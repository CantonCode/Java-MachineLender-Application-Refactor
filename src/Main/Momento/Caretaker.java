package Main.Momento;

import java.util.ArrayList;
import java.util.List;

public class Caretaker<T> {
    private ArrayList<Momento<T>> states = new ArrayList<>();

    public Caretaker(){

    }
    public void saveState(Momento<T> state){
        states.add(state);
    }
    public void refresh(int to){
        List<Momento<T>> newState = new ArrayList<>(states.subList(0,to));
        states.clear();
        this.states.addAll(newState);
    }
    public Momento<T> getState(int i){
        return states.get(i);
    }
}
