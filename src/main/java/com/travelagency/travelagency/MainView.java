/*
package com.travelagency.travelagency;

import com.travelagency.travelagency.domain.Address;
import com.travelagency.travelagency.service.AddressService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {
    @Autowired
    private AddressService addressService;
    Grid<Address> grid = new Grid<>(Address.class);

    public MainView(AddressService addressService) {
        this.addressService = addressService;
        addClassName("list-view");
        setSizeFull();
        add(grid);
    }
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("id", "customer", "street");
    }

    private void updateList() {

    }
}
*/
