package Main.InventoryHelper;

import java.text.ParseException;

public interface IAdapter {
    public void init() throws ParseException;

    public void custom(Object...args);
}
