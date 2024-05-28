package CinemaLaba;

import java.io.Serializable;

public class Client implements Serializable, Registrable {
    private String name;
    private String phone;
    private String mail;
    private int money;
    private int ticketsBought;

    public Client(String name, String phone, String mail, int budget)
    {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        money = budget;
        ticketsBought = 0;
    }

    public String getMail() {
        return mail;
    }

    public int getMoney() {
        return money;
    }

    public String getPhone() {
        return phone;
    }

    public int getTicketsBought() {
        return ticketsBought;
    }

    public void Withdraw(int cost) throws IllegalAccessException {
        if(money < cost) throw new IllegalAccessException("Недостаточно средств, не хватает: "+ (cost - money));
        money-=cost;
        ticketsBought++;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getLogin() {
        return name;
    }

    @Override
    public boolean validPass(String pass) {
        return true;
    }
}
