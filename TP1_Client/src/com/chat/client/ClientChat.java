package com.chat.client;

/**
 * Cette classe étend la classe Client pour lui ajouter des fonctionnalités
 * spécifiques au chat et au jeu d'échecs en réseau.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ClientChat extends Client {
	private EtatPartieEchecs etatPartieEchecs;


    public ClientChat() {
        etatPartieEchecs = new EtatPartieEchecs();
    }


    public EtatPartieEchecs getEtatPartieEchecs() {
        return etatPartieEchecs;
    }


    public void setEtatPartieEchecs(EtatPartieEchecs nouvelEtat) {
        etatPartieEchecs = nouvelEtat;
    }


    public void deplacementValideServeur(int initLigne, int initColonne, int finLigne, int finColonne) {

        char[][] nouvelEtat = etatPartieEchecs.getEtatEchiquier();
        char pieceDeplacee = nouvelEtat[initLigne][initColonne];


        nouvelEtat[initLigne][initColonne] = ' ';
        nouvelEtat[finLigne][finColonne] = pieceDeplacee;


        etatPartieEchecs.setEtatEchiquier(nouvelEtat);
    }

    public void nouvellePartie() {
        etatPartieEchecs = new EtatPartieEchecs();
    }

}
