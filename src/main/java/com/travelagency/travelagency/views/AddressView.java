package com.travelagency.travelagency.views;

import com.travelagency.travelagency.MainLayout;
import com.travelagency.travelagency.client.AddressClient;
import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.form.AddressForm;
import com.travelagency.travelagency.model.AddressDto;
import com.travelagency.travelagency.model.CustomerDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;
@PageTitle("Travel Agency || Addresses")
@Route(value = "addresses", layout = MainLayout.class)
public class AddressView extends VerticalLayout {

    H4 labelAddress = new H4("Addresses: \n");
    TextField filterText = new TextField();
    Grid<AddressDto> gridAddresses = new Grid<>(AddressDto.class);
    private final AddressForm addressForm;

    private CustomerClient customerClient;
    private AddressClient addressClient;

    public AddressView(AddressClient addressClient, CustomerClient customerClient) {
        this.addressClient = addressClient;
        this.customerClient = customerClient;
        configureAddressesGrid();
        getToolbar();
        addressForm = new AddressForm(getCustomersList(), customerClient);
        addressForm.addListener(AddressForm.UpdateEvent.class, this::updateAddress);
        addressForm.addListener(AddressForm.SaveEvent.class, this::saveAddress);
        addressForm.addListener(AddressForm.DeleteEvent.class, this::deleteAddress);
        addressForm.addListener(AddressForm.CloseEvent.class, e -> closeEditor());

        add(labelAddress, getToolbar(), gridAddresses, addressForm);
        closeEditor();
    }

    private List<CustomerDto> getCustomersList() {
        CustomerDto[] allCustomers = customerClient.getCustomers();
        return Arrays.asList(allCustomers);
    }

    private void updateAddress(AddressForm.UpdateEvent evt) {
        addressClient.updateAddress(evt.getAddress());
        updateAddressesList();
    }

    private void deleteAddress(AddressForm.DeleteEvent evt) {
        addressClient.deleteAddress(evt.getAddress());
        updateAddressesList();
        closeEditor();
    }

    private void saveAddress(AddressForm.SaveEvent evt) {
        addressClient.createAddress(evt.getAddress());
        updateAddressesList();
        closeEditor();
    }

    private void closeEditor() {
        addressForm.setAddressDto(null);
        addressForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by city");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateAddressesList());

        Button addAddressButton = new Button("Add Address", click -> addAddress());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addAddressButton);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void addAddress() {
        gridAddresses.asSingleSelect().clear();
        editAddress(new AddressDto());
    }

    public List<AddressDto> getAddressList() {
        if (filterText == null || filterText.isEmpty()) {
            AddressDto[] allAddresses = addressClient.getAddresses();
            return Arrays.asList(allAddresses);
        }
        AddressDto[] addresssByName = addressClient.getAddressesByName(filterText.getValue());
        return Arrays.asList(addresssByName);
    }

    private void configureAddressesGrid() {

        gridAddresses.addClassName("address-grid");
        gridAddresses.setColumns("street", "houseNumber", "city", "zipCode", "phoneNumber", "customerId");
        gridAddresses.setHeightByRows(true);
        gridAddresses.asSingleSelect().addValueChangeListener(evt -> editAddress(evt.getValue()));
        updateAddressesList();
    }

    private void editAddress(AddressDto value) {
        if (value == null ) {
            closeEditor();
        } else {
            addressForm.setAddressDto(value);
            addressForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateAddressesList() {
        gridAddresses.setItems(getAddressList());
    }
}

