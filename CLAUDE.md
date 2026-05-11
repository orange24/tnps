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

## SQL Map Locations

SQL map XML files live in **`config/th/co/nttdata/tki/persistence/`** (source) — Ant copies them to `WebContent/WEB-INF/classes/`. Always edit the `config/` version, not the compiled copy.

## Adding a New Query (Full Stack Checklist)

When adding a new filtered query, touch all 5 layers in order:

1. **SQL XML** (`config/.../persistence/*.xml`) — add `<select id="...">` with correct `parameterClass`
2. **DAO interface** (`business/.../dao/XxxDao.java`) — declare method signature
3. **DAO impl** (`business/.../dao/impl/XxxDaoImpl.java`) — implement with `queryForList("namespace.id", param)`
4. **Logic interface** (`business/.../blogic/cmm/CommonLogic.java` or module logic) — declare method
5. **Logic impl** (`business/.../blogic/cmm/impl/CommonLogicImpl.java`) — implement calling DAO

After changing any Java interface/impl, rebuild with `ant build-si` (or target env) before testing — Tomcat hot-reload does **not** pick up interface changes.

## Reason / m_reason Table Notes

- `reasonType = 1` → NG Reason, `reasonType = 2` → Machine Stop (Loss Time), `reasonType = 3` → Discast
- **`m_reason_wip`** — junction table linking reasons to WIP codes; used to restrict which reasons apply per WIP
- **`queryReasonInWip`** (`m_reason_wip.xml`) adds `AND parentreasonid IS NULL` when `reasonType` is set but `parentReasonId` is not — returns only root/parent reasons, **not all child reasons**. Do not use this query when you need the full flat list per WIP.
- Use **`queryReasonMCStopByWip`** (`m_reason.xml`) for a flat list of all loss-time reasons filtered by WIP (JOIN `m_reason_wip` directly, no parent filter).

## DAL Module Notes

- **SQL maps**: `config/.../persistence/` — files prefixed `t_daily_wk*`
- **Excel export** (`DAL_R03`): `src/.../excel/DAL_R03ExcelView.java` — column index constants defined at top of class; Grand Total row written at the end of `build()`. `COL_LOSS_TIME_EACH` (col 18) = per-reason loss time; `COL_LOSS_TIME_TOTAL` (col 16) = total per record.
- **DAL_S04 Loss Time Popup**: `boxLossTimeReason` is a cloned `<select>` populated via `getLossTimeReasonList?wip=...`; when WIP changes (`boxWIP.change`) the dropdown reloads automatically. The `comboBox.setLossTimeReason(el, wip)` helper in `common.js` forwards the `wip` param.
