class MilResept extends HvitResept {

    //Konstruktoer
    public MilResept(Legemiddel legemiddel, Lege utskrivendeLege,
    Pasient pasient){ //Tar ikke inn reit i konstruktoeren
        //Utskrives med 3 reit
        super(legemiddel, utskrivendeLege, pasient, 3); 
    }

    public int prisAaBetale(){
        return 0; //Gratis resept
    }

    @Override
    public String hentType(){
        return "militaer";
    }

    @Override
    public int hentReit(){
        return 0;
    }

    //Overskriver toString-metoden for aa printe relevant info
    //Bruker super sin toString() + mer info
    public String toString(){
        return super.toString() + "\n Resepttype: MilResept" + "\n Pris: Gratis";
    }
}
