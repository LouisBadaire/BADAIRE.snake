public class Snake{

    // attibuts 
    private int [][] body;
    private int size; 
    private int [] direction; 
    private boolean il_mange;

    public Snake(int departX, int departY, int tailleMax){ 
        // on initialise la position du serpent, grace à sa tête comme point de départ 
        body = new int[tailleMax][2];
        body[0][0] = departX;
        body[0][1] = departY;
        size = 1;
        int [] direction = {0,1};
        il_mange = False;
    }

    public void move(){
        // on déplace les segments du corps de la queue vers la tete
        for (int i = size; i > 0; i--){
            body[i][0] = body[i-1][0];
            body[i][1] = body[i-1][1];
        }

        //on addition la position de la tete avec les coordonnées de la direction. 
        body[0][0] += direction[0];
        body[0][1] += direction[1];

        //la size augmente, donc dans la boucle for le i parcourera un segment de plus
        //ca créera le segment automatiquement
        if (il_mange == True && size <= tailleMax){
            size += 1;
            il_mange = False;
        }

        // changer la direction du serpent
        public void changeDirection(int[] newdirection){
            if (newDirection[0] != -direction[0] && newdirection[1] != -direction[1]){
                direction = newDirection;
            }
        }

        public void grandir(){
            il_mange = true;
        }

        // on cherche à obtenir la position de la tete :
        public int[] getTete(){
            return body[0];
        }

        // on cherche à obtenir toutes les positions de chaque segment du corps :
        public int[][] getCorps(){
            return body;
            }
        }

        public int getSize(){
            return size;
        }



    }


}































