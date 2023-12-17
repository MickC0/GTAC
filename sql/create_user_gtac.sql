-- Création de l'utilisateur
-- A modifier suivant votre configuration
CREATE USER gtacadmin WITH PASSWORD 'gtacadmin';

-- Attribution des droits d'administrateur sur la base de données gtacdb
GRANT ALL PRIVILEGES ON DATABASE gtacdb TO gtacadmin;

-- Attribuer des droits sur toutes les tables actuelles et futures de la base de données gtacdb
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO gtacadmin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO gtacadmin;

-- Pour permettre à l'utilisateur de créer des tables et autres objets dans la base de données gtacdb
ALTER USER gtacadmin WITH CREATEDB;

-- Note : Ces commandes doivent être exécutées par un utilisateur ayant les privilèges nécessaires pour créer des utilisateurs et attribuer des droits.
