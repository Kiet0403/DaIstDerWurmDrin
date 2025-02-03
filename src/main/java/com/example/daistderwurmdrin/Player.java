/** * OOP Java Project

 * Children Board Game Simulation:  Da ist der Wurm drin

 * Link: https://www.amazon.de/Zoch-601132100-Wurm-Kinderspiel-Jahres/dp/B004L87UQO?th=1;
 * https://www.youtube.com/watch?v=kD8JI8RpTFM;

 * @author Van Tuan Kiet Vo - 1589900

 * @author Truong Anh Tuan Nguyen - 1589760

 */


package com.example.daistderwurmdrin;

public class Player {

    // Data Fields
    private String name;
    private int turnScore;
    private int totalScore;
    private int totalBoosters;
    private boolean[] usedCheckpoints;

    // Constructor
    public Player(String name) {
        this.name = name;
        turnScore = 0;
        totalScore = 0;
        totalBoosters = 2;
        usedCheckpoints = new boolean[3];
    }

    // Accessor Methods
    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTurnScore() {
        return turnScore;
    }

    public int getBoosters() {
        return totalBoosters;
    }

    // Game Play Methods
    public void resetTurnScore() {
        turnScore = 0;
    }

    public void useBooster() {
        if (totalBoosters > 0){
            totalBoosters--;
        }
    }
    // Check if the player still have any booster
    public boolean hasBoosters(){
        return totalBoosters > 0;
    }
    // Mark the checkpoints that are used by the Player
    public void usedCheckpoint(String checkpoint) {
        int ckpt = Integer.parseInt(checkpoint);
        usedCheckpoints[ckpt] = true;
    }

    // Check if the checkpoint has been used
    public boolean hasUsedCheckpoint(String checkpoint) {
        int ckpt = Integer.parseInt(checkpoint);
        return usedCheckpoints[ckpt];
    }
    // Update the turn by adding the player roll score
    public void updateTurn(int roll) {
        turnScore += roll;
    }
    // Saving the player score after rolling
    public void saveScore() {
        totalScore += turnScore;
        resetTurnScore();
    }
    // Award the player with points when they correctly predicts
    public void addBonusPoints(int points) {
        totalScore = totalScore + points;
    }
}
// Declare Human type player
class HumanPlayer extends Player{
    public HumanPlayer(String name) {
        super(name);
    }
}
// Declare Bot type player
class Bot extends Player {
    public String difficulty;
    Bot(String difficulty, String name) {
        super(name);
        this.difficulty=difficulty;
    }
}