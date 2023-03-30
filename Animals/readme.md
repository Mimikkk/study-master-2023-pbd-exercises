# Charakterystyka danych

Opis charakteru danych
Na nieznanym kontynencie istnieją ekspedycje naukowe korzystające z helikopterów, aby dokonywać przeloty i obserwować
różnorodność gatunków zwierząt zamieszkujących ten tajemniczy ląd.

Głównym celem tego przedsięwzięcia jest przeprowadzenie systematycznych badań nad różnymi gatunkami, ich klasyfikacją, a
także monitorowanie ich populacji w celu zrozumienia dynamiki ekosystemu na tym kontynencie. W trakcie przelotów,
badacze zauważają skupiska zwierząt, które następnie starają się zliczyć, zidentyfikować i sklasyfikować.

Skupiska zwierząt są kategoryzowane według kilku kryteriów, takich jak nazwa gatunkowa, nazwa naukowa, rodzaj gatunkowy
oraz liczba osobników. W ten sposób naukowcy mają możliwość śledzenia dynamiki populacji. Ważnym elementem tego procesu
jest również uwzględnienie etykiety czasowej związaną z momentem odkrycia skupiska zwierząt.

Ze względu na trudności związane z komunikacją na tym nieznanym kontynencie, istnieje możliwość, że etykieta czasowa
może losowo spóźniać się w stosunku do czasu systemowego maksymalnie do 30 sekund.

# Opis atrybutów

**Atrybuty w każdym zdarzeniu mają następujące znaczenie**:

- **name**
    - typ: string
    - znaczenie: nazwa gatunkowa
    - kategoria: atrybut, lub atrybuty, których wartości pozwalają na identyfikacje zwierzęcia
- **latin**
    - typ: string
    - znaczenie: nazwa naukowa zwierzęcia
    - kategoria: atrybut lub atrybuty dodatkowe, opisujące zdarzenie
- **species**
    - typ: string
    - znaczenie: gutunek zwierzęcia
    - kategoria: atrybut lub atrybuty, których wartości można grupować
- **genus**
    - typ: string
    - znaczenie: rodzaj gatunkowy zwierzęcia
    - kategoria: atrybut, lub atrybuty, po których można dane grupować
- **population**
    - typ: long (1 - 9999)
    - znaczenie: liczba odnalezionej grupy zwierząt
    - kategoria: atrybut lub atrybuty, których wartości można agregować
- **ts**
    - typ: string
    - znaczenie: czas odkrycia skupiska zwierząt
    - kategoria: znacznik czasowy zdarzeń

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

Utrzymuj informacje dotyczące średniej liczby osobników w odkrytych grupach względem gatunku, w ostatnich 100
odkryciach.

## Zadanie 2

Wykrywaj przypadki odnalezienia potencjalnie zagrożonej zniknięciem grupy zwierząt. Są one notowane przez badaczy do
późniejszych badań, jeżeli ich populacja jest poniżej 300 osobników.

## Zadanie 3

Wykrywaj przypadki odnalezienia potencjalnie zagrożonej zniknięciem grupy osobników.
Na zagrożenie wskazuje fakt, że liczba osobników zaobserwowana w danej grupie jest mniejsza niż 10% średnia
osobników we wcześniej spotkanych grupach całej populacji tego gatunku.

## Zadanie 4

Badaczom zależy na utrzymaniu równowagi w ekosystemie, szczególnie pomiędzy dwoma gatunkami zwierząt - pand i krokodyli.
W związku z tym wymagane jest monitorowanie tych dwóch gatunków i informować kiedy średnia liczba osobników w ostatnich
10 obserwacjach pand jest mniejsza od średniej liczby osobników w ostatnich 10 obserwacjach krokodyli.

## Zadanie 5

Badacze są szczególnie zainteresowani jednym z wymarłych gatunków, którego nie spotkamy już na znanych nam kontynentach.
Mowa oczywiście o MAMUTACH (ang. mammoth), które jak się okazuje występują na nowo odkrytym lądzie! Naukowcom zależy,
aby
określić czy jest dość rzadko spotykanym okazem, czy może jednak jego populacja zajmuje dużą część zaobserwowanych
zwierząt. W tym celu chcą sprawdzić, jakim procentem wszystkich jednostek zliczonych do zanotowania grupy Mamutów są
właśnie te stworzenia, co jednoznacznie wskaże na prawdopodobieństwo ich spotkania w trakcie wędrówki. Zakładają oni
jednak, że interesuje ich jedynie pierwsze 5 sekund od nadchodzących sygnałów dotyczących odkryć na kontynencie w
trakcie lotu helikopterem,
aby umożliwić ewentualną pieszą wycieczkę w miarę racjonalnej odległości.

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

Zwierzęta jako stworzenia stadne i terytorialne zazwyczaj żyją poruszając sie w pewnym obszarze terenu. Takie terytorium
posiada również swoje centrum, którym może być wodopój, schronienie bądź źródło pożywienia. Naukowcy korzystając z
okazji po dostaniu się do takiego punktu chcą przyjrzeć się bliżej natrafionemu gatunkowi zwierząt. W celu pozostania
jak najbliżej centrum, w którym zazwyczaj występują większe grupy, obserwują zdarzenia występowania danego gatunku.
Jeśli okaże się, że kolejne trzy obserwacje grup zwierząt tego samego gatunku maleją pod względem liczności populacji to
dla naukowców będzie to sygnał, iż powoli oddalają się od danego terytorium występowania.

###### Footnote

Jeżeli klasa [EsperClient](./src/com/esper/data/EsperClient.java) nie jest od razu rozpoznana jako główna, należy
ustawić folder [src](./src) jako katalog źródłowy.
Prawy przycisk `Mark Directory as / Sources Root`
