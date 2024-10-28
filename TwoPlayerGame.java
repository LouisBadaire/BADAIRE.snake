import java.util.Scanner;

public class TwoPlayerGame {
    private Board board;
    private Snake playerSnake;
    private Snake aiSnake;
    private boolean playerTurn;
    private Scanner scanner;

    public TwoPlayerGame(int rows, int columns, int playerX, int playerY, int aiX, int aiY, int tailleMax) {
        board = new Board(rows, columns);
        playerSnake = new Snake(playerX, playerY, tailleMax); // Initialisation du serpent contrôlé par le joueur 
        aiSnake = new Snake(aiX, aiY, tailleMax); // Initialisation du serpent contrôlé par l'IA 
        playerTurn = true; // L'utilisateur commence
        board.addFood(); // Ajouter la nourriture
        scanner = new Scanner(System.in); // Initialisation du scanner
    }

    public void start() {
        while (true) {
            System.out.println("\033c"); 
            board.draw();

            if (playerTurn) {
                System.out.println("À votre tour ! (q = gauche, x = bas, d = droite, e = haut)");
                String input = scanner.nextLine();

                int[] newDirection = {0, 0};
                if (input.equals("e")) {
                    newDirection = new int[]{-1, 0}; // Haut
                } else if (input.equals("x")) {
                    newDirection = new int[]{1, 0}; // Bas
                } else if (input.equals("d")) {
                    newDirection = new int[]{0, 1}; // Droite
                } else if (input.equals("q")) {
                    newDirection = new int[]{0, -1}; // Gauche
                }

                playerSnake.changeDirection(newDirection);
                playerSnake.move();
                if (board.hasFoodAt(playerSnake.getTete())) { // Vérifier si le joueur a mangé
                    playerSnake.grow(); // Faire grandir le serpent
                    board.addFood(); // Ajouter une nouvelle nourriture
                }
                updateBoard(playerSnake, "p");
            } else { // Tour de l'IA 
                IA_Game ia = new IA_Game(board, aiSnake);
                int[] aiDirection = ia.nextMove();
                aiSnake.changeDirection(aiDirection);
                aiSnake.move();
                if (board.hasFoodAt(aiSnake.getTete())) { // Vérifier si l'IA a mangé
                    aiSnake.grow(); // Faire grandir le serpent
                    board.addFood(); // Ajouter une nouvelle nourriture
                }
                updateBoard(aiSnake, "a"); // 'a' pour le serpent de l'IA
            }
            if (gameOver(playerSnake) || gameOver(aiSnake)) {
                System.out.println("Game Over");
                break;
            }

            playerTurn = !playerTurn;

            try {
                Thread.sleep(500); // Ralentir le jeu
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scanner.close(); // Fermer le scanner pour éviter les fuites de ressources
    }

    public void updateBoard(Snake snake, String currentPlayer) {
        int[] tete = snake.getTete();
        if (currentPlayer.equals("p")) {
            board.set(tete, "t"); // "t" pour la tête du joueur
        } else {
            board.set(tete, "T"); // "T" pour la tête de l'IA
        }

        int[][] corps = snake.getCorps();
        for (int i = 1; i < corps.length; i++) { // Commencer à 1 pour ne pas remplacer la tête
            if (currentPlayer.equals("p")) {
                board.set(corps[i], "s"); // "s" pour le corps du joueur
            } else {
                board.set(corps[i], "S"); // "S" pour le corps de l'IA
            }
        }

        // Gérer la suppression de la dernière position
        int[] delete = snake.getDelete();
        if (delete[0] != -1 || delete[1] != -1) {
            board.set(delete, "v"); // Supprimer le dernier segment de l'ancien corps
        }
    }

    public boolean gameOver(Snake snake) {
        int[] tete = snake.getTete();
        String position = board.get(tete);

        // Collision avec les murs ou un serpent
        return position.equals("w") || position.equals("s") || position.equals("S");
    }

    public static void main(String[] args) {
        TwoPlayerGame game = new TwoPlayerGame(17, 42, 8, 20, 12, 10, 100);
        game.start();
    }
}
