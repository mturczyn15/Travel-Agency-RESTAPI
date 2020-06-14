package com.travelagency.travelagency.views;

import com.travelagency.travelagency.MainLayout;
import com.travelagency.travelagency.client.BookingClient;
import com.travelagency.travelagency.client.CustomerClient;
import com.travelagency.travelagency.client.HotelClient;
import com.travelagency.travelagency.client.TravelAgencyClient;
import com.travelagency.travelagency.form.BookingForm;
import com.travelagency.travelagency.model.BookingDto;
import com.travelagency.travelagency.model.CustomerDto;
import com.travelagency.travelagency.model.HotelDto;
import com.travelagency.travelagency.model.TravelAgencyDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@PageTitle("Travel Agency || Bookings")
@Route(value = "bookings", layout = MainLayout.class)
public class BookingView extends VerticalLayout {

    H4 labelBooking = new H4("Bookings: \n");
    TextField filterText = new TextField();
    Grid<BookingDto> gridBookings = new Grid<>(BookingDto.class);
    private final BookingForm bookingForm;

    private CustomerClient customerClient;
    private HotelClient hotelClient;
    private TravelAgencyClient travelAgencyClient;
    private BookingClient bookingClient;

    public BookingView(BookingClient bookingClient, CustomerClient customerClient, HotelClient hotelClient, TravelAgencyClient travelAgencyClient) {
        this.bookingClient = bookingClient;
        this.customerClient = customerClient;
        this.travelAgencyClient = travelAgencyClient;
        this.hotelClient = hotelClient;
        configureBookingsGrid();
        getToolbar();
        bookingForm = new BookingForm(getCustomersList(), customerClient, getHotelsList(), hotelClient, getTravelAgenciesList(), travelAgencyClient);
        bookingForm.addListener(BookingForm.UpdateEvent.class, this::updateBooking);
        bookingForm.addListener(BookingForm.SaveEvent.class, this::saveBooking);
        bookingForm.addListener(BookingForm.DeleteEvent.class, this::deleteBooking);
        bookingForm.addListener(BookingForm.CloseEvent.class, e -> closeEditor());

        add(labelBooking, getToolbar(), gridBookings, bookingForm);
        closeEditor();
    }

    private List<CustomerDto> getCustomersList() {
        CustomerDto[] allCustomers = customerClient.getCustomers();
        return Arrays.asList(allCustomers);
    }

    private List<HotelDto> getHotelsList() {
        HotelDto[] allHotels = hotelClient.getHotels();
        return Arrays.asList(allHotels);
    }

    private List<TravelAgencyDto> getTravelAgenciesList() {
        TravelAgencyDto[] allTravelAgencies = travelAgencyClient.getTravelAgencies();
        return Arrays.asList(allTravelAgencies);
    }

    private void updateBooking(BookingForm.UpdateEvent evt) {
        bookingClient.updateBooking(evt.getBooking());
        updateBookingsList();
    }

    private void deleteBooking(BookingForm.DeleteEvent evt) {
        bookingClient.deleteBooking(evt.getBooking());
        updateBookingsList();
        closeEditor();
    }

    private void saveBooking(BookingForm.SaveEvent evt) {
        bookingClient.createBooking(evt.getBooking());
        updateBookingsList();
        closeEditor();
    }

    private void closeEditor() {
        bookingForm.setBookingDto(null);
        bookingForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateBookingsList());

        Button addBookingButton = new Button("Add Booking", click -> addBooking());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBookingButton);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void addBooking() {
        gridBookings.asSingleSelect().clear();
        editBooking(new BookingDto());
    }

    public List<BookingDto> getBookingList() {
        BookingDto[] allBookings = bookingClient.getBookings();
        if (allBookings.length == 0) return new ArrayList<BookingDto>();
        return Arrays.asList(allBookings);
    }

    private void configureBookingsGrid() {

        gridBookings.addClassName("booking-grid");
        gridBookings.setColumns("customerId", "hotelId", "travelAgencyId", "price", "startDate", "endDate", "paymentType");
        gridBookings.setHeightByRows(true);
        gridBookings.asSingleSelect().addValueChangeListener(evt -> editBooking(evt.getValue()));
        updateBookingsList();
    }

    private void editBooking(BookingDto value) {
        if (value == null ) {
            closeEditor();
        } else {
            bookingForm.setBookingDto(value);
            bookingForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateBookingsList() {
        gridBookings.setItems(getBookingList());
    }
}