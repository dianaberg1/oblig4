class Prioritetskoe<T extends Comparable<T>> extends Lenkeliste<T> {
    
    @Override
    public void leggTil(T x){

        //Hvis lista er tom legger vi bare til ny node med x
        if (stoerrelse()==0){
            start = new Node(x); 
            antNoder++;
        }
        /*Sammenligner foerste element med input x, hvis start.data er
        stoerre enn x saa legger vi inn x i starten av lista
        */
        else if (start.data.compareTo(x) > 0) {
            Node nyNode = new Node(x);
            nyNode.neste = start;
            start = nyNode;
            antNoder++;
        }

        /*Saa lenge peker.neste ikke er null og peker.neste.data sammenlignet
        med x er mindre enn 0, saa dytter vi inn en ny node*/
        else {
            Node peker = start;
            Node nyNode = new Node(x);
            for (int i=0; i<stoerrelse(); i++){
                if (peker.neste!= null && peker.neste.data.compareTo(x) < 0){
                    peker = peker.neste;
                }
            }
            nyNode.neste = peker.neste;
            peker.neste = nyNode;
            antNoder++;

        }
    }
    /*Trenger ikke aa overskrive hent eller fjern ettersom hent og fjern
    i Lenkeliste henter og fjerner foerste element, og vi har sortert
    slik at minste element er foerste element, ergo vil dette fjernes*/
}

