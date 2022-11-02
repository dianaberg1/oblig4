abstract class Legemiddel{ 

    //Abstract fordi det ikke skal vaere mulig aa opprette instanser
    
        protected String navn;
        protected int id;
        protected int pris;
        protected double virkestoff;
        protected static int idCount = 0; //Starter på 1 for at ID ikke skal bli 0
    
        //Konstruktoer for Legemiddel
        public Legemiddel(String navn, int pris, double virkestoff){
            this.navn = navn;
            this.pris = pris;
            this.virkestoff = virkestoff;
            //Gir en unik ID til hvert legemiddel
            idCount++;
            id = idCount;
            
        }
    
        public int hentID(){
            return id;
        }
    
        public String hentNavn(){
            return navn;
        }
    
        public int hentPris(){
            return pris;
        }
    
        public double hentVirkestoff(){
            return virkestoff;
        }
    
        public void settNyPris(int nyPris){
            pris = nyPris;
        }

        //Hjelpemetode
        abstract public String hentType();

        abstract public int hentStyrke();
    
        //Overskriver toString-metoden for aa printe relevant info
        public String toString(){
            return "\n Legemiddel-ID: " + id + "\n Navn: " + navn + "\n Pris: " + pris +
            "\n Virkestoff: " + virkestoff;
        }
    }
