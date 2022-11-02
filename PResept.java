class PResept extends HvitResept {

    //Konstruktoer
    public PResept(Legemiddel legemiddel, Lege utskrivendeLege,
    Pasient pasient, int reit){
        super(legemiddel, utskrivendeLege, pasient, reit);
    }
    
    /*Hvis prisen er mindre enn 108 kr blir legemiddelet gratis,
    ellers blir det 108 kr billigere */
    public int prisAaBetale(){
        if (etLegemiddel.hentPris() <= 108) {
            return 0;
        } else {
            return etLegemiddel.hentPris() - 108;
        }
    }

    @Override
    public String hentType(){
        return "p";
    }

    //Overskriver toString-metoden for aa printe relevant info
    //Bruker super sin toString() + mer info
    public String toString(){
        return super.toString() + "\n Resepttype: P-Resept" + 
        "\n Pris: 108 kr avslag";
    }
}