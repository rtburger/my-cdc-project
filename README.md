# my-cdc-project
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
![Alt text](/docs/images/app-design-v1.png)

## Cloud Infra Design
![Alt text](/docs/images/cloud-infra-design-v1.png)

# Tools Used
[deps-new](https://github.com/seancorfield/deps-new)