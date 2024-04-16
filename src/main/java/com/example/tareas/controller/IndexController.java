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

    @FXML
    private TextField nombreTareaTxt;
    @FXML
    private TextField responsableTxt;
    @FXML
    private TextField estatusTxT;

    private Integer idTareaInterno;

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

    public void agregarTarea(){
        if(!nombreTareaTxt.getText().isEmpty()){

            Tarea tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tarea.setIdTarea(null);
            tareaService.guardarTarea(tarea);
            mostrarMensaje("Tarea agregada","Confirmado" );
            cleanForm();
            listarTareas();
        }else{

            mostrarMensaje("Debe proporcionar un nombre de la tarea","Error");
            nombreTareaTxt.requestFocus();

        }
    }
    public void eliminarTarea(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if(tarea != null){
            tareaService.eliminarTarea(tarea);
            mostrarMensaje("Se ha eliminado la tarea seleccionada de ID: " +tarea.getIdTarea(), "Información");
            listarTareas();
            cleanForm();
        }else{
            mostrarMensaje("No se ha seleccionado la tarea", "Error");
        }
    }
    public void editarTarea(){
        if(idTareaInterno == null){
            mostrarMensaje("Debe seleccionar una tarea", "Información");
        }else if(nombreTareaTxt.getText().isEmpty()){
            mostrarMensaje("Debe proporcionar un nombre de la tarea","Error");
        }else {
            var tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tareaService.guardarTarea(tarea);
            mostrarMensaje("Tarea modificada", "Confimado");
            listarTareas();
            cleanForm();

        }
    }
    private void recolectarDatosFormulario(Tarea tarea){
        if(idTareaInterno != null) {
            tarea.setIdTarea(idTareaInterno);
            tarea.setNombreTarea(nombreTareaTxt.getText());
            tarea.setResponsable(responsableTxt.getText());
            tarea.setEstatus(estatusTxT.getText());
        }else {
            tarea.setNombreTarea(nombreTareaTxt.getText());
            tarea.setResponsable(responsableTxt.getText());
            tarea.setEstatus(estatusTxT.getText());
        }

    }
    public void cleanForm(){
        idTareaInterno = null;
        nombreTareaTxt.clear();
        responsableTxt.clear();
        estatusTxT.clear();

    }
    private void mostrarMensaje(String mensaje, String titulo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    // Carga la tarea seleccionada del formulario
    public void cargarTarea(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();
        if(tarea != null){
            idTareaInterno = tarea.getIdTarea();
            nombreTareaTxt.setText(tarea.getNombreTarea());
            responsableTxt.setText(tarea.getResponsable());
            estatusTxT.setText(tarea.getEstatus());
        }
    }
}
