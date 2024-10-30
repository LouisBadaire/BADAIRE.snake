public class Board {


    //https://docs.github.com/fr/issues/planning-and-tracking-with-projects/learning-about-projects/about-projects
    // represente l etat du "plateau de jeu "

    // t : tete 
    // w : wall
    // v : vide 
    // s : corps du serpent 
    // f : food

    
    // attributs 
    private int rows; 
    private int columns; 
    private String[][] grid; 

    //constructeur 
    public Board (int Rows, int Columns){
        this.rows = Rows; 
        this.columns = Columns;
        this.grid = new String[Rows][Columns];
        creationGrid();
    }

    public void creationGrid(){
        for (int j = 0; j < columns; j++){
            grid[0][j] = "w";
        }
        for(int i = 1; i<rows-1; i++){
            for (int j = 0; j < columns; j++){
                if (j == 0 || j == columns-1){
                    grid[i][j] = "w";
                }
                else{
                    grid[i][j] = "v";
                }
            }
        }
        for (int j = 0; j < columns; j++){
            grid[rows-1][j] = "w";
        }
    }

    public void draw(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (grid[i][j].equals("w")){
                    System.out.print("*");
                }
                if (grid[i][j].equals("v")){
                    System.out.print(" ");
                }
                if (grid[i][j].equals("f")){
                    System.out.print("X");
                }

                if (grid[i][j].equals("s")){
                    System.out.print("O");
                }

                if (grid[i][j].equals("t")){
                    System.out.print("@");
                }
                if (grid[i][j].equals("T")){
                    System.out.print("ç");
                }
                if (grid[i][j].equals("S")){
                    System.out.print("ù");
                }
            }
            System.out.println();

        }
    }


    public void set(int[]coordinates, String value){
        grid[coordinates[0]][coordinates[1]] = value;
    }
// get(coordinates) permettant de recuperer le contenu du plateau à une position donnée sous la forme d'une chaien de caractere . 
    public String get(int[] coordinates){
        return grid[coordinates[0]][coordinates[1]];
    }

    public void addFood(){
        int minRow = 1;
        int maxRow = rows -2;
        int minCol = 1;
        int maxCol = columns -2;
        int [] coordinates; 
// j'avais un probleme, des fois plus de pommes ne s'affichait sur la tableau, 'ne ai déduit qu'une pomme était parfois générée sur un segment du seprent ce qui rovoquait la disparition de la pomme le mouement du serpend suivant. et comme la pomme n'etait pas mangé, ca n'en replacait pas de nouvelle. 
        do {
            int randomRow = minRow + (int)(Math.random() * ((maxRow - minRow) + 1));
            int randomCol = minCol + (int)(Math.random() * ((maxCol - minCol) + 1));
            coordinates = new int[] {randomRow, randomCol};
        }
        while (!get(coordinates).equals("v"));
        
        set(coordinates, "f");  

    }

    public int getRows(){
        return rows; 
    }

    public int getColumns(){
        return columns;
    }

    public int[] findFood() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (get(new int[]{i, j}).equals("f")) { 
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Renvoie -1, -1 si aucune nourriture n'est trouvée
    }


    public boolean foodHere(int[] position) {
        int x = position[0];
        int y = position[1];
        
        // On vérifie que la position est dans les limites de la grille
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            return grid[x][y].equals("f"); // Retourne vrai si la cellule contient de la nourriture
        } else {
            return false; // Position hors limites
        }
    } 

}

