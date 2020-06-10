package com.travelagency.travelagency;

import com.travelagency.travelagency.client.CitiesClient;
import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.form.CustomerForm;
import com.travelagency.travelagency.model.CustomerDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route("main")
@StyleSheet("/css/style.css")
public class MainView extends VerticalLayout {

    Label labelTravelAgency = new Label("TRAVEL OFFICE \n");
    Label labelDescription = new Label("Site for travel office employers for booking travels. \n");
    Label labelCuriosities = new Label("Curiosities: \n");
    Label labelCustomer = new Label("Customers: \n");
    Grid<String> gridCities = new Grid<>(String.class);
    TextField filterText = new TextField();
    Grid<CustomerDto> gridCustomers = new Grid<>(CustomerDto.class);
    private final CustomerForm customerForm;
    private CitiesClient citiesClient;
    private CustomerClient customerClient;

    public MainView(CitiesClient citiesClient, CustomerClient customerClient) {
        this.citiesClient = citiesClient;
        this.customerClient = customerClient;
        addClassName("list-view");
        configureCitiesGrid();
        configureCustomersGrid();
        getToolbar();
        customerForm = new CustomerForm();
        customerForm.addListener(CustomerForm.UpdateEvent.class, this::updateCustomer);
        customerForm.addListener(CustomerForm.SaveEvent.class, this::saveCustomer);
        customerForm.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        customerForm.addListener(CustomerForm.CloseEvent.class, e -> closeEditor());

        add(createIntroductory(), gridCities, labelCustomer, getToolbar(), gridCustomers, customerForm);
        closeEditor();

    }

    private void updateCustomer(CustomerForm.UpdateEvent evt) {
        customerClient.updateCustomer(evt.getCustomer());
        updateCustomersList();
        closeEditor();
    }

    private void deleteCustomer(CustomerForm.DeleteEvent evt) {
        customerClient.deleteCustomer(evt.getCustomer());
        updateCustomersList();
        closeEditor();
    }

    private void saveCustomer(CustomerForm.SaveEvent evt) {
        customerClient.createCustomer(evt.getCustomer());
        updateCustomersList();
        closeEditor();
    }

    private void closeEditor() {
        customerForm.setCustomerDto(null);
        customerForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCustomersList());

        Button addCustomerButton = new Button("Add customer", click -> addCustomer());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerButton);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void addCustomer() {
        gridCustomers.asSingleSelect().clear();
        editContact(new CustomerDto());
    }

    public List<CustomerDto> getCustomerList() {
        if (filterText == null || filterText.isEmpty()) {
            CustomerDto[] allCustomers = customerClient.getCustomers();
            return Arrays.asList(allCustomers);
        }
        CustomerDto[] customersByName = customerClient.getCustomersByName(filterText.getValue());
        return Arrays.asList(customersByName);
    }

    private void configureCustomersGrid() {

        gridCustomers.addClassName("customer-grid");
        gridCustomers.setColumns("id", "firstName", "lastName", "email", "login");
        gridCustomers.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
        updateCustomersList();
    }

    private void editContact(CustomerDto value) {
        if (value == null ) {
            closeEditor();
        } else {
            customerForm.setCustomerDto(value);
            customerForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateCustomersList() {
        gridCustomers.setItems(getCustomerList());
    }

    public List<String> getCitiesList(CitiesClient citiesClient) {
        String[] cities = citiesClient.getCities();
        return Arrays.asList(cities);
    }

    private void configureCitiesGrid() {
        gridCities.addClassName("cities-grid");
        gridCities.setItems(getCitiesList(citiesClient));
        gridCities.removeAllColumns();
        gridCities.addColumn(String::toString).setHeader("Amadeus API");
    }

    private Component createIntroductory() {
        return new VerticalLayout(labelTravelAgency, labelDescription, labelCuriosities);
    }
}
