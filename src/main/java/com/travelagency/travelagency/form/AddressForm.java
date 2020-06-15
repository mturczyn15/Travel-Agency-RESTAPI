package com.travelagency.travelagency.form;

import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.model.AddressDto;
import com.travelagency.travelagency.model.CustomerDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;
import java.util.Objects;


public class AddressForm extends FormLayout {

    TextField street = new TextField("Street");
    TextField houseNumber = new TextField("House Number");
    TextField city = new TextField("City");
    TextField zipCode = new TextField("Zip code");
    TextField phoneNumber = new TextField("Phone Number");
    ComboBox<CustomerDto> customerId = new ComboBox<>("Customer");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    Button update = new Button("Update");
    private List<CustomerDto> customers;
    private CustomerClient customerClient;

    Binder<AddressDto> binder = new Binder<>(AddressDto.class);

    public AddressForm (List<CustomerDto> customers, CustomerClient customerClient) {
        this.customers = customers;
        this.customerClient = customerClient;

        customerId.setItems(customers);
        customerId.setPlaceholder("Choose customer..");
        customerId.setClearButtonVisible(false);
        binder.forField(customerId).withConverter((this::extractID), v -> findCustomer(customerClient, v))
                .withValidator(
                        Objects::nonNull,
                        "You must choose Customer")
                .bind("customerId");


        addClassName("address-form");

        binder.bindInstanceFields(this);

        add(street, houseNumber, city, zipCode, phoneNumber, customerId, createButtonsLayout());
    }

    private CustomerDto findCustomer(CustomerClient customerClient, Integer id) {
        if (id == null)
            return new CustomerDto();
        return customerClient.getCustomer(id);
    }

    private Integer extractID(CustomerDto v) {
        return (v == null) ? null : v.getId();
    }

    public void setAddressDto(AddressDto addressDto) {
        binder.setBean(addressDto);
    }

    public static abstract class AddressFormEvent extends ComponentEvent<AddressForm> {
        private AddressDto address;

        protected AddressFormEvent(AddressForm source, AddressDto address) {


            super(source, false);
            this.address = address;
        }

        public AddressDto getAddress() {
            return address;
        }
    }

    public static class SaveEvent extends AddressForm.AddressFormEvent {
        SaveEvent(AddressForm source, AddressDto address) {
            super(source, address);
        }
    }

    public static class DeleteEvent extends AddressForm.AddressFormEvent {
        DeleteEvent(AddressForm source, AddressDto address) {
            super(source, address);
        }

    }

    public static class UpdateEvent extends AddressForm.AddressFormEvent {
        UpdateEvent(AddressForm source, AddressDto address) {
            super(source, address);
        }

    }

    public static class CloseEvent extends AddressForm.AddressFormEvent {
        CloseEvent(AddressForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {


        return getEventBus().addListener(eventType, listener);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    private void validateAndUpdate() {
        if(binder.isValid()) {
            fireEvent(new UpdateEvent(this, binder.getBean()));
        }
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        update.addClickListener(click -> validateAndUpdate());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, update, delete, close);
    }
}

