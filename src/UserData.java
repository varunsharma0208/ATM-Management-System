import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserData
{
    public static void registerUser(long accountNumber,String name,int pin,double balance)
    {
        String sql = "INSERT INTO users(account_number, name, pin, balance) VALUES (?, ?, ?, ?)";

        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setLong(1, accountNumber);
            statement.setString(2, name);
            statement.setInt(3, pin);
            statement.setDouble(4, balance);
            statement.executeUpdate();
            System.out.println("User registered successfully!");
        }
        catch (SQLException e)
        {
            if (e.getMessage().contains("UNIQUE"))
            {
                System.out.println("Account number already exists!");
            }
            else
            {
                System.out.println("Registration failed!");
            }
        }
    }

    public static boolean loginUser(long accountNumber,int pin)
    {
        String sql = "SELECT * FROM users WHERE account_number = ? AND pin = ?";

        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setLong(1, accountNumber);
            statement.setInt(2, pin);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                System.out.println("Login successful!");
                return true;
            }
            else
            {
                System.out.println("Invalid account number or PIN!");
                return false;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Login failed!");
            return false;
        }
    }

    public static double getBalance(long accountNumber)
    {
        String sql = "SELECT balance FROM users WHERE account_number = ?";

        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setLong(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getDouble("balance");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Could not retrieve balance!");
        }
        return -1;
    }

    public static boolean deposit(long accountNumber,double amount)
    {
        String sql = "UPDATE users SET balance = balance + ? WHERE account_number = ?";

        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setDouble(1, amount);
            statement.setLong(2, accountNumber);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0)
            {
                System.out.println("Amount deposited successfully!");
                return true;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Deposit failed!");
        }
        return false;
    }

    public static boolean withdraw(long accountNumber,double amount)
    {
        String sql = "UPDATE users SET balance = balance - ? WHERE account_number = ? AND balance >= ?";

        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setDouble(1, amount);
            statement.setLong(2, accountNumber);
            statement.setDouble(3, amount);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0)
            {
                System.out.println("Amount withdrawn successfully!");
                return true;
            }
            else
            {
                System.out.println("Insufficient balance!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Withdrawal failed!");
        }
        return false;
    }

    public static boolean changePin(long accountNumber,int oldPin,int newPin)
    {
        String sql = "UPDATE users SET pin = ? WHERE account_number = ? AND pin = ?";

        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, newPin);
            statement.setLong(2, accountNumber);
            statement.setInt(3, oldPin);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0)
            {
                System.out.println("PIN changed successfully!");
                return true;
            }
            else
            {
                System.out.println("Incorrect old PIN!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to change PIN!");
        }
        return false;
    }

    public static boolean deleteAccount(long accountNumber,int pin)
    {
        String sql = "DELETE FROM users WHERE account_number = ? AND pin = ?";

        try (Connection connection = DataBase.connect();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setLong(1, accountNumber);
            statement.setInt(2, pin);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0)
            {
                return true;
            }
            else
            {
                System.out.println("Incorrect PIN!");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to delete account!");
        }
        return false;
    }
}