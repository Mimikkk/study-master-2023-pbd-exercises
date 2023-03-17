# Opis charakteru danych
W ramach organizacji zajmującej się kategoryzacją odkrytych zwierząt na nieznanym kontynencie w trakcie przelotów helikopterem zostają zaobserwowane skupiska różnych gatunków.

Badacze starają się zliczyć zwierzęta w odkrytych skupiskach. Skupiska są kategoryzowane na podstawie nazwy gatunkowej zwierząt, nazwy naukowej, rodzaju gatunkowego oraz liczby osobników.

Etykieta czasowa związana z momentem odkrycia skupiska zwierząt może losowo spóźniać w stosunku do czasu systemowego max do 30 sekund z powodu kiepskiego łącza. Nowy kontynent nie ma dobrego zasięgu.

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

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy populacji poniżej 500 osobników na podstawie pojedyńczego zdarzenia.

## Wykrywanie anomalii oparte na agregacji

Wykrywaj przypadki, w których ostatnie 3 odkryte grupy zwierząt określonego gatunku będą miały poniżej 1000 osobników.

# Zadania

## Zadanie 1 - 
Utrzymuj średnią liczbą odrytych zwierząt w każdym z gatunków dla każdych kolejnych 20 sekund.

Odnajdź takie nowe zdarzenia, które znajdują się w oknie liczności odkrytych gatunków oraz ich liczność przewyższa aktualną średnią wartość.

Rozwiązanie
```epl
create window AnimalCounter as select * from KursAkcji group by genus;
```

## Zadanie 2


## Zadanie 4

