package dataaccesslayer;

import static dataaccesslayer.ItemListingDaoImpl.SQL_RETRIEVE_ALL;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import model.ItemListingDTO;
import model.SubscriptionDTO;

public class SubscriptionDaoImpl extends DAOImpl<SubscriptionDTO> {

    final static String SQL_INSERT = "INSERT INTO subscription (user_id, Notific_method, Item_location, Item_price, Item_type) VALUES (?, ?, ?, ?, ?)";
    final static String SQL_DELETE_ALL = "DELETE FROM subscription";
    final static String SQL_DELETE = "DELETE FROM subscription WHERE subs_id = ?";
    final static String SQL_UPDATE = "UPDATE subscription SET user_id=?, Notific_method=?, Item_location=?, Item_price=?, Item_type=? WHERE subs_id = ?";
    final static String SQL_RETRIEVE = "SELECT subs_id, user_id, Notific_method, Item_location, Item_price, Item_type FROM subscription FROM subscription WHERE subs_id = ?";
    final static String SQL_RETRIEVE_ALL = "SELECT subs_id, user_id, Notific_method, Item_location, Item_price, Item_type FROM subscription where 1=1";
    
    @Override
    public int insert(SubscriptionDTO subscription) {
        try {
            return MyDataSource.execute(SQL_INSERT, subscription.getUserId(), subscription.getNotiMethod(), subscription.getItemLocation(), subscription.getItemPrice(), subscription.getItemTypeId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(Serializable id) {
        try {
            if (id == null) {
                return MyDataSource.execute(SQL_DELETE_ALL);
            } else {
                return MyDataSource.execute(SQL_DELETE, id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(SubscriptionDTO subscription) {
        try {
            return MyDataSource.execute(SQL_UPDATE, subscription.getUserId(), subscription.getNotiMethod(), subscription.getItemLocation(), subscription.getItemPrice(), subscription.getItemTypeId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public SubscriptionDTO Retrieve(Serializable id) {
        try (Connection connection = MyDataSource.getConnection(); PreparedStatement statement = MyDataSource.prepareStatement(connection,SQL_RETRIEVE, id); 
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                SubscriptionDTO subscription = new SubscriptionDTO();
                subscription.setSubsId(resultSet.getInt("subs_id"));
                subscription.setUserId(resultSet.getInt("user_id"));
                subscription.setNotiMethod(resultSet.getString("Notific_method"));
                subscription.setItemLocation(resultSet.getString("Item_location"));
                subscription.setItemPrice(resultSet.getDouble("Item_price"));
                subscription.setItemTypeId(resultSet.getInt("Item_type"));
                return subscription;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SubscriptionDTO> RetrieveAll() {
        List<SubscriptionDTO> subscriptions = new ArrayList<>();
        try (Connection connection = MyDataSource.getConnection(); PreparedStatement statement = MyDataSource.prepareStatement(connection,SQL_RETRIEVE_ALL); 
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SubscriptionDTO subscription = new SubscriptionDTO();
                subscription.setSubsId(resultSet.getInt("subs_id"));
                subscription.setUserId(resultSet.getInt("user_id"));
                subscription.setNotiMethod(resultSet.getString("Notific_method"));
                subscription.setItemLocation(resultSet.getString("Item_location"));
                subscription.setItemPrice(resultSet.getDouble("Item_price"));
                subscription.setItemTypeId(resultSet.getInt("Item_type"));
                subscriptions.add(subscription);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return subscriptions;
    }
    
    public List<SubscriptionDTO> RetrieveList(int userId, String itemType) {
        List<SubscriptionDTO> items = new ArrayList<>();
        try (Connection connection = MyDataSource.getConnection(); PreparedStatement statement = prepareStatement(connection,SQL_RETRIEVE_ALL,userId, itemType); 
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SubscriptionDTO subscription = new SubscriptionDTO();
                subscription.setSubsId(resultSet.getInt("subs_id"));
                subscription.setUserId(resultSet.getInt("user_id"));
                subscription.setNotiMethod(resultSet.getString("Notific_method"));
                subscription.setItemLocation(resultSet.getString("Item_location"));
                subscription.setItemPrice(resultSet.getDouble("Item_price"));
                subscription.setItemTypeId(resultSet.getInt("Item_type"));
                items.add(subscription);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return items;
    }

    //todo the following need a join table sql
    private PreparedStatement prepareStatement(Connection connection,String sql,int userId, String itemTypeFilte) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder(sql);
        queryBuilder.append(" AND user_id = "+userId);
        boolean validItemTypeFilter = itemTypeFilte != null && !itemTypeFilte.isEmpty();        
        if (validItemTypeFilter) {
            queryBuilder.append(" AND Item_type = ?");
        }

        PreparedStatement statement = MyDataSource.prepareStatement(connection,queryBuilder.toString());
        int parameterIndex = 1;
        if (validItemTypeFilter) {
            statement.setInt(parameterIndex++, Integer.parseInt(itemTypeFilte));
        }

        return statement;
    }
}

