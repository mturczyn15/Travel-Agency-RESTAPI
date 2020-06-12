package com.travelagency.travelagency.form;

import com.travelagency.travelagency.model.HotelDto;
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


public class HotelForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField city = new TextField("City");
    TextField stars = new TextField("Stars");
    TextField phoneNumber = new TextField("Phone Number");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    Button update = new Button("Update");


    Binder<HotelDto> binder = new Binder<>(HotelDto.class);

    public HotelForm () {
        addClassName("hotel-form");
        binder.bind(stars, obj -> obj.getStars() + "", null);
        binder.bindInstanceFields(this);

        add(name, city, stars, phoneNumber, createButtonsLayout());
    }

    public void setHotelDto(HotelDto hotelDto) {
        binder.setBean(hotelDto);
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

    public static abstract class HotelFormEvent extends ComponentEvent<HotelForm> {
        private HotelDto hotel;

        protected HotelFormEvent(HotelForm source, HotelDto hotel) {


            super(source, false);
            this.hotel = hotel;
        }

        public HotelDto getHotel() {
            return hotel;
        }
    }

    public static class SaveEvent extends HotelFormEvent {
        SaveEvent(HotelForm source, HotelDto hotel) {
            super(source, hotel);
        }
    }

    public static class DeleteEvent extends HotelFormEvent {
        DeleteEvent(HotelForm source, HotelDto hotel) {
            super(source, hotel);
        }

    }

    public static class UpdateEvent extends HotelFormEvent {
        UpdateEvent(HotelForm source, HotelDto hotel) {
            super(source, hotel);
        }

    }

    public static class CloseEvent extends HotelFormEvent {
        CloseEvent(HotelForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {


        return getEventBus().addListener(eventType, listener);
    }
}

