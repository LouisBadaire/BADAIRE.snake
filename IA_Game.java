public class IA_Game {

    private Board board;
    private Snake snake;
    private boolean premierMouvement = true; 

    public IA_Game(Board board, Snake snake) {
        this.board = board;
        this.snake = snake;
    }

    // Méthode qui contient la boucle principale du jeu
    public void run() {
        while (true) {
            System.out.println("\033c"); // permet d'effacer la console 
            System.out.println("Snake                           score : " + snake.getSize());
            board.draw(); // affichage de la grille 

            int[] newDirection = nextMove(); // IA décide de la prochaine direction
            snake.changeDirection(newDirection);
            snake.move();

            int[] positionTete = snake.getTete();

            // on s'occupe de la mort du serpent
            if (GameOver(positionTete)) {
                System.out.println("Game Over");
                break;
            }
            // on s'occupe de l'allongement de" la queue 
            if (board.get(positionTete).equals("f")) {
                snake.grandir();
                board.addFood();
                board.set(positionTete, "v");
            }

            // on supprime le dernier segment de la queue
            board.set(snake.getTete(), "t");
            int[] delete = snake.getDelete();
            int[][] positionQueue = snake.getCorps();

            for (int i = 1; i < positionQueue.length; i++) { 
                board.set(positionQueue[i], "s");
            }

            if (delete[0] != -1 || delete[1] != -1) {
                board.set(delete, "v");
            }

            try {
                Thread.sleep(100); // Pause entre chaque mouvement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] nextMove() {
        int[] tete = snake.getTete(); 
        int[] food = findFood();

// j'ai remarqué que lorsque la pomme etait au dessus de la tete du serpent lors du du premier mouvement apres le lancement du jeu, le serpent partait vers le bas et mourrait. pour arreter ca, j'oblige le serpent à partir vers la droite pour son premier mouvement. 
        if (premierMouvement) {
            premierMouvement = false;
            return new int[]{0, 1}; // Va à droite pour le premier mouvement
        }       
        // Calcul de la direction préférée vers la nourriture
        int[] preferredDirection = {0, 0};
        if (food[0] > tete[0]) {
            preferredDirection = new int[]{1, 0};  // Bas
        }
        else if (food[0] < tete[0]) {
            preferredDirection = new int[]{-1, 0};  // Haut
        }
        else if (food[1] > tete[1]) {
            preferredDirection = new int[]{0, 1};  // Droite
        }
        else if (food[1] < tete[1]) {
            preferredDirection = new int[]{0, -1};  // Gauche7
        }


        if (isSafePosition(new int[]{tete[0] + preferredDirection[0], tete[1] + preferredDirection[1]})) {
            return preferredDirection;
        }

        // la direction préféré mène parfois à un mur ou la queue, on essaie donc d'autre direction. 
        int[][] otherDirections = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] direction : otherDirections) {
            int[] newPos = {tete[0] + direction[0], tete[1] + direction[1]};
            if (isSafePosition(newPos)) {
                return direction;
            }
        }

        // s'il n'y a pa de solution, en renvoie la direction initiale
        return preferredDirection;

    }


    public boolean isSafePosition(int[] position) {
        String cell = board.get(position);
        
        // Vérifie que la cellule est soit vide ("v"), soit de la nourriture ("f")
        if (cell.equals("v") || cell.equals("f")) {
            return true;
        } else {
            return false;
        }


    
    }
    private int[] findFood() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                if (board.get(new int[]{i, j}).equals("f")) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public boolean GameOver(int[] positionTete) {
        String positionActuelle = board.get(positionTete);
        return positionActuelle.equals("w") || positionActuelle.equals("s");
    }

    public static void main(String[] args) {
        int rows = 17;
        int columns = 42;
        int departX = 8;
        int departY = 20;
        int tailleMax = 100;

        Board board = new Board(rows, columns);
        Snake snake = new Snake(departX, departY, tailleMax);
        IA_Game game = new IA_Game(board, snake);

        board.addFood(); 
        game.run(); // Lancement de la boucle de jeu
    }
}
