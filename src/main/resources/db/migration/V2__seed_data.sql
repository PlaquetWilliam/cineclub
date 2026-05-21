-- Mots de passe : admin@cinema.fr -> Admin12345 | alice@mail.com -> User12345
INSERT INTO users (first_name, last_name, email, password, role) VALUES
('Jean', 'Admin', 'admin@cinema.fr', '$2a$12$d6CbcjJBb1nQsK0nxK6dyuRcVWXxBhQ7BrbljVmNT9D32NF9CSqvS', 'ADMIN'),
('Alice', 'Dupont', 'alice@mail.com', '$2a$12$781r7qdCKw6MR/xlMgR8B.qWI9TVLomWFV.JOnTZnyCVdngUoxDgi', 'USER');

INSERT INTO directors (first_name, last_name, nationality, birth_date) VALUES
('Christopher', 'Nolan', 'Britannique', '1970-07-30'),
('Greta', 'Gerwig', 'Américaine', '1983-08-04'),
('Denis', 'Villeneuve', 'Canadienne', '1967-10-03');

INSERT INTO movies (title, release_year, duration_minutes, genre, synopsis, director_id) VALUES
('Inception', 2010, 148, 'Science-Fiction', 'Un voleur qui s''approprie des secrets précieux par le biais du rêve.', 1),
('Oppenheimer', 2023, 180, 'Biopic', 'Le rôle du physicien J. Robert Oppenheimer dans la création de la bombe atomique.', 1),
('Barbie', 2023, 114, 'Comédie', 'Barbie, qui vit à Barbie Land, est expulsée et part pour le monde réel.', 2),
('Lady Bird', 2017, 94, 'Drame', 'Les aventures d''une jeune femme vivant à Sacramento.', 2),
('Dune', 2021, 155, 'Science-Fiction', 'L''histoire de Paul Atreides, voué à connaître un destin hors du commun.', 3);
