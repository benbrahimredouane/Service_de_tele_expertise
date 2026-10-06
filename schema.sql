CREATE DATABASE IF NOT EXISTS tele_expertise_medicale
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tele_expertise_medicale;

CREATE TABLE utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    role ENUM(
        'INFIRMIER',
        'GENERALISTE',
        'SPECIALISTE'
    ) NOT NULL
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
    statut ENUM(
        'EN_ATTENTE',
        'EN_COURS',
        'TERMINEE'
    ) NOT NULL DEFAULT 'EN_ATTENTE',

    CONSTRAINT fk_consultation_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients(id)
);

CREATE TABLE specialistes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    utilisateur_id INT NOT NULL UNIQUE,
    specialite ENUM(
        'CARDIOLOGIE',
        'PNEUMOLOGIE',
        'DERMATOLOGIE',
        'NEUROLOGIE',
        'ENDOCRINOLOGIE'
    ) NOT NULL,
    tarif DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_specialiste_utilisateur
        FOREIGN KEY (utilisateur_id)
        REFERENCES utilisateurs(id)
);

CREATE TABLE demande_expertise (
    id INT AUTO_INCREMENT PRIMARY KEY,
    consultation_id INT NOT NULL,
    specialiste_id INT NOT NULL,
    question TEXT NOT NULL,
    priorite ENUM(
        'URGENTE',
        'NORMALE',
        'NON_URGENTE'
    ) NOT NULL,
    statut ENUM(
        'EN_ATTENTE',
        'TERMINEE'
    ) NOT NULL DEFAULT 'EN_ATTENTE',
    avis TEXT,
    recommandations TEXT,
    date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_demande_consultation
        FOREIGN KEY (consultation_id)
        REFERENCES consultations(id),

    CONSTRAINT fk_demande_specialiste
        FOREIGN KEY (specialiste_id)
        REFERENCES specialistes(id)
);