import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase
{
    private static final String URL = "jdbc:sqlite:atm.db";
    public static Connection connect()
    {
        try
        {
            return DriverManager.getConnection(URL);
        }
        catch (SQLException e)
        {
            System.out.println("Database connection failed!");
            return null;
        }
    }
}