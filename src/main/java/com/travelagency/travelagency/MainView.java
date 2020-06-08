package com.travelagency.travelagency;

import com.travelagency.travelagency.client.CitiesClient;
import com.travelagency.travelagency.client.CustomerClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Route("main")
@StyleSheet("/css/style.css")
public class MainView extends VerticalLayout {



    @Autowired
    public MainView(CitiesClient citiesClient, CustomerClient customerClient) {

        Label labelTravelAgency = new Label("TRAVEL AGENCY \n");
        Label labelDescription = new Label("Site for travel agency employers. \n");
        Label labelCuriosities = new Label("Curiosities: \n");

        String[] cities = citiesClient.getCities();
        List<String> list = Arrays.asList(cities);
        Grid<String> grid = new Grid<>(String.class);
        grid.setItems(list);
        grid.removeAllColumns();
        grid.addColumn(String::toString).setHeader("Amadeus API");

        Label labelBook = new Label("Book travel: \n");

        TextField textFieldName = new TextField("Choose customer");
        Button buttonName = new Button("Submit", new Icon(VaadinIcon.DIAMOND));
        Label labelName = new Label();
        buttonName.addClickListener(clickEvent -> {
            labelName.setText("Hello " + textFieldName.getValue());
            add(new Image("https://upload.wikimedia.org/wikipedia/commons/a/a2/Hello_%28yellow%29.png", "Brak obrazka"));
        });





        add(labelTravelAgency, labelDescription, labelCuriosities, grid);
    }

}
