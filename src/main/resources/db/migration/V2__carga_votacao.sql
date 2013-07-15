/* para o postgres, mas nao funciona no db2
insert into votacao values (1, 'Laisa', 'Yuri', current_timestamp, (SELECT current_timestamp + INTERVAL '2' DAY));
*/
/* para o db2, mas nao funciona no postgres */
insert into votacao values (1, 'Participante 1', 'Participante 2', current_timestamp, timestampadd('HOUR',3,current_timestamp));