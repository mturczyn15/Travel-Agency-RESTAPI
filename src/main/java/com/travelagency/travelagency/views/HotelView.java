package com.travelagency.travelagency.views;

import com.travelagency.travelagency.MainLayout;
import com.travelagency.travelagency.client.HotelClient;
import com.travelagency.travelagency.form.HotelForm;
import com.travelagency.travelagency.model.HotelDto;
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
@PageTitle("Travel Agency || Hotels")
@Route(value = "hotels", layout = MainLayout.class)
public class HotelView extends VerticalLayout {

    H4 labelHotel = new H4("Hotels: \n");
    TextField filterText = new TextField();
    Grid<HotelDto> gridHotels = new Grid<>(HotelDto.class);
    private final HotelForm hotelForm;
    private HotelClient hotelClient;

    public HotelView(HotelClient hotelClient) {
        this.hotelClient = hotelClient;
        configureHotelsGrid();
        getToolbar();
        hotelForm = new HotelForm();
        hotelForm.addListener(HotelForm.UpdateEvent.class, this::updateHotel);
        hotelForm.addListener(HotelForm.SaveEvent.class, this::saveHotel);
        hotelForm.addListener(HotelForm.DeleteEvent.class, this::deleteHotel);
        hotelForm.addListener(HotelForm.CloseEvent.class, e -> closeEditor());

        add(labelHotel, getToolbar(), gridHotels, hotelForm);
        closeEditor();
    }

    private void updateHotel(HotelForm.UpdateEvent evt) {
        hotelClient.updateHotel(evt.getHotel());
        updateHotelsList();
    }

    private void deleteHotel(HotelForm.DeleteEvent evt) {
        hotelClient.deleteHotel(evt.getHotel());
        updateHotelsList();
        closeEditor();
    }

    private void saveHotel(HotelForm.SaveEvent evt) {
        hotelClient.createHotel(evt.getHotel());
        updateHotelsList();
        closeEditor();
    }

    private void closeEditor() {
        hotelForm.setHotelDto(null);
        hotelForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateHotelsList());

        Button addHotelButton = new Button("Add Hotel", click -> addHotel());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addHotelButton);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void addHotel() {
        gridHotels.asSingleSelect().clear();
        editHotel(new HotelDto());
    }

    public List<HotelDto> getHotelList() {
        if (filterText == null || filterText.isEmpty()) {
            HotelDto[] allHotels = hotelClient.getHotels();
            return Arrays.asList(allHotels);
        }
        HotelDto[] hotelsByName = hotelClient.getHotelsByName(filterText.getValue());
        return Arrays.asList(hotelsByName);
    }

    private void configureHotelsGrid() {

        gridHotels.addClassName("hotel-grid");
        gridHotels.setColumns("name", "city", "stars", "phoneNumber");
        gridHotels.setHeightByRows(true);
        gridHotels.asSingleSelect().addValueChangeListener(evt -> editHotel(evt.getValue()));
        updateHotelsList();
    }

    private void editHotel(HotelDto value) {
        if (value == null ) {
            closeEditor();
        } else {
            hotelForm.setHotelDto(value);
            hotelForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateHotelsList() {
        gridHotels.setItems(getHotelList());
    }
}

