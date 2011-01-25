package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Product;

/**
 * Stores information about a bought product, the quantity and the price (for quantity 1).
 * @author Helga
 *
 */
public interface IReceiptEntry  extends IEntity<IReceiptEntry>{

	public abstract int getQuantity();

	public abstract void setQuantity(int quantity);

	public abstract double getPrice();

	public abstract void setPrice(double price);

	public abstract Product getProduct();

	public abstract void setProduct(Product product);

	public abstract Receipt getReceipt();

	public abstract void setReceipt(Receipt receipt);

	public abstract IRevisionId getProductId();

	public abstract void setProductId(IRevisionId productId);

	public abstract IRevisionId getReceiptId();

	public abstract void setReceiptId(IRevisionId receiptId);












}
