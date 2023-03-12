# Opis charakteru danych

W ramach organizacji zajmującej się kategoryzacji odkrytych grup zwierząt na nieznanym kontynencie zostają odkryte coraz
to nowsze
gatunki.

Badacze odkrywają grupy zwierząt, które są kategoryzowane na podstawie ich nazwy gatunkowej, nazwy naukowej, rodzaju
gatunkowego
oraz liczby osobników w odkrytej grupie. Badania te są niezwykle czasochłonne i wymagają wielu lat do pełnego
przebadania populacji.

Z tego powodu, dane są uzupełnione o etykietę czasową związaną z momentem odkrycia grupy populacji zwierząt dopiero po
pewnym
okresie od czasu odkrycia
pierwszego osobnika grupy. Etykieta ta może się losowo spóźniać w stosunku do czasu systemowego max do 2 lat z powodu
czasu potrzebnego do pełnego przebadania
populacji.

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
    - znaczenie: czas odkrycia grupy zwierząt
    - kategoria: znacznik czasowy zdarzeń

# Opis trzech przykładowych analiz

## Agregacja

Utrzymuj informacje dotyczące średniej liczby osobników każdego gatunku w ostatnich 5 odkryciach.

## Wykrywanie anomalii

Wykrywaj przypadki odnalezienia zagrożonej wyginięciem grupy populacji poniżej 500 osobników.

## Wykrywanie anomalii oparte na agregacji

Wykrywaj przypadki, w których ostatnie 3 odkryte grupy zwierząt określonego gatunku będą miały poniżej 1000 osobników.
