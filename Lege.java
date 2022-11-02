public class Lege implements Comparable<Lege>{
    
    protected String navn;
    protected IndeksertListe<Resept> utskrevneResepter = new IndeksertListe<>();

    //Konstruktoer
    public Lege(String navn){
        this.navn = navn;
    }

    //Sorterer legene alfabetisk etter navn
    @Override
    public int compareTo(Lege lege) {
        return this.navn.compareTo(lege.hentNavn());
    }

    //Henter listen med resepter
    public IndeksertListe<Resept> hentResept(){
        return this.utskrevneResepter;
    }

    /*Hvis en vanlig lege skriver ut narkotisk legemiddel, kastes unntaket
    UlovligUtskrift*/
    public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int
    reit) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk){
            throw new UlovligUtskrift(this, legemiddel);
        }
        HvitResept hvit = new HvitResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(hvit);
        return hvit;
    }

    /*Samme tanke som med HvitResept, vanlig lege kan ikke skrive ut
    narkotisk legemiddel*/
   public MilResept skrivMilResept(Legemiddel legemiddel, Pasient pasient) throws
    UlovligUtskrift{
        if (legemiddel instanceof Narkotisk){
            throw new UlovligUtskrift(this, legemiddel);
        } 
        MilResept mil = new MilResept(legemiddel, this, pasient);
        utskrevneResepter.leggTil(mil);
        return mil;
    }

    /*Samme tanke som med HvitResept, vanlig lege kan ikke skrive ut
    narkotisk legemiddel*/
    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient, int reit)
    throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk){
            throw new UlovligUtskrift(this, legemiddel);
        }
        PResept p = new PResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(p);
        return p;
    }

    /*Hvis legemiddelet er narkotisk men legen ikke er en Spesialist, 
    kan ikke BlaaResept utskrives og vi maa kaste UlovligUtskrift*/
    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int
    reit) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk){
            if (!(this instanceof Spesialist)){
                throw new UlovligUtskrift(this, legemiddel);
            } 
        }
        BlaaResept blaa = new BlaaResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(blaa);
        return blaa;
    }

    public String hentNavn(){
        return navn;
    }

    public String hentKontrollId(){
        return "0";
    }

    //Overskriver toString-metoden for aa printe relevant info
    public String toString(){
        return "\nLegens navn: " + navn;
    }
}
