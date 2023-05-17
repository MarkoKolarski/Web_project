
INSERT INTO KORISNIK (id, ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga)
VALUES (1, 'Marko', 'Kolarski', 'user1', 'mkolarski@example.com', 'lozinka1', '2023-03-18 18:30:00', 'C:/Users/Marko Kolarski/Pictures/Saved Pictures/profilna_slika1.jpg', 'opis1', 1);
INSERT INTO KORISNIK (id, ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga)
VALUES (2, 'Nikola', 'Nikolic', 'user2', 'nikolanikolic@gmail.com','lozinka2', '1923-03-18 17:30:00', 'C:/Users/Marko Kolarski/Pictures/Saved Pictures/profilna_slika2.jpg','opis2', 0);
INSERT INTO KORISNIK (id, ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga)
VALUES (3, 'Marko', 'Markovic', 'user3', 'markomarkovic@gmail.com','lozinka3', '2023-09-25 11:30:00', 'C:/Users/Marko Kolarski/Pictures/Saved Pictures/profilna_slika3.jpg','opis3',1);

INSERT INTO AUTOR (id, aktivan) VALUES (1, TRUE);
INSERT INTO AUTOR (id, aktivan) VALUES (3, FALSE);
--INSERT INTO AUTOR (id, aktivan) VALUES (3, FALSE);

INSERT INTO POLICA (id, naziv, primarna) VALUES (1, 'polica1', TRUE);
INSERT INTO POLICA (id, naziv, primarna) VALUES (2, 'polica2', FALSE);

INSERT INTO KORISNIK_POLICA (korisnik_id, police_id) VALUES (2, 1);

INSERT INTO KNJIGA (id, naslov, ocena, naslovna_fotografija, ISBN, datum_objavljivanja, broj_strana, opis)
VALUES (1, 'Mali_princ', 9, 'C:/Users/Marko Kolarski/Pictures/Saved Pictures/naslovna_fotografija1.jpg', 123, '2023-04-15 14:30:00', 123, 'Opis1');
INSERT INTO KNJIGA (id, naslov, ocena, naslovna_fotografija, ISBN, datum_objavljivanja, broj_strana, opis)
VALUES (2, 'Hajduci', 8, 'C:/Users/Marko Kolarski/Pictures/Saved Pictures/naslovna_fotografija2.jpg', 233, '2018-07-15 9:30:00', 253, 'Opis2');

INSERT INTO STAVKA_POLICE (id, knjiga_id) VALUES (1, 1);
INSERT INTO STAVKA_POLICE (id, knjiga_id) VALUES (2, 2);


INSERT INTO SPISAK_KNJIGA (autor_id, knjiga_id) VALUES (1,1);
INSERT INTO SPISAK_KNJIGA (autor_id, knjiga_id) VALUES (3,2);

INSERT INTO RECENZIJA (id, datum_recenzije, ocena, tekst, korisnik_id) VALUES (1, '2013-04-13 12:30:00', 9, 'Tekst1', 1);
INSERT INTO RECENZIJA (id, datum_recenzije, ocena, tekst, korisnik_id) VALUES (2, '2003-04-03 11:30:15', 6, 'Tekst2', 2);

--INSERT INTO POLICA_STAVKA_POLICE (polica_id, stavke_police_id) VALUES (1, 1);
INSERT INTO IZBOR_STAVKE_POLICE (polica_id, stavka_police_id) VALUES (1, 1);

--INSERT INTO STAVKA_POLICE_KNJIGA (stavka_police_id, knjige_id) VALUES (1, 1);


INSERT INTO STAVKA_POLICE_RECENZIJA (stavka_police_id, recenzije_id) VALUES (1, 1);
INSERT INTO STAVKA_POLICE_RECENZIJA (stavka_police_id, recenzije_id) VALUES (1, 2);

INSERT INTO ZAHTEV (zahtev_id, datum, email, poruka, telefon, status, autor_id) VALUES (1, '2013-3-18 13:23:10', 'neki1@gmail.com', 'poruka1', 0600000000, 1, 1);
INSERT INTO ZAHTEV (zahtev_id, datum, email, poruka, telefon, status, autor_id) VALUES (2, '1912-2-18 11:23:10', 'neki2@gmail.com', 'poruka2', 0601111111, 2, 3);


INSERT INTO ZANR (id, naziv) VALUES (1, 'Horor');
INSERT INTO ZANR (id, naziv) VALUES (2, 'Komedija');

INSERT INTO IZBOR_ZANRA (knjiga_id, zanr_id) VALUES (1, 1);
INSERT INTO IZBOR_ZANRA (knjiga_id, zanr_id) VALUES (1, 2);
INSERT INTO IZBOR_ZANRA (knjiga_id, zanr_id) VALUES (2, 2);
