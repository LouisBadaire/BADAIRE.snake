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
            board.draw(); // on affiche la grille 
            board.set(snake.getTete(), "t");
            System.out.println("Entrez la direction (q = gauche, x = bas, d = droite, e = haut)");
            String input = scanner.nextLine();

            // on change la direction en fonction de la direction entrée par l'utilisateur : 
            if (input.equals("q")){
                snake.changeDirection(new int[]{-1,0});
            }
            else if (input.equals("d")){
                snake.changeDirection(new int[]{1,0});
            }

            else if (input.equals("e")){
                snake.changeDirection(new int[]{0,1});
            }

            else if (input.equals("x")){
                snake.changeDirection(new int[]{0,-1});
            }
            snake.move(); // on fait deplacer le serpent maitneant qu'on sait ou il va 

            // on initialse la position de la tete : 
            int[] positionTete = snake.getTete();
            if (board.get(positionTete) == "f"){
                snake.grandir();
                board.set(positionTete, "v");
                board.addFood();
            }

            if(GameOver(positionTete) == true){
                System.out.println("Game Over");
                enMarche = false;
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