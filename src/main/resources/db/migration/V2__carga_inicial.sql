insert into votacao values (1, 'Participante 1', 'Participante 2', current_timestamp, (SELECT current_timestamp + INTERVAL '2' DAY));
