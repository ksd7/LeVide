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

import java.util.List;

/**
 * DefaultGameContent is responsible for keeping a default set of sentences for game content
 * to be used when the initial loading from the game database results in connectivity failure.
 */
public class DefaultGameContent {
    /**
     * Default Constructor
     */
    public DefaultGameContent() {
    }

    /**
     * Populates the sentence list passed based on the language passed from default game
     * content.
     * @param lang The SFIB game language.
     * @param sList A List of Sentences that needs to be populated with SFIB game content
     */
    public void setContent(String lang, List<Sent> sList) {
        for (int i = 0; i < ConstsSFIB.DEFAULT_SIZE; i++) {
            if (lang.equals(UtilsSFIB.SFIBGameLang.FRE.toString())) {
                sList.add(new Sent(ConstsSFIB.defaultEng[i], ConstsSFIB.defaultFre[i], 0));
            } else if (lang.equals(UtilsSFIB.SFIBGameLang.SPA.toString())) {
                sList.add(new Sent(ConstsSFIB.defaultEng[i], ConstsSFIB.defaultSpa[i], 0));
            } else throw new IllegalArgumentException();
        }
    }
}
