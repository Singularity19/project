package com.example.demo25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class EarthquakeData {
    private String id;
    private int depth;
    private String magnitudeType;
    private double magnitude;
    private String state;
    private String time;

    public EarthquakeData(String id, int depth, String magnitudeType, double magnitude, String state, String time) {
        this.id = id;
        this.depth = depth;
        this.magnitudeType = magnitudeType;
        this.magnitude = magnitude;
        this.state = state;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public int getDepth() {
        return depth;
    }

    public String getMagnitudeType() {
        return magnitudeType;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getState() {
        return state;
    }

    public String getTime() {
        return time;
    }
}

public class EarthquakeProcessor {

    public static void main(String[] args) {
        List<EarthquakeData> earthquakeDataList = parseCSV("C:\\Users\\Пользователь\\Desktop\\demo25\\src\\main\\java\\com\\example\\demo25\\zt.csv");

        if (earthquakeDataList != null && !earthquakeDataList.isEmpty()) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:earthquake.db")) {
                Statement statement = connection.createStatement();

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Earthquakes (" +
                        "id TEXT PRIMARY KEY," +
                        "depth INTEGER," +
                        "magnitudeType TEXT," +
                        "magnitude REAL," +
                        "state TEXT," +
                        "time TEXT)");

                for (EarthquakeData data : earthquakeDataList) {
                    String sql = "INSERT OR IGNORE INTO Earthquakes (id, depth, magnitudeType, magnitude, state, time) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, data.getId());
                    preparedStatement.setInt(2, data.getDepth());
                    preparedStatement.setString(3, data.getMagnitudeType());
                    preparedStatement.setDouble(4, data.getMagnitude());
                    preparedStatement.setString(5, data.getState());
                    preparedStatement.setString(6, data.getTime());
                    preparedStatement.executeUpdate();
;
                }

                String query1 = "SELECT strftime('%Y', time) AS Year, AVG(magnitude) AS AverageMagnitude " +
                        "FROM Earthquakes " +
                        "GROUP BY Year";

                ResultSet resultSet1 = statement.executeQuery(query1);
                while (resultSet1.next()) {
                    String year = resultSet1.getString("Year");
                    double averageMagnitude = resultSet1.getDouble("AverageMagnitude");
                    System.out.println("Year: " + year + ", Average Magnitude: " + averageMagnitude);
                }

                String query2 = "SELECT state, AVG(magnitude) AS AvgMagnitude " +
                        "FROM Earthquakes " +
                        "GROUP BY state " +
                        "HAVING state = 'West Virginia'";

                ResultSet resultSet2 = statement.executeQuery(query2);
                if (resultSet2.next()) {
                    double averageMagnitudeWV = resultSet2.getDouble("AvgMagnitude");
                    System.out.println("Average Magnitude for West Virginia: " + averageMagnitudeWV);
                }

                String query3 = "SELECT state " +
                        "FROM Earthquakes " +
                        "WHERE strftime('%Y', time) = '2013' " +
                        "ORDER BY depth DESC LIMIT 1";

                ResultSet resultSet3 = statement.executeQuery(query3);
                if (resultSet3.next()) {
                    String stateMaxDepth2013 = resultSet3.getString("state");
                    System.out.println("State with the deepest earthquake in 2013: " + stateMaxDepth2013);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<EarthquakeData> parseCSV(String filename) {
        List<EarthquakeData> earthquakeDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                EarthquakeData earthquakeData = new EarthquakeData(
                        data[0],
                        Integer.parseInt(data[1]),
                        data[2],
                        Double.parseDouble(data[3]),
                        data[4],
                        data[5]
                );

                earthquakeDataList.add(earthquakeData);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return earthquakeDataList;
    }

}
