insert into adrestp (code, type)
values ('AN', 'Anders'),
       ('BO', 'Bootnaam'),
       ('GS', 'Gesticht, inrichting'),
       ('KB', 'Kostbaas'),
       ('LP', 'Ligplaats'),
       ('PL', 'Plaats binnen gemeente'),
       ('ST', 'Straat'),
       ('WK', 'Wijk of deel gemeente');

insert into beroep (berpnaam, nwinlst)
values ('Student', ' '),
       ('Apotheker', ' '),
       ('Arts', ' '),
       ('Bouwkundige', ' '),
       ('Geneesheer', ' '),
       ('Fabrikant', ' ');

insert into plaats (gemnr, provnr, regnr, gemnaam, nwinlst, regio, wageningen, euclidmidx, euclidmidy)
values (1501, 1, 0, 'Adorp', ' ', '', 1008, 23128, 58811),
       (1502, 1, 0, 'Aduard', ' ', '', 1044, 22646, 58456),
       (1503, 1, 0, 'Appingedam', ' ', '', 1027, 25234, 59302),
       (1504, 1, 0, 'Baflo', ' ', '', 1033, 23059, 59992),
       (1505, 1, 0, 'Bedum', ' ', '', 1055, 23600, 59005),
       (1506, 1, 0, 'Beerta', ' ', '', 1020, 27236, 57940);

insert into relatie (relkode, relatie, nwinlst)
values (-3, 'Geen relatie vast te stellen', ' '),
       (-2, 'Onduidelijk', ' '),
       (-1, 'Geen relatie gegeven', ' '),
       (1, 'Hoofd', ' '),
       (2, 'Echtgenote hoofd', ' '),
       (3, 'Zoon', ' '),
       (4, 'Dochter', ' '),
       (5, 'Pleegzoon', ' '),
       (6, 'Pleegdochter', ' '),
       (8, 'Stiefzoon', ' '),
       (9, 'Stiefdochter', ' '),
       (10, 'Grootvader', ' '),
       (11, 'Vader', ' '),
       (12, 'Broer', ' '),
       (13, 'Oom', ' '),
       (14, 'Neef [geen nadere specificatie]', ' '),
       (15, 'Neef [cousin]', ' '),
       (16, 'Neef [nephew]', ' '),
       (17, 'Achterneef', ' '),
       (18, 'Oudoom', ' '),
       (20, 'Grootmoeder', ' '),
       (21, 'Moeder', ' '),
       (22, 'Zuster', ' '),
       (23, 'Tante', ' '),
       (24, 'Nicht [geen nadere specificatie]', ' '),
       (25, 'Nicht [cousin]', ' '),
       (26, 'Nicht [niece]', ' '),
       (27, 'Achternicht', ' '),
       (28, 'Oudtante', ' ');

insert into kg (kode, kerkgeno, afgekort, omschrij, nwinlst)
values ('', '', 'Geen gdsd', '0', ''),
       ('Ad', 'Ad', 'Advent/ Z.A.', 'Zevendedag(s) Adventisten', ''),
       ('Ad', 'Advent', '', '', ''),
       ('Ad', 'Z.A.', '', '', ''),
       ('Ae', 'Ae', 'Ang.Episc/ A.E.', 'Anglicaanse Episcopale kerk', ''),
       ('Ae', 'Ang.Episc', '', '', ''),
       ('Ae', 'A.E.', '', '', ''),
       ('Ag', 'Ag', 'ApGen', 'Apostolische Genootschap', '');
