package CinemaLaba;

import java.io.Serializable;
import java.util.*;

public class BasicHall extends Hall {


    public BasicHall(String name, int capacity, Collection<String> supportedTypes)
    {
        int x = (int) Math.sqrt(capacity);
        this.capacity = capacity;
        this.supportedTypes = new HashSet<>(supportedTypes);
        this.name = name;
        repairingState = 1;
    }

    @Override
    public boolean canShow(Film f)
    {
       return supportedTypes.contains(f.getType());
    }

    @Override
    public void cosmeticRepair() {
        repairingState += (1 - repairingState)/2;
    }

    @Override
    public void capitalRepair() {
        repairingState = 1;
    }


    @Override
    public String toString() {
        return name + "\nПоддерживаемые типы: "+ supportedTypes;
    }
}
