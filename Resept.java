abstract class Resept {
    //Abstract igjen fordi ingen instanser skal opprettes
    
        protected int id;
        protected Lege utskrivendeLege;
        protected Pasient pasient;
        protected static int idCount = 0;
        protected int reit;
        Legemiddel etLegemiddel;
        
        //Konstruktoer
        public Resept(Legemiddel legemiddel, Lege utskrivendeLege,
        Pasient pasient, int reit){
    
            this.etLegemiddel = legemiddel;
            this.utskrivendeLege = utskrivendeLege;
            this.pasient = pasient;
            this.reit = reit;
            //Gir en unik id
            idCount++;
            id = idCount;
        }
    
        public int hentId(){
            return id;
        }
    
        public Legemiddel hentLegemiddel(){
            return etLegemiddel;
        }
    
        public Lege hentLege(){
            return utskrivendeLege;
        }
    
        public Pasient hentPasient(){
            return pasient;
        }
    
        public int hentReit(){
            return reit;
        }

        abstract public String hentType();
    
        /*Bruker resepten en gang, minker reit med 1 hvis det er mulig.
        Hvis alt er oppbrukt returneres false. */
        public boolean bruk(){
            if (hentReit() > 0){
                reit--;
                return true;
            } else {
                System.out.println("Ugyldig resept");
                return false;
            }
        }
    
        abstract public String farge(); //Metode for aa returnere farge
        //Metode for aa returnere prisen pasienten maa betale
        abstract public int prisAaBetale(); 
    
        //Overskriver toString-metoden for aa printe relevant info
        public String toString(){ 
            return "\n Resept-ID: " + id + "\n Legemiddel: " + etLegemiddel.hentNavn() + "\n"
            + utskrivendeLege + "\n Reit: " + reit;
        }
    
    }
    
