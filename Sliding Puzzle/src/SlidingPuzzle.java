//Name: Trevin Brian Joseph
//UoW ID: w1953285
//IIT ID: 20220612

import java.util.ArrayList;

public class SlidingPuzzle {

    private static char Start = 'S';
    private static char Pathway = '.';
    private static char Obstacle = '0';
    private static char Finish = 'F';

    //making a cell class to hold information of each location of the map
    public static class Cell {
        int x, y; //holds information on coordinates of the cell
        double f, g, h; //values used in A* algo(f-cost, g-cost, h-cost)
        Cell parent; //parent si referenced to another cell object to reconstruct the path later

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

