
package com.moonscorch.dao;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD básicas.
 * 
 * @param <T> Tipo de entidad que maneja el DAO
 */
public interface DAO<T> {
    
    /**
     * Busca una entidad por su identificador.
     * 
     * @param id Identificador único de la entidad
     * @return La entidad encontrada o null si no existe
     */
    T findById(int id);
    
    /**
     * Recupera todas las entidades.
     * 
     * @return Lista con todas las entidades
     */
    List<T> findAll();
    
    /**
     * Guarda una nueva entidad.
     * 
     * @param entity Entidad a guardar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean save(T entity);
    
    /**
     * Actualiza una entidad existente.
     * 
     * @param entity Entidad con los datos actualizados
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean update(T entity);
    
    /**
     * Elimina una entidad por su identificador.
     * 
     * @param id Identificador único de la entidad a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean delete(int id);
}
