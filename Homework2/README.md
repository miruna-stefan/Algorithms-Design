STEFAN MIRUNA ANDREEA 324CA

TEMA 2 - PA

Problema 1 - Numarare

Dupa ce citesc numarul de noduri si numarul de muchii de pe prima linie, 
instantiez 2 obiecte ale clasei IntGraph, cate unul pentru fiecare dintre cele
2 grafuri. Pe masura ce citesc muchiile din fisier, le si adaug in graful 
corespunzator. Pentru a afla numarul de lanturi elementare comune de la 1 la N 
in cele 2 grafuri, putem sa formam graful intersectie (care va contine doar 
muchiile comune, cele care exista in ambele grafuri) si apoi sa calculam in 
acest graf numarul total de drumuri de la nodul 1 la nodul N. 

Pentru a forma graful intersectie, am creat metoda intersectWithAnotherGraph(),
care parcurge toate nodurile din graful 1, pentru fiecare nod parcurge lista de
adiacenta si verifica daca in graful 2 se gaseste muchia curenta. Daca da, 
atunci o adauga la graful intersectie. 

Pe graful obtinut apelam metoda getNumberOfPathsToN(), care intoarce numarul de
drumuri de la nodul 1 la nodul N. Construim un vector dp, in care fiecare 
element reprezinta numarul de drumuri de la nodul 1 pana in nodul cu numarul 
pozitiei curente in vector. Mai exact, dp[i] va reprezenta numarul de lanturi 
de la nodul 1 la nodul i. Pentru primul nod, initializam dp[1] cu 1. Parcurgem 
toate nodurile grafului (in cazul nostru, vorbim despre graful intersectie), 
iar pentru fiecare nod parcurgem toti vecinii sai si updatam dp[vecinul curent]
astfel: la numarul curent de drumuri gasite catre acest vecin al nodului 
curent, adunam numarul de drumuri care duc de la 1 la nodul curent (nodul 
caruia ii exploram vecinii acum). Explicatie: din moment ce am gasit acest 
vecin in lista de adiacenta a nodului curent, inseamna ca exista drum de la 
nodul curent la acest nod vecin, deci daca noi putem ajunge la nod prin x 
drumuri, iar de la nod la vecin avem muchie, inseamna ca putem ajunge si la 
nodul vecin tot prin x drumuri. De aceea, acum ca am mai descoperit inca x 
modalitati de a ajunge la vecin, actualizam dp[vecin], in sensul ca adunam x 
(numarul de drumuri nou descoperite de la 1 la vecin) la numarul de drumuri 
care erau deja descoperite de la 1 la vecin. Bineinteles, dupa cum se 
precizeaza in cerinta, nu uitam sa facem % 1000000007.

Rezultatul probelemei, adica numarul de lanturi de la 1 la N, va fi, prin 
urmare, dp[N].

Complexitate:
- in primul rand, pentru a construi graful intersectie, parcurgem toate 
nodurile din primul graf, apoi pentru fiecare nod parcurgem toti vecinii sai 
si verificam daca aceasta muchie exista si in graful 2, folosind contains() 
pe lista de adiacenta a nodului curent in graful 2. Pana aici avem deja 
complexitatea O(N + M).

- acum ca avem graful intersectie, mai trebuie sa evaluam complexitatea si 
pentru partea de aflare a numarului de drumuri de la 1 la N (metoda 
getNumberOfPathsToN()). Aici, iteram prin toate nodurile grafului, iar pentru 
fiecare nod iteram prin toti vecinii lui, deci avem tot o complexitate de 
O(N + M) pentru asta.

Asadar, complexitatea programului este suma complexitatilor celor 2 etape, 
ambele fiind O(N + M), deci complexitatea finala a programului este O(N + M).


Problema 2 - Trenuri

De data aceasta, in nodurile grafului nu mai retinem intregi ca la problema 
anterioara, ci String-uri (fiecare nod corespunde unui nume de oras). Dupa ce 
citim prima linie a fisierului, o split-uim la caracterul ' ', pentru a separa 
orasul sursa de orasul destinatie. De pe a doua linie citim numarul de muchii 
din graf, instantiem clasa StringGraph, iar apoi citim muchiile si le adaugam 
in graf. Pentru ca retinem String-uri, nu vom mai retine un array de liste de 
adiacenta, ci un hashmap in care cheia va fi String-ul reprezentand numele 
orasului din nod, iar valoarea va fi un arrayList de String-uri, reprezentand 
nodurile adiacente nodului curent.

Ideea de rezolvare a fost inspirata din laboratorul despre Shortest-paths 
problem, mai exact "Shortest-paths: DAG - Topological Sort", care se preteaza
pe problema noastra, deoarece lucram cu un graf orientat, despre care se 
mentioneaza in cerinta ca nu contine cicluri si ca se gaseste intotdeauna 
destinatia.  Acest algoritm este explicat foarte detaliat in acest videoclip: 
https://www.youtube.com/watch?v=ZUFQfFaU-8U, pe care l-am urmarit si eu.

Facem o sortare topologica care incepe din nodul sursa, in urma careia vom avea
in stiva nodurile in ordinea data de aceasta sortare. Dupa ce terminam cu 
sortarea, cream un hashmap in care cheia va fi un string reprezentand numele 
orasului stocat in nodul respectiv, iar valoarea va fi un intreg reprezentand 
distanta maxima de la sursa la orasul din nodul respectiv. Initializam toate 
distantele cu -1 (ne trebuia o valoare mai mica decat orice distanta posibila).
Distnta de la sursa la sine o actualizam cu 0. Dupa aceea, scoatem pe rand cate
un nod din stiva si iteram prin toti vecinii sai. Pentru fiecare vecin, 
verificam daca distanta de la sursa pana la el este mai mica decat distanta de 
la sursa la nodul prin veciniii caruia iteram, la care adaugam 1, caz in care 
actualizam distanta vecinului curent. Practic, ce facem este sa comparam 
distanta actuala stocata in hashmap de la sursa la vecinul curent cu distanta 
de la sursa daca am lua-o prin nodul prin vecinii caruia iteram. Aceasta din 
urma va fi chiar distanta de la sursa la nodul prin vecinii caruia iteram, 
stocata in dreptul acestuia in hashmap, la care adaugam 1, pentru a contoriza 
si drumul de la acest nod la vecinul curent (a carui distanta verificam daca 
trebuie sa o actualizam).

In final, returnam distanta corespunzatoare orasului destinatie, la care 
adaugam 1 ca sa contorizam si orasul destinatie in sine, pe care nu il 
numaraseram inca.

Complexitate:
- pentru sortarea topologica, complexitatea este de O(n + m), unde n este 
numarul de noduri, iar m este numarul de muchii.
- dupa sortarea topologica, avem partea de calculat distantele de la sursa la 
fiecare nod, ceea ce face fix metoda updateDistances(). Pentru asta, vom avea o
complexitate O(m), deoarece la scoarterea fiecarui nod din stiva avem O(1), iar
pentru fiecare nod scos din stiva parcurgem lista sa de adiacenta. Oricum, 
aceasta complexitate nu va influenta cu nimic complexitatea finala, deoarce 
atunci cand adunam cele 2 complexitati, termenul dominant va fi tot O(n + m).

Asadar, obtinem o complexitate finala de O(n + m).


Problema 3 - Drumuri

Problema ne cere sa gasim submultimea de muchii a caror suma sa fie minima
astfel incat sa existe minim un drum care sa plece din nodul x si sa ajunga in 
z si cel putin un drum care sa plece din y si sa ajunga tot in nodul z. 
Problema principala care se pune este aceea ca pot exista noduri intermediare: 
daca avem un nod comun "a", atunci cand calculam suma costurilor muchiilor, vom
aduna de 2 ori costul aferent distantei de la a la z pentru ca suma muschiilor 
va fi costul asociat drumului de la x la z plus costul drumului de la y la z, 
iar daca 'a' apartine ambelor drumuri, atunci portiunea de la a la z va fi 
comuna, deci ar trebui adunata o singura data, nu de 2 ori (daca am aduna direct
distanta x->z cu distanta y->z, practic am aduna portiunea a->z de 2 ori, pentru
ca x->z = x->a + a->z, iar y->z = y->a + a->z).

Pentru a trata aceasta problema, vom proceda dupa cum urmeaza: analog problemelor
anterioare, construim graful pe masura ce citim muchiile din fisier (instantiem
clasa Graph, diferenta fata de celelalte probleme fiind ca acum tinem in listele
de adiacenta perechi de forma (nod destinatie, costul muchiei de la nodul curent
la nodul destinatie), pentru ca acum conteaza costurile muchiilor. Apoi, logica
programului este concentrata in metoda getResult() din clasa Solution, care va 
intoarce chiar rezultatul. Construim graful inversat (pastram costurile 
muchiilor, dar inversam pentru fiecare muchie nodul destinatie cu nodul sursa).
Aplicam algoritmul dijkstra cu sursa in nodul x, obtinand astfel in distX 
vectorul care tine distantele minime de la nodul X la toate celelalte noduri din
graf. Analog, aplicam dijkstra si pentru sursa y, obtinand in distY vectorul de
distante minime de la nodul sursa Y catre toate celelalte noduri. Nu in ultimul
rand, aplicam dijkstra si din nodul z, dar de data aceasta pe graful inversat, 
pentru a obtine in distZ vectorul de distante minime de la nodul sursa Z catre 
toate celelalte noduri din graful inversat. Drumurile care pornesc din sursa Z 
in graful inversat sunt de fapt drumurile care se termina in Z in graful 
original. Scheletul pentru algoritmul dijkstra l-am luat din solutia oficiala 
a laboratorului 8.
Acum ca avem acesti 3 vectori de distante, ne vom folosi de ei pentru a 
identifica nodurile intermediare in felul urmator: parcurgem toate nodurile 
din graf si verificam pentru fiecare nod daca exista drum de la X la nodul 
respectiv, de la Y la nodul respectiv, precum si de la nodul respectiv la Z
(adica de la Z la nodul respectiv in graful inversat). Sa existe drum se 
traduce prin faptul ca pe pozitia corespunzatoare nodului respectiv in vectorul
de distante se afla o valoare diferita de infinit, adica valoarea de pe pozitia
respectiva a fost la un moment dat pe parcursul algoritmului modificata, nu a 
ramas valoarea default. Asadar, daca gasim un nod intermediar, calculam costul 
total adunand distana de la X la nod, de la Y la nod si de la nod la Z 
(echivalent cu de la Z la nod pe graful inversat). Daca acest cost total este
 mai mic decat costul minim, actualizam variabila minCost. La final, returnam 
costul minim. Obs: Aceasta logica merge si pentru cazul in care nu exista 
noduri intermediare, deoarece distanta de la Z la Z este 0, iar in cazul in 
care drumul de la x la z nu are niciun nod in comun cu drumul de la y la z, 
atunci in costul curent vom retine practic suma dintre distanta de la x la z, 
distanta de la y la z si 0.

Complexitate: Complexitatea algoritmului dijkstra este O(mlogn), unde n 
reprezinta numarul de noduri, iar m reprezinta numarul de muchii. Noi executam 
3 dijkstra-uri, deci ar insemna o compleitate de 3 * m * logn, care se reduce 
la O(mlogn). Dupa aceea, parcurgem din nou toate nodurile si verificam valoarea
corespunzatoare nodului in toate cele 3 array-uri de distante. Parcurgerea 
nodurilor grafurilor introduce o complexitate de O(n), deci complexitatea totala
ar fi suma lor, deci O(mlogn) + O(n), dar termenul dominant este mlogn, deci 
complexitatea finala ramane O(mlogn).
 
