package learn2;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;

public class Grid extends JFrame {

    static int[][] maze;
    static int N;
    static JLabel labels[][];
    static Color[] colors = {Color.BLUE, Color.red, Color.yellow, Color.gray, Color.green, Color.ORANGE};
    static LinkedList<Position> path = new LinkedList<>();

    public Grid(int N) {
        this.N = N;
        maze = new int[N][N];
        labels = new JLabel[N][N];
        this.setSize(600, 480);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(400, 200);
        setLayout(new GridLayout(N, N));
        drawGuideMaze(N);
        this.setBlocks();
        this.drawMaze(N);
        Position p = new Position(0, 0);

        path.push(p);
        RatMaze r = new RatMaze(maze, path, N);
        print();

        Thread t1 = new Thread(r);
        t1.start();
        this.repaint();

        System.out.println(maze);
    }

    public void drawMaze(int N) {
        Point start = new Point(0, 0);///Strat Point
        Point end = new Point(N - 1, N - 1);//End Point

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze.length; col++) {
                if (maze[row][col] == 1) {
                    Border b = BorderFactory.createLineBorder(Color.black, 1);
                    labels[row][col].setOpaque(true);
                    labels[row][col].setBackground(Color.WHITE);
                } else {

                    labels[row][col].setOpaque(true);
                    labels[row][col].setBackground(Color.black);
                }
            }
        }
    }

    public void drawGuideMaze(int N) {
        Point start = new Point(0, 0);///Strat Point
        Point end = new Point(N - 1, N - 1);//End Point
        int i = 0;

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                maze[row][col] = 1;
                Border b = BorderFactory.createLineBorder(Color.black, 1);
                labels[row][col] = new JLabel(i++ + "");
                labels[row][col].setBorder(b);
                labels[row][col].setSize(600 / (N * N), 480 / (N * N));
                labels[row][col].setOpaque(true);
                labels[row][col].setBackground(Color.white);
                add(labels[row][col]);

            }
        }
    }

    public static void print() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                System.out.print(maze[i][j] + " ");

            }
            System.out.println();
        }

    }

    public void setBlocks() {
        int tmp = 0;
        List<Integer> blocks = new ArrayList<Integer>();
        int i = 0;
        while (tmp != -1 && tmp < N * N && tmp >= 0) {
            System.out.println("Enter Number of block:");
            Scanner input = new Scanner(System.in);
            tmp = input.nextInt();
            if (tmp > 0 && tmp < (N * N) - 1) {
                blocks.add(tmp);

            } else {
                System.out.println("error");
                break;
            }
        }
        System.out.println(blocks);
        for (int j = 0; j < blocks.size(); j++) {
            maze[blocks.get(j) / N][blocks.get(j) % N] = 0;
        }
    }
    public static void main(String[] args) {
        int N = 3; // Defualt Value
        int[][] maze;
        System.out.println("Enter N-dimensions (Must be greater than or equal 4 ):");
        while (N < 4) {
            Scanner input = new Scanner(System.in);
            N = input.nextInt();
            if (N > 4) {
                break;
            }
            System.out.println("Please Enter N-dimensions (Must be greater than or equal 4 ) ");
        }
        Grid Gui = new Grid(N);
    }
}
