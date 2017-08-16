
# Ympäristön valmistelu

```bash
cd docker/database; ./build.[bat|sh]
```

Rakentaa Postgres-imagen jossa locale ja timezone korjattu.

# Tietokannan käynnistys

```bash
./startdev.[sh|bat]
```

Käynnistää Postgresin oletusporttiin 5432 ja luo tietokannan "foorumi"

# Tietokannan tuhoaminen

```bash
./killdev.[sh|bat]
```

Kill em all! Stoppaa Postgresin ja tuhoaa tietokannan.

