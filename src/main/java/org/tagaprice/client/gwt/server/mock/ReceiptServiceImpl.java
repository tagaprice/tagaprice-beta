package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.Category;
import org.tagaprice.client.gwt.shared.entities.dump.Quantity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Product;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Address;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop;
import org.tagaprice.client.gwt.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Package;
import org.tagaprice.core.entities.Unit;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3420788026998858664L;

	@Override
	public IReceipt saveReceipt(IReceipt receipt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReceipt getReceipt(long receiptid) {

		Random random = new Random(1654196865);
		IRevisionId r1 = new RevisionId(random.nextLong(), 1);
		IRevisionId r2 = new RevisionId(random.nextLong(), 1);
		IAddress tempAddres = new Address(r2, "Holzhausergasse 9", "1020", "Vienna", Country.at, 48.21975481443672, 16.38885498046875);
		IShop tempshop = new Shop(r1, "Billa");
		tempAddres.setShop(tempshop);

		//Create test product
		//IReceipt tempReceipt = new Receipt("First Receipt", new Date(), tempAddres, new ArrayList<IReceiptEntry>());
		IReceipt tempReceipt = new Receipt("First Receipt", new Date(), tempAddres);


		//Add receipt entries
		{
			IPackage ipack = new Package(new Quantity(1.2, Unit.kg));
			IProduct iprodc = new Product("Bergkäse 4", new Category("food"), Unit.g);
			ipack.setProduct(iprodc);
			iprodc.addPackage(ipack);
			IReceiptEntry ire = new ReceiptEntry(new Price(15, Currency.dkk), ipack);
			tempReceipt.addReceiptEntriy(ire);
		}

		{
			IPackage ipack = new Package(new Quantity(1.5, Unit.l));
			IProduct iprodc = new Product("CocaCola", new Category("food"), Unit.l);
			ipack.setProduct(iprodc);
			iprodc.addPackage(ipack);
			IReceiptEntry ire = new ReceiptEntry(new Price(30, Currency.dkk), ipack);
			tempReceipt.addReceiptEntriy(ire);
		}


		return tempReceipt;
	}

	@Override
	public ArrayList<ReceiptEntry> getReceiptEntriesByProductId(long productid) {
		// TODO Auto-generated method stub
		return null;
	}

}
