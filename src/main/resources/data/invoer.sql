drop table geb if exists;

create view geb as

select g.recordid, g.gemnr, p.gemnaam, g.jaar, g.aktenr, g.idnr, g.gebkode, g.oversamp,
g.ondrzko, g.opdrnri, g.opdrnr, g.datum, g.init, g.versie, g.opdrnro, g.datumo, g.inito, g.versieo
from gebakte as g
left join plaats as p
on g.gemnr = p.gemnr and g.gemnr > 0

union all

select g.recordid, g.gemnr, p.gemnaam, g.jaar, g.aktenr, g.idnr, 1 as gebkode, g.oversamp,
g.ondrzko, g.opdrnri, g.opdrnr, g.datum, g.init, g.versie, g.opdrnro, g.datumo, g.inito, g.versieo
from gebknd as g
left join plaats as p
on g.gemnr = p.gemnr and g.gemnr > 0;
