## Execution

First, package the jars
```
mvn package

```

Then run the following commands from different terminals which will initialize three raft servers:
```
java -cp target/sdql-server-1.0-SNAPSHOT-jar-with-dependencies.jar sd.nosql.Server p1
java -cp target/sdql-server-1.0-SNAPSHOT-jar-with-dependencies.jar sd.nosql.Server p2
java -cp target/sdql-server-1.0-SNAPSHOT-jar-with-dependencies.jar sd.nosql.Server p3
```