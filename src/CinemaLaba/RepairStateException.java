package CinemaLaba;

public class RepairStateException extends Exception {

    private final Hall hall;
    public RepairStateException(Hall hall, String message)
    {
        super(message);
        this.hall = hall;
    }

    public Hall getHall() {
        return hall;
    }
}
