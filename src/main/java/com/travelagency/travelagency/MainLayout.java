package com.travelagency.travelagency;

import com.travelagency.travelagency.views.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 travel_office = new H1("Travel Office");
        travel_office.addClassName("travel");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), travel_office);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Intro", MainView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(listLink, new RouterLink("Customers", CustomerView.class)
                , new RouterLink("Addresses", AddressView.class)
                , new RouterLink("Hotels", HotelView.class)
                , new RouterLink("Travel Agencies", TravelAgencyView.class)
                , new RouterLink("Bookings", BookingView.class)
        ));
    }
}
