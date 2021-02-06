import Storables.Account;
import Storables.ConsumptionObject;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseConfig {

    static String sql = "";
    static Connection c;
    static Statement stm = null;

    static {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            stm = c.createStatement();
            Class.forName("org.sqlite.JDBC");
            System.out.println("Opened database successfully");
            stm.execute("CREATE TABLE IF NOT EXISTS consumptions (username TEXT, price FLOAT, date TEXT)");
            stm.execute("CREATE TABLE IF NOT EXISTS accounts (username TEXT, password TEXT)");

        } catch (Exception e){
            System.out.println("Hit exception: " + e);
            System.exit(0);
        }
    }

    public static void addToConsumptionsDB(String username, double price, String date){
        sql = "INSERT INTO consumptions VALUES ('" + username + "', " + price + ", '" + date + "')";

        try{
            stm.execute(sql);
        } catch (Exception e){
            System.out.print(sql);
            System.out.println("Hit Exception on addToConsumptions method: " + e);
        }

    }

    public static void addToAccountsDB(String username, String password){
        sql = "INSERT INTO accounts VALUES ('" + username + "', " + password + "')";

        try{
            stm.execute(sql);
        } catch (Exception e){
            System.out.print(sql);
            System.out.println("Hit Exception on addToAccountsDB method: " + e);
        }

    }

    public static ArrayList<ConsumptionObject> getConsumptions(String username){
        ResultSet rs = null;
        ArrayList<ConsumptionObject> consumptionsArr = new ArrayList<>();
        try{
            rs = stm.executeQuery( "SELECT * FROM consumptions WHERE username='" + username + "'" );

        while (rs.next()) {
            String loadedUsername = rs.getString("username");
            double loadedPrice = rs.getDouble("price");
            String date = rs.getString("date");

            ConsumptionObject consumptionObject = new ConsumptionObject(loadedUsername, loadedPrice, date);
            consumptionsArr.add(consumptionObject);
        }
        } catch (Exception e){
            System.out.println("Hit Exception: " + e);
        }

        return consumptionsArr;
    }

    public static ArrayList<Account> getAccounts() {
        ResultSet rs = null;
        ArrayList<Account> accountsArr = new ArrayList<Account>();
        try {
            rs = stm.executeQuery( "SELECT * FROM accounts" );
            String loadedUsername = rs.getString("username");
            String loadedPassword = rs.getString("password");

            accountsArr.add(new Account(loadedUsername,loadedPassword));

        } catch (Exception e){
            System.out.println("Hit Error: " + e);
        }
        return accountsArr;
    }


    public static String getPassword(String username){
        ResultSet rs = null;
        try {
            rs = stm.executeQuery( "SELECT * FROM accounts WHERE username='" + username + "'" );
            return rs.getString("password");
        } catch (Exception e){
            System.out.println("Hit Error: " + e);
        }
        return null;
    }

    public static Account getAccount(String username){
        for (Account account : getAccounts())
            if (account.getUsername().equals(username))
                return account;
        return null;
    }

    public static boolean correctPassword(String username, String password){
        return getAccount(username).getPassword().equals(password);
    }
}
