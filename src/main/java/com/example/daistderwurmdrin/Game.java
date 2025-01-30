package com.example.daistderwurmdrin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    public static final int MAX_SCORE = 64;
    public static final int BONUS_POINTS = 3;

    // Data Fields
    private com.example.daistderwurmdrin.Die d;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    private List<Player> players;
    private int currentPlayerIndex;
    private Player current;

    private String difficulty;

    private boolean disableBoost1 = false;
    private boolean disableBoost2 = false;

    private boolean p1checkpoint1 = false;
    private boolean p1checkpoint2 = false;
    private boolean p2checkpoint1 = false;
    private boolean p2checkpoint2 = false;
    private boolean p3checkpoint1 = false;
    private boolean p3checkpoint2 = false;
    private boolean p4checkpoint1 = false;
    private boolean p4checkpoint2 = false;

    private Player[] boostersPlaced;
    private int[] booster1Location;
    private int[] booster2Location;

    private int[] avail;

    // Constructor
//    public Game(String p1name, String p2name, String p3name, String p4name) {
//        d = new Die();
//        players = new ArrayList<>();

    public Game(String[] playerNames, String[] playerTypes, String difficulty) {
        if (playerNames.length != 4 || playerTypes.length != 4) {
            throw new IllegalArgumentException("There must be exactly 4 players.");
        }

        d = new Die();
        players = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (playerTypes[i].equalsIgnoreCase("human")) {
                players.add(new HumanPlayer(playerNames[i]));
            } else if (playerTypes[i].equalsIgnoreCase("bot")) {
                players.add(new bot(difficulty,playerNames[i]));
            } else {
                throw new IllegalArgumentException("Invalid player type: " + playerTypes[i]);
            }
        }

        p1 = players.get(0);
        p2 = players.get(1);
        p3 = players.get(2);
        p4 = players.get(3);

        currentPlayerIndex = 0;        
        current = players.get(currentPlayerIndex);

        booster1Location = new int[4];
        booster2Location = new int[4];

        Arrays.fill(booster1Location, -1);
        Arrays.fill(booster2Location, -1);
        // [1, 3, 3, 3]

        //There are 10 of each length piece in the game
        avail = new int[6];
        Arrays.fill(avail, 10);


    }

    // Accessor methods

    public Die getDie() {
        return d;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Player getCurrent() {
        return current;
    }


    public Player getTargetPlayer(int index) {
        return players.get(index);
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public Player getP3() {
        return p3;
    }

    public Player getP4() {
        return p4;
    }

    // Status Methods
    public boolean gameOver() {
        return current.getTotalScore() >= MAX_SCORE;
    }


    public boolean gameOverTie() {
        for (int count : avail) {
            if (count != 0) {
                return false; // If any element is not zero, it's not a tie.
            }
        }
        return true; // All elements are zero, so it's a tie.
    }

    // Game Play Methods
    public void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        current = players.get(currentPlayerIndex);
    }

    public void roll() {
        d.roll();
        int t = d.getTop();
        t= availability(t);
        current.updateTurn(t);
        current.saveScore();

    }

    public void hold() {
        current.saveScore();
        if (!gameOver()) {
            switchTurn();
            d.setTop(0);
        }
    }

    public void gamePlaceBooster(Player currentPlayer, Player targetPlayer, String checkpoint) {
        int targetPlayerIndex = players.indexOf(targetPlayer);
        int currPlayerIndex = players.indexOf(currentPlayer);
        if (currentPlayer.getBoosters() > 0 && !currentPlayer.hasUsedCheckpoint(checkpoint)) {
            currentPlayer.useBooster();
            currentPlayer.usedCheckpoint(checkpoint);
            if (checkpoint.equals("1")) {
                booster1Location[currPlayerIndex] = targetPlayerIndex ;
            } else if (checkpoint.equals("2")) {
                booster2Location[currPlayerIndex] = targetPlayerIndex ;
            }
        }
    }

    public void checkProgress() {
        if(!p1checkpoint1 &&
                !p2checkpoint1 &&
                !p3checkpoint1 &&
                !p4checkpoint1) {
            if (p1.getTotalScore() >= 0.3 * MAX_SCORE) {
                System.out.println("Player 1 progress bar reached 30%: true");
                p1checkpoint1 = true;
                checkBoosterAtCheckpoint(1, 0);
            }
            if (p2.getTotalScore() >= 0.3 * MAX_SCORE) {
                System.out.println("Player 2 progress bar reached 30%: true");
                p2checkpoint1 = true;
                checkBoosterAtCheckpoint(1, 1);
            }
            if(p3.getTotalScore() >= 0.3 * MAX_SCORE){
                System.out.println("Player 3 progress bar reached 30%: true");
                p3checkpoint1 = true;
                checkBoosterAtCheckpoint(1, 2);
            }
            if(p4.getTotalScore() >= 0.3 * MAX_SCORE){
                System.out.println("Player 4 progress bar reached 30%: true");
                p4checkpoint1 = true;
                checkBoosterAtCheckpoint(1,3);
            }
        }
        if(!p1checkpoint2 &&
                !p2checkpoint2 &&
                !p3checkpoint2 &&
                !p4checkpoint2) {
            if (p1.getTotalScore() >= 0.6 * MAX_SCORE) {
                System.out.println("Player 1 progress bar reached 70%: true");
                p1checkpoint2 = true;
                checkBoosterAtCheckpoint(2, 0);
            }
            if (p2.getTotalScore() >= 0.6 * MAX_SCORE) {
                System.out.println("Player 2 progress bar reached 70%: true");
                p2checkpoint2 = true;
                checkBoosterAtCheckpoint(2,1 );
            }
            if(p3.getTotalScore() >= 0.6 * MAX_SCORE){
                System.out.println("Player 3 progress bar reached 70%: true");
                p3checkpoint2 = true;
                checkBoosterAtCheckpoint(2,2);
            }
            if(p4.getTotalScore() >= 0.6 * MAX_SCORE){
                System.out.println("Player 4 progress bar reached 70%: true");
                p4checkpoint2 = true;
                checkBoosterAtCheckpoint(2,3);
            }
        }
    }

    // check boosters of all players
    public void checkBoosterAtCheckpoint(int checkpoint, int playerIndex) {
        if (checkpoint == 1 && !disableBoost1) {
            System.out.println("Checkpoint 1 reached by Player " + (playerIndex + 1));
            for (int i = 0; i < 4; i++) {
                if (booster1Location[i] == playerIndex) {
                    System.out.println("Player " + players.get(i) + ": " + players.get(i).getTotalScore());
                    players.get(i).addBonusPoints(BONUS_POINTS);
                    System.out.println("Player " + (i + 1) + " gets " + BONUS_POINTS + " bonus points.");
                    System.out.println("Player " + players.get(i) + ": " + players.get(i).getTotalScore());
                }
            }
            disableBoost1 = true;
        } else if (checkpoint == 2 && !disableBoost2) {
            System.out.println("Checkpoint 2 reached by Player " + (playerIndex + 1));
            for (int i = 0; i < 4; i++) {
                if (booster2Location[i] == playerIndex) {
                    System.out.println("Player " + players.get(i) + ": " + players.get(i).getTotalScore());
                    players.get(i).addBonusPoints(BONUS_POINTS);
                    System.out.println("Player " + (i + 1) + " gets " + BONUS_POINTS + " bonus points.");
                    System.out.println("Player " + players.get(i) + ": " + players.get(i).getTotalScore());
                }
            }
            disableBoost2 = true;
        }
    }

    public boolean checkNumBooster(){
        return current.hasBoosters();
    }

    public int availability(int score){
        if(avail[score-1] <= 0){
            int available_max = 0;
            for(int i = 0; i < 6; i++) {
                if(avail[i] > 0) {
                    available_max = i + 1;
                }
            }
            if (available_max == 0) {
                return -1;
            }
            avail[available_max-1]--;
            return available_max;
        } else {
            avail[score-1]--;
            return score;
        }
    }

    public void OptimizeBoosters1(){
        for (int i = 0; i < 4; i++) {
            if (getTargetPlayer(i).getTotalScore() >= 0.2 * MAX_SCORE){
                gamePlaceBooster(current, getTargetPlayer(i), "1");
                System.out.println("placed on "+ getTargetPlayer(i));
            }
        }
    }
    public void OptimizeBoosters2(){
        for (int i = 0; i < 4; i++) {
            if (getTargetPlayer(i).getTotalScore() >= 0.6 * MAX_SCORE){
                gamePlaceBooster(current, getTargetPlayer(i), "2");
                System.out.println("placed on "+ getTargetPlayer(i));
            }
        }
    }


//    public static void main(String[] args) {
//        Game g = new Game("Mark", "Ryan", "Alice", "Ella");
//
//        g.gamePlaceBooster(g.getP1(), g.getP4(), "1"); // Player 1 places a booster on Player 4's first checkpoint
//        g.gamePlaceBooster(g.getP2(), g.getP3(), "1"); // Player 2 places a booster on Player 1's first checkpoint
//        g.gamePlaceBooster(g.getP3(), g.getP2(), "1"); // Player 3 places a booster on Player 1's first checkpoint
//        g.gamePlaceBooster(g.getP4(), g.getP1(), "1"); // Player 4 places a booster on Player 1's first checkpoint
//
//        g.gamePlaceBooster(g.getP1(), g.getP4(), "2"); // Player 1 places a booster on Player 4's second checkpoint
//        g.gamePlaceBooster(g.getP2(), g.getP3(), "2"); // Player 2 places a booster on Player 1's second checkpoint
//        g.gamePlaceBooster(g.getP3(), g.getP2(), "2"); // Player 3 places a booster on Player 1's second checkpoint
//        g.gamePlaceBooster(g.getP4(), g.getP1(), "2"); // Player 4 places a booster on Player 1's second checkpoint
//
//        System.out.println("Game Start");
//        System.out.println(Arrays.toString(g.booster1Location));
//        System.out.println(Arrays.toString(g.booster2Location));
//
//
//        while(g.gameOver() == false) {
//            g.roll();
//            g.roll();
//            g.hold();
//            g.checkProgress();
//            // System.out.println("Die Rolled " + g.getDie().getTop());
//            // System.out.println("p1 Turn: " + g.getP1().getTurnScore());
//            // System.out.println("p1 Total: " + g.getP1().getTotalScore());
//            // System.out.println("p2 Turn: " + g.getP2().getTurnScore());
//            // System.out.println("p2 Total: " + g.getP2().getTotalScore());
//            // System.out.println("p3 Turn: " + g.getP3().getTurnScore());
//            // System.out.println("p3 Total: " + g.getP3().getTotalScore());
//            // System.out.println("p4 Turn: " + g.getP4().getTurnScore());
//            // System.out.println("p4 Total: " + g.getP4().getTotalScore());
//        }
//
//        System.out.println("Final Scores:");
//        System.out.println("Player 1: " + g.getP1().getTotalScore());
//        System.out.println("Player 2: " + g.getP2().getTotalScore());
//        System.out.println("Player 3: " + g.getP3().getTotalScore());
//        System.out.println("Player 4: " + g.getP4().getTotalScore());
//    }
}
