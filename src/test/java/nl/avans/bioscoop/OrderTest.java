package nl.avans.bioscoop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private Movie movie;
    private MovieScreening weekdayScreening;
    private MovieScreening weekendScreening;

    @BeforeEach
    void setUp() {
        movie = new Movie("Test Movie");
        // Weekday: Wednesday, June 3, 2026
        weekdayScreening = movie.createScreening(LocalDateTime.of(2026, 6, 3, 20, 0), 10.0);
        // Weekend: Saturday, June 6, 2026
        weekendScreening = movie.createScreening(LocalDateTime.of(2026, 6, 6, 20, 0), 10.0);
    }

    @Test
    @DisplayName("TC1: Lege bestelling moet 0.0 kosten")
    void testEmptyOrder() {
        Order order = new Order(1, false);
        assertEquals(0.0, order.calculatePrice(), 0.001);
    }

    @Test
    @DisplayName("TC2: Student, 1 ticket (niet-premium) doordeweeks")
    void testStudentSingleTicketWeekday() {
        Order order = new Order(1, true);
        order.addSeatReservation(new MovieTicket(weekdayScreening, false, 1, 1));
        assertEquals(10.0, order.calculatePrice(), 0.001);
    }

    @Test
    @DisplayName("TC3: Student, 2 tickets (niet-premium) doordeweeks - 2e is gratis")
    void testStudentTwoTicketsWeekday() {
        Order order = new Order(1, true);
        order.addSeatReservation(new MovieTicket(weekdayScreening, false, 1, 1));
        order.addSeatReservation(new MovieTicket(weekdayScreening, false, 1, 2));
        assertEquals(10.0, order.calculatePrice(), 0.001);
    }

    @Test
    @DisplayName("TC4: Regulier, 1 ticket (premium) weekend - +3.0 toeslag")
    void testRegularPremiumWeekend() {
        Order order = new Order(1, false);
        order.addSeatReservation(new MovieTicket(weekendScreening, true, 1, 1));
        assertEquals(13.0, order.calculatePrice(), 0.001);
    }

    @Test
    @DisplayName("TC5: Student, 1 ticket (premium) weekend - +2.0 toeslag")
    void testStudentPremiumWeekend() {
        Order order = new Order(1, true);
        order.addSeatReservation(new MovieTicket(weekendScreening, true, 1, 1));
        assertEquals(12.0, order.calculatePrice(), 0.001);
    }

    @Test
    @DisplayName("TC6: Regulier, 2 tickets doordeweeks - 2e is gratis")
    void testRegularTwoTicketsWeekday() {
        Order order = new Order(1, false);
        order.addSeatReservation(new MovieTicket(weekdayScreening, false, 1, 1));
        order.addSeatReservation(new MovieTicket(weekdayScreening, false, 1, 2));
        assertEquals(10.0, order.calculatePrice(), 0.001);
    }

    @Test
    @DisplayName("TC7: Regulier, 6 tickets weekend - 10% groepskorting")
    void testRegularSixTicketsWeekend() {
        Order order = new Order(1, false);
        for (int i = 1; i <= 6; i++) {
            order.addSeatReservation(new MovieTicket(weekendScreening, false, 1, i));
        }
        // 6 * 10 = 60. 10% korting = 54.0
        assertEquals(54.0, order.calculatePrice(), 0.001);
    }

    @Test
    @DisplayName("TC8: Regulier, 6 tickets doordeweeks - 3 gratis, geen groepskorting")
    void testRegularSixTicketsWeekday() {
        Order order = new Order(1, false);
        for (int i = 1; i <= 6; i++) {
            order.addSeatReservation(new MovieTicket(weekdayScreening, false, 1, i));
        }
        // 1, 3, 5 are paid (30.0). 2, 4, 6 are free.
        assertEquals(30.0, order.calculatePrice(), 0.001);
    }
}
