package demo;

import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ClickHouseDataGenerator {

    private static final String JDBC_URL = "jdbc:clickhouse://localhost:8123/default";
    private static final String INSERT_SQL = "INSERT INTO my_table (id, name, age, score, event_date) VALUES (?, ?, ?, ?, now())";

    private static ClickHouseDataSource dataSource;
    private static Random random = new Random();

    public static void main(String[] args) {
        setupDataSource();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                insertData();
            }
        }, 0, 5000); // Schedule to run every 5 seconds
    }

    private static void setupDataSource() {
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser("default");
        properties.setPassword("");

        dataSource = new ClickHouseDataSource(JDBC_URL, properties);
    }

    private static void insertData() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {

            int id = random.nextInt(100000); // Random id
            String name = "Name_" + id; // Random name
            int age = random.nextInt(100); // Random age
            float score = random.nextFloat() * 100; // Random score

            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.setFloat(4, score);

            statement.execute();
            System.out.println("Inserted record with id: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
