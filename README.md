# Bioscoop-casus - Deel 2: Testen

Dit project is de uitwerking van deel 2 van de Bioscoop casus voor het vak Softwaredesign & -kwaliteit.

## Nieuwe Features in Deel 2
- **Unit Testing**: Automatische testen toegevoegd voor de prijsberekening in de `Order` klasse.
- **Path Testing**: Test cases zijn bepaald op basis van de path testing methode. Zie `path_testing.md` voor de graaf en toelichting.
- **CI Pipeline**: GitHub Actions workflow (`maven.yml`) die bij elke push en pull request de testen runt.
- **SonarCloud Integratie**: Voorbereid in `pom.xml` voor metrieken zoals testcoverage en complexiteit.
- **Maven**: Het project is omgezet naar een Maven project voor dependency management en build automatisering.

## Uitvoeren van testen
Om de unit testen lokaal uit te voeren, gebruik:
```bash
mvn test
```
De coverage rapporten worden gegenereerd door JaCoCo en zijn te vinden in `target/site/jacoco/index.html` na het draaien van de testen.

## Projectstructuur
- `src/main/java/nl/avans/bioscoop`: De broncode van het bioscoopsysteem.
- `src/test/java/nl/avans/bioscoop`: De unit testen.
- `.github/workflows`: De CI pipeline configuratie.
- `path_testing.md`: Documentatie van de path testing analyse.
