import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        DatabaseSetup.createTables();
        int mainChoice;
        do
        {
            System.out.println("\n---- ATM MACHINE ----");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = sc.nextInt();

            switch (mainChoice)
            {
                case 1:
                    System.out.println("\n---- REGISTRATION ----");

                    System.out.print("Enter your Account Number: ");
                    long accountNumber = sc.nextLong();
                    if (accountNumber <= 0)
                    {
                        System.out.println("Account Number must be positive!");
                        break;
                    }
                    sc.nextLine();

                    System.out.print("Enter your Name: ");
                    String name = sc.nextLine();
                    if (name.trim().isEmpty())
                    {
                        System.out.println("Name cannot be empty!");
                        break;
                    }

                    System.out.print("Set your PIN: ");
                    int pin = sc.nextInt();
                    if (pin < 1000 || pin > 9999)
                    {
                        System.out.println("PIN must be exactly 4 digits!");
                        break;
                    }

                    System.out.print("Enter initial balance: ");
                    double balance = sc.nextDouble();
                    if (balance < 0)
                    {
                        System.out.println("Balance must be positive!");
                        break;
                    }

                    UserData.registerUser(accountNumber,name,pin,balance);
                    break;

                case 2:
                    System.out.println("\n---- ATM LOGIN ----");

                    System.out.print("Enter your Account Number: ");
                    long loginAccount = sc.nextLong();

                    System.out.print("Enter PIN: ");
                    int loginPin = sc.nextInt();

                    boolean loggedIn = UserData.loginUser(loginAccount,loginPin);

                    if (loggedIn)
                    {
                        int choice;
                        do
                        {
                            System.out.println("\n---- ATM MENU ----");
                            System.out.println("1. Check Balance");
                            System.out.println("2. Deposit");
                            System.out.println("3. Withdraw");
                            System.out.println("4. Change PIN");
                            System.out.println("5. Delete Account");
                            System.out.println("6. Logout");
                            System.out.print("Enter your choice: ");

                            choice = sc.nextInt();

                            switch (choice)
                            {
                                case 1:
                                    double currentBalance = UserData.getBalance(loginAccount);
                                    System.out.println("Current balance: " + currentBalance);
                                    break;

                                case 2:
                                    System.out.print("Enter amount to deposit: ");
                                    double depositAmount = sc.nextDouble();
                                    if (depositAmount > 0)
                                    {
                                        UserData.deposit(loginAccount,depositAmount);
                                    }
                                    else
                                    {
                                        System.out.println("Invalid amount!");
                                    }
                                    break;

                                case 3:
                                    System.out.print("Enter amount to withdraw: ");
                                    double withdrawAmount = sc.nextDouble();
                                    if (withdrawAmount > 0)
                                    {
                                        UserData.withdraw(loginAccount,withdrawAmount);
                                    }
                                    else
                                    {
                                        System.out.println("Invalid amount!");
                                    }
                                    break;

                                case 4:
                                    System.out.print("Enter old PIN: ");
                                    int oldPin = sc.nextInt();

                                    System.out.print("Enter new PIN: ");
                                    int newPin = sc.nextInt();

                                    if (newPin < 1000 || newPin > 9999)
                                    {
                                        System.out.println("PIN must be exactly 4 digits!");
                                    }
                                    else
                                    {
                                        UserData.changePin(loginAccount,oldPin,newPin);
                                    }
                                    break;

                                case 5:
                                    System.out.print("Enter your PIN to confirm account deletion: ");
                                    int deletePin = sc.nextInt();

                                    boolean deleted = UserData.deleteAccount(loginAccount,deletePin);
                                    if (deleted)
                                    {
                                        System.out.println("Account deleted successfully.");
                                        choice = 6;
                                    }
                                    break;

                                case 6:
                                    System.out.println("Thank you for using the ATM!");
                                    break;

                                default:
                                    System.out.println("Invalid choice!");
                            }
                        } while (choice != 6);
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using the ATM!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (mainChoice != 3);
        sc.close();
    }
}