package sample.Home.Model;

public interface IMachineFactory {
    Machine createNewMachine(String type);
}

///TODO: refactor calling code to use abstract factory instead of concrete.