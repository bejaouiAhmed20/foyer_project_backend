-- Add UNIQUE constraints for reservations and students
ALTER TABLE etudiant ADD CONSTRAINT uk_etudiant_cin UNIQUE (cin);

ALTER TABLE reservation ADD CONSTRAINT uk_reservation_id UNIQUE (id_reservation);

-- Optional composite for active reservations per room/year
ALTER TABLE reservation ADD CONSTRAINT uk_reservation_room_year UNIQUE (chambre_idChambre, annee_universitaire, est_valide);
