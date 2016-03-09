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

package edu.uwf.ksd7.levide_1;

/**
 * Encapsulates global constants used for Intent message passing and anywhere
 * that constants need to be shared among Activities or where it
 * makes sense to place them in this central location for easy access.
 */
public final class Consts {
    /**
     * Tech support email
     */
    public static final String TECH_SUPPORT_EMAIL = "mailto:ksd7@students.uwf.edu";
    /**
     * Define Intent message name fields for name/value pairs within
     * the parent activity.
     */
    public static final String EMAIL = "email";
    public static final String NAME  = "name";
    public static final String GAME_LANG = "lang";

    /**
     * Indicates login as guest
     */
    public static final String GUEST_EMAIL = "guest@fake.com";

    /**
     * Initialization parameters to facilitate fake login testing
     */
    public static final String TEST_EMAIL = "ksd7@students.uwf.edu";
    public static final String TEST_NAME  = "Ken Dieudonné";

}
