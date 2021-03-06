package org.tagaprice.client.generics.widgets.desktopView;


import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.widgets.IInfoBox;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import static com.google.gwt.query.client.GQuery.*;

public class InfoBox extends Composite implements IInfoBox {

	private VerticalPanel _infos = new VerticalPanel();

	public InfoBox() {
		initWidget(_infos);
		_infos.setStyleName("infoBox");
	}

	@Override
	public void addInfoBoxEvent(InfoBoxShowEvent event) {
		SimpleInfo s = new SimpleInfo(event);
		$(s).hide();
		_infos.add(s);
		$(s).as(Effects).slideDown();
	}

	@Override
	public void removeInfoBoxEvent(InfoBoxDestroyEvent event) {
		Log.debug("destroy events");
		// Destroy all from this class

		if(event.getDesplayedEvent()!=null){
			for (int i = 0; i < _infos.getWidgetCount(); i++) {
				if(((SimpleInfo) _infos.getWidget(i)).getEvent().equals(event.getDesplayedEvent())){
					((SimpleInfo) _infos.getWidget(i)).removeNoSlideMe();
					i--;
				}
			}

		}else if (event.getType() == null) {
			for (int i = 0; i < _infos.getWidgetCount(); i++) {
				if (((SimpleInfo) _infos.getWidget(i)).getEvent().getSenderClass() == event.getDestroyerClass()) {
					((SimpleInfo) _infos.getWidget(i)).removeNoSlideMe();
					i--;
				}
			}
		} else {
			for (int i = 0; i < _infos.getWidgetCount(); i++) {
				if (((SimpleInfo) _infos.getWidget(i)).getEvent().getSenderClass() == event.getDestroyerClass() &&
						((SimpleInfo) _infos.getWidget(i)).getEvent().getType()==event.getType()) {
					((SimpleInfo) _infos.getWidget(i)).removeNoSlideMe();
					i--;
				}
			}
		}

		// TODO Auto-generated method stub

	}

	class SimpleInfo extends SimplePanel {

		InfoBoxShowEvent _event;

		public SimpleInfo(InfoBoxShowEvent event) {
			
			
			_event = event;

			if (event.getType() == INFOTYPE.SUCCESS) {
				setStyleName("success");
			} else if (event.getType() == INFOTYPE.INFO) {
				setStyleName("info");
			} else if (event.getType() == INFOTYPE.ERROR) {
				setStyleName("error");
			}

			if (event.getAutoCloseTime() > 0) {
				setWidget(new Label(event.getInfo()));

				Timer t = new Timer() {
					@Override
					public void run() {
						removeMe();
					}
				};

				t.schedule(event.getAutoCloseTime());

			} else {
				HorizontalPanel _hoPa = new HorizontalPanel();
				_hoPa.setWidth("100%");
				_hoPa.add(new Label(event.getInfo()));
				Label x = new Label("x");
				x.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						removeMe();
					}
				});
				_hoPa.add(x);
				_hoPa.setCellHorizontalAlignment(x, HorizontalPanel.ALIGN_RIGHT);
				_hoPa.setCellWidth(x, "1%");

				setWidget(_hoPa);
				
			}
			
			
		}

		public void removeMe() {
			$(this)
			.slideUp(new Function(){
				public void f() {
					removeNoSlideMe();
				}
			})
			;
			//removeFromParent();
		}
		
		public void removeNoSlideMe(){
			removeFromParent();
			System.out.println("removed from parent");
		}

		public InfoBoxShowEvent getEvent() {
			return _event;
		}
	}



}
