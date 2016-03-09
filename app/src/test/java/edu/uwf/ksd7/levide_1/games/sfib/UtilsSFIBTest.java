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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for UtilsSFIB
 */
public class UtilsSFIBTest {

    @Before
    public void setUp() throws Exception {
        UtilsSFIB dummy = new UtilsSFIB();
        UtilsSFIB.SFIBGameLang lang = UtilsSFIB.SFIBGameLang.FRE;
        assertEquals("FRE", lang.toString());

        lang = UtilsSFIB.SFIBGameLang.SPA;
        assertEquals("SPA", lang.toString());

    }

    @Test
    public void testSampleRandomNumbersWithoutRepetition() throws Exception {
        // nominal case, returns a number between strt and end
        int strt = 1;
        int end  = 12;
        int cnt = 1;
        int[] result = UtilsSFIB.sampleRandomNumbersWithoutRepetition(strt, end, cnt);
        assertTrue(result[0] <= end && result[0] >= strt);

        // low case, returns a number between strt and end
        strt = 0;
        end  = 4;
        cnt = 1;
        result = UtilsSFIB.sampleRandomNumbersWithoutRepetition(strt, end, cnt);
        assertTrue(result[0] <= end && result[0] >= strt);

        // high case, returns a number between strt and end
        strt = 256;
        end  = 5000;
        cnt = 1;
        result = UtilsSFIB.sampleRandomNumbersWithoutRepetition(strt, end, cnt);
        assertTrue(result[0] <= end && result[0] >= strt);

        // error case, throws exception for end < strt
        strt = 12;
        end  = 1;
        cnt = 1;
        boolean threw = false;
        try {
            UtilsSFIB.sampleRandomNumbersWithoutRepetition(strt, end, cnt);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assertTrue(threw);

        // error case, throws exception for cnt > (end - start)
        strt = 12;
        end  = 1;
        cnt = 12;
        threw = false;
        try {
            UtilsSFIB.sampleRandomNumbersWithoutRepetition(strt, end, cnt);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assertTrue(threw);

        // error case, throws exception for start < 0
        strt = -1;
        end  = 12;
        cnt = 1;
        threw = false;
        try {
            UtilsSFIB.sampleRandomNumbersWithoutRepetition(strt, end, cnt);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assertTrue(threw);

        // error case, throws exception for cnt < 0
        strt = 1;
        end  = 12;
        cnt = -1;
        threw = false;
        try {
            UtilsSFIB.sampleRandomNumbersWithoutRepetition(strt, end, cnt);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assertTrue(threw);
    }

    @Test
    public void testFindIdxSmallestWord() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");
        // count num words no spec characters less than 5 chars
        int num = UtilsSFIB.findIdxSmallestWord(words);
        assertEquals(3, num);
    }

    @Test
    public void testCntWordsNoSpecChars() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");
        // count num words no spec characters less than 5 chars
        int num = UtilsSFIB.cntWordsNoSpecChars(words);
        assertEquals(30, num);
    }

    @Test
    public void testCntWordsLessThan() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");
        // count num words less than 5 chars
        int num = UtilsSFIB.cntWordsLessThan(words, 5);
        assertEquals(18, num);

        // count num words less than 20 chars
        num = UtilsSFIB.cntWordsLessThan(words, 20);
        assertEquals(35, num);

        // count num words less than 2 chars
        num = UtilsSFIB.cntWordsLessThan(words, 2);
        assertEquals(0, num);
    }

    @Test
    public void testCntWordsLessThanNoSpec() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");
        // count num words no spec characters less than 5 chars
        int num = UtilsSFIB.cntWordsLessThanNoSpec(words, 5);
        assertEquals(18, num);

        // count num words no spec characters less than 20 chars
        num = UtilsSFIB.cntWordsLessThanNoSpec(words, 20);
        assertEquals(30, num);

        // count num words no spec characters less than 2 chars
        num = UtilsSFIB.cntWordsLessThanNoSpec(words, 2);
        assertEquals(0, num);
    }

    @Test
    public void testFindWordNoSpecChars() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");
        // Find the 5th word no spec characters
        int num = UtilsSFIB.findWordNoSpecChars(words, 5);
        assertEquals(5, num);

        // Find the 7th word no spec characters
        num = UtilsSFIB.findWordNoSpecChars(words, 7);
        assertEquals(9, num);

        // return the 15th suitable word
        num = UtilsSFIB.findWordNoSpecChars(words, 15);
        assertEquals(17, num);
    }

    @Test
    public void testFindWordLessThan() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");
        // Find the 5th word less than 8 characters
        int num = UtilsSFIB.findWordLessThan(words, 8, 5);
        assertEquals(7, num);

        // Find the 7th word less than 3 characters
        num = UtilsSFIB.findWordLessThan(words, 3, 7);
        assertEquals(30, num);

        // Expect 0 to be returned here since there are no words in this sentence less than 2 chars
        num = UtilsSFIB.findWordLessThan(words, 2, 2);
        assertEquals(0, num);

        // There are 35 words in the sentence, all less than 20 chars
        // return the 15th suitable word
        num = UtilsSFIB.findWordLessThan(words, 20, 15);
        assertEquals(14, num);
    }

    @Test
    public void testFindWordLessThanNoSpec() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");
        // Find the 5th word less than 8 characters with no special characters.
        int num = UtilsSFIB.findWordLessThanNoSpec(words, 8, 5);
        assertEquals(7, num);

        // Find the 7th word less than 3 characters with no special characters.
        num = UtilsSFIB.findWordLessThanNoSpec(words, 3, 7);
        assertEquals(30, num);

        // Expect 0 to be returned here since there are no words in this sentence less than 2 chars
        num = UtilsSFIB.findWordLessThanNoSpec(words, 2, 2);
        assertEquals(0, num);

        // There are 35 words in the sentence, all less than 20 chars, but some have spec chars,
        // return the 15th suitable word
        num = UtilsSFIB.findWordLessThanNoSpec(words, 20, 15);
        assertEquals(17, num);
    }

    @Test
    public void testNumSpecInWord() throws Exception {
        final String t1 = "à";
        assertEquals(1, UtilsSFIB.numSpecInWord(t1));

        final String t2 = "habrá";
        assertEquals(1, UtilsSFIB.numSpecInWord(t2));

        final String t3 = "scientifique,";
        assertEquals(0, UtilsSFIB.numSpecInWord(t3));

        final String t4 = "artísticas";
        assertEquals(1, UtilsSFIB.numSpecInWord(t4));

        final String t5 = "d'éducation";
        assertEquals(1, UtilsSFIB.numSpecInWord(t5));

        final String t6 = "Tête-à-tête";
        assertEquals(3, UtilsSFIB.numSpecInWord(t6));

        // Test boundary, at 0080 and above it counts as special char
        final String t7 = "\u007F\u0080";
        assertEquals(1, UtilsSFIB.numSpecInWord(t7));
    }

    @Test
    public void testComputeScore() throws Exception {
        int hintCnt;
        int guessCnt;
        int bonus = 2;
        int penalty = 3;

        // hold hintCnt and guessCnt steady
        hintCnt = 0;
        guessCnt = 0;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter, no spec, end punc
        // multi letter, 1 spec, mid punc
        // multi letter, multi spec, mid punc
        String[] tmpStr = {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        int[] ans = {3, 4, 14, 14, 16};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 1, no bonus, no penalty
        hintCnt = 1;
        guessCnt = 0;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter, no spec, end punc
        // multi letter, 1 spec, mid punc
        // multi letter, multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {1, 2, 12, 12, 14};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 2, no bonus, 1 letter revealed
        hintCnt = 2;
        guessCnt = 0;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter (13), no spec, end punc
        // multi letter (11), 1 spec, mid punc
        // multi letter (11), multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {0, 0, 11, 11, 13};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 3, no bonus, 2 letters revealed
        hintCnt = 3;
        guessCnt = 0;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter (13), no spec, end punc
        // multi letter (11), 1 spec, mid punc
        // multi letter (11), multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {0, 0, 10, 10, 11};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 4, no bonus, 3 letters revealed
        hintCnt = 4;
        guessCnt = 0;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter (13), no spec, end punc
        // multi letter (11), 1 spec, mid punc
        // multi letter (11), multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {0, 0, 9, 8, 10};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 7, no bonus, 6 letters revealed
        hintCnt = 7;
        guessCnt = 0;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter (13), no spec, end punc
        // multi letter (11), 1 spec, mid punc
        // multi letter (11), multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {0, 0, 6, 5, 6};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 11, no bonus, 10 letters revealed
        hintCnt = 11;
        guessCnt = 0;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter (13), no spec, end punc
        // multi letter (11), 1 spec, mid punc
        // multi letter (11), multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {0, 0, 2, 1, 1};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 1, guessCnt = 3, so 0 penalty
        hintCnt = 1;
        guessCnt = 2;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter, no spec, end punc
        // multi letter, 1 spec, mid punc
        // multi letter, multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {1, 2, 12, 12, 14};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }

        // use hintCnt 4, guessCnt = 8, so 2 penalty
        hintCnt = 4;
        guessCnt = 8;
        // test cases
        // single letter, no spec
        // single letter, 1 spec
        // multi letter (13), no spec, end punc
        // multi letter (11), 1 spec, mid punc
        // multi letter (11), multi spec, mid punc
        tmpStr = new String[] {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        ans = new int[] {0, 0, 7, 6, 8};

        for(int i = 0; i < tmpStr.length; i++) {
            assertEquals(ans[i], UtilsSFIB.computeScore(tmpStr[i], hintCnt, guessCnt, bonus,
                    penalty));
        }
    }

    @Test
    public void testDetermineWordToBlank() throws Exception {
        String tst = "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +
                "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
                "développement des activités des Nations Unies pour le maintien de la paix.";
        String[] words = tst.split(" ");

        // test difficulty level 1
        int difficulty = 1; // 1 - 5
        int hardLen = 11;
        int easyLen = 6;
        int blankWordNum = UtilsSFIB.determineWordToBlank(words, difficulty, hardLen, easyLen);
        assertTrue(words[blankWordNum-1].length() < easyLen &&
                   words[blankWordNum-1].matches("^[\\u0000-\\u007F]+$"));

        // test difficulty level 2
        difficulty = 2;
        blankWordNum = UtilsSFIB.determineWordToBlank(words, difficulty, hardLen, easyLen);
        assertTrue(words[blankWordNum-1].length() < easyLen);

        // test difficulty level 3
        difficulty = 3;
        blankWordNum = UtilsSFIB.determineWordToBlank(words, difficulty, hardLen, easyLen);
        assertTrue(words[blankWordNum-1].length() < hardLen &&
                words[blankWordNum-1].matches("^[\\u0000-\\u007F]+$"));

        // test difficulty level 4
        difficulty = 4;
        blankWordNum = UtilsSFIB.determineWordToBlank(words, difficulty, hardLen, easyLen);
        assertTrue(words[blankWordNum-1].matches("^[\\u0000-\\u007F]+$"));

        // test difficulty level 5
        difficulty = 5;
        blankWordNum = UtilsSFIB.determineWordToBlank(words, difficulty, hardLen, easyLen);
        assertTrue(blankWordNum >= 1 && blankWordNum <= words.length);

        boolean thrown = false;
        try {
            difficulty = 0;
            UtilsSFIB.determineWordToBlank(words, difficulty, hardLen, easyLen);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            difficulty = 6;
            UtilsSFIB.determineWordToBlank(words, difficulty, hardLen, easyLen);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            difficulty = 3;
            UtilsSFIB.determineWordToBlank(words, difficulty, easyLen, hardLen);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testBlankWordInSent() throws Exception {
        String inStr = "Here is a string to test.";
        String outStr;

        String[] words = inStr.split(" ");
        outStr = UtilsSFIB.blankWordInSent(words, 1);
        assertEquals("_._._._. is a string to test.", outStr);

        outStr = UtilsSFIB.blankWordInSent(words, 3);
        assertEquals("Here is _. string to test.", outStr);

        outStr = UtilsSFIB.blankWordInSent(words, 6);
        assertEquals("Here is a string to _._._._..", outStr);

        boolean thrown = false;
        try {
            UtilsSFIB.blankWordInSent(words, 7);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            UtilsSFIB.blankWordInSent(words, 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testCheckAnswer() {
        String t1 = "big";
        String t2 = "dumb";
        assertFalse(UtilsSFIB.checkAnswer(t1,t2));

        t1 = "elephant";
        t2 = "elephant";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "elephan";
        t2 = "elephant";
        assertFalse(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "Big";
        t2 = "big";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "étudiants.";
        t2 = "étudiants";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "étudiants.";
        t2 = "étudiants?";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "étudiants";
        t2 = "étudiants;";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "étudiants";
        t2 = "étudiants,";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "étudiants";
        t2 = "étudiants¿";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));

        t1 = "étudiants";
        t2 = "Étudiants";
        assertTrue(UtilsSFIB.checkAnswer(t1, t2));
    }
}