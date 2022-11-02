class Spesialist extends Lege implements Godkjenningsfritak {
    //Subklasse av Lege, implementerer grensesnittet Godkjenningsfritak
    
        String kontrollId;
    
        //Konstruktoer
        public Spesialist(String navn, String kontrollId){
            super(navn);
            this.kontrollId = kontrollId;
        }
    
        @Override
        public String hentKontrollId(){
            return kontrollId;
        }
    
        //Overskriver toString-metoden for aa printe relevant info
        //Bruker super sin toString() + mer info
        public String toString(){
            return super.toString() + "\n Type lege: Spesialist" + 
            "\n KontrollID: " + kontrollId;
        }
    }
