package com.travelagency.travelagency.views;

import com.travelagency.travelagency.MainLayout;
import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.form.CustomerForm;
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
@PageTitle("Travel Agency || Customers")
@Route(value = "customers", layout = MainLayout.class)
public class CustomerView extends VerticalLayout {

    H4 labelCustomer = new H4("Customers: \n");
    TextField filterText = new TextField();
    Grid<CustomerDto> gridCustomers = new Grid<>(CustomerDto.class);
    private final CustomerForm customerForm;
    private CustomerClient customerClient;

    public CustomerView(CustomerClient customerClient) {
        this.customerClient = customerClient;
        configureCustomersGrid();
        getToolbar();
        customerForm = new CustomerForm();
        customerForm.addListener(CustomerForm.UpdateEvent.class, this::updateCustomer);
        customerForm.addListener(CustomerForm.SaveEvent.class, this::saveCustomer);
        customerForm.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        customerForm.addListener(CustomerForm.CloseEvent.class, e -> closeEditor());

        add(labelCustomer, getToolbar(), gridCustomers, customerForm);
        closeEditor();
    }

    private void updateCustomer(CustomerForm.UpdateEvent evt) {
        customerClient.updateCustomer(evt.getCustomer());
        updateCustomersList();
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
        editCustomer(new CustomerDto());
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
        gridCustomers.setColumns("firstName", "lastName", "email", "login");
        gridCustomers.setHeightByRows(true);
        gridCustomers.asSingleSelect().addValueChangeListener(evt -> editCustomer(evt.getValue()));
        updateCustomersList();
    }

    private void editCustomer(CustomerDto value) {
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
}
