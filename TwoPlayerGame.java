import java.util.Scanner;

public class TwoPlayerGame {
    private Board board;
    private Snake playerSnake;
    private Snake aiSnake;
    private IA_Game iaGame;
    private boolean playerTurn;
    private Scanner scanner;

    public TwoPlayerGame(int rows, int columns, int playerX, int playerY, int aiX, int aiY, int tailleMax) {
        board = new Board(rows, columns);
        playerSnake = new Snake(playerX, playerY, tailleMax); // Serpent contrôlé par le joueur
        aiSnake = new Snake(aiX, aiY, tailleMax); // Serpent contrôlé par l'IA
        iaGame = new IA_Game(board, aiSnake);
        playerTurn = true; // L'utilisateur commence
        board.addFood(); // Ajouter la première nourriture sur le plateau
        scanner = new Scanner(System.in); 
    }

    public void start() {
        while (true) {
            System.out.println("\033c"); // clear le terminal 
            board.draw(); // Affiche le plateau 

            if (playerTurn) {
                // Tour du joueur
                System.out.println("À votre tour ! (q = gauche, x = bas, d = droite, e = haut)");
                String input = scanner.nextLine();
                int[] newDirection = {0, 0};

                // on défini la direction su serpent
                if (input.equals("e")) newDirection = new int[]{-1, 0}; // Haut
                else if (input.equals("x")) newDirection = new int[]{1, 0}; // Bas
                else if (input.equals("d")) newDirection = new int[]{0, 1}; // Droite
                else if (input.equals("q")) newDirection = new int[]{0, -1}; // Gauche




                playerSnake.changeDirection(newDirection);
                playerSnake.move();

                if (gameOver(playerSnake) || gameOver(aiSnake)) {
                    System.out.println("Game Over");
                    break;
                }

                // on Vérifie si le joueur a mangé de la nourriture
                if (board.foodHere(playerSnake.getTete())) {
                    playerSnake.grandir();
                    board.addFood(); // Ajoute une nouvelle nourriture
                }
                updateBoard(playerSnake, "p"); // Met à jour l'affichage du serpent du joueur
            } else {
                // Tour de l'IA 
                int[] aiDirection = iaGame.nextMove();
                aiSnake.changeDirection(aiDirection);
                aiSnake.move();


                // Vérifie si l'IA a mangé de la nourriture
                if (board.foodHere(aiSnake.getTete())) {
                    aiSnake.grandir();
                    board.addFood(); // Ajoute une nouvelle nourriture
                }
                updateBoard(aiSnake, "a"); // Met à jour l'affichage du serpent de l'IA
            }



            playerTurn = !playerTurn; // Alterne les tours

            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scanner.close(); // Ferme le scanner
    }

    // Méthode pour mettre à jour l'affichage du plateau avec le serpent courant
    public void updateBoard(Snake snake, String currentPlayer) {
        int[] tete = snake.getTete();
        if (currentPlayer.equals("p")) {
            board.set(tete, "t"); // "t" pour la tête du joueur
        } else {
            board.set(tete, "T"); // "T" pour la tête de l'IA
        }

        int[][] corps = snake.getCorps();
        for (int i = 1; i < corps.length; i++) {
            if (currentPlayer.equals("p")) {
                board.set(corps[i], "s"); // "s" pour le corps du joueur
            } else {
                board.set(corps[i], "S"); // "S" pour le corps de l'IA
            }
        }

        // Gère la suppression de la dernière position de la queue
        int[] delete = snake.getDelete();
        if (delete[0] != -1 || delete[1] != -1) {
            board.set(delete, "v"); // Efface le dernier segment
        }
    }

    // Vérifie les conditions de fin de jeu pour un serpent
    public boolean gameOver(Snake snake) {
        int[] tete = snake.getTete();
        String position = board.get(tete);

        // Si le serpent entre en collision avec un mur ou un autre serpent
        return position.equals("w") || position.equals("s") || position.equals("S");
    }

    public static void main(String[] args) {
        TwoPlayerGame game = new TwoPlayerGame(17, 42, 8, 20, 12, 10, 100);
        game.start(); // Démarre le jeu
    }
}
