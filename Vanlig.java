class Vanlig extends Legemiddel{

    //Konstruktoer for Vanlig
    public Vanlig(String navn, int pris, double virkestoff){
        //Kaller på konstruktoeren til Legemiddel
        super(navn, pris, virkestoff); 
    }

    @Override
    public int hentStyrke(){
        return 0;
    }

    public String hentType(){
        return "vanlig";
    }

    //Overskriver toString-metoden for aa printe relevant info
    //Bruker super sin toString() + mer info
    public String toString(){
        return super.toString() + "\n Legemiddeltype: Vanlig";
    }
}
