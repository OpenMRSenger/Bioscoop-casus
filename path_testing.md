# Path Testing - Prijsberekening Bioscoop

In dit document wordt de teststrategie voor de `calculatePrice` methode in de `Order` klasse beschreven op basis van de **path testing** methode.

## Controle-graaf (Control Flow Graph)

Hieronder staan de nodes van de graaf voor de `calculatePrice()` methode:

- **N1**: Start van de methode (initialiseer `totalPrice = 0.0`)
- **N2**: Loop conditie: `i < tickets.size()`
- **N3**: Binnen de loop: haal ticket op en bepaal `ticketNumber`
- **N4**: Besluit: `isEvenTicket && qualifiesForFree`? (Gratis kaartje regel)
- **N5**: `continue` naar volgende iteratie (gratis kaartje, terug naar N2)
- **N6**: Haal basisprijs van ticket op
- **N7**: Besluit: `isPremiumTicket`?
- **N8**: Besluit: `isStudentOrder`? (Keuze voor €2 of €3 toeslag)
- **N9**: Voeg prijs toe aan `totalPrice` (terug naar N2)
- **N10**: Besluit na loop: `!isStudentOrder && tickets.size() >= 6 && isWeekendOrder()`? (Groepskorting)
- **N11**: Pas 10% korting toe
- **N12**: Return `totalPrice`

### Tekstuele weergave van de graaf

```text
       [N1]
        |
       [N2] <-----------+
      /    \            |
   (F)      (T)         |
    |        [N3]       |
    |         |         |
    |        [N4] --(T) [N5]
    |       /           |
    |     (F)           |
    |      |            |
    |     [N6]          |
    |      |            |
    |     [N7] --(F)-- [N9]
    |      |            |
    |     (T)           |
    |      |            |
    |     [N8]          |
    |     /  \          |
    |  (St)  (Reg)      |
    |   |      |        |
    |   +------+        |
    |      |            |
    |     [N9] ---------+
    |
   [N10] --(F)-- [N12]
    |
   (T)
    |
   [N11]
    |
   [N12]
```

## Test Cases

Op basis van de graaf zijn de volgende test cases bepaald om een goede dekking te bereiken:

| ID | Omschrijving | Invoer (Order / Tickets) | Verwachte Uitkomst | Pad (Nodes) |
|----|--------------|-------------------------|--------------------|-------------|
| TC1 | Lege bestelling | 0 tickets | 0.0 | N1-N2(F)-N10(F)-N12 |
| TC2 | Student, 1 ticket (niet-premium) | 1 ticket, Student, Doordeweeks | 10.0 | N1-N2(T)-N3-N4(F)-N6-N7(F)-N9-N2(F)-N10(F)-N12 |
| TC3 | Student, 2 tickets (niet-premium) | 2 tickets, Student, Doordeweeks | 10.0 (2e gratis) | TC2 + ...-N2(T)-N3-N4(T)-N5-N2(F)-N10(F)-N12 |
| TC4 | Regulier, 1 ticket (premium) | 1 ticket, Regulier, Weekend | 13.0 (10 + 3) | N1-N2(T)-N3-N4(F)-N6-N7(T)-N8(Reg)-N9-N2(F)-N10(F)-N12 |
| TC5 | Student, 1 ticket (premium) | 1 ticket, Student, Weekend | 12.0 (10 + 2) | N1-N2(T)-N3-N4(F)-N6-N7(T)-N8(St)-N9-N2(F)-N10(F)-N12 |
| TC6 | Regulier, 2 tickets (doordeweeks) | 2 tickets, Regulier, Doordeweeks | 10.0 (2e gratis) | N1-N2(T)-N3-N4(F)-N9-...-N4(T)-N5-...-N12 |
| TC7 | Regulier, 6 tickets (weekend) | 6 tickets, Regulier, Weekend | 54.0 (60 - 10%) | N1-(N2-N9)x6-N10(T)-N11-N12 |
| TC8 | Regulier, 6 tickets (doordeweeks) | 6 tickets, Regulier, Doordeweeks | 30.0 (3x10, 3 gratis) | N1-(N2...N9/N5)x6-N10(F)-N12 |

**Toelichting op groepskorting:**
Regel 3 stelt dat groepskorting alleen geldt als het GEEN studentenbestelling is, het in het WEEKEND valt en er 6 of meer kaartjes zijn.
