package com.travelagency.travelagency.views;

import com.travelagency.travelagency.MainLayout;
import com.travelagency.travelagency.client.TravelAgencyClient;
import com.travelagency.travelagency.form.TravelAgencyForm;
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

import java.util.Arrays;
import java.util.List;
@PageTitle("Travel Office || Travel Agencies")
@Route(value = "travelAgencies", layout = MainLayout.class)
public class TravelAgencyView extends VerticalLayout {

    H4 labelTravelAgency = new H4("TravelAgencies: \n");
    TextField filterText = new TextField();
    Grid<TravelAgencyDto> gridTravelAgencies = new Grid<>(TravelAgencyDto.class);
    private final TravelAgencyForm travelAgencyForm;
    private TravelAgencyClient travelAgencyClient;

    public TravelAgencyView(TravelAgencyClient travelAgencyClient) {
        this.travelAgencyClient = travelAgencyClient;
        configureTravelAgenciesGrid();
        getToolbar();
        travelAgencyForm = new TravelAgencyForm();
        travelAgencyForm.addListener(TravelAgencyForm.UpdateEvent.class, this::updateTravelAgency);
        travelAgencyForm.addListener(TravelAgencyForm.SaveEvent.class, this::saveTravelAgency);
        travelAgencyForm.addListener(TravelAgencyForm.DeleteEvent.class, this::deleteTravelAgency);
        travelAgencyForm.addListener(TravelAgencyForm.CloseEvent.class, e -> closeEditor());

        add(labelTravelAgency, getToolbar(), gridTravelAgencies, travelAgencyForm);
        closeEditor();
    }

    private void updateTravelAgency(TravelAgencyForm.UpdateEvent evt) {
        travelAgencyClient.updateTravelAgency(evt.getTravelAgency());
        updateTravelAgenciesList();
    }

    private void deleteTravelAgency(TravelAgencyForm.DeleteEvent evt) {
        travelAgencyClient.deleteTravelAgency(evt.getTravelAgency());
        updateTravelAgenciesList();
        closeEditor();
    }

    private void saveTravelAgency(TravelAgencyForm.SaveEvent evt) {
        travelAgencyClient.createTravelAgency(evt.getTravelAgency());
        updateTravelAgenciesList();
        closeEditor();
    }

    private void closeEditor() {
        travelAgencyForm.setTravelAgencyDto(null);
        travelAgencyForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateTravelAgenciesList());

        Button addTravelAgencyButton = new Button("Add travelAgency", click -> addTravelAgency());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addTravelAgencyButton);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void addTravelAgency() {
        gridTravelAgencies.asSingleSelect().clear();
        editTravelAgency(new TravelAgencyDto());
    }

    public List<TravelAgencyDto> getTravelAgencyList() {
        if (filterText == null || filterText.isEmpty()) {
            TravelAgencyDto[] allTravelAgencies = travelAgencyClient.getTravelAgencies();
            return Arrays.asList(allTravelAgencies);
        }
        TravelAgencyDto[] travelAgencysByName = travelAgencyClient.getTravelAgenciesByName(filterText.getValue());
        return Arrays.asList(travelAgencysByName);
    }

    private void configureTravelAgenciesGrid() {

        gridTravelAgencies.addClassName("travelAgency-grid");
        gridTravelAgencies.setColumns("name", "city", "phoneNumber");
        gridTravelAgencies.setHeightByRows(true);
        gridTravelAgencies.asSingleSelect().addValueChangeListener(evt -> editTravelAgency(evt.getValue()));
        updateTravelAgenciesList();
    }

    private void editTravelAgency(TravelAgencyDto value) {
        if (value == null ) {
            closeEditor();
        } else {
            travelAgencyForm.setTravelAgencyDto(value);
            travelAgencyForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateTravelAgenciesList() {
        gridTravelAgencies.setItems(getTravelAgencyList());
    }
}


