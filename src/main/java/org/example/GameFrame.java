/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * @author Mai Huu Van
 */
public class GameFrame extends JFrame implements ActionListener{
    
    int tileSize = 50;
    int numRow;
    int numCol;
    int boardWidth;
    int boardHeight;
    int mineCount;
    
    int tileClicked = 0;
    boolean gameOver = false;
    
    JMenuBar menuBar;
    JMenu difficulty;
    JMenuItem beginnerLv,expertLv;
    
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel(); 
    JButton reset = new JButton();
    
    MineTile[][] board;
    ArrayList<MineTile> mineList; 
    Random random = new Random();
    
    GameFrame(int row, int col,int mines){    
        this.numRow = row;
        this.numCol = col;
        this.mineCount = mines;
        boardWidth = numCol*tileSize;
        boardHeight = numRow*tileSize;
        board = new MineTile[numRow][numCol];
        
        this.setSize(boardWidth, boardHeight);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle("MineSweeper");
        
        menuBar = new JMenuBar();
        
        difficulty = new JMenu("Difficulty");
        menuBar.add(difficulty);
        
        beginnerLv = new JMenuItem("Beginner");
        expertLv = new JMenuItem("Expert");
        
        beginnerLv.addActionListener(this);
        expertLv.addActionListener(this);
        
        difficulty.add(beginnerLv);
        difficulty.add(expertLv);
        
        reset.setFont(new Font("Arial Unicode MS",Font.PLAIN,20));
        reset.setSize(90,35);
        reset.setText("Reset");
        reset.addActionListener(this);
        this.add(reset,BorderLayout.EAST);
        
        textLabel.setFont(new Font("Arial",Font.BOLD,35));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Mine: " + mineCount);
        textLabel.setOpaque(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        this.add(textPanel,BorderLayout.NORTH);
        
        boardPanel.setLayout(new GridLayout(numRow,numCol));
        this.add(boardPanel);
        this.setJMenuBar(menuBar);
        
        for(int r = 0; r<numRow; r++){
            for(int c = 0; c<numCol; c++){
                MineTile tile = new MineTile(r,c);
                board[r][c] = tile;
                
                tile.setFocusable(false);
                tile.setMargin(new Insets(0,0,0,0));
                tile.setFont(new Font("Arial Unicode MS",Font.PLAIN,30));
                tile.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e){
                        if(gameOver){
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();
                        //left click
                        if(e.getButton() == MouseEvent.BUTTON1){
                            if(tile.getText().isEmpty()){
                                if(mineList.contains(tile)){
                                    revealMines();
                                }
                                else{
                                    checkMines(tile.r,tile.c);
                                }
                            }                        
                        }//right click
                        else if(e.getButton() == MouseEvent.BUTTON3){
                            if(tile.getText().isEmpty()&&tile.isEnabled()){
                                tile.setText("🚩");
                                
                            }
                            else if(tile.getText().equals("🚩")){
                                tile.setText("");
                            }
                        }
                    }     
                }); 
                
                boardPanel.add(tile);
            }
        }
        this.setVisible(true);  
        
        setMines();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == expertLv){
            this.dispose();
            GameFrame game = new GameFrame(12,22,75);
            this.add(game);    
        }
        if(e.getSource() == beginnerLv){
            this.dispose();
            GameFrame game = new GameFrame(10,10,12);
            this.add(game);
        }
        if(e.getSource() == reset){
            this.dispose();
            GameFrame game = new GameFrame(numRow,numCol,mineCount);
            this.add(game);
        }
    }
        
    void setMines(){
        mineList = new ArrayList<>();    
        int mineLeft = mineCount;
        
        while(mineLeft > 0){
            int r = random.nextInt(numRow);
            int c = random.nextInt(numCol);
            
            MineTile tile = board[r][c];
            if(!mineList.contains(tile)){
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }
    
    void revealMines(){
        for (MineTile tile : mineList) {
            tile.setText("💣");
        }
        
        gameOver = true;
        textLabel.setText("Game Over");
    }
    
    void checkMines(int r, int c){
        if(r < 0 || r >= numRow || c < 0 || c >= numCol){
            return;
        }
        
        MineTile tile = board[r][c];
        if(!tile.isEnabled()){
            return;
        }
        tileClicked += 1;
        tile.setEnabled(false);
        
        int minesFound = 0;
        //checking 
        minesFound += countMines(r-1, c-1); //top left
        minesFound += countMines(r-1, c);   //top
        minesFound += countMines(r-1, c+1); //top right
        
        minesFound += countMines(r, c-1);   //left
        minesFound += countMines(r, c+1);   //right
        
        minesFound += countMines(r+1, c-1); //bottom left
        minesFound += countMines(r+1, c);   //bottom
        minesFound += countMines(r+1, c+1); //bottom right
        
        if(minesFound > 0){
            tile.setText(Integer.toString(minesFound));
        }
        else{
            tile.setText("");
            
            checkMines(r-1, c-1); //top left;
            checkMines(r-1, c); //top;
            checkMines(r-1, c+1); //top right;
            
            checkMines(r, c-1); //left;
            checkMines(r, c+1); //right;
            
            checkMines(r+1, c-1); //bottom left;
            checkMines(r+1, c); //bottom;
            checkMines(r+1, c+1); //bottom right;
        }     
        
        if(tileClicked == numRow * numCol - mineList.size()){
            gameOver = true;
            textLabel.setText("Congratulation!");
        }
    }
    
    int countMines(int r, int c){
        if(r < 0 || r >= numRow || c < 0 || c >= numCol){
            return 0;
        }
        if(mineList.contains(board[r][c])){
            return 1;
        }
        return 0;
    }
}
