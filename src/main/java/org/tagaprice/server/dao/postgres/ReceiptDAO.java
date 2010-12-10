package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;
import org.tagaprice.shared.entities.Price;
import org.tagaprice.shared.entities.Product;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.Receipt;
import org.tagaprice.shared.entities.Shop;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exception.DAOException;

public class ReceiptDAO implements IReceiptDAO {
	private DBConnection _db;
	private EntityDAO _entityDAO;
	private ShopDAO _shopDAO;
	private ProductDAO _productDAO;
	private static Logger _log = Logger.getLogger(ReceiptDAO.class);

	public ReceiptDAO(DBConnection db) {
		this._db=db;
		_entityDAO = new EntityDAO(db);
		_shopDAO = new ShopDAO(db);
		_productDAO = new ProductDAO(db);
	}

	@Override
	public List<Receipt> getUserReceipts(long id) throws DAOException {
		ReceiptDAO._log.debug("id:"+id);
		String sql = "SELECT rid FROM " +
		"receipt re " +
		"INNER JOIN entity en " +
		"ON (en.ent_id=re.rid) " +
		"INNER JOIN entityrevision enr " +
		"ON (en.current_revision=enr.rev AND en.ent_id=enr.ent_id) " +
		"WHERE en.creator=? ";
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet resSet = pstmt.executeQuery();
			List<Receipt> receipts = new ArrayList<Receipt>();
			while(resSet.next()){
				receipts.add(getById(resSet.getLong("rid")));
			}
			return receipts;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			ReceiptDAO._log.error(msg+" Chaining and rethrowing.");
			ReceiptDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}


	@Override
	public Receipt getById(long id) throws DAOException {
		ReceiptDAO._log.debug("id:"+id);
		//get Entity Data
		Receipt receipt = _entityDAO.getById(new Receipt(), id);
		if(receipt == null)
			return null;
		receipt.setDate(new Date());
		receipt.setProductData(new ArrayList<Product>());
		receipt.setDraft(true);


		//GetReceipt
		String sql = "SELECT sid, re.created_at, draft " +
		"FROM receipt re " +
		"INNER JOIN entity en " +
		"ON re.rid=en.ent_id " +
		"WHERE en.creator = ? AND re.rid = ?";
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, receipt.getCreatorId());
			pstmt.setLong(2, receipt.getId());
			ResultSet resSet = pstmt.executeQuery();

			if(!resSet.next()) {
				return null;
			}

			receipt.setDate(resSet.getDate("created_at"));
			receipt.setDraft(resSet.getBoolean("draft"));

			//Get shop
			if(resSet.getLong("sid")>0){
				Shop shopTemp = _shopDAO.getById(resSet.getLong("sid"));
				if(shopTemp == null)
					return null;
				receipt.setShop(shopTemp);
			}

			//Get Products
			sql = "SELECT ree.pid, ree.price " +
			"FROM receipt re " +
			"INNER JOIN entity en " +
			"ON re.rid=en.ent_id " +
			"INNER JOIN receiptentry ree " +
			"ON ree.rid = re.rid " +
			"WHERE en.creator = ? AND re.rid = ?";

			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, receipt.getCreatorId());
			pstmt.setLong(2, receipt.getId());
			ResultSet resSet2 = pstmt.executeQuery();

			ArrayList<Product> productDataArray = new ArrayList<Product>();

			while(resSet2.next()){
				Product tempProduct = _productDAO.getById(resSet2.getLong("pid"));
				tempProduct.setAvgPrice(new Price(resSet2.getInt("price"), 4, 1, "€", 1));
				tempProduct.setQuantity(new Quantity(1, new Unit(23, 2, "kg", 1, null, 0)));
				productDataArray.add(tempProduct);
			}


			receipt.setProductData(productDataArray);
			return receipt;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			ReceiptDAO._log.error(msg+" Chaining and rethrowing.");
			ReceiptDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}

	@Override
	public Receipt save(Receipt receipt) throws DAOException {
		ReceiptDAO._log.debug("receipt:"+receipt);

		Receipt versionedReceipt = _entityDAO.save(receipt);

		if(versionedReceipt == null)
			return null;

		PreparedStatement pstmt;
		try {
			if(versionedReceipt.getRev()==1){
				//create new Receipt
				pstmt = _db.prepareStatement("INSERT INTO receipt (rid) VALUES (?)");
				pstmt.setLong(1, versionedReceipt.getId());
				pstmt.executeUpdate();
			} else if (versionedReceipt.getRev() < 1){
				throw new DAOException("EntityDAO returned shop with revision < 0!");
			} else if (versionedReceipt.getRev() > 1){
				pstmt = _db.prepareStatement("UPDATE receipt SET " +
						"sid = ?, " +
						"draft = ?, " +
						"created_at = ? " +
				"WHERE rid = ?");

				//Set Shop
				if(versionedReceipt.getShop()==null)
					pstmt.setNull(1, Types.BIGINT);
				else
					pstmt.setLong(1, versionedReceipt.getShop().getId());

				//setDraft
				if(versionedReceipt.getDraft())
					pstmt.setBoolean(2, true);
				else
					pstmt.setBoolean(2, false);

				//setDate
				if(versionedReceipt.getDate()==null)
					pstmt.setDate(3, new java.sql.Date(new Date().getTime()));
				else
					pstmt.setDate(3, new java.sql.Date(versionedReceipt.getDate().getTime()));

				//set WHERE
				pstmt.setLong(4, versionedReceipt.getId());
				pstmt.executeUpdate();



				//Set Price
				//Remove old
				String sql = "DELETE FROM receiptentry " +
				"WHERE rid = ?";
				pstmt = _db.prepareStatement(sql);
				pstmt.setLong(1, versionedReceipt.getId());
				pstmt.executeUpdate();

				//Add new
				for(Product pd:versionedReceipt.getProducts()){
					pstmt = _db.prepareStatement("INSERT INTO receiptentry " +
					"(rid, pid, price) VALUES (?,?,?)");

					pstmt.setLong(1, versionedReceipt.getId());
					pstmt.setLong(2, pd.getId());
					pstmt.setLong(3, pd.getAvgPrice().getAmount());

					pstmt.executeUpdate();
				}

			}
			return versionedReceipt;
		} catch (SQLException e) {
			String msg = "Failed to save receipt. SQLException: "+e.getMessage()+".";
			ReceiptDAO._log.error(msg+" Chaining and rethrowing.");
			ReceiptDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
}