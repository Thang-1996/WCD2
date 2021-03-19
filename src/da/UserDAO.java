package da;

import entity.User;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/t1907m?useSSL=false";
    private String jdbcUserName = "root";
    private String jdbcPassword = "";

    private static final String INSERT_SQL = "INSERT INTO users"+ "(name,email,country) VALUES"+"(?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT id,name,email,country FROM users WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE users set name = ?,email = ?,country=? WHERE id= ?;";
    private static final String SELECT_ALL_USER = "SELECT * FROM users";
    public UserDAO(){

    }
    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL,jdbcUserName,jdbcPassword);
        return connection;
    }
    public void insertUser(User user) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getEmail());
        preparedStatement.setString(3,user.getCountry());
        preparedStatement.executeUpdate();
    }
    public User selectUser(int id) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String country = resultSet.getString("country");
            user = new User(id,name,email,country);
        }
        return user;
    }
    public List<User> selectAllUsers() throws SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String country = resultSet.getString("country");
            users.add(new User(id,name,email,country));
        }
        return users;
    }
    public boolean deleteUser(int id) throws SQLException, ClassNotFoundException {
        boolean rowDeleted;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
        statement.setInt(1,id);
        rowDeleted = statement.executeUpdate() > 0;
        return rowDeleted;
    }
    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        boolean rowUpdated;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
        statement.setString(1,user.getName());
        statement.setString(2,user.getName());
        statement.setString(3,user.getCountry());
        statement.setInt(4,user.getId());
        rowUpdated = statement.executeUpdate() > 0;
        return rowUpdated;
    }
}
