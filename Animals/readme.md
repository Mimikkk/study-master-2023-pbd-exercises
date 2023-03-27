## Laboratorium 2.

### Opis charakteru danych

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

### Opis atrybutów

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

### Opis trzech przykładowych analiz

#### Agregacja

Utrzymuj informacje dotyczące średniej liczby osobników każdego gatunku w ostatnich 5 odkryciach.

#### Wykrywanie anomalii

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy populacji poniżej 500 osobników na podstawie pojedyńczego
zdarzenia.

#### Wykrywanie anomalii oparte na agregacji

Wykrywaj przypadki, w których ostatnie 3 odkryte grupy zwierząt określonego gatunku będą miały poniżej 1000 osobników.

## Laboratorium 3.

### Zestaw zadań 2 - Esper EPL.

#### Zadanie 1 - Prosta agregacja

Utrzymuj informacje dotyczące średniej liczby osobników względem gatunku w ostatnich 100 odkryciach.

##### Polecenie

```epl
select species, avg(population) as avg_population
  from AnimalGroupDiscoveryEvent#length(100)
  group by species;
```

#### Zadanie 2 - Proste wykrywanie anomalii (selekcją zdarzeń):

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy populacji poniżej 500 osobników na podstawie pojedynczego
zdarzenia.

##### Polecenie

```epl
select name, population
from AnimalGroupDiscoveryEvent where population < 500;
```

#### Zadanie 3 - Wykrywanie anomalii wykorzystujące wyniki agregacji:

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy osobników.
Na zagrożenie wskazuje fakt, że liczba osobników zaobserwowana w danej grupie jest mniejsza niż 25% średniej liczby
osobników w całej populacji gatunku.

##### Polecenie

```epl
select name, population, avg(population) as avg_population
from AnimalGroupDiscoveryEvent
group by name having avg(population) / 4 > population;
```

#### Zadanie 4 - Złożony przypadek:

rozwiązania wymaga użycia:

- nazwanego okna lub tabeli
- połączenia lub użycie wiele etapów przetwarzania

Badaczom zależy na utrzymaniu równowagi w ekosystemie, szczególnie pomiędzy dwoma gatunkami zwierząt - pand i krokodyli.
W związku z tym wymagane jest, aby obserwować osobno te dwa rodzaje i informować kiedy średnia liczba osobników w
ostatnich 10
obserwacjach pand jest mniejsza od średniej liczby osobników w ostatnich 10 obserwacjach krokodyli.

##### Polecenie

```epl
create window PandaWindow#length(10) as AnimalGroupDiscoveryEvent;
create window CrocodileWindow#length(10) as AnimalGroupDiscoveryEvent;
                
on AnimalGroupDiscoveryEvent(name = 'panda') merge PandaWindow insert select *;
on AnimalGroupDiscoveryEvent(name = 'crocodile') merge CrocodileWindow insert select *;
        
@name('records')
select
  avg(pw.population) as panda_population,
  avg(cw.population) as crocodile_population
  from PandaWindow pw, CrocodileWindow cw
  having avg(pw.population) > avg(cw.population);
```

## Laboratorium 2

### Zestaw zadań 2 - Esper CEP.

#### Zadanie 1 - Until/Where.

Wymagane użycie:

- operatora `until`
- operatora `where`

##### Polecenie

```epl
```

#### Zadanie 2 - Operator następstwa/Operator Logiczny

Wymagane użycie:

- dwukrotnego operatora następstwa `->`
- operatora logicznej `alternatywy` / `koniunkcji`

##### Polecenie

```epl
```

#### Zadanie 3 - Match Recognize

Wymagane użycie:

- operatora `MATCH_RECOGNIZE`

##### Polecenie

```epl
```