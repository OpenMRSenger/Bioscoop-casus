import java.time.LocalDateTime;

/**
 * Een geplande voorstelling van een {@link Movie} op een bepaald tijdstip,
 * met een eigen basisprijs per stoel.
 */
public class MovieScreening {
    private final Movie movie;
    private final LocalDateTime dateAndTime;
    private final double pricePerSeat;

    public MovieScreening(Movie movie, LocalDateTime dateAndTime, double pricePerSeat) {
        this.movie = movie;
        this.dateAndTime = dateAndTime;
        this.pricePerSeat = pricePerSeat;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    @Override
    public String toString() {
        return movie.getTitle() + " (" + dateAndTime + ")";
    }
}
