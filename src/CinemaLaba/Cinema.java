package CinemaLaba;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cinema implements Serializable {

    private String name;
    private String description;
    private List<Hall> halls;
    private List<Session> sessions;
    private int incomes;

    public Cinema(String name, String description, List<Hall> halls)
    {
        this.name = name;
        this.description = description;
        this.halls = new ArrayList<>(halls);
        sessions = new ArrayList<>();
        incomes = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public void createSession(Hall h, Date d, Film f, int cost)
    {
        if(!halls.contains(h)) throw new IllegalArgumentException("Кинотеатр не имеет этот зал");
        try{
            Session s = new Session(f,d,h,cost);
            sessions.add(s);
        }
        catch (RepairStateException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    public void buyTicket(Client c,Session s, int x, int y) throws IllegalAccessException {
        if(!sessions.contains(s)) throw new IllegalArgumentException("Такого сеанса нет в кинотеатре");
        var cost = getPrice(s,c);
        c.Withdraw(cost);
        s.allocatePlace(x,y);
        incomes += cost;

    }

    public String getName() {
        return name;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public String getDescription() {
        return description;
    }

    public int getIncomes() {
        return incomes;
    }
    public int getPrice(Session s, Client c)
    {
        int cost = s.getCost();
        if(c.getTicketsBought() > 3)
            cost = (int) (0.7 * cost);
        else if(c.getTicketsBought() >=2)
            cost = (int)(0.8*cost);
        return cost;
    }

    public void RepairHall(Hall h, int repairType)
    {
        if(h.getRepairingState() == 1) throw new IllegalArgumentException("Зал в отличном состоянии");

        if(repairType ==0)
        {
            if(incomes < 500) throw new IllegalArgumentException("Недостаточно средств для ремонта");
            h.cosmeticRepair();
            incomes-=500;
        }
        if(repairType == 1)
        {
            if(incomes < 1000) throw new IllegalArgumentException("Недостаточно средств для ремонта");
            h.capitalRepair();
            incomes-=1000;
        }
    }
}
