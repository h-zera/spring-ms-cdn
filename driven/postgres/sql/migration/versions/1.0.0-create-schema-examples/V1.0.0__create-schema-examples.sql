
CREATE TABLE public.CLIENTS (
    id SERIAL NOT NULL,
    client_id VARCHAR(255) NOT NULL,
    client_secret_hash VARCHAR(1000) NOT NULL,
    CREATION_TIME TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_TIME TIMESTAMP default CURRENT_TIMESTAMP,
    CONSTRAINT CLIENTS_PKEY PRIMARY KEY (id),
    CONSTRAINT CLIENTS_UQ1 UNIQUE (client_id)
);

DROP TYPE IF EXISTS allowed_feature;

CREATE TYPE allowed_feature AS ENUM (
    'FILE_SIGNING_ACCESS',
    'FOLDER_SIGNING_ACCESS',
    'ALL_CONFIGS',
    'EXPIRATION_CONFIG',
    'RED_RESTRICTION_CONFIG'
);

DROP TABLE IF EXISTS public.client_cdn_config CASCADE;

CREATE TABLE public.client_cdn_config (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  client_id varchar(255) UNIQUE NOT NULL,
  allowed_features allowed_feature[] NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  is_admin bool DEFAULT false,
  is_banned bool DEFAULT false,
  is_disabled bool DEFAULT true
);

CREATE TABLE public.cdn_scope_path(
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  config_id uuid REFERENCES public.client_cdn_config(id) ON DELETE CASCADE,
  path varchar(2000) NOT NULL,
  is_folder bool NOT NULL,
  subpaths_count integer default 0,
    constraint cdn_scope_path_uk1 unique (config_id, path)
);

CREATE TABLE public.cdn_resource(
    token_hash VARCHAR(255) PRIMARY KEY ,
    config_id uuid REFERENCES public.client_cdn_config(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    path VARCHAR(2000) NOT NULL,
    is_folder bool NOT NULL,
    remote_ip VARCHAR(100),
    query_params VARCHAR(2000) NOT NULL,
    requester_ip VARCHAR(100),
    mounted_url VARCHAR(4050) NOT NULL
);