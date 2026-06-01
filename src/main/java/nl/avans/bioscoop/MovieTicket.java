package nl.avans.bioscoop;

/**
 * Een kaartje voor een specifieke stoel bij een {@link MovieScreening}.
 * Een kaartje kan premium zijn; de premiumtoeslag wordt echter niet hier,
 * maar in {@link Order#calculatePrice()} berekend, omdat de hoogte van de
 * toeslag afhangt van of het een studentenbestelling is.
 */
public class MovieTicket {
    private final MovieScreening movieScreening;
    private final boolean isPremiumTicket;
    private final int rowNr;
    private final int seatNr;

    public MovieTicket(MovieScreening movieScreening, boolean isPremiumTicket, int rowNr, int seatNr) {
        this.movieScreening = movieScreening;
        this.isPremiumTicket = isPremiumTicket;
        this.rowNr = rowNr;
        this.seatNr = seatNr;
    }

    /**
     * @return de basisprijs van dit kaartje (de prijs per stoel van de
     *         voorstelling), nog zonder eventuele premiumtoeslag.
     */
    public double getPrice() {
        return movieScreening.getPricePerSeat();
    }

    public boolean isPremiumTicket() {
        return isPremiumTicket;
    }

    public MovieScreening getMovieScreening() {
        return movieScreening;
    }

    public int getRowNr() {
        return rowNr;
    }

    public int getSeatNr() {
        return seatNr;
    }

    @Override
    public String toString() {
        String prefix = isPremiumTicket ? "Premium ticket" : "Ticket";
        return prefix + " voor " + movieScreening
                + " - rij " + rowNr + ", stoel " + seatNr;
    }
}
