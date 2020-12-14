package sample.Home.Model;

public class MachineFactory {
    public Machine createNewMachine(String type){
        if(type == null || type.isEmpty())
            return null;

        if("Digger".equals(type)){
            return new Digger();
        }else if("Crane".equals(type)){
            return new Crane();
        }

        return null;
    }
}
