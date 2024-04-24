import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Algo {


    static class Node {
        int row;
        int col;
        int gScore;
        int hScore;
        Node parent;

        Node(int row, int col, int gScore, int hScore, Node parent) {
            this.row = row;
            this.col = col;
            this.gScore = gScore;
            this.hScore = hScore;
            this.parent = parent;
        }

        int getFScore() {
            return gScore + hScore;
        }
    }

    public String shortestPath(int[][] map, int[] start, int[] goal) {
        int rows = map.length;
        int cols = map[0].length;

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFScore));
        openSet.offer(new Node(start[0], start[1], 0, heuristic(start, goal), null));

        boolean[][] visited = new boolean[rows][cols];

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.row == goal[0] && current.col == goal[1]) {
                return reconstructPath(current);
            }

            visited[current.row][current.col] = true;

            for (int[] dir : directions) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];

                if (isValidMove(newRow, newCol, rows, cols, map, visited)) {
                    int newGScore = current.gScore + 1;
                    int newHScore = heuristic(new int[]{newRow, newCol}, goal);
                    Node neighbor = new Node(newRow, newCol, newGScore, newHScore, current);
                    openSet.offer(neighbor);
                }
            }
        }

        return "No Path Found, Check Map and Try Again!";
    }

    private int heuristic(int[] from, int[] to) {
        return Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
    }

    private boolean isValidMove(int row, int col, int rows, int cols, int[][] map, boolean[][] visited) {
        return row >= 0 && row < rows && col >= 0 && col < cols && map[row][col] == 0 && !visited[row][col];
    }

    private String reconstructPath(Node node) {
        List<Node> pathNodes = new ArrayList<>();
        while (node != null) {
            pathNodes.add(0, node);
            node = node.parent;
        }

        StringBuilder path = new StringBuilder();
        for (int i = 0; i < pathNodes.size() - 1; i++) {
            Node current = pathNodes.get(i);
            Node next = pathNodes.get(i + 1);

            int rowDiff = next.row - current.row;
            int colDiff = next.col - current.col;

            if (rowDiff < 0) {
                path.append(i + 1).append(". Move up to (").append(next.col + 1).append(", ").append(next.row + 1).append(")\n");
            } else if (rowDiff > 0) {
                path.append(i + 1).append(". Move down to (").append(next.col + 1).append(", ").append(next.row + 1).append(")\n");
            } else if (colDiff < 0) {
                path.append(i + 1).append(". Move left to (").append(next.col + 1).append(", ").append(next.row + 1).append(")\n");
            } else if (colDiff > 0) {
                path.append(i + 1).append(". Move right to (").append(next.col + 1).append(", ").append(next.row + 1).append(")\n");
            }
        }
        path.append(pathNodes.size()).append(". Done!\n");
        return path.toString();
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Puzzle");

        boolean graphLoadError = true;

        while (graphLoadError) {
            try {
                String userInputFileName;
                do {
                    System.out.print("\nEnter the Map file name with extension:  ");
                    userInputFileName = inputScanner.nextLine();
                } while (!userInputFileName.endsWith(".txt"));

                // Load map from file and initialize start and end points
                Parser fileParser = new Parser();
                fileParser.readFile("C:\\Users\\Trevin Joseph\\Desktop\\IIT assignmnets\\Year  2\\Algorithm\\CW\\Sliding Puzzle GIT\\Sliding Puzzle\\benchmark_series\\benchmark_series\\" + userInputFileName);
                fileParser.loadLines();
                fileParser.loadValues();
                if (!fileParser.isFileRead()) {
                    throw new Exception("File was not loaded");
                }
                int[][] n = fileParser.getMap();
                int[] s = fileParser.getStartPoint();
                int[] t = fileParser.getEndPoint();

                Algo solver = new Algo();
                Instant startTime = Instant.now();
                System.out.println("\nFinding The Shortest Distance from S to F...\n");
                System.out.println(solver.shortestPath(n, s, t));
                Instant endTime = Instant.now();
                System.out.print("Done!");
                Duration timeElapsed = Duration.between(startTime, endTime);
                System.out.print("\nTime elapsed: ");
                if (timeElapsed.toMillis() > 1000) {
                    System.out.print(timeElapsed.toSeconds() + " seconds\n");
                } else {
                    System.out.print(timeElapsed.toMillis() + " milliseconds\n");
                }


                System.out.println("Press 1 to continue or Press 2 to quit : ");
                String userInput = inputScanner.nextLine();
                if (userInput.equals("2")) {
                    graphLoadError = false;
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("\nSomething Went Wrong, Please Try Again\n");
                graphLoadError = true;
            }
        }
    }
}