# rtburger/my-cdc-project-test
The purpose of this project is to enhance my Clojure skills and use them to resolve a data pipeline problem.

# Problem
There is a requirement to transfer data from a relational database to a data lake in near real-time.

# Solution
The project proposes using Debezium to capture changes (CDC - such as inserts, updates, and deletions) in the source database. The captured data will be processed and transformed by Clojure core.async and then loaded into delta.io format on the data lake. 

# Design  v1
[Clojure & core.async]()

[Debezium Engine](https://debezium.io/documentation/reference/stable/development/engine.html)

[Delta Standalone](https://github.com/delta-io/connectors)

other???

## App Design
![Alt text](/doc/images/app-design-v1.png)

## Cloud Infra Design
![Alt text](/doc/images/cloud-infra-design-v1.png)

# Tools Used
[deps-new](https://github.com/seancorfield/deps-new)

# Learning Resources
[Core.Async in Use - Timothy Baldridge](https://www.youtube.com/watch?v=096pIlA3GDo)
[ETL with Clojure and Datomic](https://www.youtube.com/watch?v=oOON--g1PyU)
[grammarly - Building ETL Pipelines with Clojure and Transducers](https://www.grammarly.com/blog/engineering/building-etl-pipelines-with-clojure-and-transducers/)


# Plan
| Task | Status |
| :--- | :---: |  
| Install SQL Server 2022 (16.x) on Ubuntu 22.04 local machine| DONE|
| Install PostreSQL on Ubuntu 22.04 local machine | DONE |
| Configure CDC on SQL Server | DONE |
| Configure CDC on PostreSQL | TODO |
| Embed Debezium Engine in Clojure App | TODO |
| Read and process Debezium message in Clojure | TODO
| core.async | TODO |
| Transducers | TODO |
| Delta.io | TODO |
| Azure Cloud Terraform | TODO|
| Azure Cloud CICD | TODO |



## Installation

Download from https://github.com/rtburger/my-cdc-project-test

## Usage

FIXME: explanation

Run the project directly, via `:exec-fn`:

    $ clojure -X:run-x
    Hello, Clojure!

Run the project, overriding the name to be greeted:

    $ clojure -X:run-x :name '"Someone"'
    Hello, Someone!

Run the project directly, via `:main-opts` (`-m rtburger.my-cdc-project-test`):

    $ clojure -M:run-m
    Hello, World!

Run the project, overriding the name to be greeted:

    $ clojure -M:run-m Via-Main
    Hello, Via-Main!

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Run the project's CI pipeline and build an uberjar (this will fail until you edit the tests to pass):

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the uberjar in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

If you don't want the `pom.xml` file in your project, you can remove it. The `ci` task will
still generate a minimal `pom.xml` as part of the `uber` task, unless you remove `version`
from `build.clj`.

Run that uberjar:

    $ java -jar target/net.clojars.rtburger/my-cdc-project-test-0.1.0-SNAPSHOT.jar

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2023 rtburger

_EPLv1.0 is just the default for projects generated by `deps-new`: you are not_
_required to open source this project, nor are you required to use EPLv1.0!_
_Feel free to remove or change the `LICENSE` file and remove or update this_
_section of the `README.md` file!_

Distributed under the Eclipse Public License version 1.0.