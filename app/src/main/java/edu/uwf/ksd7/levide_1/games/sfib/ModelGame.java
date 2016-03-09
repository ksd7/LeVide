/*
 * Levide - An educational foreign language learning game mobile application
 * Copyright (C) 2016  K.S. Dieudonné
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.uwf.ksd7.levide_1.games.sfib;

/**
 * The model part of the MVC pattern for the SFIB game.
 * These are items that are needed by the View to display to the screen.
 * (not values used in calculations or intermediate holding variables)
 * Other game-related state variables are located in the Game Engine.
 */
public class ModelGame {
    // For debug
    //private static final String TAG = "ModelGame";

    private String mBlankedSent = ""; // holds foreign sentence with blanked characters
    private String mBlankWord = ""; // the word that has been blanked in the sentence (the answer)
    private String mEngSent = ""; // keeps the English version of the sentence
    private int mScore = 0; // player score for this game session
    private int mPoints = 0; // points awarded for particular sentence
    private int mHintIdx = 0; // keeps track of how many letters have been revealed in blanked word

    // Default Constructor
    public ModelGame () {
    }

    public String getBlankedSent() { return mBlankedSent; }
    public void setBlankedSent(String blankedSent) { mBlankedSent = blankedSent; }

    public String getBlankWord() { return mBlankWord; }
    public void setBlankWord(String blankWord) { mBlankWord = blankWord; }

    public String getEnglishSent() { return mEngSent; }
    public void setEnglishSent(String engSent) { mEngSent = engSent; }

    public int getScore() { return mScore; }
    public void setScore(int score) { this.mScore = score; }

    public int getPoints() { return mPoints; }
    public void setPoints(int p) { mPoints = p; }

    public int getHintIdx() { return mHintIdx; }
    public void setHintIdx(int hintIdx) { mHintIdx = hintIdx; }

}
