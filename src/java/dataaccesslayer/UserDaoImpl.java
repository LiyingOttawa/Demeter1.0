package dataaccesslayer;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.UserDto;

public class UserDaoImpl extends DAOImpl<UserDto> {

    final static String SQL_INSERT = "INSERT INTO user (Name, Role_id, Email, Password) VALUES (?, ?, ?, ?)";
    final static String SQL_DELETE_ALL = "DELETE FROM user";
    final static String SQL_DELETE = "DELETE FROM user WHERE user_id = ?";
    final static String SQL_UPDATE = "UPDATE user SET Name = ?, Role_id = ?, Email = ? WHERE user_id = ?";
    final static String SQL_RETRIEVE = "SELECT * FROM user WHERE user_id = ?";
    final static String SQL_RETRIEVE_ALL = "SELECT * FROM user";

    @Override
    public int insert(UserDto user) {

        try {
            return dataSource.executeUpdate(SQL_INSERT, user.getUsername(), user.getRoleId(), user.getEmail(), user.getPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(Serializable id) {
        try {
            if (id == null) {
                return dataSource.executeUpdate(SQL_DELETE_ALL);
            } else {
                return dataSource.executeUpdate(SQL_DELETE, id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(UserDto user) {

        try {
            return dataSource.executeUpdate(SQL_UPDATE, user.getUsername(), user.getRoleId(), user.getEmail(), user.getUserId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public UserDto findById(Serializable id) {
        try (PreparedStatement statement = dataSource.query(SQL_RETRIEVE, id); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                UserDto user = new UserDto();
                user.setUserId(resultSet.getInt("user_id"));
                user.setEmail(resultSet.getString("name"));
                user.setRoleId(resultSet.getInt("role_id"));
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();
        try (PreparedStatement statement = dataSource.query(SQL_RETRIEVE_ALL); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                UserDto user = new UserDto();
                user.setUserId(resultSet.getInt("user_id"));
                user.setEmail(resultSet.getString("name"));
                user.setRoleId(resultSet.getInt("role_id"));
                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }
}
