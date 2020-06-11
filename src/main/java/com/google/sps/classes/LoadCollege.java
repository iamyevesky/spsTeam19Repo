package com.google.sps.classes;

import java.io.*;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.*;

public class LoadCollege{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private LoadCollege(){}

    public static void loadColleges(){
        if (exists()) return;
        System.out.println(exists());
        String path =
        "Abilene Christian University\n"+
        "Adelphi University\n"+
        "Agnes Scott College\n"+
        "Air Force Institute of Technology\n"+
        "Alabama A&M University\n"+
        "Alabama State University\n"+
        "Alaska Pacific University\n"+
        "Albertson College of Idaho\n"+
        "Albion College\n"+
        "Alderson-Broaddus\n"+
        "Alfred University\n"+
        "Allegheny College\n"+
        "Allentown College of Saint Francis de Sales\n"+
        "Alma College\n"+
        "Alverno College\n"+
        "Ambassador University\n"+
        "American Coastline University\n"+
        "American Graduate School of International Management\n"+
        "American International College\n"+
        "American University\n"+
        "Amherst College\n"+
        "Andrews University\n"+
        "Angelo State University\n"+
        "Antioch College\n"+
        "Antioch New England\n"+
        "Antioch University-Los Angeles\n"+
        "Antioch University-Seattle\n"+
        "Appalachian State University\n"+
        "Aquinas College\n"+
        "Arizona State University\n"+
        "Arizona State University\n"+
        "Arizona State University West\n"+
        "Arizona Western College\n"+
        "Arkansas State University, Jonesboro\n"+
        "Arkansas Tech University\n"+
        "Armstrong State College\n"+
        "Ashland University\n"+
        "Assumption College\n"+
        "Athens State University\n"+
        "Auburn University\n"+
        "Auburn University - Montgomery\n"+
        "Augsburg College\n"+
        "Augustana College (IL)\n"+
        "Augustana College (SD)\n"+
        "Aurora University\n"+
        "Austin College\n"+
        "Austin Peay State University\n"+
        "Averett College\n"+
        "Avila College\n"+
        "Azusa Pacific University\n"+
        "Babson College\n"+
        "Baldwin-Wallace College\n"+
        "Ball State University\n"+
        "Baker University\n"+
        "Baptist Bible College\n"+
        "Bard College\n"+
        "Barry University\n"+
        "Bastyr University\n"+
        "Bates College\n"+
        "Baylor College of Medicine\n"+
        "Baylor University\n"+
        "Beaver College\n"+
        "Belmont University\n"+
        "Beloit College\n"+
        "Bemidji State University\n"+
        "Benedictine College\n"+
        "Bennington College\n"+
        "Bentley College\n"+
        "Berea College\n"+
        "Berklee College of Music\n"+
        "Bethany College (CA)\n"+
        "Bethany College (WV)\n"+
        "Bethel College (KS)\n"+
        "Bethel College and Seminary (MN)\n"+
        "Biola University\n"+
        "Birmingham-Southern College\n"+
        "Black Hills State University\n"+
        "Bloomsburg University of Pennsylvania\n"+
        "Bluffton College\n"+
        "Bob Jones University\n"+
        "Boise State University\n"+
        "Boston College\n"+
        "Boston Graduate School of Psychoanalysis\n"+
        "Boston University\n"+
        "Bowdoin College\n"+
        "Bowie State University\n"+
        "Bowling Green State University\n"+
        "Bradley University\n"+
        "Brandeis University\n"+
        "Brenau University\n"+
        "Briar Cliff College\n"+
        "Bridgewater College\n"+
        "Brigham Young University\n"+
        "Brigham Young University Hawaii\n"+
        "Brown University\n"+
        "Bryant College\n"+
        "Bryn Mawr College\n"+
        "Bucknell University\n"+
        "Buena Vista University\n"+
        "Butler University\n"+
        "California Coast University\n"+
        "California Institute of Technology\n"+
        "California Lutheran University\n"+
        "California Maritime Academy\n"+
        "California National University\n"+
        "California Pacific University\n"+
        "California Polytechnic State University, San Luis Obispo\n"+
        "California School of Professional\n"+
        "California State Polytechnic University, Pomona\n"+
        "California State University System\n"+
        "California State University, Bakersfield\n"+
        "California State University, Chico\n"+
        "California State University, Dominguez Hills\n"+
        "California State University, Fresno\n"+
        "California State University, Fullerton\n"+
        "California State University, Hayward\n"+
        "California State University, Long Beach\n"+
        "California State University, Los Angeles\n"+
        "California State University, Monterey Bay\n"+
        "California State University, Northridge\n"+
        "California State University, Sacramento\n"+
        "California State University, San Bernardino\n"+
        "California State University, San Jose\n"+
        "California State University, San Marcos\n"+
        "California State University, Sacramento\n"+
        "California State University, Stanislaus\n"+
        "California University of Pennsylvania\n"+
        "Calvin College\n"+
        "Campbell University\n"+
        "Campbellsville College\n"+
        "Cameron University\n"+
        "Canisius College\n"+
        "Carleton College\n"+
        "Carlow College\n"+
        "Carnegie Mellon University\n"+
        "Carroll College (MT)\n"+
        "Carroll College (WI)\n"+
        "Carson-Newman College\n"+
        "Carthage College\n"+
        "Case Western Reserve University\n"+
        "Castleton State University\n"+
        "The Catholic University of America\n"+
        "Cedarville College\n"+
        "Centenary College of Louisiana\n"+
        "Central College\n"+
        "Central Connecticut State University\n"+
        "Central Methodist College\n"+
        "Central Michigan University\n"+
        "Central Missouri State University\n"+
        "Central Washington University\n"+
        "Centre College\n"+
        "Chadron State College\n"+
        "Champlain College\n"+
        "Chapman University\n"+
        "Chatham College\n"+
        "Chesapeake College\n"+
        "Cheyney University\n"+
        "The Chicago School of Professional Psychology\n"+
        "Christian Brothers University\n"+
        "Christian Theological Seminary\n"+
        "Christopher Newport University\n"+
        "The Citadel\n"+
        "City University\n"+
        "City University of New York\n"+
        "Claremont Graduate School\n"+
        "Claremont McKenna College\n"+
        "Clarion University of Pennsylvania\n"+
        "Clark University\n"+
        "Clarke College\n"+
        "Clarkson University\n"+
        "Clayton State College\n"+
        "Clemson University\n"+
        "Cleveland State University\n"+
        "Clinch Valley College\n"+
        "Coastal Carolina University\n"+
        "Coe College\n"+
        "Coker College\n"+
        "Colby College\n"+
        "Colgate University\n"+
        "College of the Atlantic\n"+
        "College of Charleston\n"+
        "College of Eastern Utah\n"+
        "College of the Holy Cross\n"+
        "College of Saint Benedict\n"+
        "College of Saint Catherine\n"+
        "College of St. Francis\n"+
        "College of Saint Rose\n"+
        "College of St. Scholastica\n"+
        "College of William and Mary\n"+
        "The College of Wooster\n"+
        "Colorado Christian University\n"+
        "Colorado College\n"+
        "Colorado School of Mines\n"+
        "Colorado State University\n"+
        "Columbia College Chicago\n"+
        "Columbia Southern University\n"+
        "Columbia Union College\n"+
        "Columbia University\n"+
        "Concordia College-Ann Arbor\n"+
        "Concordia College-Moorhead\n"+
        "Concordia College-St. Paul\n"+
        "Concordia College-Seward\n"+
        "Concordia University River Forest, Illinois\n"+
        "Connecticut College\n"+
        "The Cooper Union for the Advancement of Science and Art\n"+
        "Coppin State College\n"+
        "Cornell College\n"+
        "Cornell University\n"+
        "Cornerstone\n"+
        "Creighton University\n"+
        "Curry College\n"+
        "Daemen College\n"+
        "Dakota State University\n"+
        "Dakota Wesleyan University\n"+
        "Dallas Baptist University\n"+
        "Dana College\n"+
        "Daniel Webster College\n"+
        "Dartmouth College\n"+
        "Davenport College Detroit College of Business\n"+
        "Davidson College\n"+
        "Davis & Elkins College\n"+
        "Delaware State University\n"+
        "Delta State University\n"+
        "Denison University\n"+
        "DePaul University\n"+
        "DePauw University\n"+
        "DeVry Institute of Technology\n"+
        "DeVry Institute of Technology-Dallas\n"+
        "DeVry Institute of Technology-Phoenix\n"+
        "Dickinson College\n"+
        "Dickinson State University\n"+
        "Dillard University\n"+
        "Dominican College\n"+
        "Dordt College\n"+
        "Dowling College\n"+
        "Drake University\n"+
        "Drew University\n"+
        "Drexel University\n"+
        "Drury College\n"+
        "Duke University\n"+
        "Duquesne University\n"+
        "Earlham College\n"+
        "East Carolina University\n"+
        "East Central University\n"+
        "East Stroudsburg State University of Pennsylvania\n"+
        "East Tennessee State University\n"+
        "East Texas State University\n"+
        "Eastern Connecticut State University\n"+
        "Eastern Illinois University\n"+
        "Eastern Kentucky University\n"+
        "Eastern Mennonite University\n"+
        "Eastern Michigan University\n"+
        "Eastern Nazarene\n"+
        "Eastern New Mexico University\n"+
        "Eastern Oregon State College\n"+
        "Eastern Washington University\n"+
        "Edgewood College\n"+
        "Edinboro University of Pennsylvania\n"+
        "Elizabeth City State University\n"+
        "Elizabethtown College\n"+
        "Elmhurst College\n"+
        "Elon College\n"+
        "Embry-Riddle Aeronautical University, Arizona\n"+
        "Embry-Riddle Aeronautical University, Florida\n"+
        "Emerson College\n"+
        "Emmanuel College\n"+
        "Emmaus Bible College\n"+
        "Emporia State University\n"+
        "Emory & Henry\n"+
        "Emory University\n"+
        "Evergreen State College\n"+
        "Fairfield University\n"+
        "Fairleigh Dickinson University\n"+
        "Fairmont State College\n"+
        "Fayetteville State University\n"+
        "Ferris State University\n"+
        "Fielding Institute\n"+
        "Fisk University\n"+
        "Fitchburg State College\n"+
        "Florida Agricultural and Mechanical University\n"+
        "Florida Atlantic University\n"+
        "Florida Gulf Coast University\n"+
        "Florida Institute of Technology\n"+
        "Florida International University\n"+
        "Florida State University\n"+
        "Fontbonne College\n"+
        "Fordham University\n"+
        "Fort Hays State University\n"+
        "Fort Lewis College\n"+
        "Franciscan College\n"+
        "Franklin and Marshall College\n"+
        "Franklin Pierce Law Center\n"+
        "Franklin University\n"+
        "Fresno Pacific University\n"+
        "Friends University\n"+
        "Frostburg State University\n"+
        "Fuller Theological Seminary\n"+
        "Furman University\n"+
        "Gallaudet University\n"+
        "Gannon University\n"+
        "Geneva College\n"+
        "George Fox College\n"+
        "George Mason University\n"+
        "George Washington University\n"+
        "Georgetown University\n"+
        "Georgia College\n"+
        "Georgia Institute of Technology\n"+
        "Georgia Southern University\n"+
        "Georgia Southwestern College\n"+
        "Georgia State University\n"+
        "Georgian Court College\n"+
        "Gettysburg College\n"+
        "GMI Engineering and Management Institute\n"+
        "Golden Gate University\n"+
        "Goldey-Beacom College\n"+
        "Gonzaga University\n"+
        "Google SPS Program\n"+
        "Goshen College\n"+
        "Goucher College\n"+
        "Governors State University\n"+
        "Grace College\n"+
        "Graceland College\n"+
        "Grand Valley State University\n"+
        "Greenleaf University\n"+
        "Grinnell College\n"+
        "Guilford College\n"+
        "Gustavus Adolphus College\n"+
        "Gutenberg\n"+
        "Hamilton College\n"+
        "Hamline University\n"+
        "Hampden-Sydney College\n"+
        "Hampshire College\n"+
        "Hampton University\n"+
        "Hanover College\n"+
        "Harding University\n"+
        "Hartwick College\n"+
        "Harvard University\n"+
        "Harvey Mudd College\n"+
        "Haskell Indian Nations University\n"+
        "Hastings College\n"+
        "Haverford College in Pennsylvania\n"+
        "Hawaii Pacific University\n"+
        "Heidelberg College\n"+
        "Hendrix College\n"+
        "Hesston College\n"+
        "High Point University\n"+
        "Hillsdale College\n"+
        "Hiram College\n"+
        "Hobart and William Smith Colleges\n"+
        "Hofstra University\n"+
        "Hollins College\n"+
        "Holy Cross College\n"+
        "Hood College\n"+
        "Hope College\n"+
        "Howard University\n"+
        "Humboldt State University\n"+
        "Hunter College\n"+
        "Huntingdon College\n"+
        "Huntington College\n"+
        "ICI University\n"+
        "Idaho State University\n"+
        "Illinois Benedictine College\n"+
        "Illinois Institute of Technology\n"+
        "Illinois State University\n"+
        "Incarnate Word College\n"+
        "Indiana Institute of Technology\n"+
        "Indiana State University\n"+
        "Indiana University System\n"+
        "Indiana University/Purdue University at Columbus\n"+
        "Indiana University/Purdue University at Fort Wayne\n"+
        "Indiana University/Purdue University at Indianapolis\n"+
        "Indiana University at Bloomington\n"+
        "Indiana University at South Bend\n"+
        "Indiana University of Pennsylvania\n"+
        "Indiana University Southeast at New Albany\n"+
        "Indiana Wesleyan University, Marion\n"+
        "Inter American University of Puerto Rico Metropolitan Campus\n"+
        "Iona College\n"+
        "Iowa State University\n"+
        "Ithaca College\n"+
        "Jackson State University\n"+
        "Jacksonville University\n"+
        "Jacksonville State University\n"+
        "James Madison University\n"+
        "Jamestown College\n"+
        "The Jewish Theological Seminary\n"+
        "John Brown University\n"+
        "John F. Kennedy University\n"+
        "Johns Hopkins University\n"+
        "Johnson Bible College\n"+
        "Johnson C. Smith University\n"+
        "Johnson & Wales University\n"+
        "Johnson & Wales\n"+
        "Jones College\n"+
        "Judson College\n"+
        "Juniata College\n"+
        "Kalamazoo College\n"+
        "Kansas State University\n"+
        "Kansas Wesleyan University\n"+
        "Kean College\n"+
        "Keene State College\n"+
        "Kent State University\n"+
        "Kenyon College\n"+
        "King's College\n"+
        "Knox College\n"+
        "Kutztown University of Pennsylvania\n"+
        "La Sierra University\n"+
        "LaGrange College\n"+
        "Lafayette College\n"+
        "Lake Forest College\n"+
        "Lake Superior State University\n"+
        "Lamar University\n"+
        "Langston University\n"+
        "LaSalle University\n"+
        "Lawrence University\n"+
        "Lawrence Technological University\n"+
        "Lebanon Valley College\n"+
        "Lehigh Univervsity\n"+
        "Le Moyne College\n"+
        "Lenoir-Rhyne College\n"+
        "LeTourneau University\n"+
        "Lewis & Clark College\n"+
        "Lewis-Clark State College\n"+
        "Lewis University\n"+
        "Liberty University\n"+
        "Lincoln University\n"+
        "Linfield College\n"+
        "Lock Haven University of Pennsylvania\n"+
        "Loma Linda University\n"+
        "Long Island University\n"+
        "Longwood College\n"+
        "Loras College\n"+
        "Louisiana College\n"+
        "Louisiana State University\n"+
        "Louisiana State University at Alexandria\n"+
        "Louisiana State University at\n"+
        "Louisiana Tech University\n"+
        "Loyola College\n"+
        "Loyola Marymount University\n"+
        "Loyola University Chicago\n"+
        "Luther College\n"+
        "Luther Seminary\n"+
        "Lycoming College\n"+
        "Lynchburg College\n"+
        "Lyndon State College\n"+
        "Lyon College\n"+
        "Macalester College\n"+
        "Maharishi University of Management\n"+
        "Maine Maritime\n"+
        "Malone College\n"+
        "Manhattan College\n"+
        "Mankato State University\n"+
        "Mansfield University of Pennsylvania\n"+
        "Marietta College\n"+
        "Marist College\n"+
        "Marlboro College\n"+
        "Marquette University\n"+
        "Marshall University\n"+
        "Mary Baldwin College\n"+
        "Marymount College\n"+
        "Marymount University\n"+
        "Mary Washington College\n"+
        "Massachusetts Institute of Technology\n"+
        "McMurry University\n"+
        "McNeese State University\n"+
        "Medical College of Georgia\n"+
        "Medical College of Wisconsin\n"+
        "Mercer University\n"+
        "Mercyhurst College\n"+
        "Meredith College\n"+
        "Messiah College\n"+
        "Metropolitan State College of Denver\n"+
        "Metropolitan State University\n"+
        "Miami Christian University (mcu.edu) Miami University of Ohio\n"+
        "Michigan State University\n"+
        "Michigan Technological University\n"+
        "Mid-America Nazarene College\n"+
        "Middlebury College\n"+
        "Middle Georgia College\n"+
        "Middle Tennessee State University\n"+
        "Midwestern State University\n"+
        "Millersville University of Pennsylvania\n"+
        "Milligan College\n"+
        "Millikin University\n"+
        "Millsaps College\n"+
        "Milwaukee School of Engineering\n"+
        "Minot State University\n"+
        "Minneapolis College of Art and Design\n"+
        "Mississippi College\n"+
        "Mississippi State University\n"+
        "Mississippi University for Women\n"+
        "Missouri Southern State College\n"+
        "Missouri Western State College\n"+
        "Molloy College\n"+
        "Monmouth College\n"+
        "Monmouth University\n"+
        "Montana State University-Billings\n"+
        "Montana State University-Bozeman\n"+
        "Montana State University-Northern\n"+
        "Montana Tech\n"+
        "Montclair State University\n"+
        "Montreat College\n"+
        "Moravian College\n"+
        "Moorhead State University\n"+
        "Morehouse College\n"+
        "Morgan State University\n"+
        "Mount Senario College\n"+
        "Mount Holyoke College\n"+
        "Mount Saint Joseph College\n"+
        "Mount Saint Mary College\n"+
        "Mount Union College\n"+
        "Murray State University\n"+
        "Muskingum College\n"+
        "National Defense University\n"+
        "National-Louis University\n"+
        "National Technological University\n"+
        "National University\n"+
        "Naval Postgraduate School\n"+
        "Nazareth College\n"+
        "Newberry College\n"+
        "New England Institute of\n"+
        "New College of California\n"+
        "New Hampshire College\n"+
        "New Jersey Institute of Technology\n"+
        "New Mexico Highlands University\n"+
        "New Mexico Institute of Mining & Technology\n"+
        "New Mexico State University\n"+
        "New York Institute of Technology\n"+
        "New York University\n"+
        "Niagara University\n"+
        "Nicholls State University\n"+
        "Norfolk State University\n"+
        "North Adams State College\n"+
        "North Carolina Central University\n"+
        "North Carolina A&T State University\n"+
        "North Carolina State University\n"+
        "North Carolina Wesleyan College\n"+
        "North Central Bible College\n"+
        "North Dakota State University\n"+
        "Northland College\n"+
        "North Park College and Theological Seminary\n"+
        "Northeastern Illinois\n"+
        "Northeastern Louisiana University\n"+
        "Northeastern State University\n"+
        "Northeastern University\n"+
        "Northern Arizona University\n"+
        "Northern Illinois University\n"+
        "Northern Kentucky University\n"+
        "Northern Michigan University\n"+
        "Northern State University\n"+
        "Northwest Missouri State University\n"+
        "Northwest Nazarene College\n"+
        "Northwestern College of Iowa\n"+
        "Northwestern State University\n"+
        "Northwestern University\n"+
        "Norwich University\n"+
        "Nova Southeastern University\n"+
        "Oakland University\n"+
        "Oberlin College\n"+
        "Occidental College\n"+
        "Ohio Dominican College\n"+
        "Ohio Northern University\n"+
        "Ohio State University, Columbus\n"+
        "Ohio State University, Marion\n"+
        "Ohio Wesleyan University\n"+
        "Ohio University, Athens\n"+
        "Oklahoma Baptist University\n"+
        "Oklahoma City University\n"+
        "Oklahoma State University\n"+
        "Old Dominion University\n"+
        "Olivet Nazarene University\n"+
        "Oral Roberts University\n"+
        "Oregon Graduate Institute of Science and Technology\n"+
        "Oregon Health Sciences University\n"+
        "Oregon Institute of Technology\n"+
        "Oregon State University\n"+
        "Otterbein College\n"+
        "Our Lady of the Lake University\n"+
        "Pace University\n"+
        "Pacific Lutheran University\n"+
        "Pacific Union College\n"+
        "Pacific University\n"+
        "Pacific Western University\n"+
        "Palm Beach Atlantic College\n"+
        "Peace College\n"+
        "Pembroke State University\n"+
        "Pennsylvania State System of Higher Education\n"+
        "Pennsylvania State University\n"+
        "Pennsylvania State University-Schuylkill Campus\n"+
        "Pensacola Christian College\n"+
        "Pepperdine University\n"+
        "Peru State College\n"+
        "Philadelphia College of Textiles and Science\n"+
        "Phillips University\n"+
        "Pittsburg State University\n"+
        "Pitzer College\n"+
        "Platt College\n"+
        "Plymouth State College\n"+
        "Point Loma Nazarene\n"+
        "Polytechnic University of New York\n"+
        "Polytechnic University of Puerto Rico\n"+
        "Pomona College\n"+
        "Portland State University\n"+
        "Prairie View A&M University\n"+
        "Pratt Institute\n"+
        "Prescott College\n"+
        "Princeton University\n"+
        "Presbyterian College\n"+
        "Providence College\n"+
        "Purdue University\n"+
        "Purdue University Calumet\n"+
        "Purdue University North Central\n"+
        "Quincy University\n"+
        "Quinnipiac College\n"+
        "Radford University\n"+
        "Ramapo College\n"+
        "Randolph-Macon College\n"+
        "Randolph-Macon Woman's College\n"+
        "Reed College\n"+
        "Regent University\n"+
        "Regis University\n"+
        "Rensselaer Polytechnic Institute\n"+
        "Rhode Island College\n"+
        "Rhodes College\n"+
        "Rice University\n"+
        "Richard Stockton College of New Jersey\n"+
        "Rider University\n"+
        "Ripon College\n"+
        "Rivier College\n"+
        "Roanoke College\n"+
        "Rochester Institute of Technology\n"+
        "The Rockefeller University\n"+
        "Rockford College\n"+
        "Rockhurst College\n"+
        "Rocky Mountain College\n"+
        "Roger Williams University\n"+
        "Rollins College\n"+
        "Rosary College\n"+
        "Rose-Hulman Institute of Technology\n"+
        "Rowan College\n"+
        "Rutgers University\n"+
        "Rutgers University, Camden\n"+
        "Rutgers University, Newark\n"+
        "The Sage Colleges\n"+
        "Sacred Heart University (CT)\n"+
        "Sacred Heart University (PR)\n"+
        "Saginaw Valley State University\n"+
        "St. Ambrose University\n"+
        "St. Andrews Presbyterian College\n"+
        "Saint Anselm College\n"+
        "St. Bonaventure University\n"+
        "Saint Cloud State University\n"+
        "Saint Edward's University\n"+
        "Saint Francis College\n"+
        "St. John's College-Annapolis\n"+
        "St. John's College-Santa Fe\n"+
        "Saint John's University (MN)\n"+
        "Saint John's University (NY)\n"+
        "St. Joseph College (CT)\n"+
        "Saint Joseph's College (IN)\n"+
        "St. Joseph's College (ME)\n"+
        "Saint Joseph's University\n"+
        "St. Lawrence University\n"+
        "St. Louis College of Pharmacy\n"+
        "Saint Louis University\n"+
        "St. Martin's College\n"+
        "Saint Mary College\n"+
        "Saint Mary's College (IN)\n"+
        "Saint Mary's College of California\n"+
        "Saint Mary's University of Minnesota\n"+
        "Saint Michael's College\n"+
        "Saint Olaf College\n"+
        "St. Thomas University (FL)\n"+
        "Saint Vincent College\n"+
        "Saint Xavier University\n"+
        "Salisbury State University\n"+
        "Salish Kootenai College\n"+
        "Sam Houston State University\n"+
        "Samford University\n"+
        "San Diego State University\n"+
        "San Francisco State University\n"+
        "San Jose State University\n"+
        "Santa Clara University\n"+
        "Sarah Lawrence College\n"+
        "School of the Art Institute of Chicago\n"+
        "Seattle Pacific University\n"+
        "Seattle University\n"+
        "Seton Hall University\n"+
        "Sewanee, University of the South\n"+
        "Shawnee State University\n"+
        "Shenandoah University\n"+
        "Shippensburg University of Pennsylvania\n"+
        "Shorter College\n"+
        "Simmons College\n"+
        "Simon's Rock College\n"+
        "Simpson College\n"+
        "Skidmore College\n"+
        "Slippery Rock University of Pennsylvania\n"+
        "Smith College\n"+
        "Sonoma State University\n"+
        "South Dakota School of Mines and Technology\n"+
        "South Dakota State University\n"+
        "Southeast Missouri State University\n"+
        "Southeastern Louisiana University\n"+
        "Southern College\n"+
        "Southern College of Technology\n"+
        "Southern Connecticut State University\n"+
        "Southern Illinois University\n"+
        "Southern Illinois University-Carbondale\n"+
        "Southern Illinois University-Edwardsville\n"+
        "Southern Methodist University\n"+
        "Southern Nazarene University\n"+
        "Southern Oregon State College\n"+
        "Southern University\n"+
        "Southern Utah University\n"+
        "Southampton College\n"+
        "South Texas College of Law\n"+
        "Southwest Baptist University\n"+
        "Southwest Missouri State University\n"+
        "Southwest State University\n"+
        "Southwest Texas State University\n"+
        "Southwestern Adventist College\n"+
        "Southwestern University\n"+
        "Spelman College\n"+
        "Spring Arbor College\n"+
        "Spring Hill College\n"+
        "Stanford University\n"+
        "State University of New York System\n"+
        "State University of New York at Albany\n"+
        "State University of New York College of Technology at Alfred\n"+
        "State University of New York at Binghamton\n"+
        "State University of New York College at Brockport\n"+
        "State University of New York at Buffalo\n"+
        "State University of New York College at Buffalo (Buffalo State College)\n"+
        "State University of New York College of Agriculture and Technology at Cobleskill\n"+
        "State University of New York College at Cortland\n"+
        "State University of New York College of Environmental Science and Forestry\n"+
        "State University of New York at Farmingdale\n"+
        "State University of New York at Fredonia\n"+
        "State University of New York College at Geneseo\n"+
        "State University of New York College at New Paltz\n"+
        "State University of New York College at\n"+
        "State University of New York at Oswego\n"+
        "State University of New York at Plattsburgh\n"+
        "State University of New York College at Potsdam\n"+
        "State University of New York at Stony Brook\n"+
        "State University of New York Institute of Technology at Utica/Rome\n"+
        "Stephen F. Austin State University\n"+
        "Stephens College\n"+
        "Stetson University\n"+
        "Stevens Institute of Technology\n"+
        "Strayer College\n"+
        "Suffolk University\n"+
        "Sul Ross State University\n"+
        "Summit University of Louisiana\n"+
        "Susquehanna University\n"+
        "Swarthmore College\n"+
        "Sweet Briar College\n"+
        "Syracuse University\n"+
        "Tabor College\n"+
        "Tarleton State University\n"+
        "Taylor University\n"+
        "Teachers College, Columbia University\n"+
        "Teikyo Marycrest University\n"+
        "Temple University\n"+
        "Tennessee State University\n"+
        "Tennessee Technological University\n"+
        "Texas A&M International University\n"+
        "Texas A&M University-College Station\n"+
        "Texas A&M University-Corpus Christi\n"+
        "Texas A&M University-Kingsville\n"+
        "Texas Christian University\n"+
        "Texas Southern University\n"+
        "Texas Tech University\n"+
        "Texas Tech University-Health Sciences Center\n"+
        "Texas Woman's University\n"+
        "Thomas College\n"+
        "Thomas Edison State College\n"+
        "Thomas Jefferson University\n"+
        "Thomas More College\n"+
        "Towson State University\n"+
        "Transylvania University\n"+
        "Trenton State College\n"+
        "Trinity College (CT)\n"+
        "Trinity College\n"+
        "Trinity University\n"+
        "Troy State University\n"+
        "Truman State University\n"+
        "Tucson University\n"+
        "Tufts University\n"+
        "Tulane University\n"+
        "Tuskegee University\n"+
        "Union College\n"+
        "The Union Institute\n"+
        "Union University\n"+
        "United States Air Force Academy\n"+
        "United States International University\n"+
        "United States Merchant Marine Academy\n"+
        "United States Military Academy\n"+
        "United States Naval Academy\n"+
        "The Uniformed Services University of the Health Sciences\n"+
        "Ursinus College\n"+
        "Ursuline\n"+
        "University of Akron\n"+
        "University of Alabama at Birmingham\n"+
        "University of Alabama at Huntsville\n"+
        "University of Alabama at Tuscaloosa\n"+
        "University of Alaska\n"+
        "University of Alaska-Anchorage\n"+
        "University of Alaska-Fairbanks\n"+
        "University of Alaska-Southeast\n"+
        "University of Arizona\n"+
        "University of Arkansas - Fayetteville\n"+
        "University of Arkansas - Little Rock\n"+
        "University of Arkansas for Medical Sciences\n"+
        "University of Arkansas - Monticello\n"+
        "University of Baltimore\n"+
        "University of Bridgeport\n"+
        "University of California, Berkeley\n"+
        "University of California, Davis\n"+
        "University of California, Irvine\n"+
        "University of California, Los Angeles\n"+
        "University of California, Riverside\n"+
        "University of California, San Diego\n"+
        "University of California, San Francisco\n"+
        "University of California, Santa Barbara\n"+
        "University of California, Santa Cruz\n"+
        "University of Central Arkansas\n"+
        "University of Central Florida\n"+
        "University of Central\n"+
        "University of Charleston\n"+
        "University of Chicago\n"+
        "University of Cincinnati\n"+
        "University of Colorado at Boulder\n"+
        "University of Colorado at Colorado Springs\n"+
        "University of Colorado at Denver\n"+
        "University of Colorado Health Sciences Center\n"+
        "University of Connecticut\n"+
        "University of Dallas\n"+
        "University of Dayton\n"+
        "University of Delaware\n"+
        "University of Denver\n"+
        "University of the District of Columbia\n"+
        "University of Detroit Mercy\n"+
        "University of Dubuque\n"+
        "University of Evansville\n"+
        "University of Florida\n"+
        "University of Georgia\n"+
        "University of Great Falls\n"+
        "University of Guam\n"+
        "University of Hartford\n"+
        "University of Hawaii at Hilo Physics and Astronomy\n"+
        "University of Hawaii at Manoa\n"+
        "University of Houston\n"+
        "University of Idaho\n"+
        "University of Illinois at Chicago\n"+
        "University of Illinois at Springfield\n"+
        "University of Ilinois at Urbana-Champaign\n"+
        "University of Indianapolis\n"+
        "University of Iowa\n"+
        "University of Kansas\n"+
        "University of Kansas School of Medicine\n"+
        "University of Kentucky\n"+
        "University of La Verne\n"+
        "University of Louisville\n"+
        "University of Maine System\n"+
        "University of Maine\n"+
        "University of Maine at Farmington\n"+
        "University of Maine at Fort Kent\n"+
        "University of Maine at Machias\n"+
        "University of Maine at Presque Island\n"+
        "University of Maryland at Baltimore\n"+
        "University of Maryland at Baltimore County\n"+
        "University of Maryland at College Park\n"+
        "University of Maryland - University College\n"+
        "University of Massachusetts System\n"+
        "University of Massachusetts at Amherst\n"+
        "University of Massachusetts at Dartmouth\n"+
        "University of Massachusetts at Lowell\n"+
        "University of Memphis\n"+
        "University of Miami\n"+
        "University of Michigan-Ann Arbor\n"+
        "University of Michigan-Dearborn\n"+
        "University of Minnesota\n"+
        "University of Minnesota-Crookston\n"+
        "University of Minnesota-Duluth\n"+
        "University of Minnesota-Morris\n"+
        "University of Minnesota-Twin Cities\n"+
        "University of Mississippi\n"+
        "University of Mississippi Medical Center\n"+
        "University of Missouri System\n"+
        "University of Missouri-Columbia\n"+
        "University of Missouri-Kansas City\n"+
        "University of Missouri-Rolla\n"+
        "University of Missouri-Saint Louis\n"+
        "University of Montana\n"+
        "University of Nebraska, Kearney\n"+
        "University of Nebraska, Lincoln\n"+
        "University of Nebraska, Omaha\n"+
        "University of Nevada, Las Vegas\n"+
        "University of Nevada, Reno\n"+
        "University of New England\n"+
        "University of New Hampshire, Durham\n"+
        "University of New Haven\n"+
        "University of New Mexico\n"+
        "University of New Orleans\n"+
        "University of North Carolina at Asheville\n"+
        "University of North Carolina at Chapel Hill\n"+
        "University of North Carolina at Charlotte\n"+
        "University of North Carolina at Greensboro\n"+
        "University of North Carolina System\n"+
        "University of North Carolina at Wilmington\n"+
        "University of North Dakota\n"+
        "University of North Florida\n"+
        "University of North Texas\n"+
        "University of North Texas Health Science Center\n"+
        "University of Northern Colorado\n"+
        "University of Northern Iowa\n"+
        "University of Notre Dame\n"+
        "University of Oklahoma\n"+
        "University of Oregon\n"+
        "University of the Ozarks\n"+
        "University of the Pacific\n"+
        "University of Pennsylvania\n"+
        "University of Phoenix\n"+
        "University of Pittsburgh\n"+
        "University of Pittsburgh at Johnstown\n"+
        "University of Portland\n"+
        "University of Puerto Rico\n"+
        "University of Puget Sound\n"+
        "University of Redlands\n"+
        "University of Rhode Island\n"+
        "University of Richmond\n"+
        "University of Rochester\n"+
        "University of San Diego\n"+
        "University of San Francisco\n"+
        "University of Sarasota\n"+
        "University of Science & Arts of Oklahoma\n"+
        "University of Scranton\n"+
        "University of Sioux Falls\n"+
        "University of Southern California\n"+
        "University of South Carolina\n"+
        "University of South Carolina - Aiken\n"+
        "University of South Dakota\n"+
        "University of South Florida\n"+
        "University of Southern Maine\n"+
        "University of Southern Mississippi\n"+
        "University of Southwestern Louisiana\n"+
        "University of Saint Thomas\n"+
        "University of Saint Thomas (MN)\n"+
        "University of South Alabama\n"+
        "University of Southern Colorado\n"+
        "University of Southern Indiana\n"+
        "University of Tampa\n"+
        "University of Tennessee, Knoxville\n"+
        "University of Tennessee, Martin\n"+
        "University of Texas System\n"+
        "University of Texas at Arlington\n"+
        "University of Texas at Austin\n"+
        "University of Texas at Brownsville\n"+
        "University of Texas at Dallas\n"+
        "University of Texas at El Paso\n"+
        "University of Texas-Pan American\n"+
        "University of Texas at San Antonio\n"+
        "University of Texas Health Science Center at Houston\n"+
        "University of Texas Health Science Center at San Antonio\n"+
        "University of Texas at\n"+
        "University of Texas Health Center at Tyler\n"+
        "University of Texas M.D. Anderson Cancer Center\n"+
        "University of Texas Medical Branch\n"+
        "University of Texas Southwestern Medical Center at Dallas\n"+
        "University of Toledo\n"+
        "University of Tulsa\n"+
        "University of Utah\n"+
        "University of Vermont\n"+
        "University of the Virgin Islands\n"+
        "University of Virginia, Charlottesville\n"+
        "University of Washington\n"+
        "University of West Alabama\n"+
        "University of West Florida\n"+
        "University of Wisconsin System\n"+
        "University of Wisconsin-Eau Claire\n"+
        "University of Wisconsin-Green Bay\n"+
        "University of Wisconsin-LaCrosse\n"+
        "University of Wisconsin-Madison\n"+
        "University of Wisconsin-Milwaukee\n"+
        "University of Wisconsin-Oshkosh\n"+
        "University of Wisconsin-Parkside\n"+
        "University of Wisconsin-Platteville\n"+
        "University of Wisconsin-River Falls\n"+
        "University of Wisconsin-Stevens Point\n"+
        "University of Wisconsin-Stout\n"+
        "University of Wisconsin-Superior\n"+
        "University of Wisconsin-Whitewater\n"+
        "University of Wyoming\n"+
        "Upper Iowa University\n"+
        "Utah State University\n"+
        "Utah Valley State College\n"+
        "Valley City State University\n"+
        "Valdosta State University\n"+
        "Valparaiso University\n"+
        "Vanderbilt University\n"+
        "Vassar College\n"+
        "Vermont Technical College\n"+
        "Villa Julie College\n"+
        "Villanova University\n"+
        "Virginia Commonwealth University\n"+
        "Virginia Intermont College\n"+
        "Virginia Military Institute\n"+
        "Virginia Polytechnic Institute and State University\n"+
        "Virginia State University\n"+
        "Virginia Wesleyan College\n"+
        "Wabash College\n"+
        "Wake Forest University\n"+
        "Walden University\n"+
        "Walla Walla College\n"+
        "Warren Wilson College\n"+
        "Wartburg College\n"+
        "Washburn University\n"+
        "Washington Bible College/Capital Bible Seminary\n"+
        "Washington & Lee University\n"+
        "Washington College\n"+
        "Washington State University\n"+
        "Washington State University at Tri-Cities\n"+
        "Washington State University at Vancouver\n"+
        "Washington University, Saint Louis\n"+
        "Wayne State University\n"+
        "Waynesburg College (waynesburg.edu) Weber State University\n"+
        "Webster University\n"+
        "Wellesley College\n"+
        "Wells College\n"+
        "Wentworth Institute of Technology\n"+
        "Wesley College\n"+
        "Wesleyan University\n"+
        "West Chester University of Pennsylvania\n"+
        "West Coast University\n"+
        "West Georgia College\n"+
        "West Liberty State College\n"+
        "West Texas A&M University\n"+
        "West Virginia University\n"+
        "West Virginia University at Parkersburg\n"+
        "Western Carolina University\n"+
        "Western Connecticut State University\n"+
        "Western Illinois University\n"+
        "Western Kentucky University\n"+
        "Western Maryland College\n"+
        "Western Michigan University\n"+
        "Western Montana College\n"+
        "Western New England College\n"+
        "Western New Mexico University\n"+
        "Western State College\n"+
        "Western Washington University\n"+
        "Westfield State College\n"+
        "Westminster College\n"+
        "Westminster College\n"+
        "Westminster College of Salt Lake City\n"+
        "Westminster Theological Seminary\n"+
        "Westmont College\n"+
        "Wheaton College\n"+
        "Wheaton College, Norton MA\n"+
        "Wheeling Jesuit College\n"+
        "Whitman College\n"+
        "Whittier College\n"+
        "Whitworth College\n"+
        "Wichita State University\n"+
        "Widener University\n"+
        "Wilberforce University\n"+
        "Wilkes University\n"+
        "Willamette University\n"+
        "William Howard Taft University\n"+
        "William Jewell College\n"+
        "William Mitchell College of Law\n"+
        "William Penn College\n"+
        "William Paterson College\n"+
        "William Woods University\n"+
        "Williams College\n"+
        "Wilmington College\n"+
        "Winona State University\n"+
        "Winthrop University\n"+
        "Wittenberg University\n"+
        "Wofford College\n"+
        "Woodbury University\n"+
        "Worcester Polytechnic Institute\n"+
        "Wright State University\n"+
        "Xavier University of Louisiana\n"+
        "Yale University\n"+
        "Yeshiva University\n"+
        "York College of Pennsylvania\n"+
        "Youngstown State University";
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine())
        {
            College.saveCollege(scanner.nextLine());
        }
        Entity entity = new Entity("data");
        datastore.put(entity);
        System.out.println("Done!");
        }

    private static boolean exists(){
        Query q = new Query("data").setKeysOnly();
        PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);
        List<Entity> list = pq.asList(FetchOptions.Builder.withLimit(1));
        if (list != null)
        {
            return list.size() > 0;
        }
        else
        {
            return false;
        }
    }
}