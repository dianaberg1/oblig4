import java.util.Iterator;

public class Lenkeliste<T> implements Liste<T>{

    protected Node start; //Foerste node med foerste datasett

    //Node-klasse
    public class Node{
        protected Node neste;
        protected T data;

        //Konstruktoer
        public Node(T data){
            this.data = data;
        }
    }

    int antNoder = 0;
    //Metode som returnerer antall elementer i listen
    public int stoerrelse(){
        return antNoder;
    }

    public class LenkelisteIterator implements Iterator<T>{
        protected Lenkeliste<T> lenkeliste;
        private Node denne;

        public LenkelisteIterator(){
            denne = start;
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }
        
        @Override
        public T next(){
            Node temp = denne;
            T verdi = temp.data;
            denne = denne.neste;
            return verdi;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LenkelisteIterator();
    }

    //Metode som legger til nytt element sist i listen
    public void leggTil(T x){
        /*Sjekker om den foerste noden er null, setter inn ny node 
        hvis den er tom*/
        if (start == null){
            start = new Node(x);
            antNoder += 1; //Oeker antall noder med 1
        /*Hvis den ikke er tom lager vi en midlertidig node som heter
        temp, og sjekker om neste node er null. Saa lenge den neste
        noden ikke er null setter vi den midlertidige noden til å 
        vaere den neste noden, og fortsetter slik til vi naar en tom
        node. Da setter vi inn en ny Node sist i listen*/
        } else {
            Node peker = start;
            while (peker.neste != null){
                peker = peker.neste;
            }
            peker.neste = new Node(x);
            antNoder += 1; //Oeker antall noder med 1
        }
    }

    //Metode som henter foerste element i listen uten aa fjerne det
    public T hent(){
        Node peker = start; // line la til neste
        return peker.data;
    }

    //Metode som fjerner foerste element og returnerer det
    public T fjern(){
        Node foerste = start; //Node som skal holde paa den foerste verdien
        if (antNoder == 0){
            throw new UgyldigListeindeks(-1);
        }
        /*Sender start videre til neste node slik at det foerste elementet
        fjernes*/
        start = start.neste; 
        antNoder -= 1; //Reduserer antall noder med 1
        return foerste.data; //Returnerer verdien som ble fjernet
        }
    

    public String toString(){
        String string = ""; //Tom til aa begynne med
        Node peker = start; //Peker
        /*Saa lenge pekeren ikke er null henter vi ut hver node
        sin data og sender pekeren videre inntil det ikke er flere
        elementer aa peke paa*/
        while (peker != null){ 
            //Legger til dataen i hvert element i strengen
            string = string + " " + peker.data; 
            peker = peker.neste; //Sender pekeren videre
        }
        return string;
        }
}
