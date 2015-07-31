CREATE SEQUENCE SEQ_Persona_id START WITH 1 INCREMENT BY 1 NOCYCLE CACHE 1;

CREATE TABLE PERSONA
(
   ID                   DOUBLE         DEFAULT (NEXT VALUE FOR PUBLIC.SEQ_Persona_id) NOT NULL,
   NOMBRE               VARCHAR(60)    NOT NULL,
   PAPELLIDO            VARCHAR(60)    NOT NULL,
   SAPELLIDO            VARCHAR(60),
   IDTIPOIDENTIFICADOR  VARCHAR(4)     NOT NULL,
   IDENTIFICADOR        VARCHAR(20)    NOT NULL,
   DIRECCION            VARCHAR(150)   NOT NULL,
   CODPOSTAL            DECIMAL(5)     NOT NULL,
   LOCALIDAD            VARCHAR(70)    NOT NULL,
   PROVINCIA            VARCHAR(70)    NOT NULL,
   TELEFONO             DECIMAL(15),
   TELMOVIL             DECIMAL(15)    NOT NULL
);

ALTER TABLE PERSONA
   ADD CONSTRAINT PK_PERSONA
   PRIMARY KEY (ID);

CREATE UNIQUE INDEX UK_PERSONA
   ON PERSONA (ID ASC);

CREATE UNIQUE INDEX UK_PERSONA_IDENTIF
   ON PERSONA (IDTIPOIDENTIFICADOR ASC, IDENTIFICADOR ASC);


COMMENT ON COLUMN PERSONA.ID IS 'identificador �nico';
COMMENT ON COLUMN PERSONA.NOMBRE IS 'Nombre de la persona';
COMMENT ON COLUMN PERSONA.PAPELLIDO IS 'primer apellido de la persona';
COMMENT ON COLUMN PERSONA.SAPELLIDO IS 'segundo apellido de la persona';
COMMENT ON COLUMN PERSONA.IDTIPOIDENTIFICADOR IS 'Tipo de documento identificador';
COMMENT ON COLUMN PERSONA.IDENTIFICADOR IS 'N�mero de documento identificador';
COMMENT ON COLUMN PERSONA.DIRECCION IS 'Direcci�n postal';
COMMENT ON COLUMN PERSONA.CODPOSTAL IS 'C�digo postal';
COMMENT ON COLUMN PERSONA.LOCALIDAD IS 'Localidad';
COMMENT ON COLUMN PERSONA.PROVINCIA IS 'Provincia';
COMMENT ON COLUMN PERSONA.TELEFONO IS 'Tel�fono';
COMMENT ON COLUMN PERSONA.TELMOVIL IS 'Tel�fono m�vil';

INSERT INTO PERSONA (NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, TELEFONO, TELMOVIL) VALUES
('Jos� Luis','Bachiller', 'Gil' ,'NIF', '16801610H', 'Direcci�n postal', 42002, 'Alconaba', 'Soria', 975759137, 627393922);

INSERT INTO PERSONA (NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, TELEFONO, TELMOVIL) VALUES
('Jos� Luis1','Bachiller1', 'Gil1' ,'NIF', '00000000H', 'Direcci�n postal', 42002, 'Alconaba', 'Soria', 975759137, 627393922);

INSERT INTO PERSONA (NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, TELEFONO, TELMOVIL) VALUES
('Abogado1','Abogado1', 'Abogado1' ,'NIF', '00000001H', 'Direcci�n postal', 42002, 'Alconaba', 'Soria', 975759137, 627393922);

INSERT INTO PERSONA (NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, TELEFONO, TELMOVIL) VALUES
('Abogado2','Abogado2', 'Abogado2' ,'NIF', '00000002H', 'Direcci�n postal', 42002, 'Alconaba', 'Soria', 975759137, 627393922);

INSERT INTO PERSONA (NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, TELEFONO, TELMOVIL) VALUES
('Abogado3','Abogado3', 'Abogado1' ,'NIF', '00000003H', 'Direcci�n postal', 42002, 'Alconaba', 'Soria', 975759137, 627393922);

CREATE TABLE USUARIO
(
   IDPERSONA   DOUBLE        NOT NULL,
   IDUSUARIO   VARCHAR(10)   NOT NULL,
   CONTRASENA  VARCHAR(10)   NOT NULL,
   IDPERFIL    VARCHAR(5)    NOT NULL
);

ALTER TABLE USUARIO
   ADD CONSTRAINT PK_USUARIO
   PRIMARY KEY (IDPERSONA);

CREATE INDEX CONSTRAINT_INDEX_2
   ON USUARIO (IDPERSONA ASC);

CREATE UNIQUE INDEX PK_USUARIO
   ON USUARIO (IDPERSONA ASC);


COMMENT ON COLUMN USUARIO.IDPERSONA IS 'Identificador de persona que puede conectarse';
COMMENT ON COLUMN USUARIO.IDUSUARIO IS 'C�digo de conexi�n del usuario';
COMMENT ON COLUMN USUARIO.CONTRASENA IS 'Contrase�a del usuario';
COMMENT ON COLUMN USUARIO.IDPERFIL IS 'Codigo del perfil dentro de la aplicaci�n';

-- BEGIN FOREIGN KEYS --
ALTER TABLE USUARIO
  ADD FOREIGN KEY (IDPERSONA)
  REFERENCES PERSONA (ID)
  ON UPDATE RESTRICT
  ON DELETE CASCADE;
-- END FOREIGN KEYS --

Insert into usuario (IDPERSONA, IDUSUARIO, CONTRASENA, IDPERFIL) Select ID, 'ADMIN', 'ADMIN', 'ADMIN' from PERSONA where IDENTIFICADOR = '16801610H';

