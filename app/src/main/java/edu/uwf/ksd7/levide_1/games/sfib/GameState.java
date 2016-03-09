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

//import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralizes the game state variables that are used by the GameEngine.
 */
public class GameState {
    // For debug
    //private static final String TAG = "GameState";
    // Bonus point setting for not taking any hints
    private static final int NO_HINT_BONUS = 2;
    // Allowed number of guesses before points are deducted
    private static final int NUM_GUESSES_PER_POINT_PENALTY = 3;
    // The default difficulty level
    private static final int DEFAULT_DIFF = 5;
    // Used for difficulty algorithm, specifies word length limits for easy and hard
    private static final int WORD_LEN_EASY = 6;
    private static final int WORD_LEN_HARD = 11;

    // Game options related
    private int mDiff = DEFAULT_DIFF; // game difficulty setting

    // Game content, i.e. sentences
    private static List<Sent> mSentList = null;

    // Game stats that are stored in DB
    private static SFIBStats stats = null;

    // how many incorrect guesses user has submitted
    private int mGuessCnt = 0;

    /**
     * Constructor
     */
    public GameState() {
        mSentList = new ArrayList<>();
        stats = new SFIBStats();
    }

    /**
     * Returns the English sentence in the active index
     * @return The English sentence string or null.
     */
    public String getEngSent() {
        String retVal = null;
        if (!mSentList.isEmpty()) {
            int idx = mSentList.size() - 1;
            retVal = mSentList.get(idx).getEnglish();
        }
        return retVal;
    }

    /**
     * Returns the foreign sentence in the active index
     * @return The foreign sentence string or null.
     */
    public String getForSent() {
        String retVal = null;
        if(!mSentList.isEmpty()) {
            int idx = mSentList.size() - 1;
            retVal = mSentList.get(idx).getForeign();
        }
        return retVal;
    }

    /**
     * Returns the key (id) associated with the English sentence
     * for the current active index.
     * @return The sentence key or -1
     */
    public int getKey() {
        int retVal = -1;
        if(!mSentList.isEmpty()) {
            int idx = mSentList.size() - 1;
            retVal = mSentList.get(idx).getKey();
        }
        return retVal;
    }

    /**
     * Work required to update player statistics as a result of
     * a player submitting a correct answer.  Located here to
     * facilitate testing.
     * @param points points awarded for current correct answer
     * @param numHints number of hints taken for current answer submittal
     */
    public void updateStats(int points, int numHints) {
        stats.mSubmitCnt += 1;
        stats.mCumScore += points;
        stats.mSigmaHint += numHints;
        stats.mSigmaDiff += mDiff;
        stats.mSigmaGuess += mGuessCnt;
    }

    /**
     * Work required to compute player statistics.  Located here
     * to facilitate testing.
     * @throws IllegalArgumentException
     */
    public void computeStats()
        throws IllegalArgumentException {
        // Compute Stats:  Get denominator and test to avoid divide by zero
        if(0==stats.mSubmitCnt) throw new IllegalArgumentException("divide by 0");

        stats.mAvgDiff = (float) stats.mSigmaDiff / (float) stats.mSubmitCnt;
        stats.mAvgGuess = (float) stats.mSigmaGuess / (float) stats.mSubmitCnt;
        stats.mAvgSkip = (float) stats.mSigmaSkip / (float) stats.mSubmitCnt;
        stats.mAvgHint = (float) stats.mSigmaHint / (float) stats.mSubmitCnt;
    }

    // Getters and Setters (tested by inspection)
    public int getNoHintBonus() { return NO_HINT_BONUS; }
    public int getNumGuessesPerPointPenalty() { return NUM_GUESSES_PER_POINT_PENALTY; }
    public int getSentBlockCnt() {return ConstsSFIB.SENT_BLOCK_CNT; }
    public int getAddThresh() { return ConstsSFIB.ADD_SENT_THRESH; }
    public int getSentBlockThresh() { return ConstsSFIB.SENT_BLOCK_THRESH; }
    public int getWordLenEasy() { return WORD_LEN_EASY; }
    public int getWordLenHard() { return WORD_LEN_HARD; }

    public int getDifficulty() { return mDiff; }
    public void setDifficulty(int diff) { mDiff = diff; }

    public static List<Sent> getSentList() { return mSentList; }
    public static SFIBStats getSFIBStats() { return stats; }

    public int getCumScore() { return stats.mCumScore; }
    public float getAvgGuess() { return stats.mAvgGuess; }
    public float getAvgDiff() { return stats.mAvgDiff; }
    public float getAvgHint() { return stats.mAvgHint; }
    public float getAvgSkip() { return stats.mAvgSkip; }
    public int getSubmitCnt() { return stats.mSubmitCnt; }
    public int getSigmaSkip() { return stats.mSigmaSkip; }
    public int getSigmaHint() { return stats.mSigmaHint; }
    public int getSigmaGuess() { return stats.mSigmaGuess; }
    public int getSigmaDiff() { return stats.mSigmaDiff; }

    public int getAddCnt() { return stats.mAddCnt; }
    public void setAddCnt(int addCnt) { stats.mAddCnt = addCnt; }

    public int getGuessCnt() { return mGuessCnt; }
    public void setGuessCnt(int guessCnt) { mGuessCnt = guessCnt; }

    // Increment the number of skips
    public void incSigSkip() { stats.mSigmaSkip += 1; }

    // Game statistics and parameters that need memory, i.e. will be stored in DB
    public class SFIBStats {
        public int mSigmaDiff = 0;
        public int mSigmaGuess = 0;
        public int mSigmaHint = 0;
        public int mSigmaSkip = 0;
        public int mSubmitCnt = 0;
        public int mCumScore = 0;
        public float mAvgDiff = 0;
        public float mAvgGuess = 0;
        public float mAvgSkip = 0;
        public float mAvgHint = 0;
        public int mAddCnt = 0;
    }
}
