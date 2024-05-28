package CinemaLaba;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Administrator implements Registrable {
    private String login;
    private String pass;

    public Administrator(String login, String pass)
    {
        this.login = login;
        this.pass = pass;
    }

    public void printCinemaIncomes(Cinema c)
    {
        System.out.println(c.getIncomes());
    }
    public void printClientTypes(List<Client> clients)
    {
        for(var c:clients)
        {
            System.out.print(c+ " ");
            var type = switch (c.getTicketsBought()) {
                case 0,1 -> "Обычный";
                case 2,3 -> "Друг сети";
                default -> "VIP";
            };
            System.out.println(type);
        }
    }

    public void createSession(Cinema c, List<Film> films) throws ParseException {
        if(films.size() == 0)
        {
            System.out.println("фильмы отсутствуют, сначала создайте их");
            films.add(createFilm());
        }
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i<films.size();i++)
        {
            System.out.println(i+ " "+ films.get(i));
        }
        var film = films.get(scanner.nextInt());
        System.out.println("Напишите дату сеанса формата dd.MM hh:mm");
        Date date = new SimpleDateFormat("dd.MM HH:mm").parse(scanner.next() +" "+ scanner.next());
        var supportedHalls = c.getHalls().stream().filter(hall -> hall.canShow(film)).toList();
        for(int i = 0; i < supportedHalls.size(); i++)
        {
            System.out.println(i+" "+ supportedHalls.get(i));
        }
        var hall = supportedHalls.get(scanner.nextInt());
        System.out.println();
        System.out.println("Введите обычную стоимость сеанса");
        var price = scanner.nextInt();
        c.createSession(hall, date,film,price);
    }

    public Film createFilm()
    {
        System.out.println("Название:");
        Scanner s = new Scanner(System.in);
        var name = s.next();
        System.out.println("Дата создания: ");
        var date = s.next();
        System.out.println("Формат(2D, 3D, 4D, 5D):");
        var type = s.next();
        System.out.println("Жанр:");
        var genre = s.next();
        return new Film(name,date, type, genre);
    }

    public void printReparingStat(Cinema c)
    {
        for(int i = 0; i < c.getHalls().size(); i++)
        {
            System.out.println(i+" "+ c.getHalls().get(i)+" Состояние: "+ c.getHalls().get(i).repairingState*100+"%");
        }
    }
    public void repairHalls(Cinema c)
    {

        printReparingStat(c);
        Scanner s = new Scanner(System.in);
        var hall = c.getHalls().get(s.nextInt());
        System.out.println("0. Косметический ремонт\n1. Капитальный ремонт");
        var ch = s.nextInt();

        try{
            c.RepairHall(hall,ch);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public boolean validPass(String pass) {
        return this.pass.equals(pass);
    }
}
