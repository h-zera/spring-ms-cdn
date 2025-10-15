
DROP TABLE public.O_EXAMPLES;

DROP SEQUENCE public.o_esamples_id_seq;

DROP TYPE identification_type;

DELETE FROM flyway_schema_history WHERE version = '1.0.0';
