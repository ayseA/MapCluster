package com.ak.client;

import com.ak.MapCluster;
import com.google.gwt.user.client.Window;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(MapCluster.class)
public class MapClusterConnector extends AbstractComponentConnector {

    // ServerRpc is used to send events to server. Communication implementation
    // is automatically created here
    MapClusterServerRpc rpc = RpcProxy.create(MapClusterServerRpc.class, this);

    public MapClusterConnector() {
        
        // To receive RPC events from server, we register ClientRpc implementation 
        registerRpc(MapClusterClientRpc.class, Window::alert);

        // We choose listed for mouse clicks for the widget
        getWidget().addClickHandler(event -> {
                final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
                        .buildMouseEventDetails(event.getNativeEvent(),
                                getWidget().getElement());
                
                // When the widget is clicked, the event is sent to server with ServerRpc
                rpc.clicked(mouseDetails);
            }
        );

    }

    // We must implement getWidget() to cast to correct type 
    // (this will automatically create the correct widget type)
    @Override
    public MapClusterWidget getWidget() {
        return (MapClusterWidget) super.getWidget();
    }

    // We must implement getState() to cast to correct type
    @Override
    public MapClusterState getState() {
        return (MapClusterState) super.getState();
    }

    // Whenever the state changes in the server-side, this method is called
    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        // State is directly readable in the client after it is set in server
        final String text = getState().text;
        getWidget().setText(text);
    }
}
