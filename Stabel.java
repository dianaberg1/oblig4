public class Stabel<T> extends Lenkeliste<T>{
    
    @Override //Overskriver metoden
    //Metode som dytter et element til starten av stabelen
    public void leggTil(T x){
        if (start == null){
            start = new Node(x); //Legger til ny node hvis start er tom
        } else {
            Node tmp = start; //Holder paa verdien til foerste node
            start = new Node(x); //Oppretter ny node
            start.neste = tmp; //Dytter noden til starten av stabelen
            
        } antNoder += 1; //Oeker antall noder med 1
        

    }

}