package Main.Momento;

import java.util.ArrayList;

public class Caretaker<T> {
    private ArrayList<Momento<T>> states = new ArrayList<>();

    public Caretaker(){

    }
    public void saveState(Momento<T> state){
        states.add(state);
    }
    public Momento<T> getState(int i){
        return states.get(i);
    }
}
