package controller;

import model.CharacterClass;
import view.CharacterMenu;

import java.util.List;

public class CharacterMenuController {

    private CharacterMenu view;
    private List<CharacterClass> characterClasses;

    public CharacterMenuController(CharacterMenu view, List<CharacterClass> characterClasses) {
        this.view = view;
        this.characterClasses = characterClasses;

        view.setLadronAction(e -> seleccionarClase("Ladrón"));
        view.setCaballeroAction(e -> seleccionarClase("Caballero"));
        view.setClerigoAction(e -> seleccionarClase("Clérigo"));
    }

    private void seleccionarClase(String nombreClase) {
        CharacterClass selected = characterClasses.stream()
                .filter(c -> c.getName().equalsIgnoreCase(nombreClase))
                .findFirst()
                .orElse(null);

        if (selected != null) {
            System.out.println("Clase seleccionada: " + selected.getName());
            // Aquí puedes agregar la lógica para avanzar a la siguiente vista
            // Por ejemplo, abrir ConfirmPlayer o DataPlayer con la clase seleccionada
        } else {
            System.err.println("Clase no encontrada: " + nombreClase);
        }
    }
}