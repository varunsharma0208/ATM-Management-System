import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup
{
    public static void createTables()
    {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    account_number INTEGER UNIQUE NOT NULL,
                    name TEXT NOT NULL,
                    pin INTEGER NOT NULL,
                    balance REAL NOT NULL
                )
                """;

        try (Connection connection = DataBase.connect();
             Statement statement = connection.createStatement())
        {
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            System.out.println("Error creating users table!");
        }
    }
}