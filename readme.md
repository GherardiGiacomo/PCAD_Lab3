# PCAD 23/24 - Laboratorio 3

Negli esercizi seguenti proponiamo di programmare il problema dei lettori/scrittori in Java. Per ciascuno degli esercizi, provare ad osservare il comportamento del programma ottenendo diversi set di esecuzione (potete addormentare i thread per un attimo grazie alla funzione statica sleep della classe Thread).

# Esercizio 1

In una prima versione, ci interessiamo solo alla concorrenza senza sincronizzazione ne mutua esclusione.

1. Scrivere una classe RWbasic contenendo un campo intero privato data inizializzato a 0 al quale l’accesso è fatto senza mutua esclusione da due funzione:
       - read chi ritorna il valore didata, e,
       - write chi aumenta di 1 il valore didatain vari fasi:
   (a) mette il valore didatain una variabile temporaneatmp;
   (b) aumenta di 1 il valore ditmp;
   (c) mette il valore ditmpindata.

2. Scrivere una classeReader che implementa l’interfaccia Runnable. Il costruttore di questa classe riceve come argomento un oggetto della classe RWbasic. Nel suo codice eseguibile (funzione run), chiama la funzione read associata e stampa il valore della data.
3. Scrivere una classe Writer che implementa l’interfaccia Runnable. Il costruttore di questa classe riceve come argomento un oggetto della classe RWbasic. Nel suo codice eseguibile (funzionerun), chiama la funzione write associata.
4. Scrivere un programma principale che crea un oggetto di classeRWbasice crea un certo numero di thread reader e writer (ad esempio 50 di ciascuno). Osservare, ad esempio mettendo i thread insleepin mezzo alla scrittura, che al termine dell’esecuzione il valore del data non è uguale al numero di writers (non esitate ad utilizzare la funzione join della classe Thread per aspettare la fine di un thread). Non esitare a dare un identità ai thread per potere capire meglio l’esecuzione ottenuta.

# Esercizio 2

In una seconda versione, ci interessiamo alla mutua esclusione.

1. Scrivere una classe RWexclusive che estende RWbasic rendendo esclusivo l’accesso alla data.
2. Modificare il codice gia scritto delle classe Reader,Writer e del programma principale perché usano la classe RWexclusive al posto di RWbasic.
3. Osservare il comportamento del programma come nel esercizio precedente. Il problema sollevato dovrebbe essere risolto.

# Esercizio 3

La terza versione che vi chiediamo è una versione dove più lettori possono accedere alla data allo stesso tempo ma invece gli scrittori devono essere in mutua esclusione e se dei lettori stanno leggendo la data, gli scrittori non possono accederci.

1. Scrivere una classeRWche estendeRWbasice verifica le proprietà date qui sopra. Per fare questo, potete usare un contattore che conta i lettori e dividere la funzione read in varie sotto funzione. In più è consigliata di usare waitenotify per svegliare gli scrittori che aspettano la fine della lettura dei lettori.

2. Osservare il comportamento del programma modificato.

# Esercizio 4

Modificare il codice precedente con una classe RWext che garantisce che ogni valore scritta e letta da al meno un lettore.
