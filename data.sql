INSERT INTO utilisateurs
    (nom, email, mot_de_passe, role)
VALUES
    ('Dr Ahmed Alaoui', 'ahmed@gmail.com', 'password', 'GENERALISTE'),
    ('Dr Sara Amrani', 'sara@gmail.com', 'password', 'GENERALISTE');

INSERT INTO specialistes (utilisateur_id, specialite)
SELECT id, 'CARDIOLOGIE'
FROM utilisateurs
WHERE email = 'ahmed@gmail.com';

INSERT INTO specialistes (utilisateur_id, specialite)
SELECT id, 'DERMATOLOGIE'
FROM utilisateurs
WHERE email = 'sara@gmail.com';