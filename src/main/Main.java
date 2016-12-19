package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        long[][] tablica = new long[1024554][2];
        ResultSet result = null;
        Connection conn=null;
        PreparedStatement p = null;
        int i=1;
        Scanner a = new Scanner(System.in);
        System.out.print("Podaj URL: ");
        String url = a.nextLine();
        System.out.print("Podaj login: ");
        String login = a.nextLine();
        System.out.print("Podaj hasło: ");
        String password = a.nextLine();
        try {
            conn = DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        long start = System.currentTimeMillis();
        try {
            p = conn.prepareStatement("SELECT * FROM dane Order BY id,data;");
            p.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Czas dla SQL: " + Long.toString(System.currentTimeMillis()-start) + "ms");
        start = System.currentTimeMillis();
        try {
            p = conn.prepareStatement("SELECT * FROM dane;");
            result = p.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while(result.next()){
                tablica[i][0] = result.getLong(1);
                tablica[i][1] = result.getLong(2);
                i++;
                }
            } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        Arrays.sort(tablica, (long[] r1, long[] r2) -> {
            int result1 = Long.compare(r1[0],r2[0]);
            if (result1 == 0) {
                return Long.compare(r1[1],r2[1]);
            } else {
                return result1;
            }
        });
        System.out.println("Czas dla Java: " + Long.toString(System.currentTimeMillis()-start) + "ms");
    }
    
    
}
