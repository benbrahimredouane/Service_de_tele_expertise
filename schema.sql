CREATE DATABASE tele_expertise_medicale;

USE tele_expertise_medicale;


CREATE TABLE utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    role ENUM('INFIRMIER', 'GENERALISTE') NOT NULL
);

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    numero_securite_sociale VARCHAR(50) NOT NULL UNIQUE,
    tension_arterielle VARCHAR(20),
    frequence_cardiaque INT,
    temperature DECIMAL(4,1),
    frequence_respiratoire INT,
    heure_arrivee DATETIME NOT NULL
);

CREATE TABLE consultations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    motif TEXT NOT NULL,
    observations TEXT,
    diagnostic TEXT,
    traitement TEXT,
    cout DECIMAL(10,2) NOT NULL DEFAULT 150.00,
    statut ENUM('EN_ATTENTE', 'EN_COURS', 'TERMINEE') NOT NULL DEFAULT 'EN_ATTENTE',

    CONSTRAINT fk_consultation_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients(id)
);

CREATE TABLE specialistes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    utilisateur_id INT NOT NULL UNIQUE,
    specialite ENUM(
        'CARDIOLOGIE',
        'DERMATOLOGIE',
        'NEUROLOGIE',
        'PEDIATRIE',
        'GYNECOLOGIE',
        'OPHTALMOLOGIE'
    ) NOT NULL,

    CONSTRAINT fk_specialiste_utilisateur
        FOREIGN KEY (utilisateur_id)
        REFERENCES utilisateurs(id)
);