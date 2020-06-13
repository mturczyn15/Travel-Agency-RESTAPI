package com.travelagency.travelagency.form;

import com.travelagency.travelagency.model.TravelAgencyDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;


public class TravelAgencyForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField city = new TextField("City");
    TextField phoneNumber = new TextField("Phone Number");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    Button update = new Button("Update");


    Binder<TravelAgencyDto> binder = new Binder<>(TravelAgencyDto.class);

    public TravelAgencyForm () {
        addClassName("travelAgency-form");
        binder.bindInstanceFields(this);

        add(createButtonsLayout(), name, city, phoneNumber);
    }

    public void setTravelAgencyDto(TravelAgencyDto travelAgencyDto) {
        binder.setBean(travelAgencyDto);
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

    public static abstract class TravelAgencyFormEvent extends ComponentEvent<TravelAgencyForm> {
        private TravelAgencyDto travelAgency;

        protected TravelAgencyFormEvent(TravelAgencyForm source, TravelAgencyDto travelAgency) {


            super(source, false);
            this.travelAgency = travelAgency;
        }

        public TravelAgencyDto getTravelAgency() {
            return travelAgency;
        }
    }

    public static class SaveEvent extends TravelAgencyFormEvent {
        SaveEvent(TravelAgencyForm source, TravelAgencyDto travelAgency) {
            super(source, travelAgency);
        }
    }

    public static class DeleteEvent extends TravelAgencyFormEvent {
        DeleteEvent(TravelAgencyForm source, TravelAgencyDto travelAgency) {
            super(source, travelAgency);
        }

    }

    public static class UpdateEvent extends TravelAgencyFormEvent {
        UpdateEvent(TravelAgencyForm source, TravelAgencyDto travelAgency) {
            super(source, travelAgency);
        }

    }

    public static class CloseEvent extends TravelAgencyFormEvent {
        CloseEvent(TravelAgencyForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {


        return getEventBus().addListener(eventType, listener);
    }
}


