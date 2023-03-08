# PV260: Code Smells and Refactoring

Identify code smells in example application code and treat selected code smells by appropriate refactoring techniques.

## 1\. Element Library Application

Familiarize yourself with `Elements` application at hand used to query the elements of [the periodic table](https://en.wikipedia.org/wiki/Periodic_table).

The application support the following command line options.
| CLI option (long) | CLI option (short) | Arguments | Description |
| ------ | ------ | ------ | ------ |
| --help | | | Print application usage |
| --name | | String | Finds element by name |
| --symbol | -s | String | Finds element by symbol (currently not implemented) |
| --number | -n | int | Finds element by atomic number |
| --year | -y | int | Finds elements by year |

The project can be built by the standard maven command

```bash
$ mvn clean install
```

The build will produce a runnable jar file `target/elements.jar`.
The following are example usages of developed application.

```bash
# Search by atomic number
$ java -jar elements.jar -n 1
-------------------------------------------------------------------------------------------------------
|         #|    Symbol|                Name|      Standard State|              Group Block|      Year|
=======================================================================================================
|         1|         H|            Hydrogen|                 gas|                 nonmetal|      1766|
-------------------------------------------------------------------------------------------------------
# Search by year of discovery
$ java -jar elements.jar --year 2003
-------------------------------------------------------------------------------------------------------
|         #|    Symbol|                Name|      Standard State|              Group Block|      Year|
=======================================================================================================
|       113|        Nh|            Nihonium|                    |    post-transition metal|      2003|
|       115|        Mc|           Moscovium|                    |    post-transition metal|      2003|
-------------------------------------------------------------------------------------------------------
```

_These are just examples, refer to tests in order to identify all possible usages._

## 2\. The assignment

In your group:
- Identify 4 - 5 examples of code smells according to [1].
- Explain them to the other groups.
- Pick a refactoring technique [2] to treat these code smells.
- Present the refactoring results to the other groups.

## References
[1] https://refactoring.guru/refactoring/smells \
[2] https://refactoring.guru/refactoring/techniques
