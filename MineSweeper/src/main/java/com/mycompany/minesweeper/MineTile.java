/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minesweeper;

import javax.swing.JButton;

/**
 *
 * @author DELL
 */
public class MineTile extends JButton{
        int r;
        int c;
        
        public MineTile(int r, int c){
            this.r = r;
            this.c = c;
        }
    }