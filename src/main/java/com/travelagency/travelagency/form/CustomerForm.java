package com.travelagency.travelagency.form;

import com.travelagency.travelagency.model.CustomerDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;


public class CustomerForm extends FormLayout {

    TextField firstName = new TextField("First name");
    TextField login = new TextField("Login");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    Button update = new Button("Update");


    Binder<CustomerDto> binder = new Binder<>(CustomerDto.class);

    public CustomerForm () {
        addClassName("customer-form");
        //binder.bind(id, obj -> obj.getId() + "", null);
        binder.bindInstanceFields(this);

        add(firstName, lastName, login, email, createButtonsLayout());
    }

    public void setCustomerDto(CustomerDto customerDto) {
        binder.setBean(customerDto);
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

    private void validateAndUpdate() {
        if(binder.isValid()) {
            fireEvent(new UpdateEvent(this, binder.getBean()));
        }
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerForm> {
        private CustomerDto customer;

        protected CustomerFormEvent(CustomerForm source, CustomerDto customer) {


            super(source, false);
            this.customer = customer;
        }

        public CustomerDto getCustomer() {
            return customer;
        }
    }

    public static class SaveEvent extends CustomerFormEvent {
        SaveEvent(CustomerForm source, CustomerDto customer) {
            super(source, customer);
        }
    }

    public static class DeleteEvent extends CustomerFormEvent {
        DeleteEvent(CustomerForm source, CustomerDto customer) {
            super(source, customer);
        }

    }

    public static class UpdateEvent extends CustomerFormEvent {
        UpdateEvent(CustomerForm source, CustomerDto customer) {
            super(source, customer);
        }

    }

    public static class CloseEvent extends CustomerFormEvent {
        CloseEvent(CustomerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {


        return getEventBus().addListener(eventType, listener);
    }
}
