# Charakterystyka danych

Na nieznanym kontynencie ekspedycje naukowe obserwują stada zwierząt.

Głównym celem jest badanie gatunków, ich klasyfikacja i monitorowanie populacji. Skupiska zwierząt są kategoryzowane
według nazwy, rodzaju i liczby osobników, z uwzględnieniem etykiety czasowej. Etykieta czasowa może się losowo spóźniać
do 30 sekund z powodu trudności komunikacyjnych na kontynencie.

W strumieniu pojawiają się zdarzenia zgodne ze schematem `AnimalGroupDiscoveryEvent`.

```sql
create
json schema AnimalGroupDiscoveryEvent(
  name string,
  latin string,
  genus string,
  species string,
  population int,
  its string
  ets string
);
```

Każde zdarzenie związane z jest z faktem zaobserwowania stada określonego gatunku.

Dane są uzupełnione są o dwie etykiety czasowe.
* Pierwsza (`ets`) związana jest z momentem zaobserwowania stada.
  Etykieta ta może się losowo spóźniać w stosunku do czasu systemowego max do 30 sekund.
* Druga (`its`) związana jest z momentem rejestracji zdarzenia obserwacji stada.
# Opis atrybutów

**Atrybuty w każdym zdarzeniu mają następujące znaczenie**:

* `name` - nazwa gatunkowa
* `latin` - nazwa naukowa
* `genus` - gatunek
* `species` - rodzaj gatunkowy
* `population` - liczba osobników w stadzie
* `ets` - czas zaobserwowania stada
* `its` - czas rejestracji faktu obserwacji w systemie

# Zadania

Opracuj rozwiązania poniższych zadań.

* Opieraj się strumieniu zdarzeń zgodnych ze schematem `AnimalGroupDiscoveryEvent`
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

Utrzymuj informacje dotyczące średniej liczby osobników w odkrytych stadach względem gatunku, w ostatnich 100
odkryciach.

Wyniki powinny zawierać kolumny:
- `avg_population` - średnia liczba osobników w stadzie
- `genus` - gatunek zwierząt w stadzie

## Zadanie 2

Wykrywaj przypadki odnalezienia potencjalnie zagrożonej zniknięciem stadach zwierząt. Są one notowane przez badaczy do
późniejszych badań, jeżeli ich populacja jest poniżej 300 osobników.

Wyniki powinny zawierać kolumny:
- `name` - nazwa zwierzęcia
- `population` - populacja odkrytego stada

## Zadanie 3

Wykrywaj przypadki odnalezienia potencjalnie zagrożonej zniknięciem grupy osobników.
Na zagrożenie wskazuje fakt, że liczba osobników zaobserwowana w danej grupie jest mniejsza niż 10% średnia
osobników we wcześniej spotkanych grupach całej populacji tego gatunku.

Wyniki powinny zawierać kolumny:
- `name` - nazwa zwierzęcia
- `population` - populacja odkrytego stada
- `avg_population` - średnia liczba osobników w stadzie

## Zadanie 4
Badaczom zależy na utrzymaniu równowagi w ekosystemie, szczególnie pomiędzy dwoma gatunkami zwierząt - pand i krokodyli.
Znajduj przypadki, w których średnia liczba osobników w ostatnich
10 obserwacjach pand jest mniejsza od średniej liczby osobników w ostatnich 10 obserwacjach krokodyli.

Wyniki powinny zawierać kolumny:
- `avg_panda_population` - średnia liczb osobników w stadach pand
- `avg_crocodile_population` - średnia liczb osobników w stadach krokodyli

## Zadanie 5

Badacze odkryli na nowym lądzie gatunek uznawany za wymarły - mamuty (ang. mammoth).
Określ ile procent pojedynczych zwierząt zaobserwowanych w sygnałach otrzymanych
w przeciągu ostatnich 5 sekund stanowi ten gatunek. Wskaże to na prawdopodobieństwo ich spotkania 
w trakcie wędrówki na aktualnie przemierzanym obszarze - jeśli jest wysokie, warto jest wylądować i zaobserwować je z bliska.


## Zadanie 6

Badacze podróżując po nowym lądzie napotykają lokalizacje z dużymi skupiskami zwierząt różnych gatunków, przykładowo
wodopoje. Grupy badawcze przemieszczają się z pewną ustaloną prędkością, więc zakładamy że różne grupy zaobserwowane
przez sygnały odkrycia
w obrębie 0.5 sekundy informują o możliwości zaistnienia takiej interakcji. W miejscach takich często dochodzi
do rywalizacji zwierząt o surowce i dominacji pewnych gatunków nad innymi. Z drugiej strony, gatunki mogą też
koegzystować w synergii.

Badaczy interesują obszary, w których można takie zjawiska zaobserwować, a wykrywane są one
poprzez odnalezienie dwóch wyjątkowo dużych grup pewnych różnych od siebie gatunków, co może wskazywać na ich synergię,
która dominuje następny, trzeci wykryty w okolicy gatunek.

Obserwacja rozpoczyna się od odkrycia dużej grupy składającej się z ponad 7000 osobników. Następnie odnalezienia innego
gatunku, również o populacji przekraczającej 7000 osobników. Ostatecznie zidentyfikowanie gatunku zdominowanego, który
nie należy ani do pierwszego, ani do drugiego gatunku, a jego populacja wynosi mniej niż 500 osobników.

## Zadanie 7

Zwierzęta jako posiadają swoje centrum ekosystemowe. Naukowcy chcą dostać się do takich punktów. W celu pozostania jak najbliżej centrum.
Jeśli okaże się, że kolejne trzy obserwacje grup zwierząt tego samego gatunku maleją pod względem liczności odkrytych populacji stad to
dla naukowców będzie to sygnał, iż powoli oddalają się od danego terytorium występowania.

Wyniki powinny zawierać kolumny:
- `name` - 
- `a_population` - Populacja stada mniejszego
- `b_population` - Populacja stada środkowego
- `c_population` - Populacja stada większego

###### Footnote

Do uruchomienia projektu została użyta wersja SDK `Amazon Corretto 19`.

Jeżeli klasa [EsperClient](./src/com/esper/data/EsperClient.java) nie jest od razu rozpoznana jako główna, należy
ustawić folder [src](./src) jako katalog źródłowy.
Prawy przycisk `Mark Directory as / Sources Root`
