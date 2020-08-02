/*
Created: 26/06/2020
Modified: 24/07/2020
Model: EMISAsql
Database: MySQL 8.0
*/

-- Create tables section -------------------------------------------------

-- Table GZZ_ASTROS

CREATE TABLE `GZZ_ASTROS`
(
    `AstCod`    Int           NOT NULL,
    `AstNom`    Varchar(60)   NOT NULL,
    `AstTip`    Decimal(1, 0) NOT NULL,
    `AstEstReg` Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `GZZ_ASTROS`
    ADD PRIMARY KEY (`AstCod`)
;

-- Table GZZ_UBICACION

CREATE TABLE `GZZ_UBICACION`
(
    `UbiCod`    Int           NOT NULL,
    `UbiNom`    Varchar(60)   NOT NULL,
    `UbiAstCod` Int           NOT NULL,
    `UbiCoorX`  Decimal(6, 4) NOT NULL,
    `UbiCoorY`  Decimal(6, 4) NOT NULL,
    `UbiCoorZ`  Decimal(6, 4) NOT NULL,
    `UbiEstReg` Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `GZZ_UBICACION`
    ADD PRIMARY KEY (`UbiCod`)
;

-- Table EMZ_FACTORIA

CREATE TABLE `EMZ_FACTORIA`
(
    `FacCod`    Int         NOT NULL,
    `FacNom`    Varchar(60) NOT NULL,
    `FacUbiCod` Int         NOT NULL,
    `FacEstReg` Char(1)     NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `EMZ_FACTORIA`
    ADD PRIMARY KEY (`FacCod`)
;

-- Table E1C_PROSPECCION

CREATE TABLE `E1C_PROSPECCION`
(
    `ProsCod`    Int           NOT NULL,
    `ProsEst`    Decimal(1, 0) NOT NULL,
    `ProsUbiCod` Int           NOT NULL,
    `ProUbiCod`  Int           NOT NULL,
    `ProsEstReg` Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `E1C_PROSPECCION`
    ADD PRIMARY KEY (`ProsCod`)
;

-- Table TZM_NAVE

CREATE TABLE `TZM_NAVE`
(
    `NavCod`    Int         NOT NULL,
    `NavNom`    Varchar(60) NOT NULL,
    `NavEstReg` Char(1)     NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `TZM_NAVE`
    ADD PRIMARY KEY (`NavCod`)
;

-- Table TZZ_VIAJE

CREATE TABLE `TZZ_VIAJE`
(
    `ViaCod`     Int           NOT NULL,
    `ViaNavCod`  Int           NOT NULL,
    `ViaFacCod`  Int           NOT NULL,
    `FecDesAnio` Decimal(4, 0) NOT NULL,
    `FecDesMes`  Decimal(2, 0) NOT NULL,
    `FecDesDia`  Decimal(2, 0) NOT NULL,
    `FecLleAnio` Decimal(4, 0) NOT NULL,
    `FecLleMes`  Decimal(2, 0) NOT NULL,
    `FecLleDia`  Decimal(2, 0) NOT NULL,
    `ViaEstReg`  Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `TZZ_VIAJE`
    ADD PRIMARY KEY (`ViaCod`)
;

-- Table R7C_PROVISION

CREATE TABLE `R7C_PROVISION`
(
    `ProvCod`        Int           NOT NULL,
    `ProvFacNom`     Varchar(60)   NOT NULL,
    `ProvPes`        Decimal(4, 2) NOT NULL,
    `ProvAliCan`     Decimal(6, 0) NOT NULL,
    `ProvAlimCod`    Int           NOT NULL,
    `ProvSumCod`     Int           NOT NULL,
    `ProvFecLleAnio` Decimal(4, 0) NOT NULL,
    `ProvFecLleMes`  Decimal(2, 0) NOT NULL,
    `ProvFecLleDia`  Decimal(2, 0) NOT NULL,
    `ProvEstReg`     Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `R7C_PROVISION`
    ADD PRIMARY KEY (`ProvCod`)
;

-- Table T5C_SUMINISTRO

CREATE TABLE `T5C_SUMINISTRO`
(
    `SumCod`    Int     NOT NULL,
    `SumViaCod` Int     NOT NULL,
    `SumEstReg` Char(1) NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `T5C_SUMINISTRO`
    ADD PRIMARY KEY (`SumCod`)
;

-- Table R7C_ALIMENTO

CREATE TABLE `R7C_ALIMENTO`
(
    `AlimCod`    Int           NOT NULL,
    `AlimNom`    Varchar(50)   NOT NULL,
    `AlimCos`    Decimal(6, 2) NOT NULL,
    `AlimCan`    Decimal(4, 0) NOT NULL,
    `AlimEstReg` Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `R7C_ALIMENTO`
    ADD PRIMARY KEY (`AlimCod`)
;

-- Table R6Z_PERSONAL

CREATE TABLE `R6Z_PERSONAL`
(
    `PerCod`    Int         NOT NULL,
    `PerNom`    Varchar(60) NOT NULL,
    `PerEmpCod` Int         NOT NULL,
    `PerEstReg` Char(1)     NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `R6Z_PERSONAL`
    ADD PRIMARY KEY (`PerCod`)
;

-- Table RZC_FACTORIA_EMPLEO

CREATE TABLE `RZC_FACTORIA_EMPLEO`
(
    `FacEmpPerCod` Int     NOT NULL,
    `FacEmpCod`    Int     NOT NULL,
    `FacCod`       Int     NOT NULL,
    `FacEmpEstReg` Char(1) NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `RZC_FACTORIA_EMPLEO`
    ADD PRIMARY KEY (`FacEmpCod`)
;

-- Table GZM_MINERAL

CREATE TABLE `GZM_MINERAL`
(
    `MinCod`    Int         NOT NULL,
    `MinNom`    Varchar(60) NOT NULL,
    `MinEstReg` Char(1)     NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `GZM_MINERAL`
    ADD PRIMARY KEY (`MinCod`)
;

-- Table P8M_CENTRO

CREATE TABLE `P8M_CENTRO`
(
    `CenCod`    Int         NOT NULL,
    `CenNom`    Varchar(60) NOT NULL,
    `CenMinCod` Int         NOT NULL,
    `CenEstReg` Char(1)     NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `P8M_CENTRO`
    ADD PRIMARY KEY (`CenCod`)
;

-- Table E1C_PRODUCCION

CREATE TABLE `E1C_PRODUCCION`
(
    `ProCod`     Int           NOT NULL,
    `ProMinCan`  Decimal(4, 0) NOT NULL,
    `ProMinCal`  Varchar(60)   NOT NULL,
    `ProFecAnio` Decimal(4, 0) NOT NULL,
    `ProFecMes`  Decimal(2, 0) NOT NULL,
    `ProFecDia`  Decimal(2, 0) NOT NULL,
    `ProAlmCod`  Int           NOT NULL,
    `ProEstReg`  Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `E1C_PRODUCCION`
    ADD PRIMARY KEY (`ProCod`)
;

-- Table RZC_EMPLEO

CREATE TABLE `RZC_EMPLEO`
(
    `EmpCod`    Int           NOT NULL,
    `EmpNom`    Varchar(50)   NOT NULL,
    `EmpSue`    Decimal(8, 2) NOT NULL,
    `EmpEst`    Decimal(1, 0) NOT NULL,
    `EmpViaCod` Int           NOT NULL,
    `EmpCatCod` Int           NOT NULL,
    `EmpEstReg` Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `RZC_EMPLEO`
    ADD PRIMARY KEY (`EmpCod`)
;

-- Table RZC_CATEGORIA

CREATE TABLE `RZC_CATEGORIA`
(
    `CatCod`    Int           NOT NULL,
    `CatNom`    Varchar(50)   NOT NULL,
    `CatSuel`   Decimal(8, 2) NOT NULL,
    `CatEstReg` Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `RZC_CATEGORIA`
    ADD PRIMARY KEY (`CatCod`)
;

-- Table E3M_ALMACEN

CREATE TABLE `E3M_ALMACEN`
(
    `AlmCod`    Int           NOT NULL,
    `AlmCap`    Decimal(8, 4) NOT NULL,
    `AlmMinCod` Int           NOT NULL,
    `AlmFacCod` Int           NOT NULL,
    `AlmEstReg` Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `E3M_ALMACEN`
    ADD PRIMARY KEY (`AlmCod`)
;

-- Table T3C_CARGAMENTO

CREATE TABLE `T3C_CARGAMENTO`
(
    `CarCod`     Int           NOT NULL,
    `CarAlmCod`  Int           NOT NULL,
    `CarRecCod`  Int           NOT NULL,
    `CarNom`     Varchar(50)   NOT NULL,
    `CarMinCan`  Decimal(6, 4) NOT NULL,
    `CarFecAnio` Decimal(4, 0) NOT NULL,
    `CarFecMes`  Decimal(2, 0) NOT NULL,
    `CarFecDia`  Decimal(2, 0) NOT NULL,
    `CarEstReg`  Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `T3C_CARGAMENTO`
    ADD PRIMARY KEY (`CarCod`)
;

-- Table T3C_RECOGIDA

CREATE TABLE `T3C_RECOGIDA`
(
    `RecCod`     Int           NOT NULL,
    `RecViaCod`  Int           NOT NULL,
    `RecFecMes`  Decimal(2, 0) NOT NULL,
    `RecFecDia`  Decimal(2, 0) NOT NULL,
    `RecFecAnio` Decimal(4, 0) NOT NULL,
    `RecEstReg`  Char(1)       NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `T3C_RECOGIDA`
    ADD PRIMARY KEY (`RecCod`)
;

-- Table E1Z_HALLAZGO

CREATE TABLE `E1Z_HALLAZGO`
(
    `HalProCod` Int     NOT NULL,
    `HalMinCod` Int     NOT NULL,
    `HalSit`    Bool    NOT NULL,
    `HalCod`    Int     NOT NULL,
    `HalEstReg` Char(1) NOT NULL DEFAULT 'A'
)
;

ALTER TABLE `E1Z_HALLAZGO`
    ADD PRIMARY KEY (`HalCod`)
;

-- Create foreign keys (relationships) section -------------------------------------------------

ALTER TABLE `GZZ_UBICACION`
    ADD CONSTRAINT `Astro_Ubicacion` FOREIGN KEY (`UbiAstCod`) REFERENCES `GZZ_ASTROS` (`AstCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `EMZ_FACTORIA`
    ADD CONSTRAINT `Factoria_Ubicacion` FOREIGN KEY (`FacUbiCod`) REFERENCES `GZZ_UBICACION` (`UbiCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `E1C_PROSPECCION`
    ADD CONSTRAINT `Ubicacion_Prospeccion` FOREIGN KEY (`ProUbiCod`) REFERENCES `GZZ_UBICACION` (`UbiCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `TZZ_VIAJE`
    ADD CONSTRAINT `Viaje_Nave` FOREIGN KEY (`ViaNavCod`) REFERENCES `TZM_NAVE` (`NavCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `TZZ_VIAJE`
    ADD CONSTRAINT `Viaje_Factoria` FOREIGN KEY (`ViaFacCod`) REFERENCES `EMZ_FACTORIA` (`FacCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `R7C_PROVISION`
    ADD CONSTRAINT `Alimento_Provision` FOREIGN KEY (`ProvAlimCod`) REFERENCES `R7C_ALIMENTO` (`AlimCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `R7C_PROVISION`
    ADD CONSTRAINT `Suministro_Provision` FOREIGN KEY (`ProvSumCod`) REFERENCES `T5C_SUMINISTRO` (`SumCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `RZC_FACTORIA_EMPLEO`
    ADD CONSTRAINT `Factoria_Empleo_Personal` FOREIGN KEY (`FacEmpPerCod`) REFERENCES `R6Z_PERSONAL` (`PerCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `P8M_CENTRO`
    ADD CONSTRAINT `Centro_Mineral` FOREIGN KEY (`CenMinCod`) REFERENCES `GZM_MINERAL` (`MinCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `R6Z_PERSONAL`
    ADD CONSTRAINT `Personal_Empleo` FOREIGN KEY (`PerEmpCod`) REFERENCES `RZC_EMPLEO` (`EmpCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `E3M_ALMACEN`
    ADD CONSTRAINT `Almacen_Mineral` FOREIGN KEY (`AlmMinCod`) REFERENCES `GZM_MINERAL` (`MinCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `T3C_CARGAMENTO`
    ADD CONSTRAINT `Cargamento_Almacen` FOREIGN KEY (`CarAlmCod`) REFERENCES `E3M_ALMACEN` (`AlmCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `RZC_EMPLEO`
    ADD CONSTRAINT `Viaje_Empleo` FOREIGN KEY (`EmpViaCod`) REFERENCES `TZZ_VIAJE` (`ViaCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `T5C_SUMINISTRO`
    ADD CONSTRAINT `Viaje_Suministro` FOREIGN KEY (`SumViaCod`) REFERENCES `TZZ_VIAJE` (`ViaCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `T3C_CARGAMENTO`
    ADD CONSTRAINT `Recogida_Cargamento` FOREIGN KEY (`CarRecCod`) REFERENCES `T3C_RECOGIDA` (`RecCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `T3C_RECOGIDA`
    ADD CONSTRAINT `Viaje_Cargamento` FOREIGN KEY (`RecViaCod`) REFERENCES `TZZ_VIAJE` (`ViaCod`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `E3M_ALMACEN`
    ADD CONSTRAINT `Factoria_Almacen` FOREIGN KEY (`AlmFacCod`) REFERENCES `EMZ_FACTORIA` (`FacCod`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `E1Z_HALLAZGO`
    ADD CONSTRAINT `Prospeccion_Hallazgo` FOREIGN KEY (`HalProCod`) REFERENCES `E1C_PROSPECCION` (`ProsCod`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `E1Z_HALLAZGO`
    ADD CONSTRAINT `MIneral_Hallazgo` FOREIGN KEY (`HalMinCod`) REFERENCES `GZM_MINERAL` (`MinCod`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `E1C_PRODUCCION`
    ADD CONSTRAINT `Almacen_Produccion` FOREIGN KEY (`ProAlmCod`) REFERENCES `E3M_ALMACEN` (`AlmCod`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `RZC_FACTORIA_EMPLEO`
    ADD CONSTRAINT `Factoria_Empleo_Factoria` FOREIGN KEY (`FacCod`) REFERENCES `EMZ_FACTORIA` (`FacCod`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `RZC_EMPLEO`
    ADD CONSTRAINT `Categoria_Empleo` FOREIGN KEY (`EmpCatCod`) REFERENCES `RZC_CATEGORIA` (`CatCod`) ON DELETE RESTRICT ON UPDATE RESTRICT
;
