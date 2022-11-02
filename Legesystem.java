import java.util.*; 
import java.io.*; 

public class Legesystem {
    
    //Bruker IndeksertListe slik at vi kan bruke hent-metoden for aa hente spesifikke objekter
    protected static IndeksertListe <Legemiddel> legemiddelliste = new IndeksertListe <Legemiddel> ();
    protected static IndeksertListe <Resept> reseptliste = new IndeksertListe <Resept> ();
    protected static IndeksertListe <Pasient> pasientliste = new IndeksertListe <Pasient> ();
    //Leger skal skrives ut i ordnet rekkefoelge
    protected static Prioritetskoe <Lege> legeliste = new Prioritetskoe <Lege> (); 

    public static void main(String[] args){
        File fil = new File("legedata.txt");
        lesFraFil(fil);
        kommando();
    }

    //E1: Leser fra fil
    public static void lesFraFil(File filnavn){
        //Kaster FileNotFoundException hvis filen ikke blir funnet
        Scanner sc = null;
        try {
            sc = new Scanner(filnavn);
        } catch (FileNotFoundException f){
            System.out.println("Filen ble ikke funnet");
        }
        String info = sc.nextLine();
        while (sc.hasNextLine()){
            //Deler opp "overskriften" i biter
            String[] overskriftbiter = info.split(" ");
            /*Hvis foerste ord etter hashtag er Pasienter, utfoerer vi
            operasjonene paa linjene som kommer etterpaa*/
            if (overskriftbiter[1].equals("Pasienter")){
                while (sc.hasNextLine()){
                    info = sc.nextLine(); //Leser neste linje
                    /*Går ut av loekken naar vi kommer til hashtag, fordi
                    da er vi ferdig med Pasienter*/
                    overskriftbiter = info.split(" ");
                    if (overskriftbiter[0].equals("#")){
                        break;
                    }
                    //Splitter de neste linjene ved komma
                    String[] biter = info.split(",");
                    String navn = biter[0];
                    String fnr = biter[1];

                    //Oppretter ny pasient og legger til i pasientliste
                    Pasient pasient = new Pasient(navn, fnr);
                    pasientliste.leggTil(pasient);
                
                }
            }
            //Gaar videre til overskriften med Legemidler og deler opp denne
            if (overskriftbiter[1].equals("Legemidler")){
                while (sc.hasNextLine()){
                    info = sc.nextLine();
                    overskriftbiter = info.split(" ");
                    if (overskriftbiter[0].equals("#")){
                        break;
                    }
                    String biter[] = info.split(",");
                    String legemiddelnavn = biter[0];
                    String type = biter[1];
                    int pris = Integer.parseInt(biter[2]); 
                    double virkestoff = Double.parseDouble(biter[3]);

                    /*Sjekker hvilken type legemidlene er, og oppretter
                    deretter objektene og legger til i listen med legemidler*/
                    if (type.equals("vanlig")){
                        Vanlig vanlig = new Vanlig(legemiddelnavn, pris, virkestoff);
                        legemiddelliste.leggTil(vanlig);
                    } 
                    else if (type.equals("narkotisk")){
                        int styrke = Integer.parseInt(biter[4]);
                        Narkotisk narkotisk = new Narkotisk(legemiddelnavn, pris, virkestoff, styrke);
                        legemiddelliste.leggTil(narkotisk);
                    } 
                    else if (type.equals("vanedannende")){
                        int styrke = Integer.parseInt(biter[4]);
                        Vanedannende vane = new Vanedannende(legemiddelnavn, pris, virkestoff, styrke);
                        legemiddelliste.leggTil(vane);
                    }
                }
            }
            //Gaar videre til overskriften med Leger, deler opp denne paa samme maate som foer
            if (overskriftbiter[1].equals("Leger")){
                while (sc.hasNextLine()){
                    info = sc.nextLine();
                    overskriftbiter = info.split(" ");
                    if (overskriftbiter[0].equals("#")){
                        break;
                    }
                    String[] legebiter = info.split(",");
                    String legenavn = legebiter[0];
                    String kontrollId = legebiter[1];

                    /*Hvis kontrollId er 0 oppretter vi vanlig lege, ellers
                    oppretter vi spesialist*/
                    if (kontrollId.equals("0")){
                        Lege nyLege = new Lege(legenavn);
                        legeliste.leggTil(nyLege);
                    } else {
                        Spesialist spesialist = new Spesialist(legenavn, kontrollId);
                        legeliste.leggTil(spesialist);
                    }
            }
        }
            //Gaar videre til overskriften med Resepter, deler opp denne
            if (overskriftbiter[1].equals("Resepter")){
                while (sc.hasNextLine()){
                    info = sc.nextLine();
                    overskriftbiter = info.split(" ");
                    if (overskriftbiter[0].equals("#")
                    ){
                        break;
                    }
                    String[] reseptbiter = info.split(",");
                    int legemidNummer = Integer.parseInt(reseptbiter[0]);
                    String legeNavn = reseptbiter[1];
                    int pasientID = Integer.parseInt(reseptbiter[2]);
                    String type = reseptbiter[3];

                    /*Maa finne riktig legemiddel ved aa lete etter legemiddelet 
                    i legemiddelliste som har riktig ID*/
                    Legemiddel tempLegemiddel = legemiddelliste.hent(legemidNummer-1);
                    
                    //Finner riktig lege 
                    Lege tempLege = null;
                    for (Lege l : legeliste){
                        if (l.hentNavn().equals(legeNavn)){
                            tempLege = l;
                            break;
                        }
                    } 
                    //Finner riktig pasient 
                    Pasient pas = pasientliste.hent(pasientID-1);
                    
                    /*Identifiserer hvilken type resept vi har, og bruker skrivResept-metodene 
                    til aa skrive riktig resepter. Legger de inn i listen med resepter*/
                    if (type.equals("hvit")){
                        try {
                        int reit = Integer.parseInt(reseptbiter[4]);
                        Resept resept = tempLege.skrivHvitResept(tempLegemiddel, pas, reit);
                        reseptliste.leggTil(resept);
                        pas.leggTilResept(resept);
                        } catch (UlovligUtskrift u) {
                            System.out.println(u);
                        }
                    }
                    if (type.equals("blaa")){
                        try {
                            int reit = Integer.parseInt(reseptbiter[4]);
                            Resept resept = tempLege.skrivBlaaResept(tempLegemiddel, pas, reit);
                            reseptliste.leggTil(resept);
                            pas.leggTilResept(resept);
                            } catch (UlovligUtskrift u) {
                                System.out.println(u);
                            }
                    }
                    if (type.equals("militaer")){
                        try {
                            Resept resept = tempLege.skrivMilResept(tempLegemiddel, pas);
                            reseptliste.leggTil(resept);
                            pas.leggTilResept(resept);
                            } catch (UlovligUtskrift u) {
                                System.out.println(u);
                            }
                    }
                    if (type.equals("p")){
                        try {
                            int reit = Integer.parseInt(reseptbiter[4]);
                            Resept resept = tempLege.skrivPResept(tempLegemiddel, pas, reit);
                            reseptliste.leggTil(resept);
                            pas.leggTilResept(resept);
                            } catch (UlovligUtskrift u) {
                                System.out.println(u);
                            }
                     }
                 }
            }
        }
        sc.close(); //Lukker scanneren
    }

    //E2: Kommando-metode til bruker, bruker kan trykke u for aa avbryte
    public static void kommando(){
        String scan = " ";
        while(scan != "u"){
            System.out.println("-------Meny-------");
            System.out.println("Tast :");
            System.out.println("1 for aa skrive ut oversikt over leger/pasienter/legemidler/resepter.");
            System.out.println("2 for aa legge til nye leger/pasienter/legemidler/resepter.");
            System.out.println("3 for aa bruke resept.");
            System.out.println("4 for aa skrive ut forskjellige former for statistikk.");
            System.out.println("5 for aa skrive alle data til fil.");

            Scanner sc = new Scanner(System.in); 
            scan = sc.nextLine();

            if(scan.equals("1")){ 
                System.out.println("Du tastet 1");
                skrivUtOversikt(); 
                System.out.println("Trykk u for aa avslutte programmet");
                scan = sc.nextLine();
            }
            else if(scan.equals("2")){
                System.out.println("Du tastet 2");
                lagNy(); 
                System.out.println("Trykk u for aa avslutte programmet");
                scan = sc.nextLine();
            }
            else if(scan.equals("3")){
                System.out.println("Du tastet 3");
                brukResept();
                System.out.println("Trykk u for aa avslutte programmet");                
                scan = sc.nextLine();
            }
            else if(scan.equals("4")){
                System.out.println("Du tastet 4");
                statistikk();
                System.out.println("Trykk u for aa avslutte programmet");
                scan = sc.nextLine();
            }
            else if(scan.equals("5")){ 
                System.out.println("Du tastet 5");
                skrivTilFil(); 
                System.exit(1); 

                System.out.println("Trykk u for aa avslutte programmet");
                scan = sc.nextLine();
            }
            else if(!scan.equals("u")){
                System.out.println("Det du skrev ble ikke gjenkjent");

                System.out.println("Trykk u for aa avslutte programmet");
                scan = sc.nextLine();
            }
            sc.close();
        }
    }

    //E3: Skriver ut en ryddig oversikt over alle elementer i legesystemet
    public static void skrivUtOversikt(){
        System.out.println("\n Info om legemidler:  \n" + legemiddelliste);
        System.out.println("\n -------------------");
        System.out.println("\n Info om pasienter:  \n" + pasientliste);
        System.out.println("\n -------------------");
        System.out.println("\n Info om resepter:  \n" + reseptliste);
        System.out.println("\n -------------------");
        System.out.println("\n Info om leger:  \n" + legeliste); 
        kommando();
    }

    //E4: Lar bruker legge til lege, pasient, resept eller legemiddel
    public static void lagNy(){
        String scan = " ";
        Scanner sc = new Scanner(System.in);
        System.out.println("Hva vil du legge til? \n1. Ny lege \n2. Ny pasient \n3. Ny resept \n4. Nytt legemiddel");
        scan = sc.nextLine();

        //Hvis bruker taster inn 1 skal vi legge til en ny lege
        if (scan.equals("1")){
            String navn = " ";
            String kontrollId = " ";
            System.out.println("Skriv inn legens navn: ");
            navn = sc.nextLine(); //Brukeren legger til navn
            System.out.println("Skriv inn kontrollID: \n(Hvis du vil opprette en vanlig lege kan du skrive inn 0)");
            kontrollId = sc.nextLine();

            /*Hvis bruker oppgir kontrollId = 0 saa oppretter vi en ny Lege, ellers oppretter
            vi en Spesialist*/
            if (kontrollId.equals("0")){
                Lege lege = new Lege(navn);
                legeliste.leggTil(lege);
                System.out.println("Legen ble lagt til");
            } else {
                Spesialist spesialist = new Spesialist(navn, kontrollId);
                legeliste.leggTil(spesialist);
                System.out.println("Legen ble lagt til");
            }
        }

        //Hvis brukeren taster inn 2 skal vi legge til en ny pasient
        else if (scan.equals("2")){
            String pasientnavn = " ";
            String fnr = " ";
            System.out.println("Skriv inn pasientens navn: ");
            pasientnavn = sc.nextLine();
            System.out.println("Skriv inn foedselsnummer: ");
            fnr = sc.nextLine();
            //Sjekker om bruker har skrevet gyldig foedselsnummer
            if (fnr.length() == 11){
                Pasient pasient = new Pasient(pasientnavn, fnr);
                pasientliste.leggTil(pasient);
                System.out.println("Pasienten ble lagt til");
            } else {
                System.out.println("Feil foedselsnummer, husk 11 sifre");
            }
        }

        //Hvis brukeren taster inn 3 skal vi legge til en ny resept
        else if (scan.equals("3")){
            Lege lege = null;
            Legemiddel legemiddel = null;
            Pasient pasient = null;
            Resept resept = null;
            int reit = 0;
            String legMid = " ";
            String legenavn = " ";
            String pasientnavn = " ";
            String reitstring = " ";
            System.out.println("Skriv inn legemiddelnummer: \n");

            //Skriver ut legemidlene slik at brukeren faar en oversikt
            for (Legemiddel l : legemiddelliste){
                System.out.println(l.hentID() + ":" + l.hentNavn());
            }

            legMid = sc.nextLine();
            //Henter legemiddelet med oppgitt ID 
            for (Legemiddel l : legemiddelliste){
                if (l.hentID() == Integer.parseInt(legMid)){
                    legemiddel = l;
                    System.out.println("\nDu har valgt legemiddel " + legemiddel.hentNavn());
                }
            }

            System.out.println("Skriv inn legens navn: \n");
            //Skriver ut legene slik at brukeren faar en oversikt
            for (Lege l : legeliste){
                System.out.println(l.hentNavn());
            }

            legenavn = sc.nextLine();
            //Henter legen med samme navn
            for (Lege l : legeliste){
                if (l.hentNavn().equals(legenavn)){
                    lege = l;
                    System.out.println("\nDu har valgt lege " + lege.hentNavn());
                }
            }

            System.out.println("Skriv inn pasientens navn: \n");
            for (Pasient p : pasientliste){
                System.out.println(p.hentNavn());
            }

            pasientnavn = sc.nextLine();
            //Henter pasienten fra pasientliste
            for (Pasient p : pasientliste){
                if (p.hentNavn().equals(pasientnavn)){
                    pasient = p;
                    System.out.println("\nDu har valgt pasient " + pasient.hentNavn());
                }
            }

            System.out.println("Skriv inn antall reit: \n");
            reitstring = sc.nextLine();
            if (Integer.parseInt(reitstring) <= 0){
                System.out.println("Ugyldig antall reit, resepten kan ikke brukes");
            } else {
                reit = Integer.parseInt(reitstring);
            }

            String type = " ";
            //Spoer brukeren hva slags type resept som skal opprettes
            System.out.println("Hva slags type resept vil du opprette? \n1.Hvit resept \n2.Blaa resept \n3.Militaer resept \n4.P-Resept");
            try {
                type = sc.nextLine();
                if (type.equals("1")){
                    resept = lege.skrivHvitResept(legemiddel, pasient, reit);
                    pasient.leggTilResept(resept);
                    System.out.println("Hvit resept ble lagt til");
                }
                else if (type.equals("2")){
                    resept = lege.skrivBlaaResept(legemiddel, pasient, reit);
                    pasient.leggTilResept(resept);
                    System.out.println("Blaa resept ble lagt til");
                }
                else if (type.equals("3")){
                    resept = lege.skrivMilResept(legemiddel, pasient);
                    pasient.leggTilResept(resept);
                    System.out.println("Militaer resept ble lagt til");
                }
                else if (type.equals("4")){
                    resept = lege.skrivPResept(legemiddel, pasient, reit);
                    pasient.leggTilResept(resept);
                    System.out.println("P-Resept ble lagt til");
                } else {
                    System.out.println("Ugyldig input, velg et tall mellom 1 og 4");
                }
                reseptliste.leggTil(resept); //Legger resepten inn i reseptliste
            } catch (UlovligUtskrift u) {
                System.out.println(u);
            }
        }

        //Hvis brukeren taster inn 4 skal vi legge til et nytt legemiddel
        else if (scan.equals("4")){
            String navn = " ";
            String pris = " ";
            String virkestoff = " ";
            String type = " ";
            System.out.println("Skriv inn legemiddelets navn: \n");
            navn = sc.nextLine();
            System.out.println("Skriv inn pris: \n");
            pris = sc.nextLine();
            System.out.println("Skriv inn mengde virkestoff: \n");
            virkestoff = sc.nextLine();
            System.out.println("Hva slags type legemiddel vil du opprette? \n1.Narkotisk \n2.Vanedannende \n3.Vanlig \n");
            //Hvis brukeren har valgt 1 oppretter vi et narkotisk legemiddel
            type = sc.nextLine();

            if (type.equals("1")){
                System.out.println("Skriv inn styrke: \n");
                String styrke = " ";
                styrke = sc.nextLine();
                Narkotisk narkotisk = new Narkotisk(navn, Integer.parseInt(pris), Double.parseDouble(virkestoff), Integer.parseInt(styrke));
                legemiddelliste.leggTil(narkotisk);
                System.out.println("Legemiddelet ble lagt til");
            }
            //Hvis brukeren har valgt 2 oppretter vi et vanedannende legemiddel
            else if (type.equals("2")){
                System.out.println("Skriv inn styrke: \n");
                String styrke = " ";
                styrke = sc.nextLine();
                Vanedannende vane = new Vanedannende(navn, Integer.parseInt(pris), Double.parseDouble(virkestoff), Integer.parseInt(styrke));
                legemiddelliste.leggTil(vane);
                System.out.println("Legemiddelet ble lagt til");
            }
            //Hvis brukeren har valgt 3 oppretter vi et vanlig legemiddel
            else if (type.equals("3")){
                Vanlig vanlig = new Vanlig(navn, Integer.parseInt(pris), Double.parseDouble(virkestoff));
                legemiddelliste.leggTil(vanlig);
                System.out.println("Legemiddelet ble lagt til");
            }
        } else {
            System.out.println("Ikke godkjent valg, tast inn et nummer mellom 1 og 4");
        }

        kommando(); //Gaar tilbake til hovedmenyen
        sc.close(); //Lukker filen
    }

    //E5: Gir bruker mulighet til aa bruke resept
    public static void brukResept() { 
        Scanner sc = new Scanner(System.in);
        System.out.println("Hvilken pasient vil du se resepter for? (Tast nr)");

            //Looper gjennom og gir bruker liste over pasientene
            for (Pasient p : pasientliste){
                System.out.println("\nPasient " + p.hentId() + " (Navn:)" + p.hentNavn() + "\nFnr: " + p.hentFnr());
            }

            String input = sc.nextLine();
            Pasient pasient = pasientliste.hent(Integer.parseInt(input)-1);
            System.out.println("Valgt pasient: " + pasient.hentNavn() + "\nfnr: " + pasient.hentFnr());
            System.out.println("  ");
            System.out.println("Hvilken resept vil du bruke? (Tast nr)");

            //Hvis pasienten ikke har noen resepter registrert, sier vi ifra om dette
            if (pasient.hentResepter().stoerrelse() == 0){
                System.out.println("Pasienten har ingen resepter");
                kommando();
                sc.close();
            }

            IndeksertListe <Resept> reseptliste1 = new IndeksertListe <Resept> ();
            /*Hvis pasienten har resepter registrert printer vi kun de som er tilgjengelige, 
            ved aa sjekke om pasient-ID-en som brukeren oppgir er lik pasient-ID-en som er
            registrert paa resepten */
            int teller = 1;
            for (Resept r : reseptliste){ //Gir liste med legemidler bruker kan velge 
                if (Integer.parseInt(input) == r.hentPasient().hentId()){
                    System.out.println(teller + " : " + r.hentLegemiddel().hentNavn() + ", reit: " + r.hentReit()); //Med alle dens egenskaper
                    reseptliste1.leggTil(r);
                    teller++;
                }
            } 

            input = sc.nextLine();
            try {
             Resept resept = reseptliste1.hent(Integer.parseInt(input)-1);
            
             //Hvis resepten brukes, bruker vi hent-metoden til aa hente resepten
             if (resept.bruk()){
                  System.out.println("Brukte resept paa " + resept.hentLegemiddel().hentNavn() + ". Antall gjenvaerende reit: " + resept.hentReit());
                } else {
                 System.out.println("Kunne ikke bruke resept paa " + resept.hentLegemiddel() + "(ingen gjenvaerende reit)");
             }  kommando(); //Gaar tilbake til hovedmenyen
                sc.close(); //Lukker filen  
            } catch (UgyldigListeindeks u) {
            System.out.println("Du tastet inn " + input);
            System.out.println("Finnes ingen tilgjengelig resept med dette nummeret");
            kommando(); //Gaar tilbake til hovedmenyen
            sc.close(); //Lukker filen
         }
            
        } 

    //E6: Skriver ut statistikk om elementene i systemet
    public static void statistikk(){

        int antVane = 0; //Teller antall vanedannende resepter
        int antNark = 0; //Teller antall narkotiske resepter

        for (Resept r : reseptliste){
            Legemiddel legmid = r.hentLegemiddel();
            /*Hvis legemiddelet til resepten i reseptliste er instanceof
            Vanedannende eller Narkotisk, saa oeker vi telleren til den
            aktuelle typen*/
            if (legmid instanceof Vanedannende){
                antVane++; 
            }
            else if (legmid instanceof Narkotisk){
                antNark++;
            }
        }
        System.out.println("------Antall vanedannende resepter: " + antVane + "------");
        System.out.println("\n------Antall narkotiske resepter: " + antNark + "------");

        //Statistikk om mulig misbruk av narkotika

        //Legene som har skrevet ut narkotiske legemidler + antall resepter
        int antNarkotiske = 0; //Teller antall narkotiske legemidler
        /*Siden legene i legeliste allerede er i alfabetisk rekkefoelge
        saa trenger vi bare aa lete gjennom listen og printe hver lege
        som har narkotiske resepter*/
        for (Lege l : legeliste){ //For hver lege i legeliste
            for (Resept r : l.hentResept()){ //For hver resept i legens liste
                //Sjekker om resepten er narkotisk
                if (r.hentLegemiddel() instanceof Narkotisk){
                    antNarkotiske++;
                }
            }
            System.out.println("\nLege: " + l.hentNavn() + "\nAntall narkotiske resepter: " + antNarkotiske);
        }

        //Pasientene som har minst en gyldig resept paa narkotiske legemidler
        int pasResept = 0; //Teller antall narkotiske resepter hos pasienten
        /*Itererer over hver pasient og deretter pasientens resepter, sjekker
        videre om resepten er instanceof Narkotisk*/
        for (Pasient p : pasientliste){
            for (Resept r : p.hentResepter()){
                //Gyldig resept hvis reit > 0 og legen som skrev ut resepten er spesialist
                if (r.hentLegemiddel() instanceof Narkotisk && r.hentReit() > 0 && r.hentLege() instanceof Spesialist){
                    pasResept++;
                }
            }
            System.out.println("\nPasient: " + p.hentNavn() + "\nAntall narkotiske resepter: " + pasResept);
        }
        kommando(); //Gaar tilbake til hovedmenyen
    }

    //E7: Gir brukeren mulighet til aa skrive alle elementene til fil
    public static void skrivTilFil(){
        PrintWriter f = null;
        try {
            f = new PrintWriter("nylegedata.txt"); 
        } catch (Exception e) {
            System.out.println("Filen kunne ikke opprettes"); //Gir feilmelding
            System.exit(1); //Gaar ut av systemet
        }

        //Maa ha samme format som innfil-eksempelet i E1

        //Skriver ut pasienter
        f.println("# Pasienter (navn, fnr)");
        for (Pasient p : pasientliste){
            f.println(p.hentNavn() + "," + p.hentFnr());
        }

        //Skriver ut legemidler
        f.println("# Legemidler (navn,type,pris,virkestoff,[styrke])");
        for (Legemiddel l : legemiddelliste){
            /*Vanlig legemiddel har ikke styrke, saa vi sjekker om legemiddelet er
            vanlig eller om det er noe annet (fordi da maa vi inkludere styrke)*/
            if (l instanceof Vanlig){
                f.println(l.hentNavn() + "," + l.hentType() + "," + l.hentPris() + "," + l.hentVirkestoff());
            } else { //Hvis det er narkotisk eller vanedannende
                f.println(l.hentNavn() + "," + l.hentType() + "," + l.hentPris() + "," + l.hentVirkestoff() + "," + l.hentStyrke());
            }
        }

        //Skriver ut leger
        f.println("# Leger (navn,kontrollid / 0 hvis vanlig lege)");
        for (Lege l : legeliste){
            f.println(l.hentNavn() + "," + l.hentKontrollId());
        }

        //Skriver ut resepter
        f.println("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])");
        for (Resept r : reseptliste){
            if (r instanceof MilResept){
                f.println(r.hentLegemiddel().hentID() + "," + r.hentLege().hentNavn() + "," + r.hentPasient().hentId() + "," + r.hentType());
            } else {
                f.println(r.hentLegemiddel().hentID() + "," + r.hentLege().hentNavn() + "," + r.hentPasient().hentId() + "," + r.hentType() + "," + r.hentReit());
            }
        }
        System.out.println("Dataen ble skrevet ut til filen nylegedata.txt");
        f.close(); //Lukker filen
        kommando();
    }
}
