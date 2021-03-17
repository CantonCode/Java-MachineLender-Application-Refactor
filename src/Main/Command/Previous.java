package Main.Command;

import Main.Navigation;

public class Previous implements ICommand {
    Navigation nav;
    public Previous(Navigation nav){this.nav=nav;}
    @Override
    public void execute() {
        nav.previous();
    }
}
