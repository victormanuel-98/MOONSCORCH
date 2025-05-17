package model;
/**
 * Representa un efecto de estado en el juego.
 */
public class StatusEffect {
    public enum EffectType {
        POISON("Veneno"),
        BURN("Quemadura"),
        FREEZE("Congelación"),
        STUN("Aturdimiento"),
        BLIND("Ceguera"),
        BUFF_ATK("Aumento ATK"),
        BUFF_DEF("Aumento DEF"),
        BUFF_EVA("Aumento EVA"),
        DEBUFF_ATK("Reducción ATK"),
        DEBUFF_DEF("Reducción DEF"),
        DEBUFF_EVA("Reducción EVA");
        
        private final String displayName;
        
        EffectType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private int id;
    private String name;
    private String description;
    private EffectType type;
    private int duration; // Número de turnos
    private int hpEffect; // Daño por turno (negativo) o curación (positivo)
    private int mpEffect;
    private int atkMod; // Modificador de estadísticas (positivo o negativo)
    private int defMod;
    private int evaMod;
    private boolean preventsActions; // Si impide realizar acciones
    
    // Campos adicionales para compatibilidad con GameState
    private int remainingTurns; // Turnos restantes
    private int lukModifier; // Modificador de suerte
    
    /**
     * Constructor por defecto.
     */
    public StatusEffect() {
        this.remainingTurns = 0;
        this.lukModifier = 0;
    }
    
    /**
     * Constructor con parámetros principales.
     */
    public StatusEffect(String name, String description, EffectType type, int duration) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.duration = duration;
        this.remainingTurns = duration; // Inicializar remainingTurns con duration
        this.hpEffect = 0;
        this.mpEffect = 0;
        this.atkMod = 0;
        this.defMod = 0;
        this.evaMod = 0;
        this.lukModifier = 0;
        this.preventsActions = false;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public EffectType getType() {
        return type;
    }
    
    public void setType(EffectType type) {
        this.type = type;
    }
    
    /**
     * Obtiene el tipo de efecto (alias para getType).
     * @return Tipo de efecto
     */
    public EffectType getEffectType() {
        return type;
    }
    
    /**
     * Establece el tipo de efecto (alias para setType).
     * @param effectType Tipo de efecto
     */
    public void setEffectType(EffectType effectType) {
        this.type = effectType;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
        // Actualizar remainingTurns si es necesario
        if (this.remainingTurns > duration) {
            this.remainingTurns = duration;
        }
    }
    
    public int getHpEffect() {
        return hpEffect;
    }
    
    public void setHpEffect(int hpEffect) {
        this.hpEffect = hpEffect;
    }
    
    public int getMpEffect() {
        return mpEffect;
    }
    
    public void setMpEffect(int mpEffect) {
        this.mpEffect = mpEffect;
    }
    
    public int getAtkMod() {
        return atkMod;
    }
    
    public void setAtkMod(int atkMod) {
        this.atkMod = atkMod;
    }
    
    public int getDefMod() {
        return defMod;
    }
    
    public void setDefMod(int defMod) {
        this.defMod = defMod;
    }
    
    public int getEvaMod() {
        return evaMod;
    }
    
    public void setEvaMod(int evaMod) {
        this.evaMod = evaMod;
    }
    
    public boolean isPreventsActions() {
        return preventsActions;
    }
    
    public void setPreventsActions(boolean preventsActions) {
        this.preventsActions = preventsActions;
    }
    
    /**
     * Reduce la duración del efecto en un turno.
     * @return true si el efecto sigue activo, false si ha terminado
     */
    public boolean reduceDuration() {
        this.duration--;
        this.remainingTurns--; // Actualizar también remainingTurns
        return this.duration > 0;
    }
    
    // Métodos adicionales para compatibilidad con GameState
    
    /**
     * Obtiene los turnos restantes del efecto.
     * @return Turnos restantes
     */
    public int getRemainingTurns() {
        return remainingTurns;
    }
    
    /**
     * Establece los turnos restantes del efecto.
     * @param remainingTurns Turnos restantes
     */
    public void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = Math.min(remainingTurns, duration);
    }
    
    /**
     * Obtiene el modificador de ataque (alias para getAtkMod).
     * @return Modificador de ataque
     */
    public int getAtkModifier() {
        return atkMod;
    }
    
    /**
     * Establece el modificador de ataque (alias para setAtkMod).
     * @param atkModifier Modificador de ataque
     */
    public void setAtkModifier(int atkModifier) {
        this.atkMod = atkModifier;
    }
    
    /**
     * Obtiene el modificador de defensa (alias para getDefMod).
     * @return Modificador de defensa
     */
    public int getDefModifier() {
        return defMod;
    }
    
    /**
     * Establece el modificador de defensa (alias para setDefMod).
     * @param defModifier Modificador de defensa
     */
    public void setDefModifier(int defModifier) {
        this.defMod = defModifier;
    }
    
    /**
     * Obtiene el modificador de evasión (alias para getEvaMod).
     * @return Modificador de evasión
     */
    public int getEvaModifier() {
        return evaMod;
    }
    
    /**
     * Establece el modificador de evasión (alias para setEvaMod).
     * @param evaModifier Modificador de evasión
     */
    public void setEvaModifier(int evaModifier) {
        this.evaMod = evaModifier;
    }
    
    /**
     * Obtiene el modificador de suerte.
     * @return Modificador de suerte
     */
    public int getLukModifier() {
        return lukModifier;
    }
    
    /**
     * Establece el modificador de suerte.
     * @param lukModifier Modificador de suerte
     */
    public void setLukModifier(int lukModifier) {
        this.lukModifier = lukModifier;
    }
    
    /**
     * Reduce la duración del efecto en un turno (alias para reduceDuration).
     * @return true si el efecto sigue activo, false si ha expirado
     */
    public boolean decrementTurn() {
        return reduceDuration();
    }
    
    /**
     * Verifica si el efecto es positivo (mejora).
     * @return true si el efecto es positivo, false en caso contrario
     */
    public boolean isPositive() {
        return type == EffectType.BUFF_ATK || type == EffectType.BUFF_DEF || type == EffectType.BUFF_EVA ||
               (atkMod > 0 || defMod > 0 || evaMod > 0 || lukModifier > 0 || hpEffect > 0 || mpEffect > 0);
    }
    
    /**
     * Verifica si el efecto es negativo (perjuicio).
     * @return true si el efecto es negativo, false en caso contrario
     */
    public boolean isNegative() {
        return type == EffectType.POISON || type == EffectType.BURN || type == EffectType.FREEZE || 
               type == EffectType.STUN || type == EffectType.BLIND || 
               type == EffectType.DEBUFF_ATK || type == EffectType.DEBUFF_DEF || type == EffectType.DEBUFF_EVA ||
               (atkMod < 0 || defMod < 0 || evaMod < 0 || lukModifier < 0 || hpEffect < 0 || mpEffect < 0 || preventsActions);
    }
    
    /**
     * Crea una copia del efecto de estado.
     * @return Una nueva instancia con los mismos valores
     */
    public StatusEffect copy() {
        StatusEffect copy = new StatusEffect();
        copy.id = this.id;
        copy.name = this.name;
        copy.description = this.description;
        copy.type = this.type;
        copy.duration = this.duration;
        copy.remainingTurns = this.remainingTurns;
        copy.hpEffect = this.hpEffect;
        copy.mpEffect = this.mpEffect;
        copy.atkMod = this.atkMod;
        copy.defMod = this.defMod;
        copy.evaMod = this.evaMod;
        copy.lukModifier = this.lukModifier;
        copy.preventsActions = this.preventsActions;
        return copy;
    }
    
    @Override
    public String toString() {
        return "StatusEffect{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", duration=" + duration + "/" + remainingTurns +
                ", hpEffect=" + hpEffect +
                ", mpEffect=" + mpEffect +
                ", atkMod=" + atkMod +
                ", defMod=" + defMod +
                ", evaMod=" + evaMod +
                ", lukMod=" + lukModifier +
                ", preventsActions=" + preventsActions +
                '}';
    }
}