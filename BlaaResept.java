class BlaaResept extends Resept {

    //Konstruktoer
    public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege,
    Pasient pasient, int reit){
        super(legemiddel, utskrivendeLege, pasient, reit);
    }
    
    public String farge(){
        return "Blaa";
    }

    @Override
    public String hentType(){
        return "blaa";
    }

    /*Prisen er 25 prosent av legemiddelets pris, og vi skal
    typekonvertere den nye prisen til int slik at den rundes av*/
    public int prisAaBetale(){
        return (int) (etLegemiddel.hentPris()*0.25); 
    }

    //Overskriver toString-metoden for aa printe relevant info
    //Bruker super sin toString() + mer info
    public String toString(){
        return super.toString() + "\n Farge: " + farge() + "\n Pris: 25% av pris";
    }
}
