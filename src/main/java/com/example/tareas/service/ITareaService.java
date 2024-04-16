package com.example.tareas.service;

import com.example.tareas.TareasApplication;
import com.example.tareas.model.Tarea;

import java.util.List;

public interface ITareaService {

    public List<Tarea> listarTareas();

    public Tarea buscarTareaPorId(Integer idTarea);

    public void guardarTarea(Tarea tarea);

    public void eliminarTarea(Tarea tarea);

}
