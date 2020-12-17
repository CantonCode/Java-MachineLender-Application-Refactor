package sample.Command;

public class NavigationInvoker {
    ICommand command;
    public NavigationInvoker(ICommand command){
        this.command=command;
    }
    public void activate(){
        this.command.execute();
    }
}
