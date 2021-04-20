package Main.InventoryHelper;

import java.text.ParseException;

public interface IAdapter {
    void init() throws ParseException;

    void custom(Object... args);
}
