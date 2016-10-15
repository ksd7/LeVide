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
 * Encapsulates SFIB global constants used for Intent message passing
 * and message requests/responses to/from the remote DB -- anywhere
 * that constants need to be shared among Activities or where it
 * makes sense to place them in this central location for easy access.
 */
public final class ConstsSFIB {

    // The PHP URLs to accomplish interaction with the game DB


    public static final String FETCH_SENT_URL      =
        " http://levidegame.com/sfib/get_json_sent.php";
    public static final String FETCH_STATS_URL     =
        " http://levidegame.com/sfib/get_json_stats.php";
    public static final String POST_NEW_SENT_URL   =
        " http://levidegame.com/sfib/add_sent.php";
    public static final String POST_SENT_FLAG_URL  =
        " http://levidegame.com/sfib/flag_sent.php";
    public static final String POST_STATS_URL      =
        " http://levidegame.com/sfib/post_json_stats.php";

/*
    public static final String FETCH_SENT_URL      =
            " http://192.168.1.119/sfib/get_json_sent.php";
    public static final String FETCH_STATS_URL     =
            " http://192.168.1.119/sfib/get_json_stats.php";
    public static final String POST_NEW_SENT_URL   =
            " http://192.168.1.119/sfib/add_sent.php";
    public static final String POST_SENT_FLAG_URL  =
            " http://192.168.1.119/sfib/flag_sent.php";
    public static final String POST_STATS_URL      =
            " http://192.168.1.119/sfib/post_json_stats.php";
*/
    /**
     * Specifies the maximum string length for sentence strings that
     * will be stored in the game DB.
     */
    public static final int MAX_SENT_LEN = 1024;

    /**
     * Specifies the number of sentences to extract from the DB in one shot
     */
    public static final int SENT_BLOCK_CNT = 5; //<todo> finalize this number

    /**
     * Specifies the number that is left in the data store at which more
     * sentences should be requested from the DB
     */
    public static final int SENT_BLOCK_THRESH = 1; //<todo> finalize this number

    /**
     * Specifies the number of sentences submitted with at least 1 point awarded
     * before the player is allowed to add a sentence to the database
     */
    public static final int ADD_SENT_THRESH = 1; //<todo> finalize this number

    /**
     * String constants for responses from remote database access
	 * Note:  These correspond directly to strings in web server PHP files,
	 * so they would need to be changed in both locations.
     */
    public static final String RESP_ADD_SENT    = "add_sent_response";
    public static final String RESP_FLAG_SENT   = "flag_sent_response";
    public static final String RESP_POST_STATS  = "post_stats_response";
    public static final String RESP_GET_SENT    = "sentence_response";
    public static final String RESP_GET_STATS   = "stats_response";
    
    /**
     * String used to indicate problem with JSON parsing of reply
     */
    public static final String RESP_ERROR       = "error_response";

    /**
     * Intent message name fields for name/value pairs used
     * with parent activity ActivityGame
     */
    public static final String NET_DOWN_BOOL        = "net_down";
    public static final String ADD_SENT_ENB_BOOL    = "add_sent_enabled";
    public static final String STATS_ENB_BOOL       = "stats_enabled";

    public static final String DIFFICULTY       = "difficulty";

    public static final String CUMULATIVE_SCORE = "cum_score";
    public static final String AVG_DIFFICULTY   = "avg_diff";
    public static final String AVG_GUESS_CNT    = "avg_guess";
    public static final String AVG_NUM_SKIPS    = "avg_skip";
    public static final String AVG_NUM_HINTS    = "avg_hint";

    /**
     * GAME_OPTION string is used as the name in the first name/value pair
     * for results that are returned via Intent extras from the Game Options activity
     * (and children).  Getting the associated value indicates what result is
     * being returned so that specific processing can occur.
     */
    public static final String GAME_OPTION  = "GAME_OPTION";
    public static final String FLAG_SENT    = "flag_sent";
    public static final String FLAG_TRUE    = "flag_true";
    public static final String ADD_SENT     = "add_sent";
    public static final String ADD_ENGLISH  = "add_english";
    public static final String ADD_FOREIGN  = "add_foreign";

    /**
     * Strings that identify the tasks that utilize either
     * FetchFromDBTask or PostToDBTask
     */
    public static final String REQ_SENT_TASK        = "req_sent_task";
    public static final String REQ_STATS_TASK       = "req_stats_task";
    public static final String POST_FLAG_SENT_TASK  = "post_flag_sent_task";
    public static final String POST_ADD_SENT_TASK   = "post_add_sent_task";
    public static final String POST_STATS_TASK      = "post_stats_task";
    
    /**
	 * Parameters needed to post for requesting sentence data from DB
	 * Note:  These correspond directly to strings in web server PHP files,
	 * so they would need to be changed in both locations.
     */
     public static final String REQ_SENT_LANG = "lang";
     public static final String REQ_SENT_LIMIT = "limit";
     public static final String REQ_SENT_ENG = "eng_sent";
     public static final String REQ_SENT_FOR = "for_sent";
     public static final String REQ_SENT_ID  = "eng_id";
     
    /**
	 * Parameters needed to post for requesting stats from DB
	 * Note:  These correspond directly to strings in web server PHP files,
	 * so they would need to be changed in both locations.
     */
     public static final String REQ_STATS_EMAIL = "email";
     public static final String REQ_STATS_GAME = "game";
     public static final String REQ_STATS_PNAME = "pname";
     public static final String REQ_STATS_SIG_DIFF = "sig_diff";
     public static final String REQ_STATS_SIG_GUESS = "sig_guess";
     public static final String REQ_STATS_SIG_HINT = "sig_hint";
     public static final String REQ_STATS_SIG_SKIP = "sig_skip";
     public static final String REQ_STATS_NUM_SUBMIT = "num_submit";
     public static final String REQ_STATS_CUM_SCORE = "cum_score";
     public static final String REQ_STATS_ADD_CNT = "add_cnt";
     public static final String REQ_STATS_AVG_DIFF = "avg_diff";
     public static final String REQ_STATS_AVG_GUESS = "avg_guess";
     public static final String REQ_STATS_AVG_SKIP = "avg_skip";
     public static final String REQ_STATS_AVG_HINT = "avg_hint";

    /**
     * Parameters needed to post for flagging a sentence in DB
     * Note:  These correspond directly to strings in web server PHP files,
     * so they would need to be changed in both locations.
     */
    public static final String POST_FLAG_ENG_ID   = "eng_id";
    public static final String POST_FLAG_LANG     = "lang";

    /**
     * Parameters needed to post for adding sentence to DB
     * Note:  These correspond directly to strings in web server PHP files,
     * so they would need to be changed in both locations.
     */
    public static final String POST_ADD_ENG_SENT = "eng_sent";
    public static final String POST_ADD_FOR_SENT = "for_sent";
    public static final String POST_ADD_LANG     = "lang";

    /**
     * Parameters needed to post for updating stats to DB
     * Note:  These correspond directly to strings in web server PHP files,
     * so they would need to be changed in both locations.
     */
    public static final String POST_STATS_EMAIL 	 = "email";
    public static final String POST_STATS_GAME_ID 	 = "game_id";
    public static final String POST_STATS_SIG_DIFF  = "sig_diff";
    public static final String POST_STATS_SIG_GUESS  = "sig_guess";
    public static final String POST_STATS_SIG_HINT   = "sig_hint";
    public static final String POST_STATS_SIG_SKIP   = "sig_skip";
    public static final String POST_STATS_NUM_SUBMIT = "num_submit";
    public static final String POST_STATS_CUM_SCORE  = "cum_score";
    public static final String POST_STATS_AVG_DIFF   = "avg_diff";
    public static final String POST_STATS_AVG_GUESS  = "avg_guess";
    public static final String POST_STATS_AVG_SKIP   = "avg_skip";
    public static final String POST_STATS_AVG_HINT   = "avg_hint";
    public static final String POST_STATS_ADD_CNT    = "add_cnt";

    /**
     * Parameters involving loading of default game content when network is down.
     */
    public final static int DEFAULT_SIZE = 10; // <TODO> beef this up, like 50ish

    public static final String[] defaultEng = {
        "Now is the time for all good men to come to the aid of their country.",            // 1
        "Everyone has the right to education.",                                             // 2
        "Education shall be free, at least in the elementary and fundamental stages.",      // 3
        "Elementary education shall be compulsory.",                                        // 4
        "Technical and professional education shall be made generally available" +          // 5
            " and higher education shall be equally accessible to all on the basis of merit.",
        "Education shall be directed to the full development of the human" +                // 6
            " personality and to the strengthening of respect for human rights and" +
            " fundamental freedoms.",
        "It shall promote understanding, tolerance and friendship among all " +             // 7
            "nations, racial or religious groups, and shall further the activities of " +
            "the United Nations for the maintenance of peace.",
        "Parents have a prior right to choose the kind of education that shall be " +       // 8
            "given to their children.",
        "Everyone has the right freely to participate in the cultural life of the" +        // 9
            " community, to enjoy the arts and to share in scientific advancement and its" +
            " benefits.",
        "Everyone has the right to the protection of the moral and material" +              // 10
            " interests resulting from any scientific, literary or artistic production" +
            " of which he is the author."
    };

    public static final String[] defaultFre = {
        "Il est maintenant temps pour tous les bons hommes à venir à l'aide de " +          // 1
            "leur pays.",
        "Toute personne a droit à l'éducation.",                                            // 2
        "L'éducation doit être gratuite, au moins en ce qui concerne " +                    // 3
            "l'enseignement élémentaire et fondamental.",
        "L'enseignement élémentaire est obligatoire.",                                      // 4
        "L'enseignement technique et professionnel doit être généralisé; l'accès" +         // 5
            " aux études supérieures doit être ouvert en pleine égalité à tous en fonction" +
            " de leur mérite.",
        "L'éducation doit viser au plein épanouissement de la personnalité humaine" +       // 5
            " et au renforcement du respect des droits de l'homme et des libertés" +
            " fondamentales.",
        "Elle doit favoriser la compréhension, la tolérance et l'amitié entre " +           // 7
            "toutes les nations et tous les groupes raciaux ou religieux, ainsi que le " +
            "développement des activités des Nations Unies pour le maintien de la paix.",
        "Les parents ont, par priorité, le droit de choisir le genre d'éducation" +         // 8
            " à donner à leurs enfants.",
        "Toute personne a le droit de prendre part librement à la vie culturelle" +         // 9
            " de la communauté, de jouir des arts et de participer au progrès scientifique" +
            " et aux bienfaits qui en résultent.",
        "Chacun a droit à la protection des intérêts moraux et matériels découlant" +       // 10
            " de toute production scientifique, littéraire ou artistique dont il est l'auteur."
    };

    public static final String[] defaultSpa = {
        "Ahora es el momento para todos los hombres de bien a acudir en ayuda de " +        // 1
        "su país.",
        "Toda persona tiene derecho a la educación.",                                       // 2
        "La educación debe ser gratuita, al menos en lo concerniente a la " +               // 3
            "instrucción elemental y fundamental.",
        "La instrucción elemental será obligatoria.",                                       // 4
        "La instrucción técnica y profesional habrá de ser generalizada; el acceso" +       // 5
            " a los estudios superiores será igual para todos, en función de los méritos" +
            " respectivos.",
        "La educación tendrá por objeto el pleno desarrollo de la personalidad" +           // 6
            " humana y el fortalecimiento del respeto a los derechos humanos y a las" +
            " libertades fundamentales.",
        "Favorecerá la comprensión, la tolerancia y la amistad entre todas las " +          // 7
            "naciones y todos los grupos étnicos o religiosos, y promoverá el desarrollo " +
            "de las actividades de las Naciones Unidas para el mantenimiento de la paz.",
        "Los padres tendrán derecho preferente a escoger el tipo de educación que" +        // 8
            " habrá de darse a sus hijos.",
        "Toda persona tiene derecho a tomar parte libremente en la vida cultural" +         // 9
            " de la comunidad, a gozar de las artes y a participar en el progreso científico" +
            " y en los beneficios que de él resulten.",
        "Toda persona tiene derecho a la protección de los intereses morales y" +           // 10
            " materiales que le correspondan por razón de las producciones científicas," +
            " literarias o artísticas de que sea autora."
    };

    /**
     * A caller references the constants using ConstsSFIB.NAME without
     * ever creating an instance of this class.
     */
    private ConstsSFIB() {
        // prevents even the native class from calling the constructor
        throw new AssertionError();
    }
}
