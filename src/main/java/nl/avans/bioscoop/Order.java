package nl.avans.bioscoop;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Een bestelling van een of meer {@link MovieTicket}s. De Order bevat de
 * prijsberekening (inclusief alle kortingsregels) en kan zichzelf exporteren
 * naar een tekst- of JSON-bestand.
 */
public class Order {
    private final int orderNr;
    private final boolean isStudentOrder;
    private final List<MovieTicket> tickets = new ArrayList<>();

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public int getNumberOfTickets() {
        return tickets.size();
    }

    /**
     * Voegt een stoelreservering (kaartje) toe aan deze bestelling.
     */
    public void addSeatReservation(MovieTicket ticket) {
        tickets.add(ticket);
    }

    /**
     * Berekent de totaalprijs van de bestelling volgens de bedrijfsregels:
     * <ol>
     *   <li>Per kaartje de basisprijs, plus een premiumtoeslag van &euro;2
     *       (studenten) of &euro;3 (regulier) als het kaartje premium is.</li>
     *   <li>Elk even kaartje (2e, 4e, 6e, ...) is gratis als het een
     *       studentenbestelling is OF als de voorstelling op een doordeweekse
     *       dag (ma t/m do) valt. Een gratis kaartje kost helemaal niets, dus
     *       ook geen premiumtoeslag.</li>
     *   <li>Groepskorting: is het geen studentenbestelling, valt het in het
     *       weekend (vr/za/zo) en bestaat de bestelling uit 6 of meer
     *       kaartjes, dan gaat er 10% van het totaal af.</li>
     * </ol>
     */
    public double calculatePrice() {
        double totalPrice = 0.0;

        for (int i = 0; i < tickets.size(); i++) {
            MovieTicket ticket = tickets.get(i);
            int ticketNumber = i + 1; // 1-gebaseerd: 1e, 2e, 3e, ...

            // Regel 2: ieder even kaartje gratis bij studenten- of doordeweekse order.
            boolean isEvenTicket = ticketNumber % 2 == 0;
            boolean qualifiesForFree = isStudentOrder || isWeekday(ticket);
            if (isEvenTicket && qualifiesForFree) {
                continue; // gratis: geen basisprijs en geen premiumtoeslag
            }

            // Regel 1: basisprijs + eventuele premiumtoeslag.
            double ticketPrice = ticket.getPrice();
            if (ticket.isPremiumTicket()) {
                ticketPrice += isStudentOrder ? 2.0 : 3.0;
            }
            totalPrice += ticketPrice;
        }

        // Regel 3: groepskorting van 10% in het weekend voor niet-studenten.
        if (!isStudentOrder && tickets.size() >= 6 && isWeekendOrder()) {
            totalPrice *= 0.9;
        }

        return totalPrice;
    }

    /**
     * Een voorstelling valt doordeweeks als deze op maandag t/m donderdag is.
     */
    private boolean isWeekday(MovieTicket ticket) {
        DayOfWeek day = ticket.getMovieScreening().getDateAndTime().getDayOfWeek();
        return day == DayOfWeek.MONDAY
                || day == DayOfWeek.TUESDAY
                || day == DayOfWeek.WEDNESDAY
                || day == DayOfWeek.THURSDAY;
    }

    /**
     * Bepaalt of de bestelling in het weekend (vr/za/zo) valt. We gaan ervan
     * uit dat alle kaartjes van een bestelling voor dezelfde voorstelling zijn
     * en nemen daarom de dag van het eerste kaartje als peildatum.
     */
    private boolean isWeekendOrder() {
        if (tickets.isEmpty()) {
            return false;
        }
        return !isWeekday(tickets.get(0));
    }

    /**
     * Exporteert de bestelling naar het gevraagde formaat en schrijft het
     * resultaat weg naar een bestand in de huidige map.
     *
     * @param exportFormat PLAINTEXT (.txt) of JSON (.json)
     * @return de geexporteerde inhoud als String
     */
    public String export(TicketExportFormat exportFormat) {
        String content;
        String fileName;

        switch (exportFormat) {
            case JSON:
                content = toJson();
                fileName = "order_" + orderNr + ".json";
                break;
            case PLAINTEXT:
            default:
                content = toPlainText();
                fileName = "order_" + orderNr + ".txt";
                break;
        }

        writeToFile(fileName, content);
        return content;
    }

    private String toPlainText() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Bestelling ").append(orderNr).append(" ===").append(System.lineSeparator());
        sb.append("Studentenbestelling: ").append(isStudentOrder ? "ja" : "nee")
                .append(System.lineSeparator());
        sb.append("Aantal kaartjes: ").append(tickets.size()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        for (MovieTicket ticket : tickets) {
            sb.append("- ").append(ticket).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        sb.append("Totaalprijs: ").append(String.format("EUR %.2f", calculatePrice()));
        return sb.toString();
    }

    private String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(System.lineSeparator());
        sb.append("  \"orderNr\": ").append(orderNr).append(",").append(System.lineSeparator());
        sb.append("  \"isStudentOrder\": ").append(isStudentOrder).append(",").append(System.lineSeparator());
        sb.append("  \"price\": ").append(String.format(java.util.Locale.ROOT, "%.2f", calculatePrice()))
                .append(",").append(System.lineSeparator());
        sb.append("  \"tickets\": [").append(System.lineSeparator());

        for (int i = 0; i < tickets.size(); i++) {
            MovieTicket ticket = tickets.get(i);
            MovieScreening screening = ticket.getMovieScreening();
            sb.append("    {").append(System.lineSeparator());
            sb.append("      \"movie\": \"").append(escapeJson(screening.getMovie().getTitle()))
                    .append("\",").append(System.lineSeparator());
            sb.append("      \"dateAndTime\": \"").append(screening.getDateAndTime())
                    .append("\",").append(System.lineSeparator());
            sb.append("      \"rowNr\": ").append(ticket.getRowNr()).append(",")
                    .append(System.lineSeparator());
            sb.append("      \"seatNr\": ").append(ticket.getSeatNr()).append(",")
                    .append(System.lineSeparator());
            sb.append("      \"isPremium\": ").append(ticket.isPremiumTicket())
                    .append(System.lineSeparator());
            sb.append("    }").append(i < tickets.size() - 1 ? "," : "")
                    .append(System.lineSeparator());
        }

        sb.append("  ]").append(System.lineSeparator());
        sb.append("}");
        return sb.toString();
    }

    /** Escaped de tekens die in een JSON-string niet zonder meer mogen. */
    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Kon bestand niet wegschrijven: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return toPlainText();
    }
}
