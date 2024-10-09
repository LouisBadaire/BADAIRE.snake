import java.util.Scanner;

public class Game{
    private Board board;
    private Snake snake; 
    private boolean enMarche; // montre si le jeu est en cours 

    public Game (int rows, int columns, int departX, int departY, int tailleMax){
        board = new Board(rows, columns);
        snake = new Snake( departX,  departY,  tailleMax);
        enMarche = true;
        board.addFood(); // on ajoute de la food sur la grille
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);

        while (enMarche == true){
            System.out.println("\033c");


            // on initialse la position de la tete : 

            board.draw(); // on affiche la grille 
            System.out.println("Entrez la direction (q = gauche, x = bas, d = droite, e = haut)");

            String input = scanner.nextLine();


            // on change la direction en fonction de la direction entrée par l'utilisateur : 
            int[] newdirection = {0,0};
            if (input.equals("e")){
                newdirection = new int[] {-1,0};
            }
            else if (input.equals("x")){
                newdirection = new int[] {1,0};
            }

            else if (input.equals("d")){
                newdirection = new int[] {0,1};
            }

            else if (input.equals("q")){
                newdirection = new int[] {0,-1};
            }

            snake.changeDirection(newdirection);
            snake.move(); // on fait deplacer le serpent maitneant qu'on sait ou il va 



            int[] positionTete = snake.getTete();

            if(GameOver(positionTete) == true){
                System.out.println("Game Over");
                enMarche = false;
            }    
            if (board.get(positionTete).equals("f")){
                snake.grandir();
                board.addFood();
                board.set(positionTete, "v");

            }
            board.set(snake.getTete(), "t");
            
            int[] Delete = snake.getDelete();


            int[][] positionQueue = snake.getCorps();

            for (int i = 1 ; i < positionQueue.length; i++)  { 
                board.set(positionQueue[i], "s");
            }

            if(Delete[0]!=-1 || Delete[1]!=-1){
                board.set(Delete,"v");
            }
       
        }
    }
    public boolean GameOver (int[] positionTete){
        String positionActuelle = board.get(positionTete);

        if (positionActuelle.equals("w") || positionActuelle.equals("s")){
            return true; // collision avec un mur ou avec soit meme
        }
        return false; 
    }


    public static void main(String[] args){
        Game jeu = new Game(17,42,8,20,100);
        jeu.start();
    }

}