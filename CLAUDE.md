# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

TKI (Thailand Kikuwa Integrated) is a manufacturing/inventory/supply chain management system built with Spring MVC + iBatis on Java 8. It manages work orders, finished goods, WIP stock, mold tracking, and production scheduling. It integrates with two external systems: TPICS and Nirvana.

## Build System (Apache Ant)

The project uses Ant, not Maven or Gradle. All commands run from `TKI/`.

```bash
ant build-si      # Build WAR for System Integration environment
ant build-UAT     # Build WAR for UAT environment
ant build-prod    # Build WAR for Production environment
ant build-TEST    # Build WAR for Test environment
ant build-batch   # Build standalone batch JAR (tki-batch.jar)
```

Build output: `~/Desktop/TNPS.war` (web) and `~/Desktop/tki-batch-{env}/tki-batch.jar` (batch).

The Ant build compiles four source trees: `src/`, `business/`, `config/`, `batch/` into `WebContent/WEB-INF/classes/`.

## Code Architecture

### Source Trees

| Directory | Purpose |
|-----------|---------|
| `src/` | Spring MVC controllers and DAOs |
| `business/` | Business logic interfaces + implementations |
| `batch/` | Standalone batch job logic |
| `config/` | Spring XML config + properties files |
| `WebContent/` | JSP views, static assets, WEB-INF |

### Functional Modules

All packages follow `th.co.nttdata.tki.{module}`:

- `mst/` — Master data (parts, molds, customers, machines, calendars)
- `prd/` — Production orders and scheduling
- `fng/` — Finished goods stock management
- `wip/` — Work-in-progress tracking
- `nir/` — Nirvana system export/sync
- `mrdc/` — Material/resource management
- `dal/` — Delivery and dispatch
- `dlv/` — Delivery orders
- `cmm/` — Shared utilities and common services
- `cfg/` — System configuration screens
- `adm/` — Admin UI and batch management

### Layer Pattern

For each module, code is organized as:
- `controller/` — Spring `@Controller`, handles HTTP
- `blogic/` — Interface + `*Impl` service class (business logic)
- `dao/` — DAO interface + `*Impl` (iBatis `SqlMapClient` queries)
- SQL maps — iBatis XML files under `WebContent/WEB-INF/sqlmap/`

### Batch Job Pattern

Batch jobs live in `batch/th/co/nttdata/tki/batch/blogic/`. Each job:
1. Extends `BatchLogicImpl` from `batch-runner.jar`
2. Implements three lifecycle methods:
   - `preProcessing()` — Validate, insert initial `m_batch_control` record (status = 1)
   - `processing()` — Main logic; sets status = 0 on success
   - `postException()` — Error handler; sets status = 2 (FAILED)
3. Is annotated `@Service` and wired into `BAT_S01LogicImpl`

Batch jobs run either as standalone JAR (`java -jar tki-batch.jar`) or async from the web UI via Spring `@Async`.

## Configuration

### Spring XML (in `config/`)

- `app-config.xml` — Component scan, AOP transaction advice, thread pool
- `dao-config.xml` — Two `SqlMapClient` beans: main DB (`sqlMapClient`) and TPICS DB (`sqlMapClientTpics`)
- `mvc-config.xml` — MVC dispatcher, JSP + Excel view resolvers, JSON/form converters
- `sec-config.xml` — Spring Security
- `xls-config.xml` — Excel export view beans
- `batch/config-batch.xml` — Separate Spring context for the batch JAR

### JDBC Properties

`config/jdbc.properties` is the active file (symlinked or copied per environment). Environment-specific files: `jdbc-si.properties`, `jdbc-prod.properties`, `jdbc-uat.properties`, `jdbc-test.properties`.

- **Main DB**: MSSQL 2008 R2 via `com.microsoft.sqlserver.jdbc.SQLServerDriver`
- **TPICS DB**: MSSQL via jTDS driver (`net.sourceforge.jtds.jdbc.Driver`)
- Connection pool: Apache Commons DBCP, max 100 active, `defaultAutoCommit=false`

### Application Settings

`config/settings.properties` — Business constants: report names, WIP types, batch NG ratio, date ranges, material type codes, log paths. Edit here before changing hardcoded values in Java.

## Key Libraries

- Spring 3.0.5 (MVC, Security, JDBC)
- iBatis 2.3.4 (SQL mapping — XML-based, not MyBatis annotations)
- JasperReports 5.2.0 (PDF/report generation)
- Apache POI 3.7 (Excel export)
- Jackson 1.7.1 (JSON)
- Log4j 1.2.16 (`config/log4j.xml`)

## Important Notes

- **iBatis, not MyBatis**: SQL maps are XML files, queries use `sqlMapClient.queryForList(...)` etc.
- **Spring 3, not Spring Boot**: No auto-configuration; all beans declared in XML config files.
- **Dual datasources**: Any DAO touching TPICS data must inject `sqlMapClientTpics`, not the default `sqlMapClient`.
- **Transaction management**: AOP-based via `tx:advice` in `app-config.xml`. Batch jobs rollback on any `Throwable`.
- **Environment switching**: The Ant build copies the correct `jdbc-{env}.properties` to `jdbc.properties` and sets `log.file.path`; do not manually edit `jdbc.properties` for permanent env changes.
