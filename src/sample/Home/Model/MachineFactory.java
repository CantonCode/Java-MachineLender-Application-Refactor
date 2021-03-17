package sample.Home.Model;

/*
    Factory design pattern for machines
 */
public class MachineFactory implements AbstractMachineFactory{
    @Override
    public Machine createNewMachine(String type) {
        if (type == null || type.isEmpty())
            return null;

        if ("Digger".equals(type)) {
            System.out.println("CREATING DIGGER TYPE");
            return new Digger();
        } else if ("Crane".equals(type)) {
            System.out.println("CREATING CRANE TYPE");
            return new Crane();
        }
        
        return null;
    }
}
