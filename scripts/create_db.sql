CREATE TABLE IF NOT EXISTS public.user
(
	id integer NOT NULL,
	first_name character varying(20) NOT NULL,
	last_name character varying(20) NOT NULL,
	email character varying(30) NOT NULL,
	password character varying(100) NOT NULL,
	description character varying(500),
	instructor boolean NOT NULL,
	registration_date date,
	token character varying(30),
	CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS public.course
(
	id integer NOT NULL,
	name character varying(30) NOT NULL,
	description character varying(500),
	user_id integer NOT NULL,
	creation_date date,
	CONSTRAINT course_pkey PRIMARY KEY (id),
	CONSTRAINT course_user_fk FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.course
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS public.section
(
	id integer NOT NULL,
	name character varying(30) NOT NULL,
	description character varying(500),
	course_id integer NOT NULL,
	creation_date date,
	CONSTRAINT section_pkey PRIMARY KEY (id),
	CONSTRAINT section_course_fk FOREIGN KEY (course_id)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.section
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS public.lesson
(
	id integer NOT NULL,
	name character varying(30) NOT NULL,
	content_text character varying(1000),
	video_location character varying(30),
	next_lesson integer,
	section_id integer NOT NULL,
	creation_date date,
	CONSTRAINT lesson_pkey PRIMARY KEY (id),
	CONSTRAINT lesson_section_fk FOREIGN KEY (section_id)
        REFERENCES public.section (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT lesson_fk FOREIGN KEY (next_lesson)
        REFERENCES public.lesson (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.lesson
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS public.rating
(
	id integer NOT NULL,
	description character varying(500),
	score integer,
	user_id integer NOT NULL,
	target_user integer,
	target_course integer,
	target_section integer,
	target_lesson integer,
	creation_date date,
	CONSTRAINT rating_pkey PRIMARY KEY (id),
	CONSTRAINT rating_user_fk FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT rating_target_user_fk FOREIGN KEY (target_user)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT rating_target_section_fk FOREIGN KEY (target_section)
        REFERENCES public.section (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT rating_target_course_fk FOREIGN KEY (target_course)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT rating_target_lesson_fk FOREIGN KEY (target_lesson)
        REFERENCES public.lesson (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.rating
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS public.comment
(
	id integer NOT NULL,
	content character varying(500),
	user_id integer NOT NULL,
	target integer NOT NULL,
	creation_date date,
	CONSTRAINT comment_pkey PRIMARY KEY (id),
	CONSTRAINT comment_user_fk FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT comment_lesson_fk FOREIGN KEY (target)
        REFERENCES public.lesson (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.comment
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS public.progress
(
	id integer NOT NULL,
	user_id integer NOT NULL,
	course_id integer,
	section_id integer,
	lesson_id integer,
	creation_date date,
	completed boolean,
	CONSTRAINT progress_pkey PRIMARY KEY (id),
	CONSTRAINT progress_user_fk FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT progress_lesson_fk FOREIGN KEY (lesson_id)
        REFERENCES public.lesson (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT progress_course_fk FOREIGN KEY (course_id)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT progress_section_fk FOREIGN KEY (section_id)
        REFERENCES public.section (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.progress
    OWNER to postgres;