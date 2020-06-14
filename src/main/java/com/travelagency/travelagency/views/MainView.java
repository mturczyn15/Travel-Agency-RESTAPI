package com.travelagency.travelagency.views;

import com.travelagency.travelagency.MainLayout;
import com.travelagency.travelagency.client.CitiesClient;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route(value = "Intro", layout = MainLayout.class)
@PageTitle("Travel Agency || Intro")
public class MainView extends VerticalLayout {

    H4 labelDescription = new H4("Site for travel office employers for booking travels. \n");
    H4 labelCuriosities = new H4("Curiosities: \n");

    Grid<String> gridCities = new Grid<>(String.class);

    private CitiesClient citiesClient;


    public MainView(CitiesClient citiesClient) {
        this.citiesClient = citiesClient;

        addClassName("list-view");
        configureCitiesGrid();
        add(createIntroductory(), gridCities);
    }

    public List<String> getCitiesList(CitiesClient citiesClient) {
        String[] cities = citiesClient.getCities();
        return Arrays.asList(cities);
    }

    private void configureCitiesGrid() {
        gridCities.addClassName("cities-grid");
        gridCities.setItems(getCitiesList(citiesClient));
        gridCities.removeAllColumns();
        gridCities.addColumn(String::toString).setHeader("Amadeus API");
    }

    private Component createIntroductory() {

        return new VerticalLayout(labelDescription, labelCuriosities);
    }
}
