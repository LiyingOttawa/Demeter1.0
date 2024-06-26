/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;

import dataaccesslayer.DAO;
import dataaccesslayer.MyDataSource;
import dataaccesslayer.ItemDaoImpl;
import dataaccesslayer.ItemListingDaoImpl;
import dataaccesslayer.ItemTypeDaoImpl;
import dataaccesslayer.LocationDaoImpl;
import dataaccesslayer.TransactionDaoImpl;
import dataaccesslayer.UserDaoImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import model.EnumStatusType;
import model.EnumTransactionType;
import model.ItemDTO;
import model.ItemListingDTO;
import model.ItemTypeDTO;
import model.LocationDTO;
import model.TransactionDTO;
import model.UserDTO;
import viewmodel.DonationViewModel;
import viewmodel.DonationViewModelItem;
import static viewmodel.DonationViewModelItem.convertFrom;
import viewmodel.InventoryViewModelItem;
import viewmodel.SaleViewModel;
import viewmodel.SaleViewModelItem;

/**
 *
 * @author Glily
 */
public class ItemListingService {

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final ItemDaoImpl itemDao = new ItemDaoImpl();
    private final DAO<LocationDTO> locationDao = new LocationDaoImpl();
    private final DAO<UserDTO> userDao = new UserDaoImpl();
    private final DAO<ItemTypeDTO> typeDao = new ItemTypeDaoImpl();

    private final ItemListingDaoImpl itemListingDao = new ItemListingDaoImpl();
    private final TransactionDaoImpl transationDao = new TransactionDaoImpl();

    public DonationViewModel buidDonationViewModel(String itemType, String expireDays) {
        DonationViewModel viewModel = new DonationViewModel();
        viewModel.setItems(retrieveItemList(itemType, expireDays));
        viewModel.setTypeOptions(typeDao.RetrieveAll());
        return viewModel;
    }

    private List<DonationViewModelItem> retrieveItemList(String itemType, String daysExpireDays) {
        List<DonationViewModelItem> result = new ArrayList<>();
        List<ItemListingDTO> items = itemListingDao.RetrieveList(true,itemType, daysExpireDays);
        items.forEach(listingItem -> {
            ItemDTO item = itemDao.Retrieve(listingItem.getItemId());
            if (item != null) {
                DonationViewModelItem viewItem = convertFrom(listingItem, item,
                        typeDao.Retrieve(item.getItemTypeId()), locationDao.Retrieve(item.getLocationId()));
                result.add(viewItem);
            }
        });
        return result;
    }

    public SaleViewModel buidSaleViewModel(String itemType, String expireDays) {
        SaleViewModel viewModel = new SaleViewModel();
        viewModel.setItems(retrieveSaleItemList(itemType, expireDays));
        viewModel.setTypeOptions(typeDao.RetrieveAll());
        return viewModel;
    }
    
    private List<SaleViewModelItem> retrieveSaleItemList(String itemType, String daysExpireDays) {
        List<SaleViewModelItem> result = new ArrayList<>();
        List<ItemListingDTO> items = itemListingDao.RetrieveList(false,itemType, daysExpireDays);
        items.forEach(listingItem -> {
            ItemDTO item = itemDao.Retrieve(listingItem.getItemId());
            if (item != null) {
                SaleViewModelItem viewItem = SaleViewModelItem.convertFrom(listingItem, item,
                        typeDao.Retrieve(item.getItemTypeId()), locationDao.Retrieve(item.getLocationId()));
                result.add(viewItem);
            }
        });
        return result;
    }

    public SaleViewModelItem buidSaleViewModelItem(int id) {
        ItemListingDTO listingItem = itemListingDao.Retrieve(id);
        if (listingItem != null) {
            ItemDTO item = itemDao.Retrieve(listingItem.getItemId());
            if (item != null) {
                SaleViewModelItem viewItem = SaleViewModelItem.convertFrom(listingItem, item,
                        typeDao.Retrieve(item.getItemTypeId()), locationDao.Retrieve(item.getLocationId()));
                return viewItem;
            }
        }

        return null;
    }

    public DonationViewModelItem buidDonationViewModelItem(int id) {
        ItemListingDTO listingItem = itemListingDao.Retrieve(id);
        if (listingItem != null) {
            ItemDTO item = itemDao.Retrieve(listingItem.getItemId());
            if (item != null) {
                DonationViewModelItem viewItem = DonationViewModelItem.convertFrom(listingItem, item,
                        typeDao.Retrieve(item.getItemTypeId()), locationDao.Retrieve(item.getLocationId()));
                return viewItem;
            }
        }

        return null;
    }
}
