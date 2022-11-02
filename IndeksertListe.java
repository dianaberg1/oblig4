class IndeksertListe<T> extends Lenkeliste<T> {

    public void leggTil(int pos, T x){
        Node nyNode = new Node(x); //Node som holder paa x-verdien
        Node peker = start;
        //Hvis lenkelisten er tom setter vi inn den nye noden
        if (start == null){
            start = nyNode;
            antNoder += 1;
            return;
        } 
        //Kaster UgyldigListeindeks hvis pos er utenfor definisjonsområdet
        else if (pos<0 || pos>stoerrelse()){
            throw new UgyldigListeindeks(pos);
        }

        /*Hvis pos er den foerste mulige posisjonen dytter vi inn
        nyNode foran start, og setter nyNode som den nye start-verdien*/
        else if (pos == 0){
            nyNode.neste = start;
            start = nyNode;
            antNoder++;
        }
        /*Hvis 0<=pos<=stoerrelse() må vi loepe gjennom alle nodene hvor
        pekeren ikke er null (der hvor det ikke er en tom plass), og
        telle oss oppover til pos. Da sender vi pekeren videre og skviser
        inn den nye noden inn i posisjonen vi har telt oss frem til, og 
        da forskyves alle elementene lenger ut i listen*/
        else {
            int teller;
            for (teller=0; teller<pos-1; teller++){
                peker = peker.neste;
            }

            /*peker stopper i noden foer pos, og saa sier vi at nyNode 
            sin neste er peker sin neste, slik at vi kan peke videre.
            Deretter setter vi inn nyNode i den neste noden som er pos*/

            nyNode.neste = peker.neste;
            peker.neste = nyNode; 
            antNoder++;
        }



    }

    public void sett(int pos, T x){
        Node peker = start;
        Node nyNode = new Node(x);
        if (start == null){
            start = nyNode;
            antNoder += 1;
            return;
        } 
        //Kaster UgyldigListeindeks hvis pos er utenfor definisjonsområdet
        else if (pos<0 || pos>=stoerrelse()){
            throw new UgyldigListeindeks(pos);
        }
        
        /*Teller oss frem til pos og erstatter deretter pos sin data med x*/
        else {
            int teller;
            for (teller=0; teller<pos-1; teller++){
                peker = peker.neste;
            }
            peker.neste.data = x; //Erstatter dataen med x
        }
    }

    public T hent(int pos){
        Node peker = start;
        //Kaster UgyldigListeindeks hvis pos er utenfor definisjonsområdet
        if (pos<0 || pos>=stoerrelse()){
            throw new UgyldigListeindeks(pos);
        }
        
        /*Teller oss frem til pos for aa finne elementet*/
        else {
            int teller;
            for (teller=0; teller<=pos-1; teller++){
                peker = peker.neste;
            }
        }
        return peker.data; //Returnerer elementet i pos
    }

    public T fjern(int pos){
        Node peker = start;
        //Kaster UgyldigListeindeks hvis pos er utenfor definisjonsområdet
        if (pos<0 || pos>=stoerrelse()){
            throw new UgyldigListeindeks(pos);
        }
        /*Hvis pos er 0 saa bruker vi super sin fjern-metode siden den
        haandterer dette tilfellet*/
        if (pos == 0) {
            return super.fjern();
        }
        //Teller oss frem til pos
        else {
            int teller;
            for (teller=0; teller<pos-1; teller++){
                peker = peker.neste;
            }

            //Lagrer verdien som skal fjernes 
            T ut = peker.neste.data;
            //Sender pekeren videre for aa fjerne elementet
            peker.neste = peker.neste.neste; 
            antNoder--; //Reduserer antNoder med 1
            
            return ut; //Returnerer verdien som ble fjernet
        }
    }
}
