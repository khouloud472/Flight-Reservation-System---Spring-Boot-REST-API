-- src/main/resources/data.sql
-- Désactiver les contraintes de clé étrangère temporairement
SET REFERENTIAL_INTEGRITY FALSE;

-- Supprimer les tables dans le bon ordre (d'abord les enfants, puis les parents)
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS vol;

-- Réactiver les contraintes
SET REFERENTIAL_INTEGRITY TRUE;

-- Créer la table VOL
CREATE TABLE vol (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    destination VARCHAR(255) NOT NULL,
    depart VARCHAR(255) NOT NULL,
    date_depart VARCHAR(255),
    places_disponibles INT NOT NULL
);

-- Créer la table RESERVATION
CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_client VARCHAR(255) NOT NULL,
    nombre_places INT NOT NULL,
    vol_id BIGINT,
    FOREIGN KEY (vol_id) REFERENCES vol(id)
);

-- Insérer les données VOL
INSERT INTO vol (destination, depart, date_depart, places_disponibles) VALUES
('Paris', 'Lyon', '2024-01-15', 150),
('New York', 'Paris', '2024-01-20', 200),
('Londres', 'Marseille', '2024-01-25', 120),
('Tokyo', 'Paris', '2024-02-01', 180),
('Dubai', 'Lyon', '2024-02-05', 220);

-- Insérer les données RESERVATION
INSERT INTO reservation (nom_client, nombre_places, vol_id) VALUES
('Jean Dupont', 2, 1),
('Marie Martin', 1, 1),
('Pierre Durand', 3, 2),
('Sophie Lambert', 2, 3),
('Thomas Moreau', 1, 1);