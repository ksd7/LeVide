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

import android.test.ActivityInstrumentationTestCase2;
/**
 * Instrumented testing for ActivityLogin.
 */
public class ActivityLoginTests extends ActivityInstrumentationTestCase2<ActivityLogin> {

    public ActivityLoginTests() {
        super(ActivityLogin.class);
    }

    public void testActivityExists() {
        ActivityLogin activity = getActivity();
        assertNotNull(activity);
    }

    // Because Google Account picker is an external activity from the package
    // com.google.android.gms, there isn't much instrumented testing that can be done
    // via espresso testing which will just end the test when an external app call is made.
    // Since it is mostly GUI in nature, it must be manually user-tested.

}
