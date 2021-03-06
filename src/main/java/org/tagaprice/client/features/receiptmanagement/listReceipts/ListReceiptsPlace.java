package org.tagaprice.client.features.receiptmanagement.listReceipts;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ListReceiptsPlace extends Place {

	public ListReceiptsPlace() {
		// TODO Auto-generated constructor stub
	}

	@Prefix("ListReceipts")
	public static class Tokenizer implements PlaceTokenizer<ListReceiptsPlace>{

		@Override
		public ListReceiptsPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			if(e.getRoot().equals("show")){
				return new ListReceiptsPlace();
			}
			return null;
		}

		@Override
		public String getToken(ListReceiptsPlace place) {
			Log.debug("Tokenizer show receipts: id="+place);

			TokenCreator.Imploder t = TokenCreator.getImploder();
			t.setRoot("show");

			return t.getToken();
		}

	}
}
