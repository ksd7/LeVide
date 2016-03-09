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
 * Created by LeVide on 2/17/2016.
 */
public class GameStateTest {
    private GameState gs;

    final String eng4 = "Technical and professional education shall be made generally available" +
            " and higher education shall be equally accessible to all on the basis of merit.";
    final String fre4 = "L'enseignement technique et professionnel doit être généralisé; l'accès" +
            " aux études supérieures doit être ouvert en pleine égalité à tous en fonction" +
            " de leur mérite.";
    final int key = 5;

    @Before
    public void setUp() throws Exception {
        gs = new GameState();
    }

    @Test
    public void testGetEngSent() throws Exception {
        String eng;
        eng = gs.getEngSent();
        assertEquals(null, eng);

        gs.getSentList().add(new Sent(eng4, fre4, key));
        assertEquals(eng4, gs.getEngSent());
    }

    @Test
    public void testGetForSent() throws Exception {
        String f;
        f = gs.getForSent();
        assertEquals(null, f);

        gs.getSentList().add(new Sent(eng4, fre4, key));
        assertEquals(fre4, gs.getForSent());
    }

    @Test
    public void testGetKey() throws Exception {
        int k;
        k = gs.getKey();
        assertEquals(-1, k);

        gs.getSentList().add(new Sent(eng4, fre4, key));
        assertEquals(key, gs.getKey());
    }

    @Test
    public void testUpdateStats() throws Exception {

    }

    @Test
    public void testComputeStats() throws Exception {

    }
}