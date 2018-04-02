# 1. Nazwa programu
CostMinimizer

# 2. Cel programu
Celem programu *CostMinimizer* jest wyznaczenie kursów dostaw towarów z gospodarstw do sklepów w taki sposób, 
aby sumaryczny koszt dostawy był jak najmniejszy.
# 3. Obsługa programu
Program możemy uruchomić za pomocą systemowego wiersza poleceń:
```
java −jar CostMinimizer.jar
```
Po uruchomieniu wyświetlony zostaje powitalny interfejs użytkownika oraz menu zawierające
opcje programu.

![alt text](https://github.com/kubabar1/CostMinimizer/blob/master/readme_images/program1.png "Interfejs użytkownika")

Aby ponownie wyświetlić menu należy wpisać *menu* natomiast aby opuścić program należy
wpisać *quit*.
Menu wygląda następująco:
1.Wyznacz kursy dostaw.
2.Wybierz lokalizację danych wyjściowych.
Po wybraniu opcji nr 2 zostaje wyświetlone menu informujace nas o możliwych sposobach
lokalizacji danych wejściowych:
Zależnie od wyboru dane wyjściowe zostaną przekierowane do wybranego celu (domyślnie na
standardowe wyjście). Po wybraniu opcji 2 lub 3 musimy podac nazwe pliku wyjściowego.

![alt text](https://github.com/kubabar1/CostMinimizer/blob/master/readme_images/program2.png "Interfejs użytkownika")

Po wybraniu opcji nr 1 zostaniemy poproszeni o podanie ścieżki do pliku na podstawie
którego wyznaczone zostaną kursy dostaw towarów.

![alt text](https://github.com/kubabar1/CostMinimizer/blob/master/readme_images/program3.png "Interfejs użytkownika")

Podany plik z danymi musi byc zapisany w formacie utf-8. Jest to bardzo istotny fakt, gdyż w
przeciwnym razie istnieje wysokie ryzyko, że nie przejdzie on walidacji. Po wprowadzeniu pliku
zostaje uruchomiona funkcja minimalizująca koszt programu i w wybranym wcześniej miejscu
(domyslnie na standardowym wyjściu) zostają wyświetlone wyznaczone ścieżki.

![alt text](https://github.com/kubabar1/CostMinimizer/blob/master/readme_images/program4.png "Interfejs użytkownika")

# 4. Opis formatu dostarczanego pliku
Program wykonuje obliczenia na podstawie podanego na wejściu pliku. W owym pliku należy
podać kolejno:
# 4.1 Parametry dotyczące ferm drobiu
[id] [nazwa] [dzienna produkcja]<br/>
[id] - id fermy (dowolny typ)<br/>
[nazwa] - nazwa fermy <br/>
[dzienna produkcja] - dzienna produkcja jaj fermy (typ całkowitoliczbowy)<br/><br/>
*zalecane jest aby id były kolejnymi liczbami całkowitoliczbowymi od 0 ...*<br/><br/>
**Przykład:**<br/><br/>
0 Ferma Kowalskich 250<br/>

# 4.2 Parametry dotyczące sklepów :
[id] [nazwa] [dzienne zapotrzebowanie]<br/>
[id] - id sklepu (dowolny typ)<br/>
[nazwa] - nazwa sklepu <br/>
[dzienna produkcja] - dzienne zapotrzebowanie sklepu na jaja (typ całkowitoliczbowy)<br/><br/>
*zalecane jest aby id były kolejnymi liczbami całkowitoliczbowymi od 0 ....*<br/><br/>
**Przykład:**<br/><br/>
0 Groszek 420<br/>

# 4.3 Parametry dotyczące połączeń między sklepami :
[id_fermy] [id_sklepu] [max_liczba_jaj] [koszt_przewozu_jaja]<br/>
[id_fermy] - id fermy<br/>
[id_sklepu] - id sklepu<br/>
[max_liczba_jaj] - maksymalna dzienna liczba przewo»onych jaj<br/>
[koszt_przewozu] - koszt przewozu jednego jaja przez dane połczenie<br/><br/>

Poszczególne fragmenty pliku powinny od siebie zostać oddzielone znakiem oddzielnej linii rozpoczynającej się od znaku *#*. 
Wszystko co znajduje się w danej linii za tym znakiem zostanie zignorowane. Plik powinien również na początku posiadać linię
rozpoczynającą się od tego znaku.<br/>

Plik po podaniu go do programu przed rozpoczęciem obliczeń poddany jest procedurze walidacji.
Sprawdzane są między innymi poprawny format poszczególnych elementów pliku (sklepu, farmy,
połczeń), takich jak typ podanych argumentów, ich ilość, czy kolejność, następnie sprawdzone
zostanie czy podane są w pliku połączenia pomiędzy wszystkimi fermami i sklepami oraz czy
wszystkie dane liczbowe są nieujemne.<br/><br/>

**Przykład poprawnie wykonanego pliku:**
```
# Fermy drobiu [id | nazwa | dzienna produkcja]<br/>
0 Józef Żuczek Jaja rodzinne 300<br/>
1 Marian Kundera Moje kury 330<br/>
2 Łazarewicz i Spółka - Jaja od pokoleń 500<br/>
# Sklep [id | nazwa | dzienne zapotrzebowanie]<br/>
0 Żuk 150
1 Pszczółka 230
2 Jodłowiec 400
3 Iskierka - sklep rodzinny 200
# Połączenia ferm i sklepów [id_fermy] [id_sklepu] [max_liczba_jaj] [koszt_przewozu_jaja]
0 0 200 3
0 1 200 3
0 2 250 2
0 3 100 4
1 0 300 3
1 1 200 4
1 2 150 5
1 3 300 2
2 0 300 4
2 1 300 4
2 2 100 6
2 3 200 5
```

# 5. Opis formatu danych wyjściowych
[nazwa_fermy] -> [nazwa_sklepu] [Suma: liczba_jaj*cena_transportu= ... ]
Program na wyjściu wypisuje kolejne wiersze zawierające kolejne trasy pomiędzy fermami a
sklepami, a na samym końcu wypisuje sumę kosztów wszystkich dostaw:<br/>

**Przykład:**
```
Józef Żuczek Jaja rodzinne -> Pszczółka [Opłata: 240]
Marian Kundera Moje kury -> Jodłowiec [Opłata: 600]
...
...
...
...
Opłaty całkowite: 840
```

# 6. Opis funkcji
Program *CostMinimizer* służy do wyznaczenia kursów dostaw towarów z ferm do sklepów. Po
uruchomieniu wyświetlony zostaje tekstowy interfejs użytkownika, a po podaniu pliku program
pobiera z niego dane i wyznacza kursy dostaw. Głównym zadaniem programu jest wyznaczenie
kursów dostaw jak najbardziej zoptymalizowanych pod względem kosztów, tak aby ich sumaryczna
cena była jak najniższa. Ponadto program musi uwzględnić ograniczoną dzienną ilość
przesyłanych towarów pomiędzy fermą a sklepem. Główne zagadnienie rozpatrywane przez program
podczas jego pracy to problem minimalizacji kosztów przepływu przy zachowaniu jak
największego przepływu.

# 7. Komunikaty błędów
Program *CostMinimizer* wyposażony jest w mechanizm obsługi błędów. W razie wystąpienia
jakiegokolwiek komunikatu, należy odnaleźć go w przedstawionym poniżej spisie, a następnie
postępować zgodnie z umieszczoną pod nim instrukcją.<br/><br/>

**NIEUDANA PRÓBA OTWARCIA PLIKU**<br/>
W razie pojawienia się takiego komunikatu należy sprawdzić czy podaliśmy nazwę pliku wejściowego 
oraz czy jego lokalizacja jest prawidłowa (w systemie Windows aby odnaleźć lokalizację
klikamy na dany plik prawym przyciskiem myszy, wybieramy opcję *Właściwości* i przechodzimy
do karty *Ogólne*). Zalecany format pliku to ".txt".<br/><br/>

**NIEUDANA PRÓBA UTWORZENIA PLIKU: Plik o podanej nazwie nie istnieje w podanej lokalizacji**<br/>
W razie pojawienia się takiego komunikatu należy sprawdzić czy podana przez nas nazwa
pliku w którym mają zostać umieszczone dane wyjściowe nie istnieje w określonej przez nas
lokalizacji (lub jeżeli jej nie określiliśmy to w głównym folderze programu). Jeżeli istnieje to
powinniśmy wybrać inną nazwę pliku.<br/><br/>

**NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii "x" -> Nieprawidłowy format wprowadzonych danych**<br/>
W razie pojawienia się takiego komunikatu należy sprawdzić czy plik z którego pobieramy
dane jest utworzony w odpowiednim formacie (określonym w podpunkcie 4.) oraz czy wszystkie
argumenty w danej linii mają odpowiedni typ danych (liczba, łańcuch znaków ... ). Jeżeli format
nie jest poprawny to powinniśmy poprawić format danych w podanej przez program linii (nr x).<br/><br/>

**NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii "x" -> Podano ujemną dzienną produkcję**<br/>
W razie pojawienia się takiego komunikatu należy przejść do pliku plik z którego pobieramy
dane i sprawdzić czy znajdująca się w linii nr "x" dzienna produkcja nie jest liczbą ujemną.<br/><br/>

**NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii "x" -> Podano ujemne dzienne zapotrzebowanie**<br/>
W razie pojawienia się takiego komunikatu należy przejść do pliku z którego pobieramy
dane i sprawdzić czy znajdujące się w linii nr "x" dzienne zapotrzebowanie nie jest liczbą ujemną.<br/><br/>

**NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii "x" -> Podano ujemną maksymalną liczbę jaj**<br/>
W razie pojawienia się takiego komunikatu należy przejść do pliku z którego pobieramy
dane i sprawdzić czy znajdująca się w linii nr "x" maksymalna liczba jaj nie jest liczbą ujemną.<br/><br/>

**NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii "x" -> Podano ujemny koszt przewozu jednego jaja**<br/>
W razie pojawienia się takiego komunikatu należy przejść do pliku z którego pobieramy
dane i sprawdzić czy znajdujący się w linii nr "x" koszt przewozu jaja nie jest liczbą ujemną.<br/><br/>

**NIEUDANA WALIDACJA PLIKU: Nie uwzględniono wszystkich połączeń pomiędzy fermami a sklepami**<br/>
W razie pojawienia się takiego komunikatu należy przejść do pliku z którego pobieramy
dane i sprawdzić czy uwzględniliśmy połączenia pomiędzy wszystkimi fermami a sklepami.<br/><br/>

*jeżeli program nie może się uruchomić należy upewnić się czy podajemy podczas uruchomienia
prawidłową lokalizację programu oraz czy mamy zainstalowany na naszym urządzeniu
program JAR dołączony do darmowego pakietu JDK firmy Sun Microsystems*

# 8. Diagram modułów
![alt text](https://github.com/kubabar1/CostMinimizer/blob/master/readme_images/UML_diagram.png "Diagram modułów")

# 9. Opis modułów
# 9.1 CostMinimizer
Klasa ta jest głównym modułem programu odpowiedzialnym za wywołanie pozostałych modułów
w celu pobrania danych i wyliczenia na ich podstawie kosztu minimalnego.<br/><br/>
**Metody:**<br/>
– public void minimizeCost() - metoda ta wylicza koszt minimalny przez wykorzystanie funkcji
znajdujących się w pozostałych modułach<br/>

# 9.2 Parser
Klasa ta przechowuje parser czytający dane z pliku i umieszczający je w odpowiednich strukturach.<br/><br/>
**Metody:**<br/>
– public void readFromFile(String filename) - metoda ta pobiera dane z pliku, a następnie
umieszcza je w odpowiednich strukturach,<br/>
– private boolean validateFile(String filename) - metoda ta przeprowadza walidację pliku z danymi<br/>

# 9.3 Writer
Klasa ta posiada metody pozwalające na zapis otrzymanych rezultatów na standardowe wyjście
lub do pliku.<br/><br/>
**Metody:**<br/>
– public void writeToStdout() - metoda ta wypisuje otrzymane rezultaty na standardowe wyjście,<br/>
– public void writeToFile(String filename) – metoda ta wypisuje otrzymane dane do pliku<br/>

# 9.4 Matrix
Klasa ta przechowuje implementacje macierzy jako dynamicznej struktury danych.<br/><br/>
**Metody:**<br/>
– public void put(double obj, int column, int row) - metoda ta służy do umieszczania obiektu
*obj* w macierzy do komórki o współrzednych *column*, *row* (usuwa stare dane i wpisuje nowe),<br/>
– public void add(double obj, int column, int row) – metoda ta służy do dodawania obiektu *obj*
do komórki macierzy o współrzednych *column*, *row* (dodaje nowe dane do starych),<br/>
– public double get(int column, int row) – metoda ta służy do pobierania obiektu z macierzy z
komórki o współrzednych *column*, *row*,<br/>
– public int getColumnNumber() - metoda ta zwraca liczbę kolumn w macierzy,<br/>
– public int getRowNumber() - metoda ta zwraca liczbę rzędów w macierzy<br/>

# 9.5 RBTree
Klasa ta przechowuje implementację drzewa czerwono-czarnego jako dynamicznej struktury danych.<br/><br/>
**Metody:**<br/>
– public V getValue(K key) - metoda ta pobiera z drzewa element o kluczu *key*,<br/>
– public void setValue(K key, V value) - metoda ta wstawia nowy element *value* do drzewa
nadając mu klucz *key* <br/>
# 9.6 Queue
Klasa ta przechowuje implementację kolejki jako dynamicznej struktury danych.<br/><br/>
**Metody:**<br/>
– public boolean isEmpty() – metoda ta zwraca true gdy kolejka jest pusta,<br/>
– public V getFront() - metoda ta zwraca wartość pierwszego elementu w kolejce,<br/>
– public void push(V v) – metoda ta wstawia nowy element *v* do kolejki,<br/>
– public V pop() - metoda ta zdejmuje pierwszy element z kolejki i zwraca jego wartość<br/>
# 9.7 Edmonds-KarpAlgorithm
Klasa ta przechowuje funkcje potrzebne do obliczenia maksymalnego przepływu za pomoca algorytmu
Edmonds-KarpAlgorithm<br/><br/>
**Metody:**<br/>
– public void edmondsKarp(Matrix c, Matrix f, int[] p, int [][] cfp) - metoda ta otrzymuje
macierz kosztów *c*, macierz przepływów maksymalnych *f*, tablice poprzedników *p* oraz tablice
przepustowości rezydualnych i na ich podstawie wylicza maksymalny przepływ<br/>
# 9.8 Bellman-FordAlgorithm
Klasa ta przechowuje funkcje potrzebne do realizacji algorytmu *Bellmana-Forda*, który ma za
zadanie usunąć ujemne cykle w grafie rezydualnym.<br/><br/>
**Metody:**<br/>
– public boolean bellmanFord(ArrayList Neighbours, int[] cost, int[] p, int v) - metoda ta otrzymuje
liste sasiedzwa, talice kosztów cost, tablice poprzedników p oraz wierzchołek poczatkowy
*v* i na ich podstawie wylicza najkrótsza sciezkę w grafie. Po wykryciu ujemnego cyklu w grafie
zwraca false.<br/>

# 10. Opis struktur i algorytmów
# 10.1 Struktury:
**Queue** jest liniową struktura danych, w której nowe dane dopisywane sa na koncu kolejki,
a pobierane sa z jej poczatku (FIFO). W programie wykorzystywana jest m.in. przez moduł
*Edmonds-KarpAlgorithm* podczas wykonywania obliczeń (wykorzystuje go do przeszukiwania
grafu metoda BFS).<br/><br/>

**RBTree** samoorganizujace sie binarne drzewo poszukiwan, wykorzystujace kolorowanie wezłów
w celu ich odpowiedniego i stabilnego ustawienia.<br/><br/>

**Matrix** dynamiczna struktura danych zrealizowana jako jednowymiarowa tablica imitująca za
pomocą odpowiednich metod dwuwymiarową macierz. Macierz zrealizowana jest za pomoca tablicy typu double.<br/><br/>
# 10.2 Algorytmy:
**Algorytm Edmondsa-Karpa** - algorytm ten służy do wyliczania maksymalnego przepływu w
grafie. Algorytm ten wykorzystuje algorytm Forda-Fulkersona oraz metode BFS do wyszukiwania
sciezek rozszerzajacych w sieci rezydualnej.<br/><br/>

**Algorytm Bellmana-Forda** - algorytm ten wyszukuje najkrótsze sciezki od wybranego wierzchołka
startowego do wszystkich pozostałych wierzchołków. Pozwala on na wyszukiwanie dostepnych
scieżek o najmniejszym koszcie, umożliwiając przez to minimalizację kosztów.

# 11. Plan testów
# 11.1 Testy całościowe
Aby w sposób ogólny przetestować działanie programu, nalezy utworzyć kilka przykładowych
plików zawierajacych dane róznych ferm oraz sklepów i połaczeń miedzy nimi, a nastepnie sprawdzić
czy zwróci on odpowiednie rezultaty w postaci jak najlepiej zoptymalizowanych połaczeń
oraz kosztów. Przede wszystkim należy przetestować jak program zachowa się po dostarczeniu
dużej ilości danych.
# 11.2 Testy modułów
**CostMinimizer**<br/>
Aby przetestować działanie tej klasy, należy sprawdzić czy metoda *minimizeCost* działa
poprawnie i zwraca poprawne wyniki.<br/><br/>

**Parser**<br/>
Aby przetestować działanie tej klasy należy sprawdzić czy metoda *readFromFile* poprawnie
odczytuje pliki i umieszcza pobrane dane w odpowiednich miejscach w strukturze, a nastepnie
dla kilku plików wejściowych należy sprawdzić, czy metoda *validatefile* przeprowadza poprawnie
proces ich walidacji.<br/><br/>

**Writer**<br/>
Aby przetestować działanie tej klasy należy sprawdzić czy jej metody poprawnie wypisują
dane.<br/><br/>

**Matrix**<br/>
Aby przetestować działanie tej klasy należy sprawdzić czy jej metody umieszczają i pobierają
wartości z odpowiednich komórek w tabeli oraz czy da sie z ich pomoca przeprowadzać w
poprawny sposób obliczenia.<br/><br/>

**RBTree**<br/>
Aby przetestować działanie tej klasy należy sprawdzić czy metoda *getValue* pobiera odpowiednie
dane, a metoda *setValue* umieszcza dane w drzewie w poprawny sposób. Należy
również przetestować działanie tej klasy dla różnych typów danych.<br/><br/>

**Queue**<br/>
Aby przetestować działanie tej klasy należy sprawdzić czy jej metody poprawnie umieszczają
dane w kolejce oraz je z niej pobieraja. Należy również przetestować działanie kolejki dla
przypadków szczególnych jak próba usunięcia elementu z pustej kolejki, czy metoda *getFront*
zwraca wartość pierwszego elementu w kolejce.<br/><br/>

**Edmonds-KarpAlgorithm**<br/>
Aby przetestować działanie tej klasy należy sprawdzić czy metoda *edmondsKarp* wyznacza
maksymalny przepływ, oraz czy algorytm działa poprawnie, zgodnie z oczekiwanymi założeniami.<br/><br/>

**Bellman-FordAlgorithm**<br/>
Aby przetestować działanie tej klasy należy sprawdzić czy wyznacza ona najbardziej optymalną
scieżkę oraz jej zachowanie wobec pojawienia sie ujemnego cyklu.<br/><br/>

# 12. Podsumowanie
Program w poprawny sposób spełnia początkowe założenia i prawidłowo wyznacza scieżki przepływu
pomiędzy fermami w taki sposób, aby koszt był jak najniższy.
