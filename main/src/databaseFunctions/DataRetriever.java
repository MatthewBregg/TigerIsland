package databaseFunctions;

import tigerislandserver.gameplay.identifiers.ChallengeID;
import tigerislandserver.gameplay.identifiers.GameID;

import java.sql.*;
import java.util.List;

/**
 * created by Christine on 4/2/2017
 *
 * database names
 *
 * TilesPlaced
 * BuildAction
 * Matches
 */
public class DataRetriever {
    private static Connection connection;

    public static void main(String[] args)
    {
        try {
            // create our mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost/test";
            Class.forName(myDriver);
            connection = DriverManager.getConnection(myUrl, "root", "");
        }
        catch (Exception e) {
            System.err.println("Error with connecting to the database");
            System.err.println(e.getMessage());
        }
    }


    public static void getNumberOfMovesByGameID(ChallengeID challengeID, GameID gameID){
        String query = "SELECT turn_number FROM tiles_placed WHERE challenge_id = " + challengeID + " and game_id = " + gameID;

        ResultSet resultSet = getResultSet(query);
        if(resultSet != null){
            try {
                int counter = 0;
                while (resultSet.next()) {
                    counter++;
                }
                System.out.println("For challengeID " + challengeID + " and gameID " + gameID + " there were " + counter + " moves total");
            }
            catch(Exception e){
                System.err.println("Error in pulling data");
                System.err.println(e.getMessage());
            }
        }
    }


    // room for any other queries we would want to make

    public static ResultSet getResultSet(String query){
        ResultSet rs;
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
            return rs;
        }
        catch(Exception e){

            System.err.println("Error creating statement");
            System.err.println(e.getMessage());
        }
        // i know this is bad im sorry
        return null;
    }




    /*            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM users";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date dateCreated = rs.getDate("date_created");
                boolean isAdmin = rs.getBoolean("is_admin");
                int numPoints = rs.getInt("num_points");

                // print the results
                System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
            }
            st.close();
        }
        */

}