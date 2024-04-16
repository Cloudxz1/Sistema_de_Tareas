package com.example.tareas.controller;

import com.example.tareas.model.Tarea;
import com.example.tareas.service.TareaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private TareaService tareaService;

    @FXML
    private TableView<Tarea> tareaTabla;
    @FXML
    private TableColumn<Tarea,Integer> idTarea;
    @FXML
    private TableColumn<Tarea,String> nombreTarea;
    @FXML
    private TableColumn<Tarea,String> responsable;
    @FXML
    private TableColumn<Tarea,String> estatus;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configColumns();
        listarTareas();
    }
    public void configColumns(){
        idTarea.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        nombreTarea.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsable.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }
    private void listarTareas(){
        tareaList.clear();
        tareaList.addAll(tareaService.listarTareas());
        tareaTabla.setItems(tareaList);
    }
}
