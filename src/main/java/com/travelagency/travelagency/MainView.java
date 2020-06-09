package com.travelagency.travelagency;

import com.travelagency.travelagency.client.CitiesClient;
import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.model.CustomerDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
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
    private CitiesClient citiesClient;
    private CustomerClient customerClient;

    public MainView(CitiesClient citiesClient, CustomerClient customerClient) {
        this.citiesClient = citiesClient;
        this.customerClient = customerClient;
        addClassName("list-view");
        configureCitiesGrid();
        configureCustomersGrid();
        configureCustomersFilter();


        /*List<CustomerDto> customerDtos = Arrays.asList(customerClient.getCustomers());
        List<Integer> ids = customerDtos.stream()
                .map(CustomerDto::getId)
                .collect(Collectors.toList());


        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.setLabel("Customer");
       // comboBox.setItemLabelGenerator(ids.toString());
        comboBox.setItems(ids);
        Div value = new Div();
        value.setText("Select a value");
        comboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                value.setText("No option selected");

            } else {
                value.setText("Selected: " + event.getValue());
            }
        });*/

        add(createIntroductory(), gridCities, labelCustomer,filterText, gridCustomers);


    }

    private void configureCustomersFilter() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCustomersList());
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
        gridCustomers.setColumns("id", "firstName", "lastName", "email", "login", "mainAddressId");
        updateCustomersList();
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
