package com.travelagency.travelagency.form;

import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.client.HotelClient;
import com.travelagency.travelagency.client.TravelAgencyClient;
import com.travelagency.travelagency.model.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class BookingForm extends FormLayout {

    ComboBox<Payment> paymentType = new ComboBox<>("Payment");
    BigDecimalField price = new BigDecimalField("Total cost");
    DatePicker startDate = new DatePicker("Start Date");
    DatePicker endDate = new DatePicker("End Date");
    ComboBox<CustomerDto> customerId = new ComboBox<>("Customer");
    ComboBox<HotelDto> hotelId = new ComboBox<>("Hotel");
    ComboBox<TravelAgencyDto> travelAgencyId = new ComboBox<>("Travel Agency");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    Button update = new Button("Update");
    private List<CustomerDto> customers;
    private List<HotelDto> hotels;
    private List<TravelAgencyDto> travelAgencies;
    private CustomerClient customerClient;
    private HotelClient hotelClient;
    private TravelAgencyClient travelAgencyClient;

    Binder<BookingDto> binder = new Binder<>(BookingDto.class);

    public BookingForm (List<CustomerDto> customers, CustomerClient customerClient,
                        List<HotelDto> hotels, HotelClient hotelClient,
                        List<TravelAgencyDto> travelAgencies, TravelAgencyClient travelAgencyClient) {
        this.customers = customers;
        this.customerClient = customerClient;
        this.hotels = hotels;
        this.hotelClient = hotelClient;
        this.travelAgencies = travelAgencies;
        this.travelAgencyClient = travelAgencyClient;

        paymentType.setItems(Payment.values());

        binder.forField(startDate).withConverter((this::convertLocalDateToDate), this::convertDateToLocalDate)
                .bind("startDate");

        binder.forField(endDate)
                .withValidator(
                endDate -> !endDate
                        .isBefore(startDate.getValue()),
                "Cannot return before departing")
                .withConverter((this::convertLocalDateToDate), this::convertDateToLocalDate)
                .bind("endDate");

        customerId.setItems(customers);
        binder.forField(customerId).withConverter((this::extractCustomerID), v -> findCustomer(customerClient, v))
                .withValidator(
                        Objects::nonNull,
                        "You must choose Customer")
                .bind("customerId");

        hotelId.setItems(hotels);
        binder.forField(hotelId).withConverter((this::extractHotelID), v -> findHotel(hotelClient, v))
                .withValidator(
                        Objects::nonNull,
                        "You must choose Hotel")
                .bind("hotelId");

        travelAgencyId.setItems(travelAgencies);
        binder.forField(travelAgencyId).withConverter((this::extractTravelAgencyID), v -> findTravelAgency(travelAgencyClient, v))
                .withValidator(
                        Objects::nonNull,
                        "You must choose Travel Agency")
                .bind("travelAgencyId");


        addClassName("booking-form");

        binder.bindInstanceFields(this);

        add(paymentType, price, startDate, endDate, customerId, hotelId, travelAgencyId, createButtonsLayout());
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
        delete.addClickListener(click -> fireEvent(new BookingForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new BookingForm.CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, update, delete, close);
    }

    private CustomerDto findCustomer(CustomerClient customerClient, Integer id) {
        // User selected blank, return blank
        if (id == null)
            return new CustomerDto();
        // Change this to properly interface with your service
        return customerClient.getCustomer(id);
    }

    private HotelDto findHotel(HotelClient hotelClient, Integer id) {
        // User selected blank, return blank
        if (id == null)
            return new HotelDto();
        // Change this to properly interface with your service
        return hotelClient.getHotel(id);
    }

    private TravelAgencyDto findTravelAgency(TravelAgencyClient travelAgencyClient, Integer id) {
        // User selected blank, return blank
        if (id == null)
            return new TravelAgencyDto();
        // Change this to properly interface with your service
        return travelAgencyClient.getTravelAgency(id);
    }

    private LocalDate convertDateToLocalDate(String date) {
        if (date == null)
            return LocalDate.now();
        return Date.valueOf(date).toLocalDate();
    }


    private String convertLocalDateToDate(LocalDate date) {
        if (date == null)
            return null;
        return Date.valueOf(date).toString();
    }

    private Integer extractCustomerID(CustomerDto v) {
        return (v == null) ? null : v.getId();
    }

    private Integer extractHotelID(HotelDto v) {
        return (v == null) ? null : v.getId();
    }

    private Integer extractTravelAgencyID(TravelAgencyDto v) {
        return (v == null) ? null : v.getId();
    }

    public void setBookingDto(BookingDto bookingDto) {
        binder.setBean(bookingDto);
    }

    public static abstract class BookingFormEvent extends ComponentEvent<BookingForm> {
        private BookingDto booking;

        protected BookingFormEvent(BookingForm source, BookingDto booking) {


            super(source, false);
            this.booking = booking;
        }

        public BookingDto getBooking() {
            return booking;
        }
    }

    public static class SaveEvent extends BookingForm.BookingFormEvent {
        SaveEvent(BookingForm source, BookingDto booking) {
            super(source, booking);
        }
    }

    public static class DeleteEvent extends BookingForm.BookingFormEvent {
        DeleteEvent(BookingForm source, BookingDto booking) {
            super(source, booking);
        }

    }

    public static class UpdateEvent extends BookingForm.BookingFormEvent {
        UpdateEvent(BookingForm source, BookingDto booking) {
            super(source, booking);
        }

    }

    public static class CloseEvent extends BookingForm.BookingFormEvent {
        CloseEvent(BookingForm source) {
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
}