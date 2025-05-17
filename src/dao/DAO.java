// Archivo: src/dao/DAO.java

package dao;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD en la base de datos.
 * 
 * @param <T> Tipo de entidad que maneja el DAO
 */
public interface DAO<T> {
    
    /**
     * Busca una entidad por su ID.
     * 
     * @param id ID de la entidad a buscar
     * @return Entidad encontrada o null si no existe
     */
    T findById(int id);
    
    /**
     * Busca todas las entidades.
     * 
     * @return Lista de todas las entidades
     */
    List<T> findAll();
    
    /**
     * Guarda una nueva entidad en la base de datos.
     * 
     * @param entity Entidad a guardar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean save(T entity);
    
    /**
     * Actualiza una entidad existente en la base de datos.
     * 
     * @param entity Entidad a actualizar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean update(T entity);
    
    /**
     * Elimina una entidad de la base de datos por su ID.
     * 
     * @param id ID de la entidad a eliminar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    boolean delete(int id);
}