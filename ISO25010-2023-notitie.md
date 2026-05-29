# Notitie: Wat verandert er in ISO/IEC 25010:2023 t.o.v. 2011?

**Aan:** ontwikkelteam
**Onderwerp:** update van het kwaliteitsmodel voor productkwaliteit
**Datum:** 29-05-2026

## Aanleiding

Tot nu toe baseren we onze kwaliteitsdiscussies op ISO/IEC 25010:2011. In 2023 is een
herziene versie verschenen. Het model is geen complete ombouw, maar er zijn genoeg
wijzigingen om onze checklists en reviewsjablonen op aan te passen. Hieronder een
beknopt overzicht van wat er is toegevoegd, hernoemd of inhoudelijk gewijzigd in het
**product quality model**. (Het aparte *quality in use*-model laat ik hier buiten
beschouwing.)

## Het grote plaatje

In 2011 had het productkwaliteitsmodel **8 hoofdkarakteristieken**. In 2023 zijn dat er
**9**: er is een volledig nieuwe karakteristiek **Safety** bijgekomen, en twee bestaande
karakteristieken zijn hernoemd en van inhoud gewijzigd (*Usability* -> *Interaction
Capability*, *Portability* -> *Flexibility*). Daarnaast zijn er op subniveau diverse
toevoegingen en hernoemingen.

| 2011 | 2023 | Status |
|------|------|--------|
| Functional Suitability | Functional Suitability | ongewijzigd |
| Performance Efficiency | Performance Efficiency | ongewijzigd |
| Compatibility | Compatibility | ongewijzigd |
| Usability | **Interaction Capability** | hernoemd + uitgebreid |
| Reliability | Reliability | subkarakteristiek hernoemd |
| Security | Security | subkarakteristiek toegevoegd |
| Maintainability | Maintainability | ongewijzigd |
| Portability | **Flexibility** | hernoemd + uitgebreid |
| - | **Safety** | **nieuw** |

## Wijzigingen per karakteristiek

### Nieuw: Safety
De grootste verandering. Safety gaat over het beperken van schade aan mensen, bezit of
omgeving onder zowel normale als afwijkende omstandigheden. Subkarakteristieken o.a.:
*operational constraint*, *risk identification*, *fail safe*, *hazard warning* en *safe
integration*. Relevant nu software steeds vaker in veiligheidskritische en
cyber-physical contexten draait (denk aan medische apparatuur, automotive, IoT).

### Usability -> Interaction Capability
De karakteristiek is verbreed van "hoe makkelijk is het te gebruiken" naar "hoe goed
ondersteunt het de interactie tussen gebruiker en systeem". Behouden blijven o.a.
*appropriateness recognizability*, *learnability* en *operability*. Wijzigingen:
- *User interface aesthetics* -> **User engagement** (breder dan alleen esthetiek;
  gaat over motiverende, prettige interactie).
- *Accessibility* -> **Inclusivity** (toegankelijk voor mensen met uiteenlopende
  kenmerken en mogelijkheden).
- Nieuw: **Self-descriptiveness** en **User assistance** (de mate waarin het systeem
  zichzelf uitlegt en hulp biedt).

### Portability -> Flexibility
Verbreed van "kun je het naar een andere omgeving verhuizen" naar "hoe goed past het
zich aan veranderende eisen, contexten en gebruik aan". Behouden: *adaptability*,
*installability*, *replaceability*. Toegevoegd:
- **Scalability** (meeschalen met groeiende of krimpende belasting/omvang).

### Security
Grotendeels gelijk (*confidentiality*, *integrity*, *non-repudiation*, *accountability*,
*authenticity*), met een toevoeging:
- **Resistance** (weerstand bieden tegen aanvallen en onder druk blijven functioneren).

### Reliability
Inhoudelijk vergelijkbaar, maar:
- *Maturity* -> **Faultlessness** (de mate waarin het systeem onder normaal gebruik
  foutloos functioneert). *Availability*, *fault tolerance* en *recoverability* blijven.

### Ongewijzigd
*Functional Suitability* (completeness, correctness, appropriateness),
*Performance Efficiency* (time behaviour, resource utilization, capacity),
*Compatibility* (co-existence, interoperability) en *Maintainability* (modularity,
reusability, analysability, modifiability, testability) zijn qua structuur en
benaming gelijk gebleven.

## Wat betekent dit voor ons?
- **Checklists/reviews bijwerken**: hernoem Usability -> Interaction Capability en
  Portability -> Flexibility, en voeg de nieuwe subkarakteristieken toe.
- **Safety expliciet meewegen** bij projecten met fysieke of veiligheidsimpact; voor
  puur administratieve software zal het vaak beperkt van toepassing zijn, maar het
  moet bewust afgewogen worden.
- **Security**: neem *resistance* mee in dreigingsanalyses.
- **Niet-functionele eisen**: bij Flexibility kunnen we voortaan expliciet over
  *scalability* praten in plaats van het impliciet onder portability te scharen.

*Bron: ISO/IEC 25010:2023, Systems and software engineering — SQuaRE — Product
quality model. Raadpleeg de officiele standaard voor de exacte definities.*
