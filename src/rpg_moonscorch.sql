-- Script de creación de base de datos para MOONSCORCH
-- Basado en el patrón MVC para un juego RPG con persistencia

-- Creación de la base de datos
DROP DATABASE IF EXISTS rpg_moonscorch;
CREATE DATABASE rpg_moonscorch CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE rpg_moonscorch;

-- Tabla para jugadores/usuarios
CREATE TABLE player (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
);

-- Tabla para clases de personajes (con estadísticas base)
CREATE TABLE character_class (
    class_id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    base_atk INT NOT NULL,
    base_def INT NOT NULL,
    base_eva INT NOT NULL,
    base_hp INT NOT NULL,
    base_mp INT NOT NULL,
    base_luk INT NOT NULL
);

-- Tabla para partidas guardadas
CREATE TABLE saved_game (
    save_id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT NOT NULL,
    character_class_id INT NOT NULL,
    character_name VARCHAR(50) NOT NULL,
    current_atk INT NOT NULL,
    current_def INT NOT NULL,
    current_eva INT NOT NULL,
    current_hp INT NOT NULL,
    current_max_hp INT NOT NULL,
    current_mp INT NOT NULL,
    current_max_mp INT NOT NULL,
    current_luk INT NOT NULL,
    experience_points INT NOT NULL DEFAULT 0,
    level INT NOT NULL DEFAULT 1,
    gold INT NOT NULL DEFAULT 0,
    play_time_seconds INT NOT NULL DEFAULT 0,
    current_node_id INT NULL,
    save_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES player(player_id) ON DELETE CASCADE,
    FOREIGN KEY (character_class_id) REFERENCES character_class(class_id)
);

-- Tabla para tipos de objetos
CREATE TABLE item_type (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Tabla para objetos del juego
CREATE TABLE game_item (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    type_id INT NOT NULL,
    description TEXT,
    value INT NOT NULL DEFAULT 0,
    weight DECIMAL(5,2) DEFAULT 0,
    atk_bonus INT DEFAULT 0,
    def_bonus INT DEFAULT 0,
    eva_bonus INT DEFAULT 0,
    hp_bonus INT DEFAULT 0,
    mp_bonus INT DEFAULT 0,
    luk_bonus INT DEFAULT 0,
    consumable BOOLEAN DEFAULT FALSE,
    equippable BOOLEAN DEFAULT FALSE,
    stackable BOOLEAN DEFAULT TRUE,
    max_stack INT DEFAULT 99,
    FOREIGN KEY (type_id) REFERENCES item_type(type_id)
);

-- Tabla para el inventario (relacionada con partidas guardadas)
CREATE TABLE inventory_item (
    save_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    is_equipped BOOLEAN DEFAULT FALSE,
    slot_position INT DEFAULT NULL,
    PRIMARY KEY (save_id, item_id),
    FOREIGN KEY (save_id) REFERENCES saved_game(save_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES game_item(item_id)
);

-- Tabla para nodos del mapa
CREATE TABLE map_node (
    node_id INT AUTO_INCREMENT PRIMARY KEY,
    node_name VARCHAR(100) NOT NULL,
    description TEXT,
    node_type ENUM('start', 'normal', 'boss', 'treasure', 'shop', 'rest') NOT NULL,
    x_coordinate INT NOT NULL,
    y_coordinate INT NOT NULL,
    is_secret BOOLEAN DEFAULT FALSE
);

-- Tabla para conexiones entre nodos (aristas del grafo)
CREATE TABLE map_connection (
    connection_id INT AUTO_INCREMENT PRIMARY KEY,
    from_node_id INT NOT NULL,
    to_node_id INT NOT NULL,
    is_bidirectional BOOLEAN DEFAULT TRUE,
    is_locked BOOLEAN DEFAULT FALSE,
    required_item_id INT NULL,
    FOREIGN KEY (from_node_id) REFERENCES map_node(node_id),
    FOREIGN KEY (to_node_id) REFERENCES map_node(node_id),
    FOREIGN KEY (required_item_id) REFERENCES game_item(item_id),
    UNIQUE KEY (from_node_id, to_node_id)
);

-- Tabla para el progreso en el mapa
CREATE TABLE map_progress (
    save_id INT NOT NULL,
    node_id INT NOT NULL,
    discovered BOOLEAN DEFAULT FALSE,
    visited BOOLEAN DEFAULT FALSE,
    completed BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (save_id, node_id),
    FOREIGN KEY (save_id) REFERENCES saved_game(save_id) ON DELETE CASCADE,
    FOREIGN KEY (node_id) REFERENCES map_node(node_id)
);

-- Tabla para enemigos
CREATE TABLE enemy (
    enemy_id INT AUTO_INCREMENT PRIMARY KEY,
    enemy_name VARCHAR(100) NOT NULL,
    description TEXT,
    atk INT NOT NULL,
    def INT NOT NULL,
    eva INT NOT NULL,
    hp INT NOT NULL,
    mp INT NOT NULL,
    luk INT NOT NULL,
    experience_reward INT NOT NULL DEFAULT 0,
    gold_reward INT NOT NULL DEFAULT 0,
    difficulty_level INT NOT NULL DEFAULT 1
);

-- Tabla para asignar enemigos a nodos
CREATE TABLE node_enemy (
    node_id INT NOT NULL,
    enemy_id INT NOT NULL,
    spawn_probability DECIMAL(5,2) NOT NULL DEFAULT 1.0,
    is_boss BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (node_id, enemy_id),
    FOREIGN KEY (node_id) REFERENCES map_node(node_id),
    FOREIGN KEY (enemy_id) REFERENCES enemy(enemy_id)
);

-- Tabla para tesoros/loot
CREATE TABLE treasure (
    treasure_id INT AUTO_INCREMENT PRIMARY KEY,
    treasure_name VARCHAR(100) NOT NULL,
    description TEXT,
    gold_min INT NOT NULL DEFAULT 0,
    gold_max INT NOT NULL DEFAULT 0,
    is_mimic BOOLEAN DEFAULT FALSE
);

-- Tabla para contenido de tesoros (items)
CREATE TABLE treasure_item (
    treasure_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity_min INT NOT NULL DEFAULT 1,
    quantity_max INT NOT NULL DEFAULT 1,
    drop_probability DECIMAL(5,2) NOT NULL DEFAULT 1.0,
    PRIMARY KEY (treasure_id, item_id),
    FOREIGN KEY (treasure_id) REFERENCES treasure(treasure_id),
    FOREIGN KEY (item_id) REFERENCES game_item(item_id)
);

-- Tabla para asignar tesoros a nodos
CREATE TABLE node_treasure (
    node_id INT NOT NULL,
    treasure_id INT NOT NULL,
    is_discovered BOOLEAN DEFAULT FALSE,
    is_looted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (node_id, treasure_id),
    FOREIGN KEY (node_id) REFERENCES map_node(node_id),
    FOREIGN KEY (treasure_id) REFERENCES treasure(treasure_id)
);

-- Tabla para loot de enemigos
CREATE TABLE enemy_loot (
    enemy_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity_min INT NOT NULL DEFAULT 1,
    quantity_max INT NOT NULL DEFAULT 1,
    drop_probability DECIMAL(5,2) NOT NULL DEFAULT 0.1,
    PRIMARY KEY (enemy_id, item_id),
    FOREIGN KEY (enemy_id) REFERENCES enemy(enemy_id),
    FOREIGN KEY (item_id) REFERENCES game_item(item_id)
);

-- Tabla para habilidades/skills
CREATE TABLE skill (
    skill_id INT AUTO_INCREMENT PRIMARY KEY,
    skill_name VARCHAR(100) NOT NULL,
    description TEXT,
    mp_cost INT NOT NULL DEFAULT 0,
    cooldown INT NOT NULL DEFAULT 0,
    damage_base INT DEFAULT 0,
    healing_base INT DEFAULT 0,
    effect_description TEXT
);

-- Tabla para asignar habilidades a clases
CREATE TABLE class_skill (
    class_id INT NOT NULL,
    skill_id INT NOT NULL,
    level_required INT NOT NULL DEFAULT 1,
    PRIMARY KEY (class_id, skill_id),
    FOREIGN KEY (class_id) REFERENCES character_class(class_id),
    FOREIGN KEY (skill_id) REFERENCES skill(skill_id)
);

-- Tabla para habilidades aprendidas por personaje
CREATE TABLE character_skill (
    save_id INT NOT NULL,
    skill_id INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    skill_level INT NOT NULL DEFAULT 1,
    PRIMARY KEY (save_id, skill_id),
    FOREIGN KEY (save_id) REFERENCES saved_game(save_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skill(skill_id)
);

-- Tabla para efectos de estado (buffs/debuffs)
CREATE TABLE status_effect (
    effect_id INT AUTO_INCREMENT PRIMARY KEY,
    effect_name VARCHAR(100) NOT NULL,
    description TEXT,
    atk_modifier INT DEFAULT 0,
    def_modifier INT DEFAULT 0,
    eva_modifier INT DEFAULT 0,
    hp_modifier_per_turn INT DEFAULT 0,
    mp_modifier_per_turn INT DEFAULT 0,
    duration_turns INT NOT NULL DEFAULT 1,
    is_negative BOOLEAN DEFAULT TRUE
);

-- Tabla para efectos activos en personaje
CREATE TABLE character_status (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    save_id INT NOT NULL,
    effect_id INT NOT NULL,
    remaining_turns INT NOT NULL,
    FOREIGN KEY (save_id) REFERENCES saved_game(save_id) ON DELETE CASCADE,
    FOREIGN KEY (effect_id) REFERENCES status_effect(effect_id)
);

-- Tabla para quests/misiones
CREATE TABLE quest (
    quest_id INT AUTO_INCREMENT PRIMARY KEY,
    quest_name VARCHAR(100) NOT NULL,
    description TEXT,
    gold_reward INT NOT NULL DEFAULT 0,
    experience_reward INT NOT NULL DEFAULT 0,
    item_reward_id INT NULL,
    item_reward_quantity INT DEFAULT 1,
    FOREIGN KEY (item_reward_id) REFERENCES game_item(item_id)
);

-- Tabla para objetivos de quests
CREATE TABLE quest_objective (
    objective_id INT AUTO_INCREMENT PRIMARY KEY,
    quest_id INT NOT NULL,
    objective_type ENUM('kill_enemy', 'collect_item', 'visit_node', 'defeat_boss') NOT NULL,
    target_id INT NOT NULL, -- ID del enemigo, item o nodo según el tipo
    quantity_required INT NOT NULL DEFAULT 1,
    description TEXT,
    FOREIGN KEY (quest_id) REFERENCES quest(quest_id)
);

-- Tabla para quests activas
CREATE TABLE character_quest (
    save_id INT NOT NULL,
    quest_id INT NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (save_id, quest_id),
    FOREIGN KEY (save_id) REFERENCES saved_game(save_id) ON DELETE CASCADE,
    FOREIGN KEY (quest_id) REFERENCES quest(quest_id)
);

-- Tabla para progreso de objetivos de quests
CREATE TABLE quest_objective_progress (
    save_id INT NOT NULL,
    objective_id INT NOT NULL,
    current_quantity INT NOT NULL DEFAULT 0,
    is_completed BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (save_id, objective_id),
    FOREIGN KEY (save_id) REFERENCES saved_game(save_id) ON DELETE CASCADE,
    FOREIGN KEY (objective_id) REFERENCES quest_objective(objective_id)
);

-- Tabla para tiendas
CREATE TABLE shop (
    shop_id INT AUTO_INCREMENT PRIMARY KEY,
    shop_name VARCHAR(100) NOT NULL,
    description TEXT,
    node_id INT NOT NULL,
    FOREIGN KEY (node_id) REFERENCES map_node(node_id)
);

-- Tabla para inventario de tiendas
CREATE TABLE shop_inventory (
    shop_id INT NOT NULL,
    item_id INT NOT NULL,
    price INT NOT NULL,
    quantity_available INT NULL, -- NULL significa stock ilimitado
    PRIMARY KEY (shop_id, item_id),
    FOREIGN KEY (shop_id) REFERENCES shop(shop_id),
    FOREIGN KEY (item_id) REFERENCES game_item(item_id)
);

-- Tabla para registro de eventos del juego
CREATE TABLE game_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    save_id INT NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    event_description TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (save_id) REFERENCES saved_game(save_id) ON DELETE CASCADE
);

-- Insertar datos iniciales para las clases de personajes
INSERT INTO character_class (class_name, description, base_atk, base_def, base_eva, base_hp, base_mp, base_luk) VALUES
('Ladrón', 'Especialista en evasión y golpes críticos. Baja defensa pero alta evasión.', 8, 5, 15, 80, 40, 12),
('Caballero', 'Guerrero con alta defensa y puntos de vida. Especialista en combate cuerpo a cuerpo.', 10, 12, 5, 120, 20, 6),
('Mago', 'Domina la magia arcana. Alta inteligencia y puntos de maná, pero baja defensa.', 5, 4, 8, 70, 100, 8);

-- Insertar tipos de objetos
INSERT INTO item_type (type_name, description) VALUES
('Poción', 'Objetos consumibles que restauran salud o maná'),
('Arma', 'Equipamiento para aumentar el ataque'),
('Armadura', 'Equipamiento para aumentar la defensa'),
('Accesorio', 'Equipamiento para efectos especiales'),
('Gema', 'Objetos valiosos que pueden venderse o usarse para mejoras'),
('Llave', 'Objetos que permiten acceder a áreas bloqueadas'),
('Pergamino', 'Objetos que enseñan habilidades o proporcionan efectos temporales');

-- Insertar algunos objetos básicos
INSERT INTO game_item (item_name, type_id, description, value, atk_bonus, def_bonus, hp_bonus, mp_bonus, consumable, equippable, stackable) VALUES
('Poción de Vida', 1, 'Restaura 50 puntos de vida', 25, 0, 0, 50, 0, TRUE, FALSE, TRUE),
('Poción de Maná', 1, 'Restaura 30 puntos de maná', 25, 0, 0, 0, 30, TRUE, FALSE, TRUE),
('Espada Corta', 2, 'Arma básica para combate cuerpo a cuerpo', 100, 5, 0, 0, 0, FALSE, TRUE, FALSE),
('Daga Afilada', 2, 'Arma ligera con bonus de crítico', 80, 3, 0, 0, 0, FALSE, TRUE, FALSE),
('Bastón de Roble', 2, 'Bastón básico para magos', 90, 2, 0, 0, 10, FALSE, TRUE, FALSE),
('Armadura de Cuero', 3, 'Protección básica', 120, 0, 3, 0, 0, FALSE, TRUE, FALSE),
('Túnica de Aprendiz', 3, 'Vestimenta básica para magos', 100, 0, 1, 0, 5, FALSE, TRUE, FALSE),
('Anillo de Vitalidad', 4, 'Aumenta la salud máxima', 200, 0, 0, 20, 0, FALSE, TRUE, FALSE),
('Gema Roja', 5, 'Gema valiosa con un brillo intenso', 150, 0, 0, 0, 0, FALSE, FALSE, TRUE),
('Llave de Bronce', 6, 'Abre puertas cerradas en el calabozo', 50, 0, 0, 0, 0, FALSE, FALSE, FALSE);

-- Insertar algunos nodos del mapa
INSERT INTO map_node (node_name, description, node_type, x_coordinate, y_coordinate) VALUES
('Entrada del Calabozo', 'El punto de inicio de la aventura', 'start', 0, 0),
('Pasillo Oscuro', 'Un corredor tenuemente iluminado', 'normal', 1, 0),
('Sala de Tesoros', 'Una habitación llena de cofres', 'treasure', 2, 0),
('Guarida del Jefe', 'La habitación donde reside el jefe del nivel', 'boss', 3, 0),
('Tienda del Viajero', 'Un comerciante ofrece sus mercancías', 'shop', 1, 1),
('Fuente Curativa', 'Una fuente mágica que restaura la salud', 'rest', 2, 1);

-- Insertar conexiones entre nodos
INSERT INTO map_connection (from_node_id, to_node_id, is_bidirectional) VALUES
(1, 2, TRUE),
(2, 3, TRUE),
(2, 5, TRUE),
(3, 4, TRUE),
(3, 6, TRUE),
(5, 6, TRUE);

-- Insertar algunos enemigos
INSERT INTO enemy (enemy_name, description, atk, def, eva, hp, mp, luk, experience_reward, gold_reward) VALUES
('Goblin', 'Criatura pequeña y agresiva', 5, 3, 5, 30, 0, 3, 20, 15),
('Esqueleto', 'Guerrero no-muerto', 7, 5, 3, 45, 0, 2, 30, 20),
('Murciélago Vampiro', 'Criatura voladora que succiona sangre', 4, 2, 10, 20, 0, 5, 15, 10),
('Mago Oscuro', 'Hechicero que domina la magia negra', 8, 4, 6, 40, 50, 7, 40, 35),
('Golem de Piedra', 'Criatura masiva hecha de roca', 10, 12, 1, 100, 0, 1, 80, 50);

-- Insertar enemigos en nodos
INSERT INTO node_enemy (node_id, enemy_id, spawn_probability) VALUES
(2, 1, 0.7),
(2, 3, 0.3),
(3, 2, 0.5),
(3, 4, 0.2),
(4, 5, 1.0);

-- Insertar tesoros
INSERT INTO treasure (treasure_name, description, gold_min, gold_max) VALUES
('Cofre Pequeño', 'Un cofre de madera con cerradura simple', 10, 30),
('Cofre Mediano', 'Un cofre reforzado con hierro', 30, 80),
('Cofre Grande', 'Un cofre ornamentado con detalles dorados', 80, 200),
('Cofre Trampa', 'Parece un cofre normal pero contiene una trampa', 0, 0);

-- Insertar items en tesoros
INSERT INTO treasure_item (treasure_id, item_id, quantity_min, quantity_max, drop_probability) VALUES
(1, 1, 1, 2, 0.8),
(1, 9, 1, 1, 0.3),
(2, 2, 1, 3, 0.7),
(2, 8, 1, 1, 0.4),
(3, 9, 2, 5, 0.9),
(3, 10, 1, 1, 0.5);

-- Insertar tesoros en nodos
INSERT INTO node_treasure (node_id, treasure_id) VALUES
(2, 1),
(3, 2),
(3, 3),
(4, 3);

-- Insertar loot de enemigos
INSERT INTO enemy_loot (enemy_id, item_id, quantity_min, quantity_max, drop_probability) VALUES
(1, 1, 1, 1, 0.3),
(2, 10, 1, 1, 0.1),
(3, 9, 1, 1, 0.2),
(4, 2, 1, 2, 0.5),
(5, 9, 2, 4, 0.8);

-- Insertar algunas habilidades
INSERT INTO skill (skill_name, description, mp_cost, cooldown, damage_base, healing_base) VALUES
('Golpe Rápido', 'Ataque veloz que ignora parte de la defensa enemiga', 5, 1, 15, 0),
('Escudo Protector', 'Aumenta temporalmente la defensa', 10, 3, 0, 0),
('Bola de Fuego', 'Lanza una esfera de fuego al enemigo', 15, 2, 25, 0),
('Curación', 'Restaura puntos de vida', 20, 3, 0, 30),
('Robo', 'Intenta robar un objeto al enemigo', 8, 4, 5, 0);

-- Asignar habilidades a clases
INSERT INTO class_skill (class_id, skill_id, level_required) VALUES
(1, 1, 1), -- Ladrón: Golpe Rápido
(1, 5, 3), -- Ladrón: Robo
(2, 1, 1), -- Caballero: Golpe Rápido
(2, 2, 2), -- Caballero: Escudo Protector
(3, 3, 1), -- Mago: Bola de Fuego
(3, 4, 2); -- Mago: Curación

-- Insertar efectos de estado
INSERT INTO status_effect (effect_name, description, atk_modifier, def_modifier, hp_modifier_per_turn, duration_turns, is_negative) VALUES
('Envenenado', 'Pierde vida cada turno', 0, 0, -5, 3, TRUE),
('Quemado', 'Sufre daño por quemaduras', 0, 0, -8, 2, TRUE),
('Fortalecido', 'Aumenta el ataque temporalmente', 5, 0, 0, 3, FALSE),
('Protegido', 'Aumenta la defensa temporalmente', 0, 5, 0, 3, FALSE),
('Debilitado', 'Reduce el ataque', -5, 0, 0, 2, TRUE);

-- Insertar tiendas
INSERT INTO shop (shop_name, description, node_id) VALUES
('Tienda del Viajero', 'Un comerciante ambulante que vende objetos útiles', 5);

-- Insertar inventario de tienda
INSERT INTO shop_inventory (shop_id, item_id, price, quantity_available) VALUES
(1, 1, 30, NULL), -- Poción de Vida (stock ilimitado)
(1, 2, 30, NULL), -- Poción de Maná (stock ilimitado)
(1, 3, 120, 1),   -- Espada Corta (stock limitado)
(1, 6, 150, 1),   -- Armadura de Cuero (stock limitado)
(1, 8, 250, 1);   -- Anillo de Vitalidad (stock limitado)

-- Insertar quests
INSERT INTO quest (quest_name, description, gold_reward, experience_reward, item_reward_id, item_reward_quantity) VALUES
('Limpieza de Goblins', 'Elimina a los goblins que infestan el calabozo', 100, 50, 9, 1),
('Tesoro Perdido', 'Encuentra el cofre grande escondido en el calabozo', 150, 75, 8, 1),
('Derrota al Golem', 'Vence al poderoso Golem de Piedra', 300, 150, 10, 1);

-- Insertar objetivos de quests
INSERT INTO quest_objective (quest_id, objective_type, target_id, quantity_required, description) VALUES
(1, 'kill_enemy', 1, 5, 'Elimina 5 goblins'),
(2, 'visit_node', 3, 1, 'Encuentra la Sala de Tesoros'),
(2, 'collect_item', 9, 3, 'Recolecta 3 Gemas Rojas'),
(3, 'defeat_boss', 5, 1, 'Derrota al Golem de Piedra');
