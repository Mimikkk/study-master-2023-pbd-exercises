# Opis charakteru danych

Na nieznanym kontynencie istnieją ekspedycje naukowe korzystające z helikopterów, aby dokonywać przeloty i obserwować
różnorodność gatunków zwierząt zamieszkujących ten tajemniczy ląd.

Głównym celem tego przedsięwzięcia jest przeprowadzenie systematycznych badań nad różnymi gatunkami, ich klasyfikacja, a
także monitorowanie ich populacji w celu zrozumienia dynamiki ekosystemu na tym kontynencie. W trakcie przelotów,
badacze zauważają skupiska zwierząt, które następnie starają się zliczyć, zidentyfikować i sklasyfikować.

Skupiska zwierząt są kategoryzowane według kilku kryteriów, takich jak nazwa gatunkowa, nazwa naukowa, rodzaj gatunkowy
oraz liczba osobników. W ten sposób naukowcy mają możliwość śledzenia dynamiki populacji. Ważnym elementem tego procesu
jest również uwzględnienie etykiety czasowej związaną z momentem odkrycia skupiska zwierząt.

Ze względu na trudności związane z komunikacją na tym nieznanym kontynencie, istnieje możliwość, że etykieta czasowa
może losowo spóźniać się w stosunku do czasu systemowego maksymalnie do 30 sekund.

# Opis atrybutów

Atrybuty w każdym zdarzeniu mają następujące znaczenie:

- name
    - typ: string
    - znaczenie: nazwa gatunkowa
    - kategoria: atrybut, lub atrybuty, których wartości pozwalają na identyfikacje zwierzęcia
- latin
    - typ: string
    - znaczenie: nazwa naukowa zwierzęcia
    - kategoria: atrybut lub atrybuty dodatkowe, opisujące zdarzenie
- species
    - typ: string
    - znaczenie:  gutunek zwierzęcia
    - kategoria: atrybut lub atrybuty, których wartości można grupować
- genus
    - typ: string
    - znaczenie: rodzaj gatunkowy zwierzęcia
    - kategoria: atrybut, lub atrybuty, po których można dane grupować
- population
    - typ: long (1 - 9999)
    - znaczenie: liczba odnalezionej grupy zwierząt
    - kategoria: atrybut lub atrybuty, których wartości można agregować
- ts
    - typ: string
    - znaczenie: czas odkrycia skupiska zwierząt
    - kategoria: znacznik czasowy zdarzeń

# Opis trzech przykładowych analiz

## Agregacja

Utrzymuj informacje dotyczące średniej liczby osobników każdego gatunku w ostatnich 5 odkryciach.

## Wykrywanie anomalii

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy populacji poniżej 500 osobników na podstawie pojedyńczego
zdarzenia.

## Wykrywanie anomalii oparte na agregacji

Wykrywaj przypadki, w których ostatnie 3 odkryte grupy zwierząt określonego gatunku będą miały poniżej 1000 osobników.

# Zadania

## Zestaw zadań 2:

## Zadanie 1 - Prosta agregacja

Utrzymuj informacje dotyczące średniej liczby osobników względem gatunku w ostatnich 100 odkryciach.

### Polecenie

```epl
select species, avg(population) as avg_population
  from AnimalGroupDiscoveryEvent#length(100)
  group by species;
```

## Zadanie 2 - Proste wykrywanie anomalii (selekcją zdarzeń):

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy populacji poniżej 500 osobników na podstawie pojedynczego
zdarzenia.

### Polecenie

```epl
select name, population
from AnimalGroupDiscoveryEvent#length(25) where population < 500;
```

## Zadanie 3 - Wykrywanie anomalii wykorzystujące wyniki agregacji:

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy osobników.
Na zagrożenie wskazuje fakt, że liczba osobników zaobserwowana w danej grupie jest mniejsza niż 25% średniej liczby
osobników w całej populacji gatunku.

### Polecenie

```epl
select name, population, avg(population) as avg_population
from AnimalGroupDiscoveryEvent
group by name having avg(population) / 4 > population;
```

## Zadanie 4 - Złożony przypadek:

rozwiązania wymaga użycia:

- nazwanego okna lub tabeli
- połączenia lub wielu etapów przetwarzania

Utrzymuj średnią liczbą odrytych zwierząt w każdym z gatunków dla każdych kolejnych 20 sekund.

Odnajdź takie nowe zdarzenia, które znajdują się w oknie liczności odkrytych gatunków oraz ich liczność przewyższa
aktualną średnią wartość.

```epl
create window AnimalCounter as select * from KursAkcji group by genus;
```
