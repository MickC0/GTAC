--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-12-17 22:48:20

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS gtacdb;
--
-- TOC entry 4854 (class 1262 OID 16399)
-- Name: gtacdb; Type: DATABASE; Schema: -; Owner: gtacadmin
--

CREATE DATABASE gtacdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';


ALTER DATABASE gtacdb OWNER TO gtacadmin;

\connect gtacdb

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 137203)
-- Name: availability; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.availability (
    ending_time time(6) without time zone,
    starting_time time(6) without time zone,
    availability_id bigint NOT NULL,
    volunteer_id bigint,
    uuid uuid NOT NULL,
    day character varying(255),
    CONSTRAINT availability_day_check CHECK (((day)::text = ANY ((ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying])::text[])))
);


ALTER TABLE public.availability OWNER TO gtacadmin;

--
-- TOC entry 215 (class 1259 OID 137202)
-- Name: availability_availability_id_seq; Type: SEQUENCE; Schema: public; Owner: gtacadmin
--

CREATE SEQUENCE public.availability_availability_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.availability_availability_id_seq OWNER TO gtacadmin;

--
-- TOC entry 4855 (class 0 OID 0)
-- Dependencies: 215
-- Name: availability_availability_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gtacadmin
--

ALTER SEQUENCE public.availability_availability_id_seq OWNED BY public.availability.availability_id;


--
-- TOC entry 218 (class 1259 OID 137211)
-- Name: mission; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.mission (
    report_done boolean,
    required_volunteer_number integer,
    ending_date timestamp(6) without time zone,
    mission_id bigint NOT NULL,
    mission_type_id bigint NOT NULL,
    starting_date timestamp(6) without time zone,
    uuid uuid NOT NULL,
    comment character varying(255),
    description character varying(255),
    status character varying(255),
    title character varying(255),
    CONSTRAINT mission_status_check CHECK (((status)::text = ANY ((ARRAY['NEW'::character varying, 'PLANNED'::character varying, 'CONFIRMED'::character varying, 'ONGOING'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying])::text[])))
);


ALTER TABLE public.mission OWNER TO gtacadmin;

--
-- TOC entry 220 (class 1259 OID 137221)
-- Name: mission_assignment; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.mission_assignment (
    has_participated boolean,
    is_chief boolean,
    assigned_from timestamp(6) without time zone,
    assigned_until timestamp(6) without time zone,
    mission_assignment_id bigint NOT NULL,
    mission_id bigint,
    volunteer_id bigint,
    uuid uuid NOT NULL
);


ALTER TABLE public.mission_assignment OWNER TO gtacadmin;

--
-- TOC entry 219 (class 1259 OID 137220)
-- Name: mission_assignment_mission_assignment_id_seq; Type: SEQUENCE; Schema: public; Owner: gtacadmin
--

CREATE SEQUENCE public.mission_assignment_mission_assignment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mission_assignment_mission_assignment_id_seq OWNER TO gtacadmin;

--
-- TOC entry 4856 (class 0 OID 0)
-- Dependencies: 219
-- Name: mission_assignment_mission_assignment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gtacadmin
--

ALTER SEQUENCE public.mission_assignment_mission_assignment_id_seq OWNED BY public.mission_assignment.mission_assignment_id;


--
-- TOC entry 217 (class 1259 OID 137210)
-- Name: mission_mission_id_seq; Type: SEQUENCE; Schema: public; Owner: gtacadmin
--

CREATE SEQUENCE public.mission_mission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mission_mission_id_seq OWNER TO gtacadmin;

--
-- TOC entry 4857 (class 0 OID 0)
-- Dependencies: 217
-- Name: mission_mission_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gtacadmin
--

ALTER SEQUENCE public.mission_mission_id_seq OWNED BY public.mission.mission_id;


--
-- TOC entry 222 (class 1259 OID 137228)
-- Name: mission_type; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.mission_type (
    is_active boolean,
    mission_type_id bigint NOT NULL,
    uuid uuid NOT NULL,
    name character varying(20),
    description character varying(255)
);


ALTER TABLE public.mission_type OWNER TO gtacadmin;

--
-- TOC entry 221 (class 1259 OID 137227)
-- Name: mission_type_mission_type_id_seq; Type: SEQUENCE; Schema: public; Owner: gtacadmin
--

CREATE SEQUENCE public.mission_type_mission_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mission_type_mission_type_id_seq OWNER TO gtacadmin;

--
-- TOC entry 4858 (class 0 OID 0)
-- Dependencies: 221
-- Name: mission_type_mission_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gtacadmin
--

ALTER SEQUENCE public.mission_type_mission_type_id_seq OWNED BY public.mission_type.mission_type_id;


--
-- TOC entry 224 (class 1259 OID 137235)
-- Name: role; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.role (
    role_id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.role OWNER TO gtacadmin;

--
-- TOC entry 223 (class 1259 OID 137234)
-- Name: role_role_id_seq; Type: SEQUENCE; Schema: public; Owner: gtacadmin
--

CREATE SEQUENCE public.role_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.role_role_id_seq OWNER TO gtacadmin;

--
-- TOC entry 4859 (class 0 OID 0)
-- Dependencies: 223
-- Name: role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gtacadmin
--

ALTER SEQUENCE public.role_role_id_seq OWNED BY public.role.role_id;


--
-- TOC entry 226 (class 1259 OID 137242)
-- Name: unavailability; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.unavailability (
    end_date date,
    start_date date,
    unavailability_id bigint NOT NULL,
    volunteer_id bigint,
    uuid uuid NOT NULL
);


ALTER TABLE public.unavailability OWNER TO gtacadmin;

--
-- TOC entry 225 (class 1259 OID 137241)
-- Name: unavailability_unavailability_id_seq; Type: SEQUENCE; Schema: public; Owner: gtacadmin
--

CREATE SEQUENCE public.unavailability_unavailability_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.unavailability_unavailability_id_seq OWNER TO gtacadmin;

--
-- TOC entry 4860 (class 0 OID 0)
-- Dependencies: 225
-- Name: unavailability_unavailability_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gtacadmin
--

ALTER SEQUENCE public.unavailability_unavailability_id_seq OWNED BY public.unavailability.unavailability_id;


--
-- TOC entry 228 (class 1259 OID 137249)
-- Name: volunteer; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.volunteer (
    must_change_password boolean,
    volunteer_id bigint NOT NULL,
    uuid uuid NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    phone_number character varying(255)
);


ALTER TABLE public.volunteer OWNER TO gtacadmin;

--
-- TOC entry 229 (class 1259 OID 137257)
-- Name: volunteer_mission_type; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.volunteer_mission_type (
    mission_type_id bigint NOT NULL,
    volunteer_id bigint NOT NULL
);


ALTER TABLE public.volunteer_mission_type OWNER TO gtacadmin;

--
-- TOC entry 230 (class 1259 OID 137262)
-- Name: volunteer_role; Type: TABLE; Schema: public; Owner: gtacadmin
--

CREATE TABLE public.volunteer_role (
    role_id bigint NOT NULL,
    volunteer_id bigint NOT NULL
);


ALTER TABLE public.volunteer_role OWNER TO gtacadmin;

--
-- TOC entry 227 (class 1259 OID 137248)
-- Name: volunteer_volunteer_id_seq; Type: SEQUENCE; Schema: public; Owner: gtacadmin
--

CREATE SEQUENCE public.volunteer_volunteer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.volunteer_volunteer_id_seq OWNER TO gtacadmin;

--
-- TOC entry 4861 (class 0 OID 0)
-- Dependencies: 227
-- Name: volunteer_volunteer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gtacadmin
--

ALTER SEQUENCE public.volunteer_volunteer_id_seq OWNED BY public.volunteer.volunteer_id;


--
-- TOC entry 4672 (class 2604 OID 137206)
-- Name: availability availability_id; Type: DEFAULT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.availability ALTER COLUMN availability_id SET DEFAULT nextval('public.availability_availability_id_seq'::regclass);


--
-- TOC entry 4673 (class 2604 OID 137214)
-- Name: mission mission_id; Type: DEFAULT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission ALTER COLUMN mission_id SET DEFAULT nextval('public.mission_mission_id_seq'::regclass);


--
-- TOC entry 4674 (class 2604 OID 137224)
-- Name: mission_assignment mission_assignment_id; Type: DEFAULT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission_assignment ALTER COLUMN mission_assignment_id SET DEFAULT nextval('public.mission_assignment_mission_assignment_id_seq'::regclass);


--
-- TOC entry 4675 (class 2604 OID 137231)
-- Name: mission_type mission_type_id; Type: DEFAULT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission_type ALTER COLUMN mission_type_id SET DEFAULT nextval('public.mission_type_mission_type_id_seq'::regclass);


--
-- TOC entry 4676 (class 2604 OID 137238)
-- Name: role role_id; Type: DEFAULT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.role ALTER COLUMN role_id SET DEFAULT nextval('public.role_role_id_seq'::regclass);


--
-- TOC entry 4677 (class 2604 OID 137245)
-- Name: unavailability unavailability_id; Type: DEFAULT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.unavailability ALTER COLUMN unavailability_id SET DEFAULT nextval('public.unavailability_unavailability_id_seq'::regclass);


--
-- TOC entry 4678 (class 2604 OID 137252)
-- Name: volunteer volunteer_id; Type: DEFAULT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.volunteer ALTER COLUMN volunteer_id SET DEFAULT nextval('public.volunteer_volunteer_id_seq'::regclass);


--
-- TOC entry 4682 (class 2606 OID 137209)
-- Name: availability availability_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.availability
    ADD CONSTRAINT availability_pkey PRIMARY KEY (availability_id);


--
-- TOC entry 4686 (class 2606 OID 137226)
-- Name: mission_assignment mission_assignment_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission_assignment
    ADD CONSTRAINT mission_assignment_pkey PRIMARY KEY (mission_assignment_id);


--
-- TOC entry 4684 (class 2606 OID 137219)
-- Name: mission mission_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission
    ADD CONSTRAINT mission_pkey PRIMARY KEY (mission_id);


--
-- TOC entry 4688 (class 2606 OID 137233)
-- Name: mission_type mission_type_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission_type
    ADD CONSTRAINT mission_type_pkey PRIMARY KEY (mission_type_id);


--
-- TOC entry 4690 (class 2606 OID 137240)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- TOC entry 4692 (class 2606 OID 137247)
-- Name: unavailability unavailability_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.unavailability
    ADD CONSTRAINT unavailability_pkey PRIMARY KEY (unavailability_id);


--
-- TOC entry 4696 (class 2606 OID 137261)
-- Name: volunteer_mission_type volunteer_mission_type_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.volunteer_mission_type
    ADD CONSTRAINT volunteer_mission_type_pkey PRIMARY KEY (mission_type_id, volunteer_id);


--
-- TOC entry 4694 (class 2606 OID 137256)
-- Name: volunteer volunteer_pkey; Type: CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.volunteer
    ADD CONSTRAINT volunteer_pkey PRIMARY KEY (volunteer_id);


--
-- TOC entry 4704 (class 2606 OID 137305)
-- Name: volunteer_role fk621ytfqa9uikkhlm0v0ybhbjm; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.volunteer_role
    ADD CONSTRAINT fk621ytfqa9uikkhlm0v0ybhbjm FOREIGN KEY (volunteer_id) REFERENCES public.volunteer(volunteer_id);


--
-- TOC entry 4697 (class 2606 OID 137265)
-- Name: availability fk8wabet7fkrggnfap461sfjfk2; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.availability
    ADD CONSTRAINT fk8wabet7fkrggnfap461sfjfk2 FOREIGN KEY (volunteer_id) REFERENCES public.volunteer(volunteer_id);


--
-- TOC entry 4702 (class 2606 OID 137295)
-- Name: volunteer_mission_type fkbtqeievvde50wpfvvukly3e5p; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.volunteer_mission_type
    ADD CONSTRAINT fkbtqeievvde50wpfvvukly3e5p FOREIGN KEY (volunteer_id) REFERENCES public.volunteer(volunteer_id);


--
-- TOC entry 4699 (class 2606 OID 137280)
-- Name: mission_assignment fkc4l0lrsapn2x55yqajwr7f1j6; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission_assignment
    ADD CONSTRAINT fkc4l0lrsapn2x55yqajwr7f1j6 FOREIGN KEY (volunteer_id) REFERENCES public.volunteer(volunteer_id);


--
-- TOC entry 4698 (class 2606 OID 137270)
-- Name: mission fkeur3cml0f4dn4utdeqpw334xe; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission
    ADD CONSTRAINT fkeur3cml0f4dn4utdeqpw334xe FOREIGN KEY (mission_type_id) REFERENCES public.mission_type(mission_type_id);


--
-- TOC entry 4705 (class 2606 OID 137300)
-- Name: volunteer_role fkfe1fpoxlxf7pl8889bqo94qdc; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.volunteer_role
    ADD CONSTRAINT fkfe1fpoxlxf7pl8889bqo94qdc FOREIGN KEY (role_id) REFERENCES public.role(role_id);


--
-- TOC entry 4703 (class 2606 OID 137290)
-- Name: volunteer_mission_type fkfta5ohnaahp70ufp35oa00s86; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.volunteer_mission_type
    ADD CONSTRAINT fkfta5ohnaahp70ufp35oa00s86 FOREIGN KEY (mission_type_id) REFERENCES public.mission_type(mission_type_id);


--
-- TOC entry 4700 (class 2606 OID 137275)
-- Name: mission_assignment fkpty5ww2n5cb23tauh8ejwop7j; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.mission_assignment
    ADD CONSTRAINT fkpty5ww2n5cb23tauh8ejwop7j FOREIGN KEY (mission_id) REFERENCES public.mission(mission_id);


--
-- TOC entry 4701 (class 2606 OID 137285)
-- Name: unavailability fkr5xp20jfamivwn92pmdge85gr; Type: FK CONSTRAINT; Schema: public; Owner: gtacadmin
--

ALTER TABLE ONLY public.unavailability
    ADD CONSTRAINT fkr5xp20jfamivwn92pmdge85gr FOREIGN KEY (volunteer_id) REFERENCES public.volunteer(volunteer_id);


-- Completed on 2023-12-17 22:48:20

--
-- PostgreSQL database dump complete
--

