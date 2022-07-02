package learn2;

import static java.lang.Thread.*;
import java.util.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;

public class RatMaze implements Runnable {

    int w;
    int maze[][];
    int i = 0 ;
    LinkedList<Position> path = new LinkedList<Position>();
    LinkedList<Position> visited = new LinkedList<Position>();

    RatMaze(int maze[][], LinkedList<Position> path, int w) {
        this.maze = maze;
        this.path = path;
        this.w = w;
    }

    synchronized boolean moveForward(int maze[][]) {
        if (path.peek().y == w - 1) {
            
            return true;
        }
        if (path.peek().x < w && path.peek().y < w) {
            if (maze[path.peek().x][path.peek().y + 1] == 1) {
                Position p = new Position(path.peek().x, path.peek().y + 1);
                if (visited.contains(path.peek())) {
                    
                    return false;
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RatMaze.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String s = Thread.currentThread().getName();
                    int lastN =  Integer.parseInt(s.substring(s.length() - 1));
                    Grid.labels[path.peek().x][path.peek().y].setBackground(Grid.colors[ lastN]);
                    
                    path.push(p);
                    System.out.println("Moved Forward " + path.peek().x + " " + path.peek().y + " " + Thread.currentThread().getName());
                    return true;
                }
            } else {
                System.out.println("Dead End Forward " + Thread.currentThread().getName());
                return false;
            }
        } else {
            System.out.println("Can't get out of Maze Borders");
            return false;
        }
    }

    synchronized boolean isSafeForward(int maze[][]) {
        if (path.peek().y == w - 1) {
            return false;
        }
        if (maze[path.peek().x][path.peek().y + 1] == 1) {
            return true;
        } else {
            return false;
        }
    }

    synchronized boolean moveDown(int maze[][]) {
        if (path.peek().x == w - 1) {
            return false;
        }
        if (path.peek().x < w && path.peek().y < w) {
            if (maze[path.peek().x + 1][path.peek().y] == 1) {
                Position p = new Position(path.peek().x + 1, path.peek().y);
                if (visited.contains(path.peek())) {
                    return false;
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RatMaze.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Grid.labels[path.peek().x][path.peek().y].setOpaque(true);
                    String s = Thread.currentThread().getName();
                    int lastN =  Integer.parseInt(s.substring(s.length() - 1));
                    
                    Grid.labels[path.peek().x][path.peek().y].setBackground(Grid.colors[ lastN]);
                    
                    path.push(p);
                    System.out.println("Moved Down " + path.peek().x + " " + path.peek().y + " " + Thread.currentThread().getName());
//                    Grid.labels[path.peek().x][path.peek().y].setOpaque(true);
//                    Grid.labels[path.peek().x][path.peek().y].setBackground(Grid.colors[i]);
                    return true;
                }
            } else {

                System.out.println("Dead end Down " + Thread.currentThread().getName());
                return false;
            }
        } else {
            System.out.println("Can't get out of Maze Borders");
            return false;
        }
    }

    synchronized boolean isSafeDown(int maze[][]) {
        if (path.peek().x == w - 1) {
            return false;
        }
        if (maze[path.peek().x + 1][path.peek().y] == 1) {
            return true;
        } else {
            return false;
        }
    }

    synchronized int solution(int maze[][]) {
        Grid.labels[path.peek().x][path.peek().y].setOpaque(true);
        Grid.labels[path.peek().x][path.peek().y].setBackground(Grid.colors[i++]);
        while (!(path.peek().x == w - 1 && path.peek().y == w - 1)) {
            while (isSafeDown(maze)) {
                if (isSafeDown(maze) && isSafeForward(maze)) {
                    return 2;
                }
                moveDown(maze);
                if (!isSafeDown(maze) && isSafeForward(maze)) {
                    moveForward(maze);
                }
            }
            if (path.peek().x == w - 1 && path.peek().y == w - 1) {
                System.out.println("Solution is Found " + Thread.currentThread().getName());
                return 1;
            }
            if (!moveDown(maze) && !moveForward(maze)) {
                System.out.println("No Solution is Found " + Thread.currentThread().getName());
                return 0;
            }
        }
        return 0;
    }

    @Override
    public void run() {
        
        
        solution(maze);
        if (solution(maze) == 2) {
            Position temp1 = new Position(path.peek().x, path.peek().y + 1);
            Grid.labels[temp1.x][temp1.y].setOpaque(true);
//            Grid.labels[temp1.x][temp1.y].setBackground();

            Position temp2 = new Position(path.peek().x + 1, path.peek().y);
            Grid.labels[temp2.x][temp2.y].setOpaque(true);
//            Grid.labels[temp2.x][temp2.y].setBackground(Grid.colors[i]);

            System.out.println("I ENTERED 2 WAYS WITH " + path.peek().x + " " + path.peek().y);
            LinkedList<Position> path2 = new LinkedList<>();
            path.push(temp1);
            RatMaze r2 = new RatMaze(maze, path, w);
            Thread t2 = new Thread(r2);
            t2.start();
            //r2.moveForward(maze);
            path2.push(temp2);
            RatMaze r3 = new RatMaze(maze, path2, w);
            Thread t3 = new Thread(r3);
            t3.start();
            //r3.moveDown(maze);
        }
        i++;
    }

    public static void main(String[] args) {
        LinkedList<Position> path = new LinkedList<>();
        Position p = new Position(0, 0);
        path.push(p);
        int maze[][] = {{1, 1, 1, 0, 0, 1, 1, 0},
        {0, 0, 1, 1, 0, 1, 0, 1},
        {0, 1, 0, 1, 1, 1, 0, 1},
        {1, 1, 1, 1, 0, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 0, 0},
        {1, 0, 1, 1, 1, 1, 1, 1}};

//        RatMaze r = new RatMaze(maze, path, 8);
//        Grid gui = new Grid(8);
//        Thread t1 = new Thread(r);
//        t1.start(); 
    }
}
