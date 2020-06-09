/*
package com.travelagency.travelagency.form;

import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.model.Booking;
import com.travelagency.travelagency.model.CustomerDto;
import com.travelagency.travelagency.model.HotelDto;
import com.travelagency.travelagency.model.TravelAgencyDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;

import java.util.Arrays;
import java.util.List;


public class CustomerForm extends FormLayout {

    ComboBox<CustomerDto> customer = new ComboBox<>("Customer");
    //ComboBox<TravelAgencyDto> travelAgency = new ComboBox<>("TravelAgency");
    //ComboBox<HotelDto> hotel = new ComboBox<>("Hotel");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    Binder<Booking> binder = new Binder<>();



    public CustomerForm (CustomerClient customerClient) {
        List<CustomerDto> customers = Arrays.asList(customerClient.getCustomers());

        addClassName("customer-form");
        customer.setItems(CustomerDto::getId);
        customer.setItemLabelGenerator(CustomerDto::toString);
        add(customer, save, delete, close, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, close);
    }
}
*/
