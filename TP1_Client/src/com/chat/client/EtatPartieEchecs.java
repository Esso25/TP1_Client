package com.chat.client;

public class EtatPartieEchecs {
	private char[][] etatEchiquier;


    public EtatPartieEchecs() {
        etatEchiquier = new char[8][8];
        etatEchiquier = new char[][] {
            {'t', 'c', 'f', 'd', 'r', 'f', 'c', 't'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'T', 'C', 'F', 'D', 'R', 'F', 'C', 'T'}
        };
    }


    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            result.append(i+1);
            for (int j = 0; j < 8; j++) {
                result.append(etatEchiquier[i][j]).append(" ");
            }
            result.append("\n");
        }
        result.append(" a b c d e f g h");
        return result.toString();
    }


    public char[][] getEtatEchiquier() {
        return etatEchiquier;
    }


    public void setEtatEchiquier(char[][] nouvelEtat) {

        if (nouvelEtat.length == 8 && nouvelEtat[0].length == 8) {
            etatEchiquier = nouvelEtat;
        } else {
            System.out.println("Taille incorrecte");
        }
    }
}
