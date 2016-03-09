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
 * The following interface is used in a class that instantiates an AsyncTask
 * (or a class that extends an AsyncTask).  The instantiator class will specify that
 * it implements this particular interface.  This requires that it create an
 * processFinish() method.  When the AsyncTask has completed and calls onPostExecute(),
 * processing results can be passed backed to the class that instantiated the AsyncTask.
 * Setting of a "delegate" variable is necessary so that the AsyncTask knows how to
 * get the results back to the instantiator class.
 */
public interface AsyncResponse {
    void processFinish(String output);
}
