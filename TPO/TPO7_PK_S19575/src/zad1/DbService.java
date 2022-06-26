package zad1;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class DbService {
    public Statement db_statement;

    public DbService() {
        try {
            Properties properties = System.getProperties();
            properties.setProperty("derby.system.home", "/home/gattto113/Documents/pjatk/TPO7_PK_S19575/src/zad1");

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            db_statement = DriverManager.getConnection("jdbc:derby:DerbyDB").createStatement();
        } catch (Exception ignored) {}
    }

    public List<List<String>> getPositions(Map<String, String[]> parameters) {
        StringBuilder whereStatement = new StringBuilder("WHERE");

        for (Entry<String, String[]> entry : parameters.entrySet()) {
            if (!entry.getKey().equals("submit-btn") && entry.getValue()[0].length() != 0) {
                if ("autor".equals(entry.getKey())) {
                    whereStatement.append(" AUTOR.name LIKE '%").append(entry.getValue()[0]).append("%'");
                }
                if ("title".equals(entry.getKey())) {
                    whereStatement.append(" tytul LIKE '%").append(entry.getValue()[0]).append("%'");
                }
                if ("publisher".equals(entry.getKey())) {
                    whereStatement.append(" WYDAWCA.name LIKE '%").append(entry.getValue()[0]).append("%'");
                }
                if ("year".equals(entry.getKey())) {
                    whereStatement.append(" rok = ").append(entry.getValue()[0]);
                }
                if ("price_from".equals(entry.getKey())) {
                    whereStatement.append(" cena >= ").append(entry.getValue()[0]);
                }
                if ("price_to".equals(entry.getKey())) {
                    whereStatement.append(" cena <= ").append(entry.getValue()[0]);
                }
                whereStatement.append(" AND");
            }
        }

        if (whereStatement.length() <= 5) {
            whereStatement = new StringBuilder();
        } else {
            whereStatement = new StringBuilder(whereStatement.substring(0, whereStatement.length() - 4));
        }

        List<List<String>> db_answer = new ArrayList<>();
        try {
            ResultSet resultSet = db_statement.executeQuery("SELECT AUTOR.name, tytul, WYDAWCA.name, rok, " +
                    "cena FROM pozycje JOIN autor ON POZYCJE.autid = AUTOR.autid JOIN wydawca ON WYDAWCA.wydid = " +
                    "POZYCJE.wydid " + whereStatement);

            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i < 6; i++) row.add(resultSet.getString(i));
                db_answer.add(row);
            }
            resultSet.close();
        } catch (Exception ignored) {}

        return db_answer;
    }
}