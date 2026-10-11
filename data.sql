USE tele_expertise_medicale;
INSERT INTO utilisateurs (id, nom, email, mot_de_passe, role)
VALUES (
        1,
        'Specialiste One',
        'specialiste1@test.com',
        '$2a$12$1HuengwG32HQ5W3CtujWVeyfNYjScmtod47sw793g/d6yW7jvkocO23!',
        'SPECIALISTE'
    ),
    (
        2,
        'Specialiste Two',
        'specialiste2@test.com',
        '$2a$12$1HuengwG32HQ5W3CtujWVeyfNYjScmtod47sw793g/d6yW7jvkocO',
        'SPECIALISTE'
    ),
    (
        3,
        'Generaliste One',
        'generaliste@test.com',
        '$2a$12$1HuengwG32HQ5W3CtujWVeyfNYjScmtod47sw793g/d6yW7jvkocO',
        'GENERALISTE'
    );
INSERT INTO specialistes (id, utilisateur_id, specialite, tarif)
VALUES (1, 1, 'CARDIOLOGIE', 300.00),
    (2, 2, 'DERMATOLOGIE', 250.00);
INSERT INTO patients (
        id,
        nom,
        prenom,
        date_naissance,
        numero_securite_sociale,
        tension_arterielle,
        frequence_cardiaque,
        temperature,
        frequence_respiratoire,
        heure_arrivee
    )
VALUES (
        1,
        'Alaoui',
        'Youssef',
        '1990-05-12',
        'TEST-SS-001',
        '120/80',
        72,
        36.7,
        16,
        '2026-10-09 09:00:00'
    ),
    (
        2,
        'Amrani',
        'Salma',
        '1985-09-20',
        'TEST-SS-002',
        '130/85',
        80,
        37.0,
        18,
        '2026-10-09 10:00:00'
    ),
    (
        3,
        'Idrissi',
        'Amine',
        '2000-03-15',
        'TEST-SS-003',
        '118/78',
        70,
        36.6,
        15,
        '2026-10-09 11:00:00'
    );
INSERT INTO consultations (
        id,
        patient_id,
        motif,
        observations,
        diagnostic,
        traitement,
        cout,
        statut
    )
VALUES (
        1,
        1,
        'Douleur thoracique',
        'Douleur depuis ce matin',
        NULL,
        NULL,
        150.00,
        'EN_ATTENTE'
    ),
    (
        2,
        1,
        'Controle cardiaque',
        'Controle de routine',
        NULL,
        NULL,
        150.00,
        'EN_ATTENTE'
    ),
    (
        3,
        2,
        'Probleme dermatologique',
        'Eruption cutanee',
        NULL,
        NULL,
        150.00,
        'EN_COURS'
    ),
    (
        4,
        3,
        'Suivi medical',
        'Consultation de suivi',
        NULL,
        NULL,
        150.00,
        'TERMINEE'
    ),
    (
        5,
        2,
        'Examen dermatologique',
        'Examen complementaire',
        NULL,
        NULL,
        150.00,
        'EN_ATTENTE'
    );
INSERT INTO demande_expertise (
        id,
        consultation_id,
        specialiste_id,
        question,
        priorite,
        statut,
        avis,
        recommandations
    )
VALUES (
        1,
        1,
        1,
        'Evaluer la douleur thoracique',
        'URGENTE',
        'EN_ATTENTE',
        NULL,
        NULL
    ),
    (
        2,
        2,
        1,
        'Analyser le controle cardiaque',
        'NORMALE',
        'EN_ATTENTE',
        NULL,
        NULL
    ),
    (
        3,
        3,
        1,
        'Evaluer le probleme dermatologique',
        'NON_URGENTE',
        'EN_ATTENTE',
        NULL,
        NULL
    ),
    (
        4,
        4,
        1,
        'Demande deja terminee',
        'URGENTE',
        'TERMINEE',
        'Avis de test',
        'Suivi recommande'
    ),
    (
        5,
        1,
        2,
        'Deuxieme avis medical',
        'NORMALE',
        'EN_ATTENTE',
        NULL,
        NULL
    );