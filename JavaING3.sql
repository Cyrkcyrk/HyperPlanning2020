-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Client :  localhost:3306
-- Généré le :  Dim 07 Juin 2020 à 15:36
-- Version du serveur :  10.0.38-MariaDB-0+deb8u1
-- Version de PHP :  7.0.33-0+deb9u3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `JavaING3`
--

-- --------------------------------------------------------

--
-- Structure de la table `cours`
--

CREATE TABLE `cours` (
  `id` int(255) NOT NULL,
  `nom` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `cours`
--

INSERT INTO `cours` (`id`, `nom`) VALUES
(1, 'Java'),
(2, 'Probabilité '),
(3, 'Traitement du signal '),
(4, 'Web Dynamique'),
(5, 'Initiation Réseaux'),
(6, 'Analyse de Fourier'),
(7, 'Analyse Financière'),
(8, 'English'),
(9, 'Amphi d\'information'),
(10, 'Droit du travail'),
(11, 'Antropologie'),
(12, 'Informatique C++');

-- --------------------------------------------------------

--
-- Structure de la table `enseignant`
--

CREATE TABLE `enseignant` (
  `id_utilisateur` int(255) NOT NULL,
  `id_cours` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `enseignant`
--

INSERT INTO `enseignant` (`id_utilisateur`, `id_cours`) VALUES
(13, 1),
(13, 4),
(13, 5),
(13, 9),
(13, 12),
(14, 3),
(15, 2),
(15, 6),
(15, 9),
(18, 3),
(18, 9),
(20, 10),
(21, 2),
(21, 6),
(22, 8),
(23, 11),
(24, 1),
(24, 4),
(25, 5),
(26, 2),
(26, 6),
(26, 9),
(27, 4),
(27, 9),
(28, 7);

-- --------------------------------------------------------

--
-- Structure de la table `etat_cours`
--

CREATE TABLE `etat_cours` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `etat_cours`
--

INSERT INTO `etat_cours` (`id`, `nom`) VALUES
(0, 'En cours de validation'),
(1, 'Validé'),
(2, 'Annulé');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `id_utilisateur` int(255) NOT NULL,
  `numero` int(255) NOT NULL,
  `id_groupe` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `etudiant`
--

INSERT INTO `etudiant` (`id_utilisateur`, `numero`, `id_groupe`) VALUES
(1, 10101, 1),
(2, 20202, 1),
(3, 30303, 2),
(4, 40404, 2),
(5, 50505, 3),
(6, 60606, 3),
(7, 70707, 4),
(8, 80808, 4),
(9, 90909, 5),
(10, 101010, 5),
(11, 111111, 6),
(12, 121212, 6);

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

CREATE TABLE `groupe` (
  `id` int(255) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `id_promotion` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `groupe`
--

INSERT INTO `groupe` (`id`, `nom`, `id_promotion`) VALUES
(1, 'TD1', 1),
(2, 'TD2', 1),
(3, 'TD1', 2),
(4, 'TD2', 2),
(5, 'TD1', 3),
(6, 'TD2', 3);

-- --------------------------------------------------------

--
-- Structure de la table `promotion`
--

CREATE TABLE `promotion` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `promotion`
--

INSERT INTO `promotion` (`id`, `nom`) VALUES
(1, 'ING1'),
(2, 'ING2'),
(3, 'ING3');

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

CREATE TABLE `salle` (
  `id` int(255) NOT NULL,
  `nom` varchar(60) NOT NULL,
  `capacite` int(255) NOT NULL,
  `id_site` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `salle`
--

INSERT INTO `salle` (`id`, `nom`, `capacite`, `id_site`) VALUES
(1, 'EM09', 150, 1),
(2, 'EM10', 150, 1),
(3, 'P330', 100, 2),
(4, 'P415', 30, 2),
(5, 'G006', 25, 3),
(6, 'G007', 25, 3),
(7, 'P417', 25, 2),
(8, 'P317', 25, 2),
(9, 'P445', 150, 2);

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

CREATE TABLE `seance` (
  `id` int(255) NOT NULL,
  `semaine` int(60) NOT NULL,
  `date` date NOT NULL,
  `heure_debut` time NOT NULL,
  `heure_fin` time NOT NULL,
  `etat` int(10) NOT NULL,
  `id_cours` int(255) NOT NULL,
  `id_type` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `seance`
--

INSERT INTO `seance` (`id`, `semaine`, `date`, `heure_debut`, `heure_fin`, `etat`, `id_cours`, `id_type`) VALUES
(1, 21, '2020-05-18', '10:15:00', '11:45:00', 1, 1, 1),
(2, 21, '2020-05-18', '12:00:00', '13:30:00', 1, 3, 5),
(3, 21, '2020-05-18', '12:45:00', '13:30:00', 1, 2, 1),
(4, 21, '2020-05-18', '08:30:00', '10:00:00', 1, 1, 1),
(5, 21, '2020-05-18', '17:15:00', '18:45:00', 1, 2, 1),
(6, 21, '2020-05-18', '13:45:00', '15:15:00', 1, 3, 5),
(7, 25, '2020-06-17', '08:45:00', '10:00:00', 1, 1, 3),
(8, 23, '2020-06-02', '10:00:00', '11:30:00', 0, 1, 1),
(9, 23, '2020-06-05', '13:45:00', '15:15:00', 0, 2, 4),
(10, 23, '2020-06-04', '08:30:00', '10:00:00', 0, 3, 1),
(11, 23, '2020-06-04', '10:15:00', '11:45:00', 1, 3, 1),
(12, 23, '2020-06-05', '10:15:00', '11:45:00', 1, 5, 5),
(13, 23, '2020-06-05', '15:30:00', '17:00:00', 2, 8, 2),
(14, 24, '2020-06-08', '08:30:00', '10:00:00', 1, 6, 3),
(15, 24, '2020-06-10', '13:45:00', '15:15:00', 0, 9, 3),
(16, 24, '2020-06-11', '10:15:00', '11:45:00', 0, 11, 3),
(17, 24, '2020-06-12', '15:30:00', '17:00:00', 0, 2, 4),
(18, 24, '2020-06-12', '17:15:00', '18:45:00', 2, 5, 5),
(19, 23, '2020-06-02', '17:15:00', '18:45:00', 1, 12, 5),
(20, 23, '2020-06-05', '10:00:00', '11:45:00', 0, 2, 3),
(21, 23, '2020-06-05', '15:30:00', '17:00:00', 0, 2, 4),
(22, 24, '2020-06-10', '08:30:00', '09:00:00', 0, 7, 1),
(23, 23, '2020-06-03', '10:15:00', '11:45:00', 1, 7, 1),
(24, 23, '2020-06-04', '08:30:00', '10:00:00', 1, 6, 4),
(25, 23, '2020-06-04', '10:15:00', '11:45:00', 1, 6, 4),
(26, 24, '2020-06-12', '10:15:00', '11:45:00', 1, 8, 2),
(27, 24, '2020-06-12', '19:00:00', '20:30:00', 1, 8, 2),
(28, 24, '2020-06-08', '08:30:00', '10:00:00', 1, 12, 1),
(29, 24, '2020-06-08', '10:15:00', '11:45:00', 1, 12, 1),
(30, 24, '2020-06-10', '13:45:00', '15:15:00', 1, 8, 2),
(31, 24, '2020-06-12', '10:00:00', '11:45:00', 1, 10, 1),
(32, 23, '2020-06-02', '13:45:00', '15:15:00', 1, 12, 3),
(33, 23, '2020-06-02', '15:30:00', '17:00:00', 1, 12, 5),
(34, 23, '2020-06-05', '17:15:00', '18:45:00', 1, 12, 5),
(35, 24, '2020-06-10', '15:30:00', '17:00:00', 1, 6, 1),
(36, 24, '2020-06-10', '17:15:00', '18:45:00', 1, 6, 1),
(37, 24, '2020-06-11', '08:00:00', '10:00:00', 1, 2, 9),
(38, 24, '2020-06-10', '15:30:00', '17:00:00', 1, 6, 1);

-- --------------------------------------------------------

--
-- Structure de la table `seance_enseignants`
--

CREATE TABLE `seance_enseignants` (
  `id_seance` int(255) NOT NULL,
  `id_enseignant` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `seance_enseignants`
--

INSERT INTO `seance_enseignants` (`id_seance`, `id_enseignant`) VALUES
(1, 13),
(2, 14),
(3, 15),
(4, 13),
(5, 15),
(6, 14),
(7, 13),
(8, 13),
(9, 21),
(10, 18),
(11, 18),
(12, 25),
(13, 22),
(14, 15),
(15, 13),
(15, 15),
(15, 18),
(15, 26),
(16, 23),
(17, 21),
(18, 25),
(19, 13),
(20, 15),
(21, 15),
(22, 28),
(23, 28),
(24, 15),
(25, 15),
(26, 22),
(27, 22),
(28, 13),
(29, 13),
(30, 22),
(31, 20),
(32, 13),
(33, 13),
(34, 13),
(35, 26),
(36, 26),
(37, 26),
(38, 15);

-- --------------------------------------------------------

--
-- Structure de la table `seance_groupes`
--

CREATE TABLE `seance_groupes` (
  `id_seance` int(255) NOT NULL,
  `id_groupe` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `seance_groupes`
--

INSERT INTO `seance_groupes` (`id_seance`, `id_groupe`) VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 1),
(3, 2),
(4, 1),
(4, 2),
(5, 1),
(5, 2),
(6, 2),
(7, 1),
(8, 5),
(8, 6),
(9, 6),
(10, 5),
(10, 6),
(11, 5),
(11, 6),
(12, 6),
(13, 5),
(14, 5),
(14, 6),
(15, 5),
(15, 6),
(16, 5),
(17, 6),
(18, 6),
(19, 1),
(20, 1),
(20, 2),
(21, 2),
(22, 1),
(22, 2),
(23, 3),
(23, 4),
(24, 4),
(25, 3),
(26, 2),
(27, 1),
(28, 3),
(28, 4),
(29, 3),
(29, 4),
(30, 4),
(31, 3),
(32, 3),
(32, 4),
(33, 4),
(34, 3),
(35, 3),
(35, 4),
(36, 3),
(36, 4),
(37, 5),
(38, 5),
(38, 6);

-- --------------------------------------------------------

--
-- Structure de la table `seance_salles`
--

CREATE TABLE `seance_salles` (
  `id_seance` int(255) NOT NULL,
  `id_salle` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `seance_salles`
--

INSERT INTO `seance_salles` (`id_seance`, `id_salle`) VALUES
(1, 3),
(2, 5),
(3, 3),
(4, 3),
(5, 3),
(6, 5),
(7, 2),
(8, 2),
(9, 5),
(10, 9),
(11, 9),
(12, 8),
(13, 4),
(14, 2),
(15, 9),
(16, 4),
(17, 4),
(18, 8),
(19, 6),
(20, 3),
(21, 6),
(22, 2),
(23, 9),
(24, 8),
(25, 8),
(26, 6),
(27, 8),
(28, 9),
(29, 9),
(30, 4),
(31, 7),
(32, 3),
(33, 4),
(34, 4),
(35, 2),
(36, 2),
(37, 2),
(38, 2);

-- --------------------------------------------------------

--
-- Structure de la table `site`
--

CREATE TABLE `site` (
  `id` int(255) NOT NULL,
  `nom` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `site`
--

INSERT INTO `site` (`id`, `nom`) VALUES
(1, 'Eiffel 1 (E1)'),
(2, 'Eiffel 2 (E2)'),
(3, 'Eiffel 4 (E4)');

-- --------------------------------------------------------

--
-- Structure de la table `type_cours`
--

CREATE TABLE `type_cours` (
  `id` int(255) NOT NULL,
  `nom` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `type_cours`
--

INSERT INTO `type_cours` (`id`, `nom`) VALUES
(1, 'Cours Magistral'),
(2, 'Cours Interactif'),
(3, 'Cours à distance (Zoom)'),
(4, 'TD'),
(5, 'TP'),
(6, 'Projet'),
(7, 'Soutien'),
(8, 'Soutenance'),
(9, 'Examen');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(255) NOT NULL,
  `email` varchar(60) NOT NULL,
  `passwd` varchar(60) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `Droit` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `passwd`, `nom`, `prenom`, `Droit`) VALUES
(1, 'herve.fanchon@edu.ece.fr', 'azerty', 'Fanchon ', 'Hervé', 4),
(2, 'raoul.desjardins@edu.ece.fr', 'azerty', 'Desjardins', 'Raoul', 4),
(3, 'etienne.couture@edu.ece.fr', 'azerty', 'Couture', 'Étienne ', 4),
(4, 'ansel.chatigny@edu.ece.fr', 'azerty', 'Chatigny', 'Ansel', 4),
(5, 'sibyla.grondin@edu.ece.fr', 'azerty', 'Grondin', 'Sibyla', 4),
(6, 'alexandre.loring@edu.ece.fr', 'azerty', 'Loring', 'Alexandre', 4),
(7, 'joseph.dupont@edu.ece.fr', 'azerty', 'Dupont', 'Joseph', 4),
(8, 'claire.garceau@edu.ece.fr', 'azerty', 'Garceau', 'Claire', 4),
(9, 'julien.genereux@edu.ece.fr', 'azerty', 'Généreux', 'Julien', 4),
(10, 'amarante.bourdette@edu.ece.fr', 'azerty', 'Bourdette', 'Amarante', 4),
(11, 'honore.brisebois@edu.ece.fr', 'azerty', 'Brisebois', 'Honoré', 4),
(12, 'fleurette.proulx@edu.ece.fr', 'azerty', 'Proulx', 'Fleurette', 4),
(13, 'jean-pierre.segado@ece.fr', 'azerty', 'Segado', 'Jean-Pierre', 3),
(14, 'thierry.minot@ece.fr', 'azerty', 'Minot', 'Thierry', 3),
(15, 'fabienne.coudray@ece.fr', 'azerty', 'Coudray', 'Fabienne', 3),
(16, 'secretariat@ece.fr', 'azerty', 'Service', 'Secretariat', 2),
(17, 'edt@ece.fr', 'azerty', 'Service', 'EDT', 1),
(18, 'arash.mokhber@ece.fr', 'azerty', 'Mokhber', 'Arash', 3),
(19, 'najma.guldner@ece.fr', 'azerty', 'Guldner', 'Najma', 3),
(20, 'yves.maupile@ece.fr', 'azerty', 'Maupile', 'Yves', 3),
(21, 'anis.chaari@ece.fr', 'azerty', 'Chaari', 'Anis', 3),
(22, 'james.reese@ece.fr', 'azerty', 'Reese', 'James', 3),
(23, 'bertrand.crimail@ece.fr', 'azerty', 'Crimail', 'Bertrand', 3),
(24, 'albin.morelle@ece.fr', 'azerty', 'Morelle', 'Albin', 3),
(25, 'larbi.boubchir@ece.fr', 'azerty', 'Boubchir', 'Larbi', 3),
(26, 'luc.lecor@ece.fr', 'azerty', 'Le Cor', 'Luc', 3),
(27, 'manolo.hina@ece.fr', 'azerty', 'Hina', 'Manolo', 3),
(28, 'samuel.godard@ece.fr', 'azerty', 'Godard', 'Samuel', 3);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD PRIMARY KEY (`id_utilisateur`,`id_cours`);

--
-- Index pour la table `etat_cours`
--
ALTER TABLE `etat_cours`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`id_utilisateur`);

--
-- Index pour la table `groupe`
--
ALTER TABLE `groupe`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `promotion`
--
ALTER TABLE `promotion`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `salle`
--
ALTER TABLE `salle`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `seance`
--
ALTER TABLE `seance`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `seance_enseignants`
--
ALTER TABLE `seance_enseignants`
  ADD PRIMARY KEY (`id_seance`,`id_enseignant`);

--
-- Index pour la table `seance_groupes`
--
ALTER TABLE `seance_groupes`
  ADD PRIMARY KEY (`id_seance`,`id_groupe`);

--
-- Index pour la table `seance_salles`
--
ALTER TABLE `seance_salles`
  ADD PRIMARY KEY (`id_seance`,`id_salle`);

--
-- Index pour la table `site`
--
ALTER TABLE `site`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_cours`
--
ALTER TABLE `type_cours`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `cours`
--
ALTER TABLE `cours`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT pour la table `etat_cours`
--
ALTER TABLE `etat_cours`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `groupe`
--
ALTER TABLE `groupe`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `promotion`
--
ALTER TABLE `promotion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `salle`
--
ALTER TABLE `salle`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT pour la table `seance`
--
ALTER TABLE `seance`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT pour la table `site`
--
ALTER TABLE `site`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `type_cours`
--
ALTER TABLE `type_cours`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
