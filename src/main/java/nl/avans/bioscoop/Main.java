package nl.avans.bioscoop;

import java.time.LocalDateTime;

/**
 * Kleine demonstratie van het bioscoopmodel. Maakt een paar bestellingen aan,
 * print de berekende prijs en exporteert naar tekst en JSON.
 */
public class Main {
    public static void main(String[] args) {
        Movie movie = new Movie("Dune: Part Two");

        // Een doordeweekse voorstelling (woensdag) en een weekendvoorstelling (zaterdag).
        MovieScreening weekday = movie.createScreening(LocalDateTime.of(2026, 6, 3, 20, 0), 10.0);
        MovieScreening weekend = movie.createScreening(LocalDateTime.of(2026, 6, 6, 20, 0), 10.0);

        // Reguliere bestelling, doordeweeks: 2e kaartje gratis.
        Order regularWeekday = new Order(1, false);
        regularWeekday.addSeatReservation(new MovieTicket(weekday, false, 1, 1));
        regularWeekday.addSeatReservation(new MovieTicket(weekday, true, 1, 2)); // gratis
        System.out.println("Order 1 (regulier, doordeweeks): EUR "
                + String.format("%.2f", regularWeekday.calculatePrice())
                + "  (verwacht 10,00)");

        // Studentenbestelling, weekend: 2e kaartje gratis dankzij studentenregel.
        Order studentWeekend = new Order(2, true);
        studentWeekend.addSeatReservation(new MovieTicket(weekend, true, 2, 1));  // 10 + 2 toeslag
        studentWeekend.addSeatReservation(new MovieTicket(weekend, false, 2, 2)); // gratis
        System.out.println("Order 2 (student, weekend):      EUR "
                + String.format("%.2f", studentWeekend.calculatePrice())
                + "  (verwacht 12,00)");

        // Reguliere groepsbestelling van 6 in het weekend: geen gratis kaartjes,
        // wel 10% groepskorting. 6 x 10 = 60 -> 10% korting = 54,00.
        Order group = new Order(3, false);
        for (int seat = 1; seat <= 6; seat++) {
            group.addSeatReservation(new MovieTicket(weekend, false, 3, seat));
        }
        System.out.println("Order 3 (regulier, weekend, 6x): EUR "
                + String.format("%.2f", group.calculatePrice())
                + "  (verwacht 54,00)");

        // Exporteren naar bestand.
        group.export(TicketExportFormat.PLAINTEXT);
        group.export(TicketExportFormat.JSON);
        System.out.println();
        System.out.println("Order 3 geexporteerd naar order_3.txt en order_3.json");
    }
}
