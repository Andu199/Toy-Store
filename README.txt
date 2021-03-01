Student: Boldisor Dragos-Alexandru
Grupa: 322CB


Tema 2 este compusa din: fisierele .java (in src) cu clasele aferente, Tema2.jar
care contine intreaga functionalitate a temei (se poate rula:
java -jar Tema2.jar < input.txt > output.txt ), folderele TestX (X de la 1 la 5)
care contin inputul cu comenzile aplicate si outputul din consola precum si
fisierele create (.csv si store.ser) si acest README.

Pentru Bonus:
- am folosit suplimentar Design Pattern-ul Facade cu ajutorul clasei Facade.
- am folosit Apache Commons CSV Reader: https://www.baeldung.com/apache-commons-csv.
- am implementat comenzile savestore si loadstore.

Clasele folosite:

I. Main
Clasa care contine doar metoda main. Metoda va crea un nou element facade, va
adauga Euro ca si currency curent si, cu ajutorul unui while(true), va citi
fiecare comanda din consola si va chema metoda aferenta.

II. Facade
Clasa ale carei metode vor fi chemate in main. Fiecare metoda reprezinta o
comanda suportata de program. In majoritatea metodelor este chemata instanta
Singleton de Store si cu ajutorul metodelor implementate in Store, se produce
functionalitatea specifica comenzii primite. De asemenea, se va face o
manipulare asupra stringului primit ca input daca este cazul.

III. Store
Clasa Store implementata cu Design Pattern-ul Singleton. Are ca atribute o
instanta privata (specific Design Pattern-ului), numele (String), currency-ul
curent, precum si ArrayList-urile products (pentru produsele existente in store),
manufacturers (pentru producatori), currencies (retine toate monezile) precum si
discounts care retine toate discounturile salvate in store. Constructorul privat
va initializa aceste ArrayList-uri. Metodele private formatPrice si
formatQuantity primesc un string si returneaza un float reprezentand pretul
respectiv un int reprezentand cantitatea. Aceste metode sunt folosite in readCSV
unde se va citii cu ajutorul functionalitatilor din biblioteca Apache Commons
fisierul .csv. Cu ajutorul ProductBuilder-ului sunt create produsele si
introduse in ArrayList. Metoda writeCSV afiseaza cu ajutorul CSVPrinter toate
produsele. Metodele saveStore si loadStore sunt folosite pentru citirea si
printarea in binar (toate clasele necesare care vor fi citite si scrise in
binar implementeaza Serializable). Metodele addProduct si addManufacturer adauga
produse/producatori, iar getProductsByManufacturer returneaza un ArrayList cu
produsele producatorului aferent. Metoda changeCurrency schimba moneda curenta
si cu ajutorul metodei changePrice se schimba preturile tuturor produselor.
Metoda applyDiscount aplica discountul aferent primit ca argument.

Restul claselor implementeaza functionalitatile prezentate in enunt.
