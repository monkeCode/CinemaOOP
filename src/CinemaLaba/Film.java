package CinemaLaba;
import java.io.Serializable;

public class Film implements Serializable {
    private String name;
    private String creatingDate;
    private String type;
    private String genre;

    public Film(String name, String creatingDate, String type, String genre)
    {
        this.name = name;
        this.creatingDate = creatingDate;
        this.type = type;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name +" Жанр: "+ genre+ " Дата создания: "+ creatingDate;
    }

}

