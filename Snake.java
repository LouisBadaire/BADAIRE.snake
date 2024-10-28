public class Snake{

    // attibuts 
    private int [][] body;
    private int size; 
    private int [] direction; 
    private boolean il_mange;
    private int[] delete;
    

    public Snake(int departX, int departY, int tailleMax){ 
        // on initialise la position du serpent, grace à sa tête comme point de départ 
        body = new int[tailleMax][2];
        body[0][0] = departX;
        body[0][1] = departY;
        size = 1;
        direction = new int[]{1, 0}; // direction initiale = droite 
        il_mange = false;
        delete = new int[]{-1,-1};

    }

    public void move(){

        if (il_mange) {
            if (size < body.length) {
                size += 1; // On agrandit le corps
                body[size - 1][0] = delete[0]; // On place le nouveau segment à l'ancienne position de la queue
                body[size - 1][1] = delete[1];
            }
            il_mange = false; // Reset de l'état de "manger"
        }


        // on déplace les segments du corps de la queue vers la tete
        delete[0] = body[size-1][0];
        delete[1] = body[size-1][1];


        for (int i = size-1; i > 0; i--){
            body[i][0] = body[i-1][0];
            body[i][1] = body[i-1][1];
        }

        //on addition la position de la tete avec les coordonnées de la direction. 
        body[0][0] += direction[0];
        body[0][1] += direction[1];

        //la size augmente, donc dans la boucle for le i parcourera un segment de plus
        //ca créera le segment automatiquement


        if (il_mange == true){
            if (size < body.length){
                size +=1; 
            }
            il_mange = false;
        }

    }

    // changer la direction du serpent
    public void changeDirection(int[] newdirection){
        if (newdirection[0] != -direction[0] && newdirection[1] != -direction[1]){
            direction = newdirection;
        }
    }

    public void grandir(){
        il_mange = true;
    }

    // on cherche à obtenir la position de la tete :
    public int[] getTete(){
        return body[0];
    }

    public int[] getDelete(){
        return delete;
    }

    // on cherche à obtenir toutes les positions de chaque segment du corps :
    public int[][] getCorps(){
        int[][] res = new int[size][2];
        for(int i = 1; i<size;i++){
            res[i]=body[i];
        }
        return res;
    }
    

    public int getSize(){
        return size-1;
    }



    


}































