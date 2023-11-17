package com.echecs;

import com.echecs.pieces.Cavalier;
import com.echecs.pieces.Dame;
import com.echecs.pieces.Fou;
import com.echecs.pieces.Piece;
import com.echecs.pieces.Pion;
import com.echecs.pieces.Roi;
import com.echecs.pieces.Tour;
import com.echecs.util.EchecsUtil;

/**
 * Représente une partie de jeu d'échecs. Orcheste le déroulement d'une partie :
 * déplacement des pièces, vérification d'échec, d'échec et mat,...
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class PartieEchecs {
    /**
     * Grille du jeu d'échecs. La ligne 0 de la grille correspond à la ligne
     * 8 de l'échiquier. La colonne 0 de la grille correspond à la colonne a
     * de l'échiquier.
     */
    private Piece[][] echiquier;
    private String aliasJoueur1, aliasJoueur2;
    private char couleurJoueur1, couleurJoueur2;

    /**
     * La couleur de celui à qui c'est le tour de jouer (n ou b).
     */
    private char tour = 'b'; //Les blancs commencent toujours
    /**
     * Crée un échiquier de jeu d'échecs avec les pièces dans leurs positions
     * initiales de début de partie.
     * Répartit au hasard les couleurs n et b entre les 2 joueurs.
     */
    public PartieEchecs() {
        echiquier = new Piece[8][8];
        //Placement des pièces :
        
        //BLANCS
        //Tours
        echiquier[7][0] = new Tour('b');
        echiquier[7][7] = new Tour('b');
        //Fous
        echiquier[7][2] = new Fou('b');
        echiquier[7][5] = new Fou('b');
        //Cavaliers
        echiquier[7][1] = new Cavalier('b');
        echiquier[7][6] = new Cavalier('b');
        //Roi et dame
        echiquier[7][3] = new Roi('b');
        echiquier[7][4] = new Dame('b');
        //Pions
        echiquier[6][0] = new Pion('b');
        echiquier[6][1] = new Pion('b');
        echiquier[6][2] = new Pion('b');
        echiquier[6][3] = new Pion('b');
        echiquier[6][4] = new Pion('b');
        echiquier[6][5] = new Pion('b');
        echiquier[6][6] = new Pion('b');
        echiquier[6][7] = new Pion('b');

        
        //NOIRS
        //Tours
        echiquier[0][0] = new Tour('n');
        echiquier[0][7] = new Tour('n');
        //Fous
        echiquier[0][2] = new Fou('n');
        echiquier[0][5] = new Fou('n');
        //Cavaliers
        echiquier[0][1] = new Cavalier('n');
        echiquier[0][6] = new Cavalier('n');
        //Roi et dame
        echiquier[0][3] = new Roi('n');
        echiquier[0][4] = new Dame('n');
        //Pions
        echiquier[1][0] = new Pion('n');
        echiquier[1][1] = new Pion('n');
        echiquier[1][2] = new Pion('n');
        echiquier[1][3] = new Pion('n');
        echiquier[1][4] = new Pion('n');
        echiquier[1][5] = new Pion('n');
        echiquier[1][6] = new Pion('n');
        echiquier[1][7] = new Pion('n');
        
       // Initialiser le tour des blancs
        tour = 'b'; 

    }

    /**
     * Change la main du jeu (de n à b ou de b à n).
     */
    public void changerTour() {
        if (tour=='b')
            tour = 'n';
        else
            tour = 'b';
    }
    /**
     * Tente de déplacer une pièce d'une position à une autre sur l'échiquier.
     * Le déplacement peut échouer pour plusieurs raisons, selon les règles du
     * jeu d'échecs. Par exemples :
     *  Une des positions n'existe pas;
     *  Il n'y a pas de pièce à la position initiale;
     *  La pièce de la position initiale ne peut pas faire le mouvement;
     *  Le déplacement met en échec le roi de la même couleur que la pièce.
     *
     * @param initiale Position la position initiale
     * @param finale Position la position finale
     *
     * @return boolean true, si le déplacement a été effectué avec succès, false sinon
     */
    public boolean deplace(Position initiale, Position finale) {
    	//on verifie la validité des positions finale et initiale
    	if (!EchecsUtil.positionValide(initiale) || !EchecsUtil.positionValide(finale)) {
            return false;
        }

        int ligne_initiale = EchecsUtil.indiceLigne(initiale.getLigne());
        int ligne_finale = EchecsUtil.indiceLigne(finale.getLigne());
        int col_initiale = EchecsUtil.indiceColonne(initiale.getColonne());
        int col_finale = EchecsUtil.indiceColonne(finale.getColonne());

        // on verifie s'il y a une pièce à déplacer à la position initiale
        Piece pieceADeplacer = echiquier[ligne_initiale][col_initiale];
        if (pieceADeplacer == null) {
            return false;
        }

        //on vérifie si la couleur du joueur correspond au tour actuel
        if (pieceADeplacer.getCouleur() != tour) {
            return false;
        }

        // on véfirie s'il y a une pièce de même couleur à la position finale
        Piece pieceFinale = echiquier[ligne_finale][col_finale];
        if (pieceFinale != null && pieceFinale.getCouleur() == tour) {
            return false;
        }


        // Si la pièce peut se déplacer
        if (!pieceADeplacer.peutSeDeplacer(initiale, finale, echiquier)) {
            return false;
        }

        // Effectuer le déplacement
        echiquier[ligne_finale][col_finale] = pieceADeplacer;
        echiquier[ligne_initiale][col_initiale] = null;
     // Changer le tour après un déplacement réussi
        changerTour(); 
        return true;
    }

    /**
     * Vérifie si un roi est en échec et, si oui, retourne sa couleur sous forme
     * d'un caractère n ou b.
     * Si la couleur du roi en échec est la même que celle de la dernière pièce
     * déplacée, le dernier déplacement doit être annulé.
     * Les 2 rois peuvent être en échec en même temps. Dans ce cas, la méthode doit
     * retourner la couleur de la pièce qui a été déplacée en dernier car ce
     * déplacement doit être annulé.
     *
     * @return char Le caractère n, si le roi noir est en échec, le caractère b,
     * si le roi blanc est en échec, tout autre caractère, sinon.
     */
    public char estEnEchec() {
    	//Juste du remplissage temporaire
    	return 'Y';
    }
    /**
     * Retourne la couleur n ou b du joueur qui a la main.
     *
     * @return char la couleur du joueur à qui c'est le tour de jouer.
     */
    public char getTour() {
        return tour;
    }
    /**
     * Retourne l'alias du premier joueur.
     * @return String alias du premier joueur.
     */
    public String getAliasJoueur1() {
        return aliasJoueur1;
    }
    /**
     * Modifie l'alias du premier joueur.
     * @param aliasJoueur1 String nouvel alias du premier joueur.
     */
    public void setAliasJoueur1(String aliasJoueur1) {
        this.aliasJoueur1 = aliasJoueur1;
    }
    /**
     * Retourne l'alias du deuxième joueur.
     * @return String alias du deuxième joueur.
     */
    public String getAliasJoueur2() {
        return aliasJoueur2;
    }
    /**
     * Modifie l'alias du deuxième joueur.
     * @param aliasJoueur2 String nouvel alias du deuxième joueur.
     */
    public void setAliasJoueur2(String aliasJoueur2) {
        this.aliasJoueur2 = aliasJoueur2;
    }
    /**
     * Retourne la couleur n ou b du premier joueur.
     * @return char couleur du premier joueur.
     */
    public char getCouleurJoueur1() {
        return couleurJoueur1;
    }
    /**
     * Retourne la couleur n ou b du deuxième joueur.
     * @return char couleur du deuxième joueur.
     */
    public char getCouleurJoueur2() {
        return couleurJoueur2;
    }
}