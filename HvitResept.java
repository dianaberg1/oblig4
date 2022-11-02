class HvitResept extends Resept{

    //Konstruktoer
    public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege,
    Pasient pasient, int reit){
        super(legemiddel, utskrivendeLege, pasient, reit);
    }

    public String farge(){
        return "Hvit";
    }

    public int prisAaBetale(){
        return etLegemiddel.hentPris();
    }

    @Override
    public String hentType(){
        return "hvit";
    }

    //Overskriver toString-metoden for aa printe relevant info
    //Bruker super sin toString() + mer info
    public String toString(){
        return super.toString() + "\n Farge: " + farge();
    }

}