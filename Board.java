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
    private String[][] GRID; 

    //constructeur 
    public Board (int Rows, int Columns){
        this.rows = Rows; 
        this.columns = Columns;
        this.GRID = new String[Rows][Columns];
        creationGrid();
    }

    public void creationGrid(){
        for (int j = 0; j < columns; j++){
            GRID[0][j] = "w";
        }
        for(int i = 1; i<rows-1; i++){
            for (int j = 0; j < columns; j++){
                if (j == 0 || j == columns-1){
                    GRID[i][j] = "w";
                }
                else{
                    GRID[i][j] = "v";
                }
            }
        }
        for (int j = 0; j < columns; j++){
            GRID[rows-1][j] = "w";
        }
    }

    public void draw(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (GRID[i][j].equals("w")){
                    System.out.print("*");
                }
                if (GRID[i][j].equals("v")){
                    System.out.print(" ");
                }
                if (GRID[i][j].equals("f")){
                    System.out.print("X");
                }

                if (GRID[i][j].equals("s")){
                    System.out.print("O");
                }

                if (GRID[i][j].equals("t")){
                    System.out.print("@");
                }
            }
            System.out.println();

        }
    }


    public void set(int[]coordinates, String value){
        GRID[coordinates[0]][coordinates[1]] = value;
    }
// get(coordinates) permettant de recuperer le contenu du plateau à une position donnée sous la forme d'une chaien de caractere . 
    public String get(int[] coordinates){
        return GRID[coordinates[0]][coordinates[1]];
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


     
}

