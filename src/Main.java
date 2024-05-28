import CinemaLaba.*;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Client> clients;
    private static List<Film> films;
    private static List<Cinema> cinemas;

    private static Administrator administrator = new Administrator("Admin", "pass");

    private static void Load()
    {
        try{
            cinemas = (ArrayList<Cinema>) loadObject("cinemas.bin");
            films = (ArrayList<Film>) loadObject("films.bin");
            clients = (ArrayList<Client>) loadObject("clients.bin");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            clients = new ArrayList<>();
            films = new ArrayList<>();
            cinemas = new ArrayList<>();

            cinemas.add(new Cinema("Default cinema", "Decr",
                    List.of(
                            new BasicHall("Обычный зал",81, List.of("2D")),
                            new BasicHall("Зал с диванами", 16, List.of("2D", "3D", "4D")),
                            new BasicHall("Детский зал", 36, List.of("2D", "3D")))));
            films.add(new Film("test", "20.01.1990", "2D","Comedy"));
        }

    }

    private static void Save() throws IOException {
       saveObj(clients,"clients.bin");
       saveObj(films,"films.bin");
       saveObj(cinemas, "cinemas.bin");
    }

    private static void saveObj(Object data, String filename) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename));
        stream.writeObject(data);
        stream.close();
    }
    private static Object loadObject(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename));
        Object obj = stream.readObject();
        stream.close();
        return obj;
    }

    private static Client register()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваши имя, номер и почту:");
        var name = scanner.next();
        var number = scanner.next();
        var mail = scanner.next();
        System.out.println("Введите желаемый депозит:");
        var money = scanner.nextInt();
        System.out.println("Транкзакция успешна");
        var client = new Client(name,number,mail,money);
        clients.add(client);
        return client;
    }
    private static Client login()
    {
        System.out.println("Введите ваше имя: ");
        Scanner scanner = new Scanner(System.in);
        var name = scanner.next();
        var client = clients.stream().filter(c -> c.getLogin().equals(name)).findFirst();
        if(client.isEmpty())
        {
            System.out.println("Вы не заригистрированны\nПеренаправление на регистрацию...");
            return register();
        }
        return client.get();
    }
    private static void administrating(Administrator adm)
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("""
                    1.Вывести доходы кинотератра
                    2.Вывести список пользователей
                    3.Создать фильм
                    4.Создать сеанс
                    5.Вывсти статус залов
                    6.Отремонтировать зал
                    0.Выйти""");
            var res = scanner.nextInt();
            switch (res) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    for(int i = 0;i<cinemas.size();i++)
                        System.out.println(i+" "+ cinemas.get(i));
                    var cinema = cinemas.get(scanner.nextInt());
                    adm.printCinemaIncomes(cinema);
                }
                case 2 -> adm.printClientTypes(clients);
                case 3 -> films.add(adm.createFilm());
                case 4 -> {
                    for(int i = 0;i<cinemas.size();i++)
                        System.out.println(i+" "+ cinemas.get(i));
                    var cinema = cinemas.get(scanner.nextInt());
                    try {
                        adm.createSession(cinema,films);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 5 -> {
                    for(int i = 0;i<cinemas.size();i++)
                        System.out.println(i+" "+ cinemas.get(i));
                    var cinema = cinemas.get(scanner.nextInt());
                    adm.printReparingStat(cinema);
                }
                case 6 -> {
                    for(int i = 0;i<cinemas.size();i++)
                        System.out.println(i+" "+ cinemas.get(i));
                    var cinema = cinemas.get(scanner.nextInt());
                    adm.repairHalls(cinema);
                }
            }
        }

    }
    private static void clienting(Client c)
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("1.Купить билет\n0.Выйти");
            var res = scanner.next();
            if(res.equals("0")) return;
            if(res.equals("1"))
            {
                for(int i = 0;i<cinemas.size();i++)
                    System.out.println(i+" "+ cinemas.get(i));
                var cinema = cinemas.get(scanner.nextInt());
                if(cinema.getSessions().size() == 0)
                {
                    System.out.println("Нет доступных сеансов");
                    continue;
                }
                for(int i = 0;i<cinema.getSessions().size();i++)
                    System.out.println(i+" "+ cinema.getSessions().get(i));
                var session = cinema.getSessions().get(scanner.nextInt());
                session.outputScheme();
                System.out.println("Цена: "+cinema.getPrice(session,c));
                System.out.println("Выберите ряд и место:");
                int y = scanner.nextInt();
                int x = scanner.nextInt();
                try {
                    cinema.buyTicket(c,session,x,y);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.println("Билет успешно приобретен");
            }
        }

    }
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Load();
        while (true)
        {
            System.out.println("1.Войти\n2.Зарегистрироваться\n0.Выйти");
            var res = scanner.next();
            if(res.equals("0")) break;
            if(res.equals("Secret"))
            {
                System.out.print("Loading");
                for(int i = 0; i <3;i++)
                {
                    Thread.sleep(i*150);
                    System.out.print(".");
                }
                System.out.println();
                System.out.println("ВВЕДИТЕ ПАРОЛЬ:");
                var pass = scanner.next();
                if(!administrator.validPass(pass))
                {
                    System.out.println("НЕВЕРНЫЙ ПАРОЛЬ");
                    continue;
                }
                System.out.println("ПАРОЛЬ ПРИНЯТ");
                System.out.print("ОБРАБОТКА");
                for(int i = 0; i <3;i++)
                {
                    Thread.sleep(i* 200);
                    System.out.print("-A");
                }
                System.out.println();
                System.out.println("Добро пожаловать {name};");
                administrating(administrator);
                continue;
            }
            Client client;
            if(res.equals("2"))
            {
                client = register();
            }
            else if(res.equals("1"))
            {
                client = login();
            }
            else break;
            clienting(client);
        }
        try {
            Save();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}