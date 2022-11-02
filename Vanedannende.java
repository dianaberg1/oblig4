class Vanedannende extends Legemiddel{

    private int styrke;

    //Konstruktoer for Vanedannende;
    public Vanedannende(String navn, int pris, double virkestoff, int styrke){
        //Kaller på konstruktoeren til Legemiddel
        super(navn, pris, virkestoff);
        this.styrke = styrke;
    }

    public int hentVanedannendeStyrke(){
        return styrke;
    }

    @Override
    public String hentType(){
        return "vanedannende";
    }

    @Override
    public int hentStyrke(){
        return styrke;
    }

    //Overskriver toString-metoden for aa printe relevant info
    //Bruker super sin toString() + mer info
    @Override
    public String toString(){
        return super.toString() + "\n Legemiddeltype: Vanedannende" + 
        "\n Styrke: " + styrke;
    }
}
