class Narkotisk extends Legemiddel{

    private int styrke;

    //Konstruktoer for Narkotisk
    public Narkotisk(String navn, int pris, double virkestoff, int styrke){
        //Kaller på konstruktoeren til Legemiddel
        super(navn, pris, virkestoff); 
        this.styrke = styrke;
    }

    public int hentNarkotiskStyrke(){
        return styrke;
    }

    @Override
    public String hentType(){
        return "narkotisk";
    }

    @Override
    public int hentStyrke(){
        return styrke;
    }

    //Overskriver toString-metoden for aa printe relevant info
    //Bruker super sin toString() + mer info
    public String toString(){
        return super.toString() + "\n Legemiddeltype: Narkotisk" + 
        "\n Styrke: " + styrke;
    }
}
