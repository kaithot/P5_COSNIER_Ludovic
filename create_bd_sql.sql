
CREATE TABLE public.projet (
                id INTEGER NOT NULL,
                nom VARCHAR(100) NOT NULL,
                CONSTRAINT projet_pk PRIMARY KEY (id)
);


CREATE SEQUENCE public.tache_id_seq;

CREATE TABLE public.tache (
                id INTEGER NOT NULL DEFAULT nextval('public.tache_id_seq'),
                nom VARCHAR(100) NOT NULL,
                date_creation DATE NOT NULL,
                CONSTRAINT tache_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.tache_id_seq OWNED BY public.tache.id;

CREATE TABLE public.liste (
                tache_id INTEGER NOT NULL,
                projet_id INTEGER NOT NULL,
                CONSTRAINT liste_pk PRIMARY KEY (tache_id, projet_id)
);


ALTER TABLE public.liste ADD CONSTRAINT projet_list_fk
FOREIGN KEY (projet_id)
REFERENCES public.projet (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.liste ADD CONSTRAINT tache_list_fk
FOREIGN KEY (tache_id)
REFERENCES public.tache (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
