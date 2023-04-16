# Charakterystyka danych

Na nieznanym kontynencie ekspedycje naukowe dokonują rejestracji zaobserwowanych stad zwierząt.

W strumieniu pojawiają się zdarzenia zgodne ze schematem `AnimalGroupDiscoveryEvent`.

```sql
create
json schema AnimalGroupDiscoveryEvent(
  name string,  latin string,  genus string,  species string,
  population int,  its string,  ets string);
```

Każde zdarzenie związane z jest z faktem rejestracji zaobserwowanego stada zwierząt określonego gatunku w określonej
liczbie.

Dane są uzupełnione są o dwie etykiety czasowe.

* Pierwsza (`ets`) związana jest z momentem zaobserwowania stada.
  Etykieta ta może się losowo spóźniać w stosunku do czasu systemowego max do 30 sekund.
* Druga (`its`) związana jest z momentem rejestracji zdarzenia obserwacji stada.

# Opis atrybutów

Atrybuty w każdym zdarzeniu `AnimalGroupDiscoveryEvent` mają następujące znaczenie:

* `name` - nazwa zwierzęcia
* `latin` - nazwa naukowa
* `genus` - gatunek
* `species` - rodzaj gatunkowy
* `population` - liczba osobników w stadzie
* `ets` - czas zaobserwowania stada
* `its` - czas rejestracji faktu obserwacji w systemie

# Zadania

Opracuj rozwiązania poniższych zadań.

* Opieraj się na strumieniu zdarzeń zgodnych ze schematem `AnimalGroupDiscoveryEvent`
* W każdym rozwiązaniu możesz skorzystać z jednego lub kilku poleceń EPL.
* Ostatnie polecenie będące ostatecznym rozwiązaniem zadania musi
    * być poleceniem `select`
    * posiadającym etykietę `answer`, przykładowo:
  ```sql
    @name('answer') 
    select *
    from AnimalGroupDiscoveryEvent;
  ```

## Zadanie 1

Dla rejestracji dokonanych w ciągu ostatnich 15 sekund, utrzymuj informacje dotyczące sumarycznej liczby osobników w
zaobserwowanych stadach dla każdego z gatunków.

Wyniki powinny zawierać kolumny:

- `sum_population` - sumaryczna liczba osobników w stadach
- `genus` - nazwa gatunku

## Zadanie 2

Wykrywaj przypadki zaobserwowania zagrożonych stad zwierząt liczących poniżej 10 osobników.

Wyniki powinny zawierać kolumny:

- `name` - nazwa zwierzęcia
- `population` - populacja zaobserwowanego stada
- `ets` - czas dokonania obserwacji stada

## Zadanie 3

Wykrywaj przypadki obserwacji stosunkowo dużych grupy osobników dla danego gatunku.
Przez stosunkową dużą grupę rozumiemy tak, w której liczba osobników jest większa niż 90% liczby osobników w największej
grupie zaobserwowanej w ramach tego samego gatunku, dla rejestracji z ostatnich 30 sekund.

Nie wykrywaj przypadków trywialnych, w których w w ciągo ostatnich 30 sekund została zarejestrowana tylko jedna grupa
osobników.

Wyniki powinny zawierać kolumny:

- `name` - nazwa zwierzęcia
- `genus` - nazwa gatunku
- `population` - populacja odkrytej stosunkowo dużej grupy
- `max_population` - maksymalna liczba osobników w stadach tego gatunku zarejestrowanych w ciągu ostatnich 30 sekund.

## Zadanie 4

Badaczy szczególnie martwią sytuację, w których gatunek dramatycznie zmniejsza swoją populację.
Dlatego chcą, aby dla każdych kolejnych 10 sekund czasu rejestracji były wyliczane sumy osobników w odkrytych grupach
zwięrząt dla każdego z gatunków. Na podstawie tych statystyk powinny być utrzymywane dwie listy:

- lista 5 gatunków o sumarycznej największej liczbie osobników - nazwijmy ją 'heaven5'
- lista 5 gatunków o sumarycznej najmniejszej liczbie osobników w zaobserwowanych grupach - nazwijmy ją 'hell5'

Znajduj przypadki, w których gatunek w jednym interwale 10 sekund znajdował się na liście 'heaven5', a już w następnym
pojawił się na liście 'hell5'.

Wyniki powinny zawierać kolumny:

- `genus` - nazwa gatunku
- `population_heaven5` - sumaryczna liczba osobników zarejestrowanych w grupach tego gatunku z listy 'heaven5' dla
  poprzedniego interwału 10 sekund
- `population_hell5` - sumaryczna liczba osobników zarejestrowanych w grupach tego gatunku z listy 'hell5' dla właśnie
  zakończonego interwału 10 sekund

## Zadanie 5

Badaczy martwią sytuacje, w których gatunek nie pojawia się w ramach obserwacji przez dłuższy czas.

Dla każdego gatunku znajduj przypadki, w po dokonanej obserwacji stada określonego gatunku następuje przerwa trwająca co
najmniej 10 sekund (czasu systemowego) w trakcie, której nie dokonano obserwacji nawet jednego stada tego samego
gatunku.

Wyniki powinny zawierać kolumny:

- `genus` - nazwa gatunku
- `ets` - czas ostatniej obserwacji danego gatunku przed przerwą

## Zadanie 6

Liczba stad, które należy obserwować przekracza możliwości badaczy. Dlatego postanowiono, że te gatunki, które są
stabilne nie będą już więcej tak dokładnie badane.
Gatunek stabilny to taki, którego co najmniej trzy stada zostały kolejno zaobserwowane (nie konieczne bezpośrednio po
sobie) i za każdym razem wielkość stada przekraczała 200 sztuk.
Uwaga! Jeśli pomiędzy pierwszym a trzecim zaobserwowanym stadem, dokonano obserwacji stada tego samego gatunku o liczbie
sztuk poniżej 10, wówczas gatunek do stabilnych zaliczyć nie można.

Wykrywaj stabilne gatunki.

Wyniki powinny zawierać kolumny:

- `genus` - nazwa gatunku
- `population1` - Populacja pierwszego stada
- `population2` - Populacja drugiego stada
- `population3` - Populacja trzeciego stada
- `start_its` - czas rejestracji pierwszego stada
- `end_its` - czas rejestracji trzeciego stada

## Zadanie 7

Zwierzęta zmieniają swoje instynkty stadne. Chcemy znajdywać te gatunki, w których instynkty stadne zanikają.
Wyszukuj te serie rejestracji stad tego samego gatunku, które składają się przynajmniej z trzech zdarzeń, w trakcie
których każda kolejna obserwacja okazywała się mniej liczna niż poprzednia. Każda taka seria kończyć się powinna
obserwacją, w której liczba osobników w stadzie tego samego gatunku wreszcie wzrosła.

Wyniki powinny zawierać kolumny:

- `first_population` - populacja pierwszego stada w serii malejących stad
- `last_population` - populacja ostatniego stada w serii malejących stad
- `how_many` - liczba rejestracji stad w serii malejących stad
- `start_ets` - czas obserwacji pierwszego stada w serii malejących stad
- `end_ets` - czas obserwacji ostatniego stada w serii malejących stad
