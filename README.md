# hello-world-java-jdbc

This repo has a "Hello World" Java application that uses [JDBC](https://jdbc.postgresql.org) to talk to [CockroachDB](https://www.cockroachlabs.com/docs/stable/).

To run the code:

1. Start a local, insecure CockroachDB cluster or single instance. The [cluster-setup.sh](/cluster-setup.sh) script can be used to setup a local Docker cluster.

2. A `postgres` database is already available. You can use the `root` user for the purposes of this example.

4. In your terminal, from this directory run: `make`

Note that this repo includes the Postgres JDBC driver JAR file. You can use DBeaver or any other DB Manager tool 
to access the CockroachDB cluster through a PostgreSQL connection (Connections string is : `jdbc:postgresql://localhost:26257/postgres`). 
