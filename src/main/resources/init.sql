--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.0

-- Started on 2020-07-20 00:18:17 +04

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

--
-- TOC entry 3257 (class 1262 OID 42365)
-- Name: cafemanagement; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE cafemanagement WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';
CREATE USER test WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE cafemanagement TO test;


ALTER DATABASE cafemanagement OWNER TO postgres;

\connect cafemanagement

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
-- TOC entry 204 (class 1259 OID 42905)
-- Name: orders; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.orders (
    id integer NOT NULL,
    created timestamp without time zone,
    status character varying(255),
    creator_id integer,
    table_id integer
);


ALTER TABLE public.orders OWNER TO test;

--
-- TOC entry 203 (class 1259 OID 42903)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_id_seq OWNER TO test;

--
-- TOC entry 3258 (class 0 OID 0)
-- Dependencies: 203
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 202 (class 1259 OID 42803)
-- Name: orders_products; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.orders_products (
    orders_id integer NOT NULL,
    products_id integer NOT NULL
);


ALTER TABLE public.orders_products OWNER TO test;

--
-- TOC entry 206 (class 1259 OID 42913)
-- Name: products; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products (
    id integer NOT NULL,
    created timestamp without time zone,
    name character varying(255),
    price double precision NOT NULL,
    creator_id integer
);


ALTER TABLE public.products OWNER TO test;

--
-- TOC entry 205 (class 1259 OID 42911)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO test;

--
-- TOC entry 3259 (class 0 OID 0)
-- Dependencies: 205
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 208 (class 1259 OID 42921)
-- Name: products_in_order; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products_in_order (
    id integer NOT NULL,
    amount integer NOT NULL,
    created timestamp without time zone,
    status character varying(255) NOT NULL,
    creator_id integer,
    order_id integer,
    product_id integer
);


ALTER TABLE public.products_in_order OWNER TO test;

--
-- TOC entry 207 (class 1259 OID 42919)
-- Name: products_in_order_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.products_in_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_in_order_id_seq OWNER TO test;

--
-- TOC entry 3260 (class 0 OID 0)
-- Dependencies: 207
-- Name: products_in_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.products_in_order_id_seq OWNED BY public.products_in_order.id;


--
-- TOC entry 210 (class 1259 OID 42929)
-- Name: tables; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.tables (
    id integer NOT NULL,
    created timestamp without time zone,
    assigned_to_id integer,
    creator_id integer
);


ALTER TABLE public.tables OWNER TO test;

--
-- TOC entry 209 (class 1259 OID 42927)
-- Name: tables_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.tables_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tables_id_seq OWNER TO test;

--
-- TOC entry 3261 (class 0 OID 0)
-- Dependencies: 209
-- Name: tables_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.tables_id_seq OWNED BY public.tables.id;


--
-- TOC entry 212 (class 1259 OID 42937)
-- Name: users; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255) NOT NULL,
    role character varying(255),
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO test;

--
-- TOC entry 211 (class 1259 OID 42935)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO test;

--
-- TOC entry 3262 (class 0 OID 0)
-- Dependencies: 211
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: test
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3086 (class 2604 OID 42908)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- TOC entry 3087 (class 2604 OID 42916)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 3088 (class 2604 OID 42924)
-- Name: products_in_order id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_in_order ALTER COLUMN id SET DEFAULT nextval('public.products_in_order_id_seq'::regclass);


--
-- TOC entry 3089 (class 2604 OID 42932)
-- Name: tables id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.tables ALTER COLUMN id SET DEFAULT nextval('public.tables_id_seq'::regclass);


--
-- TOC entry 3090 (class 2604 OID 42940)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3243 (class 0 OID 42905)
-- Dependencies: 204
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.orders (id, created, status, creator_id, table_id) FROM stdin;
\.


--
-- TOC entry 3241 (class 0 OID 42803)
-- Dependencies: 202
-- Data for Name: orders_products; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.orders_products (orders_id, products_id) FROM stdin;
\.


--
-- TOC entry 3245 (class 0 OID 42913)
-- Dependencies: 206
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products (id, created, name, price, creator_id) FROM stdin;
\.


--
-- TOC entry 3247 (class 0 OID 42921)
-- Dependencies: 208
-- Data for Name: products_in_order; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products_in_order (id, amount, created, status, creator_id, order_id, product_id) FROM stdin;
\.


--
-- TOC entry 3249 (class 0 OID 42929)
-- Dependencies: 210
-- Data for Name: tables; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.tables (id, created, assigned_to_id, creator_id) FROM stdin;
\.


--
-- TOC entry 3251 (class 0 OID 42937)
-- Dependencies: 212
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.users (id, first_name, last_name, password, role, username) FROM stdin;
1	admin	admin	$2y$12$5ptHa3A2HfKJ3BwiHxUMaeBbgvXkuSkregPKiw2foZulnDIYlzLta	MANAGER	admin
\.


--
-- TOC entry 3263 (class 0 OID 0)
-- Dependencies: 203
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.orders_id_seq', 1, false);


--
-- TOC entry 3264 (class 0 OID 0)
-- Dependencies: 205
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.products_id_seq', 1, false);


--
-- TOC entry 3265 (class 0 OID 0)
-- Dependencies: 207
-- Name: products_in_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.products_in_order_id_seq', 1, false);


--
-- TOC entry 3266 (class 0 OID 0)
-- Dependencies: 209
-- Name: tables_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.tables_id_seq', 1, false);


--
-- TOC entry 3267 (class 0 OID 0)
-- Dependencies: 211
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- TOC entry 3094 (class 2606 OID 42910)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3100 (class 2606 OID 42926)
-- Name: products_in_order products_in_order_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_in_order
    ADD CONSTRAINT products_in_order_pkey PRIMARY KEY (id);


--
-- TOC entry 3096 (class 2606 OID 42918)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 3102 (class 2606 OID 42934)
-- Name: tables tables_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.tables
    ADD CONSTRAINT tables_pkey PRIMARY KEY (id);


--
-- TOC entry 3098 (class 2606 OID 42947)
-- Name: products uk_o61fmio5yukmmiqgnxf8pnavn; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT uk_o61fmio5yukmmiqgnxf8pnavn UNIQUE (name);


--
-- TOC entry 3092 (class 2606 OID 42842)
-- Name: orders_products uk_qmviv5y7625wak8tjq4nirybh; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders_products
    ADD CONSTRAINT uk_qmviv5y7625wak8tjq4nirybh UNIQUE (products_id);


--
-- TOC entry 3104 (class 2606 OID 42949)
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- TOC entry 3106 (class 2606 OID 42945)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3114 (class 2606 OID 42985)
-- Name: tables fk2w1f0ubpjiotdtqs2lo5dmndj; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.tables
    ADD CONSTRAINT fk2w1f0ubpjiotdtqs2lo5dmndj FOREIGN KEY (creator_id) REFERENCES public.users(id);


--
-- TOC entry 3111 (class 2606 OID 42970)
-- Name: products_in_order fk52yge8hl6teqf4n4bkul5k5dm; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_in_order
    ADD CONSTRAINT fk52yge8hl6teqf4n4bkul5k5dm FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- TOC entry 3112 (class 2606 OID 42975)
-- Name: products_in_order fk60tyehiqoydncbdl4sy6b3tic; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_in_order
    ADD CONSTRAINT fk60tyehiqoydncbdl4sy6b3tic FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 3113 (class 2606 OID 42980)
-- Name: tables fkcf1lrddog93h9tnnl94s0rnnd; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.tables
    ADD CONSTRAINT fkcf1lrddog93h9tnnl94s0rnnd FOREIGN KEY (assigned_to_id) REFERENCES public.users(id);


--
-- TOC entry 3109 (class 2606 OID 42960)
-- Name: products fkid1quonr11ajt1rq1xbvx9p47; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fkid1quonr11ajt1rq1xbvx9p47 FOREIGN KEY (creator_id) REFERENCES public.users(id);


--
-- TOC entry 3108 (class 2606 OID 42955)
-- Name: orders fkrkhrp1dape261t3x3spj7l5ny; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fkrkhrp1dape261t3x3spj7l5ny FOREIGN KEY (table_id) REFERENCES public.tables(id);


--
-- TOC entry 3110 (class 2606 OID 42965)
-- Name: products_in_order fksqkyy20u77pe74w1csg0lyl8h; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_in_order
    ADD CONSTRAINT fksqkyy20u77pe74w1csg0lyl8h FOREIGN KEY (creator_id) REFERENCES public.users(id);


--
-- TOC entry 3107 (class 2606 OID 42950)
-- Name: orders fktjvw78tj731d1puuoaps78oay; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fktjvw78tj731d1puuoaps78oay FOREIGN KEY (creator_id) REFERENCES public.users(id);


-- Completed on 2020-07-20 00:18:17 +04

--
-- PostgreSQL database dump complete
--

