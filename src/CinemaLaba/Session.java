package CinemaLaba;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable {
    private Film film;
    private Date date;
    private Hall hall;
    private int cost;

    private boolean[][] places;

    public Session(Film f, Date d, Hall h, int cost) throws RepairStateException {
        if(!h.canShow(f)) throw new IllegalCallerException("Фильм не поддерживается");
        film = f;
        date = d;
        hall = h;
        this.cost = cost;
        var x = (int)Math.sqrt(hall.getCapacity());
        hall.ShowFilm();

        places = new boolean[x][x];
    }

    public Film getFilm() {
        return film;
    }

    public Hall getHall() {
        return hall;
    }

    public Date getDate() {
        return date;
    }

    public int getCost() {
        return cost;
    }

    public void allocatePlace(int x, int y)
    {
        if(x <0 || y < 0 || y > places.length || x > places[0].length )
            throw new IndexOutOfBoundsException("Некорректное место в зале");
        if(places[y][x]) throw new IllegalCallerException("Место занято");

        places[y][x] = true;

    }

    public void outputScheme(){
        for(int i = 0; i < places.length; i++)
        {
            System.out.print(i+"\t");
            for(int j = 0; j < places[i].length; j++)
            {
                System.out.print(j);
                if(!places[i][j])
                    System.out.print("\uD83D\uDFE7");
                else System.out.print("\uD83D\uDD32");
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Фильм: "+ film.getName()+ " Дата сеанса: "+ date;
    }

}
