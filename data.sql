INSERT INTO utilisateurs
    (nom, email, mot_de_passe, role)
VALUES
    ('Dr Ahmed Alaoui', 'ahmed@gmail.com', 'password', 'SPECIALISTE'),
    ('Dr Sara Amrani', 'sara@gmail.com', 'password', 'SPECIALISTE');

INSERT INTO specialistes (utilisateur_id, specialite, tarif)
SELECT id, 'CARDIOLOGIE', 300.00
FROM utilisateurs
WHERE email = 'ahmed@gmail.com';

INSERT INTO specialistes (utilisateur_id, specialite, tarif)
SELECT id, 'DERMATOLOGIE', 250.00
FROM utilisateurs
WHERE email = 'sara@gmail.com';