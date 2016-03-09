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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for GameEngine.
 */
public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLoadDefaultContent() throws Exception {
        GameEngine ge = new GameEngine();
        String fre = "Chacun a droit à la protection des intérêts moraux et matériels découlant" +
                " de toute production scientifique, littéraire ou artistique dont il est l'auteur.";
        String spa = "Toda persona tiene derecho a la protección de los intereses morales y" +
                " materiales que le correspondan por razón de las producciones científicas," +
                " literarias o artísticas de que sea autora.";
        String eng = "Everyone has the right to the protection of the moral and material" +
                " interests resulting from any scientific, literary or artistic production" +
                " of which he is the author.";
        ge.loadDefaultContent(UtilsSFIB.SFIBGameLang.FRE.toString());
        assertEquals(fre, ge.getForSent());
        assertEquals(eng, ge.getEngSent());

        ge = new GameEngine();
        ge.loadDefaultContent(UtilsSFIB.SFIBGameLang.SPA.toString());
        assertEquals(spa, ge.getForSent());
        assertEquals(eng, ge.getEngSent());

        boolean thrown = false;
        try {
            ge = new GameEngine();
            ge.loadDefaultContent("dummy");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testProcessStats() throws Exception {
    }

    @Test
    public void testAdvanceSent() throws Exception {
        String eng8 = "Everyone has the right freely to participate in the cultural life " +
                "of the" +
                " community, to enjoy the arts and to share in scientific advancement and its" +
                " benefits.";
        String fre8 = "Toute personne a le droit de prendre part librement à la vie " +
                "culturelle" +
                " de la communauté, de jouir des arts et de participer au progrès scientifique" +
                " et aux bienfaits qui en résultent.";
        GameEngine ge = new GameEngine();
        ge.loadDefaultContent(UtilsSFIB.SFIBGameLang.FRE.toString());
        boolean loadContent = ge.advanceSent();
        assertEquals(fre8, ge.getForSent());
        assertEquals(eng8, ge.getEngSent());
        assertFalse(loadContent);

        loadContent = ge.advanceSent();
        while(!loadContent) {
            loadContent = ge.advanceSent();
        }

        // regardless of what the threshold is set to, should be able to pull one more
        // and should continue to return true
        loadContent = ge.advanceSent();
        assertTrue(loadContent);

        ge = new GameEngine();
        boolean thrown = false;
        try {
            ge.advanceSent();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testCheckJSONResponse() throws Exception {
        // test each kind of message with status payload

        String s1 = "{\"add_sent_response\":[{\"status\":\"success\",\"msg\":\"good result\"}]}";
        String s2 = "{\"flag_sent_response\":[{\"status\":\"success\",\"msg\":\"good result\"}]}";
        String s3 = "{\"post_stats_response\":[{\"status\":\"success\",\"msg\":\"good result\"}]}";
        String s4 = "{\"sentence_response\":[{\"status\":\"success\",\"msg\":\"good result\"}]}";
        String s5 = "{\"stats_response\":[{\"status\":\"success\",\"msg\":\"good result\"}]}";
        String f1 = "{\"flag_sent_response\":[{\"status\":\"error\",\"msg\":\"fake " +
                "error\"}]}";
        String f2 = "13898238"; // gibberish for JSON exception
        String f3 = "{\"stats_response2\":[{\"status\":\"success\",\"msg\":\"good result\"}]}";
        String result;
        GameEngine ge = new GameEngine();

        result = ge.checkJSONResponse(s1);
        assertEquals(ConstsSFIB.RESP_ADD_SENT, result);

        result = ge.checkJSONResponse(s2);
        assertEquals(ConstsSFIB.RESP_FLAG_SENT, result);

        result = ge.checkJSONResponse(s3);
        assertEquals(ConstsSFIB.RESP_POST_STATS, result);

        result = ge.checkJSONResponse(s4);
        assertEquals(ConstsSFIB.RESP_GET_SENT, result);

        result = ge.checkJSONResponse(s5);
        assertEquals(ConstsSFIB.RESP_GET_STATS, result);

        result = ge.checkJSONResponse(f1);
        assertEquals(ConstsSFIB.RESP_ERROR, result);

        result = ge.checkJSONResponse(f2);
        assertEquals(ConstsSFIB.RESP_ERROR, result);

        result = ge.checkJSONResponse(f3);
        assertEquals(ConstsSFIB.RESP_ERROR, result);
    }

    @Test
    public void testProcessJSONSent() throws Exception {
        String s1 = "{\"sentence_response\":[{\"status\":\"success\",\"msg\":\"good result\"}," +
                "{\"eng_sent\":\"Technical and professional education shall be made generally " +
                "available and higher education shall be equally accessible to all on the basis" +
                " of merit.\",\"for_sent\":\"L'enseignement technique et professionnel doit" +
                " \\u00eatre g\\u00e9n\\u00e9ralis\\u00e9; l'acc\\u00e8s aux \\u00e9tudes" +
                " sup\\u00e9rieures doit \\u00eatre ouvert en pleine \\u00e9galit\\u00e9" +
                " \\u00e0 tous en fonction de leur m\\u00e9rite.\",\"eng_id\":\"5\"}]}";
        String f2 = "13898238"; // gibberish for JSON exception

        GameEngine ge = new GameEngine();
        List<Sent> sentList = new ArrayList<>();
        boolean error = ge.processJSONSent(s1, sentList);
        assertFalse(error);
        String eng4 = "Technical and professional education shall be made generally available" +
                " and higher education shall be equally accessible to all on the basis of merit.";
        String fre4 = "L'enseignement technique et professionnel doit être généralisé; l'accès" +
                " aux études supérieures doit être ouvert en pleine égalité à tous en fonction" +
                " de leur mérite.";
        assertEquals(eng4, sentList.get(0).getEnglish());
        assertEquals(fre4, sentList.get(0).getForeign());
        assertEquals(5, sentList.get(0).getKey());

        sentList = new ArrayList<>();
        error = ge.processJSONSent(f2, sentList);
        assertTrue(error);
    }

    @Test
    public void testProcessJSONStats() throws Exception {
        GameEngine ge = new GameEngine();
        GameState.SFIBStats stats = ge.getSFIBStats();

        String s1 = "{\"stats_response\":[{\"status\":\"success\",\"msg\":\"good result\"}" +
                ",{\"regno\":\"1\"" +
                ",\"game_id\":\"FRE\"" +
                ",\"sig_diff\":\"45\"" +
                ",\"sig_guess\":\"11\"" +
                ",\"sig_hint\":\"37\"" +
                ",\"sig_skip\":\"0\"" +
                ",\"num_submit\":\"9\"" +
                ",\"cum_score\":\"26\"" +
                ",\"avg_diff\":\"5.0\"" +
                ",\"avg_guess\":\"1.2\"" +
                ",\"avg_skip\":\"0.0\"" +
                ",\"avg_hint\":\"4.1\"" +
                ",\"add_cnt\":\"6\"}]}";

        String s2 = "{\"stats_response\":[{\"status\":\"success\",\"msg\":\"good result\"},{\"regno\":\"2\",\"game_id\":\"FRE\",\"sig_diff\":\"0\",\"sig_guess\":\"0\",\"sig_hint\":\"0\",\"sig_skip\":\"0\",\"num_submit\":\"0\",\"cum_score\":\"0\",\"avg_diff\":\"0.0\",\"avg_guess\":\"0.0\",\"avg_skip\":\"0.0\",\"avg_hint\":\"0.0\",\"add_cnt\":\"0\"}]}";

/*
        String s2 = "{\"stats_response\":[{\"status\":\"success\",\"msg\":\"good result\"}" +
                ",{\"regno\":\"2\"" +
                ",\"game_id\":\"FRE\"" +
                ",\"sig_diff\":\"45\"" +
                ",\"sig_guess\":\"11\"" +
                ",\"sig_hint\":\"37\"" +
                ",\"sig_skip\":\"0\"" +
                ",\"num_submit\":\"9\"" +
                ",\"cum_score\":\"26\"" +
                ",\"avg_diff\":\"5.0\"" +
                ",\"avg_guess\":\"1.2\"" +
                ",\"avg_skip\":\"0.0\"" +
                ",\"avg_hint\":\"4.1\"" +
                ",\"add_cnt\":\"6\"}]}";
*/
        String f2 = "13898238"; // gibberish for JSON exception

        boolean error = ge.processJSONStats(s1, stats);
        double delta = 0.0002;
        assertFalse(error);
        assertEquals(45, stats.mSigmaDiff);
        assertEquals(11, stats.mSigmaGuess);
        assertEquals(37, stats.mSigmaHint);
        assertEquals(0, stats.mSigmaSkip);
        assertEquals(9, stats.mSubmitCnt);
        assertEquals(26, stats.mCumScore);
        assertEquals(5.0, stats.mAvgDiff, delta);
        assertEquals(1.2, stats.mAvgGuess, delta);
        assertEquals(0.0, stats.mAvgSkip, delta);
        assertEquals(4.1, stats.mAvgHint, delta);
        assertEquals(6, stats.mAddCnt);

        error = ge.processJSONStats(f2, stats);
        assertTrue(error);

        error = ge.processJSONStats(s2, stats);
        assertFalse(error);
    }

    @Test
    public void testProcessSubmit() throws Exception {
        GameEngine ge = new GameEngine();
        GameState.SFIBStats stats = ge.getSFIBStats();

        String[] tmpStr = {"a", "à", "scientifique,", "d'éducation", "Tête-à-tête"};
        int[] ans = {1, 2, 13, 12, 14};

        // stats before
        stats.mSigmaDiff = 10;
        stats.mSigmaGuess = 10;
        stats.mSigmaHint = 10;
        stats.mSigmaSkip = 10;
        stats.mSubmitCnt = 10;
        stats.mCumScore = 10;
        stats.mAddCnt = 10;

        int addCnt = Integer.parseInt(ge.getAddCnt());
        int hintIdx = 1;
        ge.setGuessCnt(2);
        int result = ge.processSubmit(tmpStr[3], tmpStr[3], hintIdx, true);
        assertEquals(ans[3], result);

        assertEquals(11, stats.mAddCnt);
        assertEquals(addCnt+1, Integer.parseInt(ge.getAddCnt()));
        assertEquals(0, ge.getGuessCnt());

        assertEquals(10+1, stats.mSubmitCnt);
        assertEquals(ans[3]+10, stats.mCumScore);
        assertEquals(10+1, stats.mSigmaHint);
        assertEquals(10+5, stats.mSigmaDiff);
        assertEquals(10+2, stats.mSigmaGuess);

        double delta = 0.0002;
        assertEquals((float) 15 / (float) 11, stats.mAvgDiff, delta);
        assertEquals((float) 12 / (float) 11, stats.mAvgGuess, delta);
        assertEquals((float) 10 / (float) 11, stats.mAvgSkip, delta);
        assertEquals((float) 11 / (float) 11, stats.mAvgHint, delta);

        int gCnt = ge.getGuessCnt();
        result = ge.processSubmit(tmpStr[3], tmpStr[2], hintIdx, false);
        assertEquals(-1, result);
        assertEquals(gCnt+1, ge.getGuessCnt());
    }

    @Test
    public void testProcessSkip() throws Exception {
        GameEngine ge = new GameEngine();

        int skipCnt = Integer.parseInt(ge.getSigmaSkip());
        ge.processSkip(true);
        assertEquals(skipCnt + 1, Integer.parseInt(ge.getSigmaSkip()));

        skipCnt = Integer.parseInt(ge.getSigmaSkip());
        ge.processSkip(false);
        assertEquals(skipCnt, Integer.parseInt(ge.getSigmaSkip()));
    }

    @Test
    public void testPutGameOptExtras() throws Exception {

    }

}