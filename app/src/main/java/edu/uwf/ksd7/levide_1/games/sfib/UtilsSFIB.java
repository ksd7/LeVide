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

import java.util.Random;

/**
 * UtilsSFIB provides miscellaneous utility routines used in the SFIB game.
 */
public class UtilsSFIB {
    // For debug
    private static final String TAG = "UtilsSFIB";

    /**
     * Specifies the appropriate language codes for SFIB games
     */
    public enum SFIBGameLang {
        FRE,
        SPA;

        @Override
        public String toString() {
            String ret = "";
            switch(this) {
                case FRE:
                    ret = "FRE";
                    break;
                case SPA:
                    ret = "SPA";
                    break;
             }
            return ret;
        }
    }

    /**
     * Provides an array of "count" integers between starting number and ending
     * number without repeats. See Programming Pearls p. 127. It will return the
     * integers in order from start to end. Therefore, providing a count that is
     * greater than (end - start) is not expected (since numbers can only be
     * represented once). If that happens, an array will be returned of size
     * count, but the largest integer will be "end" and the rest of the values
     * will be zero.
     *
     * @param start the starting number
     * @param end the ending number
     * @param count the quantity of numbers to be returned
     * @return array of "count" random ints in consecutive order without repetition
     * @throws IllegalArgumentException
     */
    public static int[] sampleRandomNumbersWithoutRepetition (
            int start,
            int end,
            int count) throws IllegalArgumentException {
        // Validate passed parameters
        if (start >= 0 && end >= 0 && end >= start && count >= 0
                && (end - start) >= count) {
            Random rng = new Random();

            int[] result = new int[count];
            int cur = 0;
            int remaining = end - start;
            for (int i = start; i < end && count > 0; i++) {
                double probability = rng.nextDouble();
                if (probability < ((double) count) / (double) remaining) {
                    count--;
                    result[cur++] = i;
                }
                remaining--;
            }
            return result;
        } else {
            throw new IllegalArgumentException("parameters out of range");
        }
    }

    /**
     * Determines the index of the smallest word in the passed
     * String array.
     * @param str Array of words
     * @return The index number
     */
    public static int findIdxSmallestWord(String[] str) {
        int min = Integer.MAX_VALUE; // large initializer
        int minIdx = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i].length() < min) {
                min = str[i].length();
                minIdx = i;
            }
        }
        return minIdx;
    }

    /**
     * Determines how many words in the passed String array that do
     * not contain special (non-English) characters.
     * @param str Array of words
     * @return The word count
     */
    public static int cntWordsNoSpecChars(String[] str) {
        int cnt = 0;
        for(String value : str) {
            if(value.matches("^[\u0000-\u007F]+$")) cnt++;
        }
        return cnt;
    }

    /**
     * Determines how many words in the passed String array are less than
     * the specified size.
     * @param str Array of words
     * @param size Specifies the word length of interest
     * @return The word count
     */
    public static int cntWordsLessThan(String[] str, int size) {
        int cnt = 0;
        for(String value : str) {
            if(value.length() < size) cnt++;
        }
        return cnt;
    }

    /**
     * Determines how many words in the passed String array are less than
     * the specified size AND do not contain special (non-English)
     * characters.
     * @param str Array of words
     * @param size Specifies the word length of interest
     * @return The word count
     */
    public static int cntWordsLessThanNoSpec(String[] str, int size) {
        int cnt = 0;
        for(String value : str) {
            if(value.length() < size && value.matches("^[\u0000-\u007F]+$")) cnt++;
        }
        return cnt;
    }

    /**
     * Locates the "cnt" English word (no special characters)
     * in the passed String array and returns the index of that word
     * from the perspective of the entire String array
     * It is assumed that the caller already knows how many
     * of the appropriate words are in the array and is merely
     * requesting that this method return the "cnt" appropriate
     * word.  Thus, input parameters are not sanity tested.
     * @param str Array of words
     * @param cnt Specifies the target word number
     * @return Index of the ith suitable word in the array
     */
    public static int findWordNoSpecChars(String[] str, int cnt) {
        int idx = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i].matches("^[\u0000-\u007F]+$")) {
                idx++;
                if (idx == cnt) {
                    idx = i;
                    break;
                }
            }
        }
        return idx;
    }

    /**
     * Locates the "cnt" word of size less than the specified size
     * limit in the passed String array and returns the index of that word
     * from the perspective of the entire String array
     * It is assumed that the caller already knows how many
     * of the appropriate words are in the array and is merely
     * requesting that this method return the "cnt" appropriate
     * word.  Thus, input parameters are not sanity tested.
     * @param str Array of words
     * @param size specifies the desired word size limit
     * @param cnt specifies the target word number
     * @return Index of the ith suitable word in the array
     */
    public static int findWordLessThan(String[] str, int size, int cnt) {
        int idx = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i].length() < size) {
                idx++;
                if (idx == cnt) {
                    idx = i;
                    break;
                }
            }
        }
        return idx;
    }

    /**
     * Locates the "cnt" word of size less than the specified size
     * limit in the passed String array AND no special characters
     * and returns the index of that word
     * from the perspective of the entire String array
     * It is assumed that the caller already knows how many
     * of the appropriate words are in the array and is merely
     * requesting that this method return the "cnt" appropriate
     * word.  Thus, input parameters are not sanity tested.
     * @param str Array of words
     * @param size specifies the desired word size limit
     * @param cnt specifies the target word number
     * @return Index of the ith suitable word in the array
     */
    public static int findWordLessThanNoSpec(String[] str, int size, int cnt) {
        int idx = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i].length() < size && str[i].matches("^[\u0000-\u007F]+$")) {
                idx++;
                if (idx == cnt) {
                    idx = i;
                    break;
                }
            }
        }
        return idx;
    }

    /**
     * Counts the number of special (non-English) characters in a word
     * (outside of the basic ASCII range of 0x0000 to 0x007F)
     * @param word The word of interest.
     * @return The count of the number of special (non-English) characters.
     */
    public static int numSpecInWord(String word) {
        int cnt = 0;

        //Replace all characters (except the last one) with the character
        // itself followed by a space
        word = word.replaceAll(".(?=.)", "$0 ");
        String[] str = word.split(" ");

        // Determine how many characters are not in the basic ASCII range
        for(String value : str) {
            if(!value.matches("^[\u0000-\u007F]+$")) cnt++;
        }
        return cnt;
    }

    /**
     * Computes the score based on if the user took hints, how many guesses,
     * and how difficult the word was.
     * @param word The guessed word used to determine difficulty.
     * @param hintCnt Indicates how many hints player took.
     * @param guessCnt How many guesses player took
     * @param bonus Bonus awarded for no hints.
     * @param numGuessPerPtPenalty Number of guesses allowed before
     *                             a 1 point penalty is incurred.
     * @return The score for the word submitted.
     */
    public static int computeScore(String word, int hintCnt, int guessCnt,
                                   int bonus, int numGuessPerPtPenalty) {
        int score; // for return
        // A hint count of 1 indicates only that the English sentence revealed
        // not that a letter has been revealed
        int numLtrsRevealed = hintCnt - 1;
        if (numLtrsRevealed < 0) numLtrsRevealed = 0;
        if (numLtrsRevealed > word.length()) numLtrsRevealed = word.length();

        // Get the substring of the word the player has actually answered
        String submittedWord = word.substring(numLtrsRevealed);
        // Use length and number of special characters as difficulty
        int difficulty = submittedWord.length() + numSpecInWord(submittedWord);

        // If user took no hint whatsoever
        if (0 == hintCnt) {
            // Award bonus points if no peek at English
            score = difficulty + bonus;
        }
        else {
            // Award points based on difficulty
            score = difficulty;
            // Detect if all letters have been revealed and award 0
            if(numLtrsRevealed >= word.length()) score = 0;
        }

        // Allow the user x number of guesses without penalty before starting
        // to decrease the score value
        score -= (guessCnt / numGuessPerPtPenalty);

        // If last letter is punctuation, remove a point
        if(isLastCharPunc(word)) score--;

        // Detect if score has dropped below zero
        if(score < 0) score = 0;

        return score;
    }

    /**
     * Determines the word in the sentence to blank by filtering based on difficulty level.
     * Higher difficulty allows large words and words with special characters through.
     * Lower difficulty filters these out.
     * @param words The input sentence contained in a String array.
     * @param difficulty The desired difficulty level.
     * @param wordLenHard The setting that specifies the length at which a word is considered hard.
     * @param wordLenEasy The setting that specifies the length at which a word is considered easy.
     * @return The index into the input array that points to the chosen word.
     * @throws IllegalArgumentException
     */
    public static int determineWordToBlank(String[] words, int difficulty,
                                    int wordLenHard, int wordLenEasy)
        throws IllegalArgumentException {
        if(wordLenHard < wordLenEasy)
            throw new IllegalArgumentException("wordLen parameters invalid hard < easy");
        //Log.d(TAG, "determineWordToBlank");
        int numWords = words.length;
        int wordNum = 0;

        if (numWords > 0) {
            // Based on difficulty, decide on what word number to use
            // word number is the string selected for blanking (the
            // first word in the sentence is 1, last word is
            // numWords)
            int nums[];
            int cnt;
            int validNum;
            int idx;

            // initialize word to blank to smallest word
            wordNum = findIdxSmallestWord(words) + 1;

            switch (difficulty) {
                // For highest difficulty level, use a purely random word selection
                // method --> exclude nothing
                case 5:
                    nums = sampleRandomNumbersWithoutRepetition(1,
                            numWords + 1, 1);
                    wordNum = nums[0];
                    break;

                // For medium hi difficulty, use random (any word length, no special
                // characters), or the shortest word --> exclude word with special
                // characters
                case 4:
                    // 1. Determine how many of the words do not have special
                    // characters
                    cnt = cntWordsNoSpecChars(words);
                    //System.out.println("num words any length (no special) = " + cnt);
                    if (cnt > 0) {
                        // 2. Get a random int between 1 and cnt (to use the ith
                        // acceptable word)
                        nums = sampleRandomNumbersWithoutRepetition(1, cnt + 1,
                                1);
                        validNum = nums[0];
                        // 3. Now target the ith acceptable word
                        idx = findWordNoSpecChars(words, validNum);
                        wordNum = idx + 1;
                    }
                    break;

                // For medium difficulty, use random (1-10 letter words, no special
                // characters), or the shortest word --> exclude word length > 10,
                // exclude special characters
                case 3:
                    // 1. Determine how many of the words do not have special
                    // characters
                    cnt = cntWordsLessThanNoSpec(words,
                            wordLenHard);
                    //System.out.println("num words less than 10 (no special) = " + cnt);
                    if (cnt > 0) {
                        // 2. Get a random int between 1 and cnt (to use the ith
                        // acceptable word)
                        nums = sampleRandomNumbersWithoutRepetition(1, cnt + 1,
                                1);
                        validNum = nums[0];
                        //System.out.println("random number returned is " + validNum);
                        // 3. Now target the ith acceptable word
                        idx = findWordLessThanNoSpec(words,
                                wordLenHard, validNum);
                        wordNum = idx + 1;
                        //System.out.println("Selected from original sent word num = " + wordNum);
                    }
                    break;

                // For medium lo difficulty, use random (1-5 letter words, allowing
                // special characters), or the shortest word --> exclude word length
                // > 5
                case 2:
                    // 1. Determine how many of the words do not have special
                    // characters
                    cnt = cntWordsLessThan(words,
                            wordLenEasy);
                    //System.out.println("num words less than 6 (with special) = " + cnt);
                    if (cnt > 0) {
                        // 2. Get a random int between 1 and cnt (to use the ith
                        // acceptable word)
                        nums = sampleRandomNumbersWithoutRepetition(1, cnt + 1,
                                1);
                        validNum = nums[0];
                        // 3. Now target the ith acceptable word
                        idx = findWordLessThan(words,
                                wordLenEasy, validNum);
                        wordNum = idx + 1;
                        //System.out.println("Selected from original sent word num = " + wordNum);
                    }
                    break;

                // For lowest difficulty level, use only random (1-5 letter words,
                // no special characters), or the shortest word --> exclude word
                // length > 5, exclude special characters
                case 1:
                    // 1. Determine how many of the words do not have special
                    // characters
                    cnt = cntWordsLessThanNoSpec(words,
                            wordLenEasy);
                    //System.out.println("num words less than 6 (no special) = " + cnt);
                    if (cnt > 0) {
                        // 2. Get a random int between 1 and cnt (to use the ith
                        // acceptable word)
                        nums = sampleRandomNumbersWithoutRepetition(1, cnt + 1,
                                1);
                        validNum = nums[0];
                        // 3. Now target the ith acceptable word
                        idx = findWordLessThanNoSpec(words,
                                wordLenEasy, validNum);
                        wordNum = idx + 1;
                        //System.out.println("Selected from original sent word num = " + wordNum);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("difficulty out of range");
            }

        } // end if numWords > 0
        return wordNum;
    }

    /**
     * Takes an array of words and a word number and returns a combined
     * String with the word number word replaced with a blank of the
     * appropriate number of underscores.
     * @param words Array of words.
     * @param wordNum Word number to blank.
     * @return Combined string with word blanked.
     * @throws IllegalArgumentException
     */
    public static String blankWordInSent(String[] words, int wordNum)
        throws IllegalArgumentException {
    	// Bound the input
    	if(wordNum < 1 || wordNum > words.length)
    		throw new IllegalArgumentException("parameters out of range");
    
        // Determine how many letters in word to blank
        int blankLen = words[wordNum - 1].length();

        // Don't blank the punctuation at the end of a word
        boolean lastPunc = false;
        if(isLastCharPunc(words[wordNum - 1])) {
            lastPunc = true;
            blankLen--;
        }

        // Build a blanked version of the word
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < blankLen; i++) {
            sb.append("_.");
        }

        // Restore the last character if punctuation
        if(lastPunc) sb.append(words[wordNum - 1].substring(words[wordNum - 1].length() - 1));

        // Create representation of blanked word
        String blankedWord = sb.toString();

        // Create updated foreign sentence
        sb.setLength(0);
        for (int i = 0; i < words.length; i++) {
        	if(i==(wordNum-1)) sb.append(blankedWord);
        	else sb.append(words[i]);
            sb.append(" ");
        }

		String outStr = sb.toString();
        return outStr.substring(0,outStr.length()-1); // trim last space char
    }

    /**
     * Test the submitted word against the word stored as the answer.
     * From playing the game, it is tedious to not award a correct answer
     * due to a comma or a period at the end of a sentence or some missed
     * capitalization.  The game needs to be fun, and not overly tedious.
     * @param userAns The submitted answer string.
     * @param ansCorr The stored (correct) answer.
     * @return true if the player should get credit for the answer being correct
     */
    public static boolean checkAnswer(String userAns, String ansCorr) {
        // Trivial case where answer is purely correct
        if(userAns.equalsIgnoreCase(ansCorr)) return true;

        // Check for punctuation in last letter and remove it
        if(isLastCharPunc(ansCorr)) {
            ansCorr = ansCorr.substring(0,ansCorr.length()-1);
        }
        if(isLastCharPunc(userAns)) {
            userAns = userAns.substring(0, userAns.length()-1);
        }
        return userAns.equalsIgnoreCase(ansCorr);
    }

    /**
     * Determines if the last character of a word is relevant punctuation
     * @param word word to test
     * @return true if is relevant punctuation
     */
    public static boolean isLastCharPunc(String word) {
        String punctuations = ".,:;?¿!¡";
        return punctuations.contains(word.substring(word.length()-1));
    }
}
