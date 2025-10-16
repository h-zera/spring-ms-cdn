/*==============================================================*/
/* Table: O_EXAMPLES                                              */
/*==============================================================*/

CREATE TYPE identification_type AS ENUM ('DNI', 'NIE');

CREATE TABLE public.O_EXAMPLES (
                                   ID BIGSERIAL NOT NULL,
                                   CREATION_TIME TIMESTAMP NULL,
                                   DESCRIPTION VARCHAR(255) NULL,
                                   IDENTIFICATION VARCHAR(255) NULL,
                                   IDENTIFICATION_TYPE identification_type NULL,
                                   NAME VARCHAR(255) NULL,
                                   CONSTRAINT O_EXAMPLES_PKEY PRIMARY KEY (ID)
);

CREATE TABLE public.CLIENTS (
    id SERIAL NOT NULL,
    client_id VARCHAR(255) NOT NULL,
    client_secret_hash VARCHAR(1000) NOT NULL,
    CREATION_TIME TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_TIME TIMESTAMP default CURRENT_TIMESTAMP,
    CONSTRAINT CLIENTS_PKEY PRIMARY KEY (id),
    CONSTRAINT CLIENTS_UQ1 UNIQUE (client_id)
);

comment on table O_EXAMPLES is
    'Tabla que contiene los ejemplos';

comment on column O_EXAMPLES.ID is
    'Código del ejemplo.';

comment on column O_EXAMPLES.CREATION_TIME is
    'Fecha de creación del ejemplo.';

comment on column O_EXAMPLES.DESCRIPTION is
    'descripción del ejemplo.';

comment on column O_EXAMPLES.IDENTIFICATION is
    'Identificación del ejemplo.';

comment on column O_EXAMPLES.IDENTIFICATION_TYPE is
    'Tipo de identificación del ejemplo.';

comment on column O_EXAMPLES.NAME is
    'Nombre del ejemplo.';

