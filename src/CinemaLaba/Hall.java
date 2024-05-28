package CinemaLaba;

import java.io.Serializable;
import java.util.Random;
import java.util.Set;

public abstract class Hall implements Serializable {

    protected Set<String> supportedTypes;
    protected String name;
    protected float repairingState;
    protected int capacity;
    public abstract boolean canShow(Film f);

    public abstract void cosmeticRepair();

    public abstract void capitalRepair();

    public void ShowFilm() throws RepairStateException {
        repairingState -= new Random().nextInt(0, Math.min(20, (int)repairingState*100))/100.0f;

        if(repairingState <= 0)
        {
            throw new RepairStateException(this,"Зал полностью уничтожен)");
        }
    }

    public float getRepairingState() {
        return repairingState;
    }

    public int getCapacity() {
        return capacity;
    }

}
