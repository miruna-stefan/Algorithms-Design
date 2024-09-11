STEFAN MIRUNA ANDREEA 324CA

TEMA1 - PA

Problema 1 - PA

Cititm numarul de servere n, iar pe masura ce citim urmatoarele 2 linii 
construim 2 array-uri: prima linie va corespunde unui array de n int-uri, care
vor reprezenta puterile (p-urile), iar a doua linie va corespunde unui alt 
array de n int-uri, care vor reprezenta limitele de alimentare (c-urile). 
Problema ne cere sa gasim cel mai mare minim pentru puterea sistemului (pe care
o voi nota cu "rez" si care pe exemplul din cerinta este 2.5) (corespunzatoare 
unui prag de alimentare convenabil (pe care il voi nota cu "Cx" si care pentru 
exemplul nostru este 5.5). Pentru fiecare server, trebuie sa se respecte 
inegalitatea:
 Pi - |Cx - Ci| >= rez , unde rez trebuie sa fie "minimul maxim" (adica cea mai
mare valoare pentru care se respecta inegalitatea).

Acest rez este o putere, deci va avea o valoare cuprinsa in intervalul 
[-10^9; 10^9], deoarece se precizeaza in cerinta ca puterea este limitata la 
10^9. Dar din inegalitatea de mai sus rezulta ca Pi - rez >= |Cx - Ci|, iar 
modulul este mereu pozitiv, deci si Pi - rez trebuie sa fie pozitiv, ceea ce 
inseamna ca rez <= Pi, deci rez trebuie sa fie mai mic decat cea mai mica 
putere din vectorul de puteri => rez apartine [-10^9; minPower]. Pe acest 
interval vom face cautare binara.

Prelucram inegalitatea de mai sus:
	Pi - |Cx - Ci| >= rez <=>
	Pi - rez >= |Cx - Ci| <=>
	|Cx - Ci| <= Pi - rez <=>
	- (Pi - rez) <= Cx - Ci <= Pi - rez <=>
	Ci - Pi + rez <= Cx <= Ci + Pi - rez
	
Consideram rez fixat (initial il luam la jumatatea intervalului, apoi ne mutam 
la jumatatea intervalului ramas in stanga sau in dreapta si tot asa, in functie
de ce urmeaza sa explic). Noi vrem ca acest Cx sa existe, nu ne intereseaza in 
mod special valoarea lui. Ca sa existe acest Cx, trebuie ca cea mai mare 
valoare din stanga inegalitatii (pentru Ci - Pi + rez) sa fie mai mica decat 
cea mai mica valoare din dreapta (pentru Ci + Pi - rez). Daca se respecta 
aceasta conditie, inseamna ca exista Cx, deci incercam sa gasim un rez mai mic,
motiv pentru care continuam cautarea in intervalul ramas in stanga, adica mutam
capatul din dreapta al intervalului la mijloc. Daca, insa, nu se respecta 
conditia, inseamna ca nu exista un astfel de Cx, deci am ingradit prea mult 
intervalul lui rez. Asta inseamna ca trebuie sa cautam un rez mai mare, deci ne
mutam in intervalul ramas in dreapta, adica mutam capatul din stanga al 
intervalului de cautare in locul fostului mijloc si tot asa pana la o anumita 
precizie.
 
Obs: Pentru ca folosesc de foarte multe ori sumele Ci + Pi si diferentele 
Ci - Pi, am construit un vector "differences" de n elemente, in care stochez 
toate aceste diferente si unul "sums", tot de n elemente, in care stochez 
sumele.

Complexitatea temporala este O(nlog(10^9 + minPower)), pentru ca noi cautam 
rez-ul facand cautare binara in intervalul [-10^9; minPower], de unde ne vine 
log(10^9 + minPower), iar pentru fiecare rez parcurgem toate cele n servere 
ca sa cautam acel minim si maxim.
Complexitatea spatiala este O(n) pentru ca tinem doi vectori, unul de p-uri si 
unul de c-uri, ambii de lungime n, deci in total ar fi un O(2n), adica O(n).

Problema 2 - Colorare

Vom separa inputul in doua array-uri de k (numarul citit de pe prima linie) 
elemente: un array de intregi care va stoca numerele de zone consecutive cu 
aceeasi orientare si un array de caractere H sau V, care indica orientarile 
corespunzatoare acestor zone. Astfel, elementul de pe pozitia i din vectorul de
zone va tine numarul de zone (vericale sau orizontale) dintr-un grup (prin grup
de zone ma refer la o secventa de zone consecutive care au aceeasi orientare) 
si se va "asocia" practic cu elementul de pe aceeasi pozitie i din vectorul de 
orientari, care indica orientarea (orizontala (H) sau verticala (V)) a grupului
corespunzator de zone.

Folosind programare dinamica, acest index i se va asocia si cu pozitia din 
vectorul dp, tot de lungime k, care va stoca numarul de combinatii posibile in 
care putem colora dreptunghiurile din chunk-ul corespunzator index-ului 
respectiv astfel incat sa nu existe 2 laturi adiacente colorate cu aceeasi 
culoare. Incepem sa populam vectorul dp uitandu-ne la prima zona (zones[0]). In
functie de orientarea acesteia, initializam primul element din dp (dp[0]):

	-Daca prima zona este orizontala, vom initializa dp[0] cu 6, deoarece o
	zona orizontala este compusa din 2 dreptunghiuri orizontale asezate 
	unul peste celalalt. Cum nu exista inca alti vecini, singura conditie 
	care trebuie respectata este ca cele 2 dreptunghiuri sa nu aiba aceeasi
	culoare, asa ca daca dreptunghiul de deasupra are culoarea roz, cel de 
	dedesubtul lui va putea avea doar galben sau mov (2 posibilitati), daca
	cel de sus va fi galben, cel de jos va putea fi roz sau mov, iar daca 
	cel de sus va fi mov, cel de jos va putea fi galben sau roz. Prin 
	urmare, cel de sus va putea lua 3 culori, iar pentru fiecare din aceste
	3 cazuri, cel de jos va putea lua una dintre cele 2 culori ramase 
	nefolosite, de unde rezulta un total de 2 * 3 = 6 moduri de a colora 
	prima zona orizontala.
	
	- Daca prima zona este verticala, lucrurile sunt mai simple in sensul 
	ca vom aseza un singur dreptunghi in picioare. Acesta este primul, deci
	nu are vecini de care sa ne facem griji ca ar putea avea aceeasi 
	culoare ca si el, deci nu avem nicio restrictie: il putem colora in 
	oricare din cele 3 culori disponibile, deci vom initializa dp[0] cu 3.

Mai este un aspect de luat in calcul, si anume faptul ca primul chunck de zone 
va putea avea mai multe zone, nu numai una singura (elementul de pe pozitia 
zones[0] poate sa fie mai mare strict decat 1). In acest caz trebuie sa 
actualizam dp[0] astfel incat sa cuprindem si combinatiile pentru zonele ramase
(prima dintre zonele ramase se va invecina la stanga cu zona pe care deja am 
considerat-o si la dreapta cu urmatoarea zona "nevizitata" si tot asa, 
formandu-se un lant de vecini). Astfel,
	-daca orientarea asociata zonelor de pe pozitia 0 va fi orizontala 
	(orientations[0] == 'H'), va trebui sa actualizam dp[0] astfel: sa il 
	inmultim cu 3 la puterea numarul de zone invecinate ramase in chunk-ul 
	de pe pozitia 0 (fara prima zona, pe care am luat-o deja in 
	considerare). Baza acestei puteri, 3, vine de la faptul ca, atunci cand
	avem de tratat o adiacenta de tipul H-H (lipim o zona orizontala de o 
	alta zona orizontala), noi vom lipi de fapt 2 perechi de dreptunghiuri 
	(deoarece o zona orizontala inseamna de fapt 2 dreptunghiuri orizontale
	puse unul peste altul). Avem la dispozitie 3 culori: r, g, m. Daca 
	ultima zona pe care o pusesem avea dreptunghiul de sus r si cel de jos 
	m, atunci cand adaugam o noua pereche de dreptunghiuri orizontale, vom 
	putea avea dora 3 combinatii de culori: sus m si jos r (culorile de 
	dianinte inversate), sus m si jos g sau sus g si jos r (adica atunci 
	cand lipim o noua zona, putem pune pe dreptunghiul de sus culoarea 
	dreptunghiului de jos al zonei anterioare, combinata cu oricare dintre 
	cele 2 culori ramase - de aici avem 2 optiuni- sau putem pune pe pe 
	dreptunghiul de sus cea de-a treia culoare (care nu a fost folosita 
	in zona anterioara), iar pe dreptunghiul de jos culoarea dreptunghiului
	de sus din zona anterioara - de aici vine cea de-a treia optiune).
	
	- daca orientarea asociata zonelor de pe pozitia 0 va fi verticala 
	(orientations[0] == 'V'), va trebui sa actualizam dp[0] astfel: sa 
	il inmultim cu 2 la puterea numarul de zone invecinate ramase in 
	chunk-ul de pe pozitia 0 (fara prima zona, pe care am luat-o deja in 
	considerare). Baza acestei puteri, 2, vine de la faptul ca, atunci cand
	avem de tratat o adiacenta de tipul V-V (lipim o zona verticala de o 
	alta zona verticala), nu vom avea decat 2 variante de a aseza 
	dreptunghiul vertical langa dreptunghiul vertical anterior, si anume 
	cele 2 culori ramase (noi avem la dispozitie 3 culori, pe una o are 
	deja dreptunghiu vertical anterior, deci ne mai raman 2).
	
Dupa ce am terminat cu dp[0], avem baza pentru a construi (bottom-up) vectorul 
dp. Pe pozitia i-1, vom stoca numarul de posibiliti in care putem alatura toate
primele i zone astfel incat sa nu avem 2 laturi adiacente colorate la fel. 
Pentru fiecare noua zona, ne uitam atat la orientarea ei, cat si la orientarea 
zonei de dinainte si verificam ce fel de adiacenta avem:

	- H-H: pentru a obtine dp[i], vom inmulti dp-ul anterior cu 3 la 
	puterea cate zone consecutive de acelasi fel avem in chunck-ul curent 
	(am explicat mai sus de ce baza puterii este 3 in acest caz)
	
	- V-V: pentru a obtine dp[i], vom inmulti dp-ul anterior cu 2 la 
	puterea cate zone consecutive de acelasi fel avem in chunck-ul curent 
	(am explicat mai sus de ce baza puterii este 3 in acest caz)
	
	- V-H: deja intram in cazurile alaturarii unor zone de orientari 
	diferite, care necesita un pic mai multa atentie, deoarece daca avem 
	mai multe zone consecutive in chunck-ul curent (zones[i] > 1), doar 
	prima va fi in cazul de adiacenta V - H, pentru ca dupa ce o asezam pe 
	prima, fiecare dintre urmatoarele zone se va afla in cazul de adiacenta
	H-H cu cea de dinaintea ei. Astfel, doar pentru prima zona din chunck 
	vom inmulti dp-ul anterior cu 2 (pentru ca in cazul de adiacenta V - H,
	noi avem deja un dreptunghi vertical (colorat cu o culare din cele 3) 
	si vrem sa adaugam langa acesta o zona orizontala, adica 2 
	dreptunghiuri orizontale puse unul deasupra celuilalt, deci pentru 
	acestea 2 raman doar doua culori, pe care le putem aranja oricum (fie 
	punem sus culoarea ramasa 1 si jos culoarea ramasa 2, fie punem sus 
	culoarea ramasa 2 si jos culoarea ramasa 1). Deci, ne raman 2 
	posibilitati de a aseza culorile. Dupa ce terminam cu prima zona din 
	chunck, ramanem doar cu adiacente H-H, pentru care am explicat deja 
	algoritmul (vom inmulti cu puterea lui 3).
	
	-H-V: din nou avem un caz de alaturare a unor zone cu orientari 
	diferite, deci daca avem mai mult de o zona in grupul curent, o vom 
	trata mai intai pe prima, care se afla in cazul (V-H) si apoi pe toate
	celelalte (care se vor afla in cazul V-V). Prima, adiacenta V-H, este 
	cea mai simpla, deoarece ramane acelasi dp ca pe pozitia anterioara. 
	Explicatia: noi avem deja o zona cu 2 dreptunghiuri orizontale asezate 
	unul peste celalalt, in care fiecare trebuie sa fie colorat cu o 
	culoare diferita, deci cand vrem sa adaugam un dreptunghi vertical 
	langa acestea doua, nu mai avem decat o culoare disponibila din cele 3,
	deci nu avem nicio alegere de facut. Pentru zonele ramase, aplicam 
	cazul de adiacenta V-V, pe care l-am explicat mai sus si care consta in
	inmultirea cu puterea lui 2.
	
La final, returnam elementul de pe ultima pozitie din dp, adica numarul total 
de configuratii distincte care se pot forma. Complexitatea va fi de O(n) pentru
ca parcurgem vectorul dp, iar pentru fiecare ridicare l putere mai adaugam un
log, deci complexitatea temporala ar fi O(nlogn) si spatiala O(n).

Bonus:
Pentru bonus, a trebuit sa optimizez modul de calcul al puterii si am venit cu 
functia performPower, care calculeaza puterea in timp logaritmic. In spiritul 
ideii de divide et impera, se verifica paritatea exponentului: daca este par, 
rezultatul final se poate scrie ca produsul dintre baza ridicata la puterea 
(exponent / 2) si el insusi inca o data, deci este mai eficient sa ridicam baza
doar la jumatate din exponent si apoi la final sa inmultim rezultatul cu el 
insusi pentru a obtine puterea la exponentul initial, asa ca functia se 
apeleaza recursiv pentru aceeasi baza, dar exponentul impartit la 2. Daca 
exponentul este impar, rezultatul se poate scrie ca inainte, dar se mai 
inmulteste inca o data la final cu baza in sine (pentru ca exponentul se poate
scrie sub forma 2k + 1). Deci se procedeaza la fel, se apeleaza recursiv 
functia de putere pentru aceeasi baza, dar exponentul injumatatit, insa pe 
acest caz se mai face un pas la final, se mai inmulteste o data rezultatul cu
baza.

Problema 3 - Compresie

Pentru a rezolva aceasta problema, trebuie sa afisam numarul de elemente din 
vectorul final, nu vectorul in sine, asa ca eu in loc sa formez si sa stochez 
in memorie intregul vector-rezultat, voi tine doar o variabila de tip counter 
pe care o voi incrementa de fiecare data cand ar trebui sa adaug un nou element
in array-ul rezultat. Astfel, in final aceasta variabila va stoca fix numarul 
de elemente din vectorul-solutie. 
Vom parcurge cele 2 array-uri in paralel, formand niste sume partiale pe masura
ce avansam in vectori. Mai concret: vom avea o variabila care tine suma 
partiala in vectorul a si una pentru suma partiala din vectorul b. Parcurgem cu
indecsi diferiti cei doi vectori de la inceput si comparam suma dintre 
elementul de pe pozitia curenta din vectorul a si suma partiala de pana atunci 
din vectorul a cu suma dintre elementul de pe pozitia curenta din vectorul b si
suma partiala de pana atunci din vectorul b.

	- Daca prima e mai mica decat a doua, adaugam elementul curent din a la
	suma partiala din vectorul a si avansam in vectorul a (in timp ce in 
	vectorul b ramanem pe aceeasi pozitie)
	
	- Daca prima e mai mare decat a doua, adaugam elementul curent din b la
	suma partiala din vectorul b si avansam in vectorul b (in timp ce 
	ramanem pe aceeasi pozitile in vectorul a)
	
	- Daca sunt egale, inseamna ca am construit o "compresie" corecta si 
	atunci aceasta poate fi adaugata in vectorul final, se reseteaza sumele
	partiale si se inainteaza in ambii vectori
	
Daca am epuizat doar unul dintre vectori, iar in celalalt au ramas elemente 
"nevizitate", inseamna ca cele doua array-uri nu pot fi comprimate la fel (nu 
se pot obtine 2 siruri egale). In acest caz, returnam -1. Si in cazul in care 
variabila contor a ramas pe 0, inseamna ca vectorul final nu are niciun 
element, deci, din nou, returnam -1.

Complexitatea este de O(n + m), intrucat noi parcurgem cei doi vectori in paralel,
unde primul vector are n elemente, iar al doilea are m elemente. Si complexitatea
spatiala este tot O(n + m).


Problema 4 - Criptat

Dupa ce am citit numarul de cuvinte, am construit un ArrayList de n String-uri
"words", pe care l-am populat cu cuvintele date pe masura ce le 
citeam. Pentru a retine frecventa fiecarei litere in fiecare cuvant am construit
un alt ArrayList ("frequencies"), care, pe pozitia corespunzatoare unui anumit 
cuvant din "words", retine un hashmap care face asocierea dintre litera si 
numarul sau de aparitii in cuvantul respectiv. De asemenea, pe masura ce 
parcurg vectorul de cuvinte pentru a popula vectorul de hashmap-uri pentru 
frecvente, construiesc si un hashset in care sa stochez toate literele 
distincte din toate cuvintele (care vor fi oricum maxim 8, conform enuntului 
problemei). Tot in acest timp, calculez si numarul total de litere din toate
cuvintele din input.

Acum ca avem toate aceste structuri de date populate, putem incepe logica 
propriu-zisa a problemei, care se bazeaza pe programare dinamica, prin analogie
cu problema rucsacului de tip 0 / 1 (fara fractiuni). Vom crea cate o matrice 
dp pentru fiecare litera din hashset-ul care tine toate literele distincte din
toate cuvintele din text. Prin analogie cu rucsacul, frecventa in cuvantul 
curent a literei pentru care am facut matricea va fi echivalentul profitului 
(valorii) pentru obiectul pus in rucsac (in cazul nostru cuvantul pus in 
parola), iar lungimea cuvantului curent este echivalentul greutatii obiectului
curent. De asemenea, lungimea totala a parolei este echivalentul greutatii 
totale a sacului.
Pentru fiecare litera din hashset, vom retine frecventa maxima a acelei litere
in intreaga parola (variabila "maxFreqForCurrLetter") si lungimea parolei 
corespunzatoare, adica a celei mai lungi parole care s-ar putea forma 
considerand litera curenta ca fiind litera dominanta a parolei (variabila
"passwdLengthForCurrLetter"). Pe masura ce construim matricea pentru fiecare
litera din hashset, actualizam si aceste 2 variabile. Obs: Actualizam 
variabilele doar atunci cand se schimba valoarea dp[i][j] si este respectata 
conditia ca frecventa maxima a literei (adica numarul de aparitii ale literei 
in parola) sa fie mai mare decat jumatate din lungimea parolei. Dupa ce am 
terminat de construit matricea dp pentru litera curenta, vom avea frecventa 
maxima a literei pe care am considerat-o dominanta in parola si lungimea 
parolei, insa parola pe care am construit-o noi va fi formata doar din cuvinte 
care contin aceasta litera dominanta (litera din hashset pentru care am construit
matricea), intrucat cuvintele care au avut frecventa literei curente 0 au fost 
complet ignorate, nu au fost incluse in parola (prin analogie, in problema 
rucsacului, obiectele cu profit 0 nu sunt bagate in rucsac, nu sunt luate deloc
in seama pentru ca nu cresc cu nimic valoare rucsacului, care trebuie sa fie 
maxim).
Noua, in schimb, ne trebuie cea mai lunga parola posibila, deci trebuie sa 
includem si cuvintele care nu contin litera dominanta, pe care le putem adauga 
la parola fara a strica conditia ca frecventa literei dominante trebuie sa fie 
mai mare decat jumatate din lungimea parolei. Asadar, pentru a maximiza 
lungimea parolei, parcurgem cuvintele care nu contin litera dominanta, 
verificam daca s-ar respecta in continuare conditia precizata anterior daca 
le-am adauga in parola si in caz afirmativ, le adaugam in parola, crescand 
astfel lungimea parolei cu lungimea cuvantului pe care tocmai l-am adaugat. 
Obs: pentru a fi siguri ca luam cuvintele cu lungime maxima, ele trebuie sa fie
sortate descarescator dupa lungime. (Sa zicem ca noi am mai avea spatiu sa 
adaugam 3 litere in parola fara sa stricam conditia. Daca nu ar fi sortate 
descrescator, am putea sa gasim mai intai un cuvant de 2 litere care s-ar 
potrivi, iar dupa sa gasim si unul de 3 care s-ar potrivi, dar noi nu am mai 
avea spatiu pentru cel de 3 pentru ca am fi umplut deja cele 2 litere cu 
cuvantul anterior gasit. Noua ne trebuie lungimea maxima, asa ca ar fi trebuit 
sa il adaugam pe cel de 3 litere.)

Problema rucsacului are complexitatea O(n * w), adica se inmultesc dimensiunile
matricei dp, in cazul nostru n (numarul de cuvinte) * lungimea maxima a parolei
(adica suma lungimilor celor n cuvinte), despre care se precizeaza in cerinta 
ca nu va depasi 10^4. Noi, insa vom face cate o matrice pentru fiecare litera 
distincta din input, care in cel mai rau caz vor fi 8, deci ar fi 
O(8 * n * 10^4), dar putem neglija acest 8 pt ca e o constanta. Noi, in plus,
trebuie sa mai parcurgem cuvintele inca o data dupa ce terminam de completat 
matricea pentru a le adauga pe cele care nu contin litera dominanta, deci de 
aici ni se mai inmulteste un n, rezultand complexitatea O(n^2 * 10^4).

Problema 5 - Oferta

In clasa Solution5, se afla o metoda publica getResult, care intoarce un 
rezultat de tip double. Rezolvarea se bazeaza pe programare dinamica, intrucat 
tin un vector de n + 1 double-uri dp (n + 1 (si nu n) pentru ca incep indexarea
de la 1, unde n este numarul de produse de pe banda), care stocheaza pretul 
minim cu care putem cumpara produsele de pe banda de pana atunci. Pentru un 
produs, noi avem 3 posibilitati de a-l cumpara:
	1. sa il cumparam singur, de sine statator, fara a fi inclus in vreo 
	oferta
	2. sa il cumparam ca membru al unei oferte de 2 produse
	3. sa il cumparam ca membru al unei oferte de 3 produse
	
Pentru fiecare produs, noi trebuie sa vedem care este cea mai avantajoasa 
modalitate de a-l cumpara in functie de produsele de dinainte. Asadar, pentru 
fiecare nou produs, calculam care ar fi pretul daca l-am cumpara singur, daca 
l-am cumpara in oferta cu produsul de pe pozitia anterioara (dp[i - 1]), 
respectiv in oferta cu cele 2 de pe pozitiile anterioare (dp[i - 1] si 
dp[i - 2]) si apoi alegem minimul dintre aceste preturi si il punem pe pozitia
curenta din vectorul dp. Astfel, pe fiecare pozitie a vectoului dp vom tine cel
mai mic pret posibil pentru a cumpara produsele de pana atunci (pe dp[i] este 
pretul minim cu care pot fi cumparate primele i produse).

Indexarea incepe de la 1 pentru ca indexul reprezinta numarul de produse 
consecutive cumparate si ar fi fost nenatural sa calculam pretul pentru 0 
produse. Pentru primul produs, nu avem alte produse inainte cu care sa-l 
combinam (deci unica modalitate prin care il putem cumpara este de unul 
singur), asa ca vom pune pe prima pozitie din dp direct pretul primului produs
de pe banda. Pentru al doilea produs, avem doar 2 variante: singur sau combinat
cu primul produs in oferta de 2. In mod evident, este mai ieftina a doua 
varianta (pentru ca unul dintre ele (cel mai ieftin) va avea reducere) decat sa
le luam separat (pentru ca am plati pret intreg pentru amandoua). Prin urmare, 
nici nu mai are rost sa facem vreo comparatie, ci o sa punem direct pretul 
variantei cu oferta.

Complexitatea este de O(n).
