package org.infobase.web.view;

import org.infobase.model.Company;
import org.infobase.service.CompanyService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final CompanyService companyService;

    final Grid<Company> grid;

    final TextField filter;

    private final Button addBtn;
    private final Button editBtn;
    private final Button delBtn;

    public MainView(CompanyService companyService) {
        this.companyService = companyService;

        this.grid = new Grid<>();
        this.filter = new TextField("", "Поиск");
        this.addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        this.editBtn = new Button("Редактировать", VaadinIcon.PENCIL.create());
        this.delBtn = new Button("Удалить", VaadinIcon.MINUS.create());

        HorizontalLayout actions = new HorizontalLayout(addBtn, editBtn, delBtn, filter);
        add(actions, grid);

        grid.setHeight("300px");
        grid.addColumn(Company::getId).setHeader("ID");
        grid.addColumn(Company::getName).setHeader("Название");
        grid.addColumn(Company::getTin).setHeader("ИНН");
        grid.addColumn(Company::getAddress).setHeader("Адрес");
        grid.addColumn(Company::getPhoneNumber).setHeader("Телефонный номер");

        setItemsToGrid();
    }

    private void setItemsToGrid() {
        grid.setItems(companyService.getAll());
    }
}
