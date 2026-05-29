import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Een film die in de bioscoop gedraaid kan worden. Een film houdt zelf bij
 * welke voorstellingen ({@link MovieScreening}) er van hem gepland zijn.
 */
public class Movie {
    private final String title;
    private final List<MovieScreening> screenings = new ArrayList<>();

    public Movie(String title) {
        this.title = title;
    }

    /**
     * Plant een nieuwe voorstelling van deze film en koppelt deze meteen
     * aan de film.
     *
     * @param dateAndTime  het tijdstip waarop de voorstelling begint
     * @param pricePerSeat de basisprijs per stoel voor deze voorstelling
     * @return de aangemaakte voorstelling
     */
    public MovieScreening createScreening(LocalDateTime dateAndTime, double pricePerSeat) {
        MovieScreening screening = new MovieScreening(this, dateAndTime, pricePerSeat);
        screenings.add(screening);
        return screening;
    }

    public String getTitle() {
        return title;
    }

    public List<MovieScreening> getScreenings() {
        return screenings;
    }

    @Override
    public String toString() {
        return title;
    }
}
