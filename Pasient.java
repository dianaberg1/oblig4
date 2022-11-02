public class Pasient {
    
    protected String navn;
    protected String foedselsNummer;
    protected int id;
    protected static int idCount = 0;
    protected Stabel<Resept> reseptListe = new Stabel<>();

    public Pasient(String navn, String foedselsNummer){
        this.navn = navn;
        this.foedselsNummer = foedselsNummer;
        idCount++;
        id = idCount;
        
    }

    //Hjelpemetode for aa hente pasientens unike id
    public int hentId(){
        return id;
    }

    //Hjelpemetode for aa returnere pasientens navn
    public String hentNavn(){
        return navn;
    }

    //Hjelpemetode for aa returnere pasientens foedselsnummer
    public String hentFnr(){
        return foedselsNummer;
    }

    //Metode for aa legge til resept, bruker leggTil-metoden i Stabel
    public void leggTilResept(Resept resept){
        reseptListe.leggTil(resept);
    }

    //Returnerer reseptlisten til pasienten
    public Stabel<Resept> hentResepter(){
        return reseptListe;
    }

    /*Maatte legge til denne for aa bruke metoden for lesing av fil
    i Legesystem*/
    public int compareTo(Pasient pasient) {
        return 0;
    }

    public String toString(){
        return "\n Pasientens navn: " + navn + "\n Foedselsnummer: " + foedselsNummer + "\n Pasient-Id: " + id;
    }

}
