package uet.oop.bomberman.entities.Enemy.AI;

import javafx.util.Pair;
import uet.oop.bomberman.Const;

import java.util.*;

public class AStar {
    private static final int ROW = Const.mapHeight;
    private static final int COL = Const.mapWidth;
    static class Cell {
        public int parentX = 0, parentY = 0;
        public double f, g, h;
    }


    private static boolean isValid(int y, int x) {
        return ((x >= 0) && (x < COL))
                && ((y >= 0) && (y < ROW));
    }

    private static boolean isUnBlocked(char[][] grid, int y, int x) {
        return grid[y][x] != '*' && grid[y][x] != '#';
    }

    private static boolean isDestination(int y, int x, Pair<Integer, Integer> dest) {
        return y == dest.getKey() && x == dest.getValue();
    }

    private static double calculateHValue(int x, int y, Pair<Integer, Integer> dest) {
        //Manhattan Distance
        return Math.abs(x - dest.getKey()) + Math.abs(y - dest.getValue());
    }

    private static Pair<Integer, Integer> tracePath(Cell[][] cellDetails, Pair<Integer, Integer> dest) {
        try {
            int row = dest.getKey();
            int col = dest.getValue();
            int y = row, x = col;

            while (cellDetails[row][col].parentY != row || cellDetails[row][col].parentX != col) {
                y = row;
                x = col;
                int tempRow = cellDetails[row][col].parentY;
                int tempCol = cellDetails[row][col].parentX;
                row = tempRow;
                col = tempCol;
            }
//            System.out.printf("(" + p.getKey() +", " + p.getValue() + ")%n");
            return new Pair<>(y, x);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static Pair<Integer, Integer> aStarSearch(char[][] grid, Pair<Integer, Integer> src, Pair<Integer, Integer> dest) {
        Cell[][] cellDetails = new Cell[ROW][COL];
        boolean[][] closedList = new boolean[ROW][COL];
        Set<Pair<Double, Pair<Integer, Integer>>> openList = new HashSet<>();

        // If the source is out of range
        if (!isValid(src.getKey(), src.getValue())) {
            System.out.println("Source is invalid");
            return null;
        }

        // If the destination is out of range
        if (!isValid(dest.getKey(), dest.getValue())) {
            System.out.println("Destination is invalid");
            return null;
        }

        // Either the source or the destination is blocked
        if (!isUnBlocked(grid, src.getKey(), src.getValue())
                || !isUnBlocked(grid, dest.getKey(), dest.getValue())) {
            System.out.println("Source or the destination is blocked");
            return null;
        }

        // If the destination cell is the same as source cell
        if (isDestination(src.getKey(), src.getValue(), dest)) {
            System.out.println("We are already at the destination");
            return null;
        }

        int i, j;

        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                cellDetails[i][j] = new Cell();
                cellDetails[i][j].parentX = -1;
                cellDetails[i][j].parentY = -1;
                cellDetails[i][j].f = Double.MAX_VALUE;
                cellDetails[i][j].h = Double.MAX_VALUE;
                cellDetails[i][j].g = Double.MAX_VALUE;
            }
        }

        // Initialising the parameters of the starting node
        i = src.getKey(); j = src.getValue();
        cellDetails[i][j].f = 0.0;
        cellDetails[i][j].g = 0.0;
        cellDetails[i][j].h = 0.0;
        cellDetails[i][j].parentY = i;
        cellDetails[i][j].parentX = j;

        openList.add(new Pair<>(0.0, new Pair<>(i, j)));

        boolean foundDest = false;

        while (!openList.isEmpty()) {
            Pair<Double, Pair<Integer, Integer>> p = openList.iterator().next();

            openList.remove(p);

            i = p.getValue().getKey();
            j = p.getValue().getValue();
            closedList[i][j] = true;

            double gNew, hNew, fNew;

            //----------- 1st Successor (North) ------------

            if (isValid(i - 1, j)) {
                if (isDestination(i - 1, j, dest)) {
                    cellDetails[i-1][j].parentY = i;
                    cellDetails[i-1][j].parentX = j;
                    System.out.println("The destination cell is found");
                    foundDest = true;
                    closedList = null;
                    openList = null;
                    return tracePath(cellDetails, dest);
                } else if (!closedList[i - 1][j]
                        && isUnBlocked(grid, i - 1, j)) {
                    gNew = cellDetails[i][j].g + 1;
                    hNew = calculateHValue(i-1, j, dest);
                    fNew = gNew + hNew;

                    if (cellDetails[i-1][j].f == Double.MAX_VALUE
                        || cellDetails[i-1][j].f > fNew) {
                        openList.add(new Pair<>(fNew, new Pair<>(i-1, j)));

                        cellDetails[i - 1][j].f = fNew;
                        cellDetails[i - 1][j].g = gNew;
                        cellDetails[i - 1][j].h = hNew;
                        cellDetails[i - 1][j].parentY = i;
                        cellDetails[i - 1][j].parentX = j;
                    }
                }
            }

            //----------- 2nd Successor (South) ------------
            if (isValid(i+1, j)) {
                if (isDestination(i + 1, j, dest)) {
                    cellDetails[i+1][j].parentY = i;
                    cellDetails[i+1][j].parentX = j;
                    System.out.println("The destination cell is found");
                    foundDest = true;
                    closedList = null;
                    openList = null;
                    return tracePath(cellDetails, dest);
                } else if (!closedList[i + 1][j]
                        && isUnBlocked(grid, i + 1, j)) {
                    gNew = cellDetails[i][j].g + 1;
                    hNew = calculateHValue(i+1, j, dest);
                    fNew = gNew + hNew;

                    if (cellDetails[i+1][j].f == Double.MAX_VALUE
                            || cellDetails[i+1][j].f > fNew) {
                        openList.add(new Pair<>(fNew, new Pair<>(i+1, j)));

                        cellDetails[i + 1][j].f = fNew;
                        cellDetails[i + 1][j].g = gNew;
                        cellDetails[i + 1][j].h = hNew;
                        cellDetails[i + 1][j].parentY = i;
                        cellDetails[i + 1][j].parentX = j;
                    }
                }
            }

            //----------- 3rd Successor (East) ------------
            if (isValid(i, j + 1)) {
                if (isDestination(i, j + 1, dest)) {
                    cellDetails[i][j+1].parentY = i;
                    cellDetails[i][j+1].parentX = j;
                    System.out.println("The destination cell is found");
                    foundDest = true;
                    closedList = null;
                    openList = null;
                    return tracePath(cellDetails, dest);
                } else if (!closedList[i][j+1]
                        && isUnBlocked(grid, i, j + 1)) {
                    gNew = cellDetails[i][j].g + 1;
                    hNew = calculateHValue(i, j + 1, dest);
                    fNew = gNew + hNew;

                    if (cellDetails[i][j + 1].f == Double.MAX_VALUE
                            || cellDetails[i][j+1].f > fNew) {
                        openList.add(new Pair<>(fNew, new Pair<>(i, j+1)));

                        cellDetails[i][j + 1].f = fNew;
                        cellDetails[i][j + 1].g = gNew;
                        cellDetails[i][j + 1].h = hNew;
                        cellDetails[i][j + 1].parentY = i;
                        cellDetails[i][j + 1].parentX = j;
                    }
                }
            }

            //----------- 4th Successor (West) ------------
            if (isValid(i, j - 1)) {
                if (isDestination(i, j - 1, dest)) {
                    cellDetails[i][j-1].parentY = i;
                    cellDetails[i][j-1].parentX = j;
                    System.out.println("The destination cell is found");
                    foundDest = true;
                    closedList = null;
                    openList = null;
                    return tracePath(cellDetails, dest);
                } else if (!closedList[i][j-1]
                        && isUnBlocked(grid, i, j - 1)) {
                    gNew = cellDetails[i][j].g + 1;
                    hNew = calculateHValue(i, j - 1, dest);
                    fNew = gNew + hNew;

                    if (cellDetails[i][j - 1].f == Double.MAX_VALUE
                            || cellDetails[i][j-1].f > fNew) {
                        openList.add(new Pair<>(fNew, new Pair<>(i, j-1)));

                        cellDetails[i][j - 1].f = fNew;
                        cellDetails[i][j - 1].g = gNew;
                        cellDetails[i][j - 1].h = hNew;
                        cellDetails[i][j - 1].parentY = i;
                        cellDetails[i][j - 1].parentX = j;
                    }
                }
            }
        }

        System.out.println("Failed to find the Destination Cell");
        return null;
    }
}

