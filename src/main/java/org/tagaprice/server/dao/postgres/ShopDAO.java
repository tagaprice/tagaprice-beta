package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.shared.entities.Shop;
import org.tagaprice.shared.exception.DAOException;

public class ShopDAO implements IShopDAO {
	private DBConnection _db;
	private EntityDAO _entityDAO;
	private CountryDAO _countryDAO;
	private static Logger _log = Logger.getLogger(ShopDAO.class);

	public ShopDAO(DBConnection db) {
		this._db = db;
		_entityDAO = new EntityDAO(db);
		_countryDAO = new CountryDAO(db);
	}

	@Override
	public Shop getById(long id) throws DAOException {
		return getByIdAndRef(id, 0);
	}


	@Override
	public Shop getByIdAndRef(long id, long rev) throws DAOException {
		ShopDAO._log.debug("id:"+id);
		//Get Entity Data
		Shop shop;
		shop = _entityDAO.getByIdAndRev(new Shop(), id, rev);
		if(shop == null) return null;

		// TODO implement fetching of a specific shop revision
		//Get Shop Data
		String sql = "SELECT type_id, imageUrl, lat, lng, street, city, country_code " +
		"FROM shopRevision " +
		"INNER JOIN ENTITY ON(ent_id = shop_id) " +
		"WHERE shop_id = ? AND rev = ?";
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, shop.getId());
			pstmt.setLong(2, shop.getRev());
			ResultSet res = pstmt.executeQuery();

			if (!res.next()) return null;

			if (res.getString("type_id") != null) shop.setTypeId(res.getLong("type_id"));
			else shop.setTypeId(null);

			shop.setImageSrc(res.getString("imageurl"));

			if (res.getString("lat") != null && res.getString("lng") != null) {
				shop.getAddress().setCoordinates(res.getDouble("lat"), res.getDouble("lng"));
			}
			else shop.getAddress().setCoordinates(null, null);

			String countryCode = res.getString("country_code");

			shop.getAddress().setAddress(
					res.getString("street"),
					res.getString("city"),
					countryCode != null ? _countryDAO.getByCountryCode(countryCode) : null);
			return shop;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			ShopDAO._log.error(msg+" Chaining and rethrowing.");
			ShopDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}

	@Override
	public Shop save(Shop shop) throws DAOException {
		ShopDAO._log.debug("shop:"+shop);
		PreparedStatement pstmt;

		try {
			Shop versionedShop = _entityDAO.save(shop);

			if(versionedShop == null)
				return null;

			if (versionedShop.getRev() == 1) {
				// create a new Shop
				pstmt = _db.prepareStatement("INSERT INTO shop (shop_id) VALUES (?)");
				pstmt.setLong(1, versionedShop.getId());
				pstmt.executeUpdate();
			} else if (versionedShop.getRev() < 1) {
				throw new DAOException("EntityDAO returned shop with revision < 0!");
			}

			String sql = "INSERT INTO shopRevision (shop_id, rev, type_id, imageUrl, lat, lng, street, city, country_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, versionedShop.getId());
			pstmt.setInt(2, versionedShop.getRev());

			if (versionedShop.getTypeId() != null) pstmt.setLong(3, versionedShop.getTypeId());
			else pstmt.setNull(3, Types.BIGINT);

			pstmt.setString(4, versionedShop.getImageSrc());


			if (versionedShop.getLat() != null && versionedShop.getLng() != null) {
				pstmt.setDouble(5, versionedShop.getLat());
				pstmt.setDouble(6, versionedShop.getLng());
			} else {
				pstmt.setNull(5, Types.DOUBLE);
				pstmt.setNull(6, Types.DOUBLE);
			}

			if (versionedShop.getAddress() != null) {
				pstmt.setString(7, versionedShop.getAddress().getStreet());
				pstmt.setString(8, versionedShop.getAddress().getCity());
				if (versionedShop.getAddress().getCountry() != null)
					pstmt.setString(9, versionedShop.getAddress().getCountry().getCode());
				else pstmt.setNull(9, Types.VARCHAR);

			}
			else {
				pstmt.setNull(7, Types.VARCHAR);
				pstmt.setNull(8, Types.VARCHAR);
				pstmt.setNull(9, Types.VARCHAR);
			}

			pstmt.executeUpdate();
			return versionedShop;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			ShopDAO._log.error(msg+" Chaining and rethrowing.");
			ShopDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
}