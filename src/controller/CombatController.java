package controller;

import model.EnemyCombatData;
import model.PlayerCombatData;
import view.CombatLog;

import java.util.Random;

public class CombatController { //Aquí se muestran los controles de la ventana de combate

    private final PlayerCombatData jugador;
    private final EnemyCombatData enemigo;

    private boolean defensaActivaJugador = false;
    private boolean defensaActivaEnemigo = false;
    private boolean evasivoJugador = false;
    private boolean evasivoEnemigo = false;

    public CombatController(PlayerCombatData jugador, EnemyCombatData enemigo) {
        this.jugador = jugador;
        this.enemigo = enemigo;
    }

    public String elegirAccionEnemigo() {
        String[] acciones = {"Atacar", "Defender", "Evadir"};
        return acciones[new Random().nextInt(acciones.length)];
    }

    public void turnoEnemigoAccion(String accion, CombatLog log) {
        switch (accion) {
            case "Atacar" -> {
                int dano = enemigo.getATK();

                if (evasivoJugador && new Random().nextInt(100) < jugador.getEVA()) {
                    log.agregarLinea("¡Has evadido el ataque enemigo!");
                    evasivoJugador = false;
                    return;
                }

                if (defensaActivaJugador) {
                    dano -= jugador.getDEF();
                    defensaActivaJugador = false;
                }

                dano = Math.max(dano, 0);
                jugador.recibirDanio(dano);
                log.agregarLinea("El enemigo te ha hecho " + dano + " de daño. Vida restante: " + jugador.getHPActual());
            }
            case "Defender" -> {
                defensaActivaEnemigo = true;
                log.agregarLinea("El enemigo se defiende.");
            }
            case "Evadir" -> {
                evasivoEnemigo = true;
                log.agregarLinea("El enemigo se prepara para evadir.");
            }
        }
    }

    public void atacarJugador(CombatLog log) {
        int dano = jugador.getATK();

        if (evasivoEnemigo && new Random().nextInt(100) < enemigo.getEVA()) {
            log.agregarLinea("¡El enemigo evadió el ataque!");
            evasivoEnemigo = false;
            return;
        }

        if (defensaActivaEnemigo) {
            dano -= enemigo.getDEF();
            defensaActivaEnemigo = false;
        }

        dano = Math.max(dano, 0);
        enemigo.recibirDanio(dano);
        log.agregarLinea("Has infligido " + dano + " de daño al enemigo. Vida restante: " + enemigo.getHPActual());
    }

    public void defenderJugador() {
        defensaActivaJugador = true;
    }

    public void evadeJugador() {
        evasivoJugador = true;
    }

    public void especial1Jugador(CombatLog log) {
        int dano = jugador.getATK() * 2;
        enemigo.recibirDanio(dano);
        jugador.usarEspecial(1);
        log.agregarLinea("¡Especial I! Has causado " + dano + " de daño al enemigo. Vida restante: " + enemigo.getHPActual());
    }

    public void especial2Jugador(CombatLog log) {
        int rebote = 15;
        enemigo.recibirDanio(rebote);
        jugador.curar(rebote);
        jugador.usarEspecial(2);
        log.agregarLinea("¡Especial II! Rebotas daño al enemigo y recuperas HP: " + rebote + ". Vida del enemigo: " + enemigo.getHPActual());
    }

    public void recompensas(CombatLog log) {
        Random rand = new Random();
        boolean pocion = rand.nextInt(100) < 10;
        boolean gema = rand.nextInt(1000) < 5;

        if (pocion) {
            log.agregarLinea("¡Has obtenido una poción de salud!");
            jugador.agregarObjeto("Poción de Vida", 1);
        }

        if (gema) {
            log.agregarLinea("¡Has encontrado una gema de la suerte!");
            jugador.agregarObjeto("Gema de la Suerte", 1);
        }

        jugador.ganarExperiencia(10);
        log.agregarLinea("Has ganado 10 puntos de experiencia. Total: " + jugador.getExperiencia());
    }

    public boolean hayGanador() {
        return !jugador.estaVivo() || !enemigo.estaVivo();
    }

    public boolean jugadorGano() {
        return enemigo.getHPActual() <= 0;
    }

    public EnemyCombatData getEnemigo() {
        return enemigo;
    }
}
