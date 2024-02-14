# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

```Phase 2 Diagram:
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZkwBzKBACu2GAGI0wKgE8YAJRRakEsFEFIIaYwHcAFkjAdEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAej0VKAAdNABvLMpTAFsUABoYXCl3aBlKlBLgJAQAX0wKcNgQsLZxKKhbe18oAAoiqFKKquUJWqh6mEbmhABKDtZ2GB6BIVFxKSitFDAAVWzx7Kn13ZExSQlt0PUosgBRABk3uCSYCamYAAzXQlP7ZTC3fYPbY9Tp9FBRNB6BAIDbULY7eRQw4wECDQQoc6US7FYBlSrVOZ1G5Y+5SJ5qBRRACSADl3lZfv8ydNKfNFssWjA2Ul4mDaHCMaFIXSJFE8SgCcI9GBPCTJjyaXtZQyXsL2W9OeKNeSYMAVZ4khAANbofWis0WiG0g6PQKwzb9R2qq22tBo+Ew0JwyLey029AByhB+DIdBgKIAJgADMm8vlzT6I2h2ugZJodPpDEZoLxjjAPhA7G4jF4fH440Fg6xQ3FEqkMiopC40Onuaa+XV2iHus30V6EFXmWh1VMKbN+etJeIGTLXUcTkSxv2UFq7q6nnr3l8fsaAcCIKCJs7tQf3cFlwilgAPYbSR9PNcPKKDhYAWUEEBPEkUZfxkPdsQkEI9TgeI-1iYQrDeGYajqIEQWNG99wee9HyiRw9A4R8Qi-Q4FSVC1Z01LDIOgpl9Q5Lkrh5SpM3DP17TFNiaNlXDPSfNiYEgP0A3YPiuiiQThMjEd738eMIhTNMCik7NczQfNtF0AxjB0FA7UrLR9GYWtvF8TB5KbXpWz4T43iSN40nSLsJB7PI2N9GSW1HXp2CiGQUAQE4UGVVUqLKJd+NXF1vxgY4zguHcIJ1Hojzs08JnQy8zx5HjXXE+EohQV8HHffiSJinEAqC3xQrVJK8pw4IXlsr4HLDITsyyq9wVIt0PXHASLU6kSP3vayoEk4bpP9EcY0shMYCU9NVL9dTNMLHSjEGGQK2GGAAHEeUeUz6wsxtmAGmhJpiA77KcrQeRWi1PJzKMoBCPCYGQBwjrKCRRg87NIsGirb1i+K-pQD5hkBl7gcaqQ6NedLfmED4Pji47Eagz7+KifIACJHv+wmomifJ0gAKhJ1RBEcJAFBVSQqfSVoUmHcq8YkmAidpiQyZiSmaZ5M0wAZpnfAkVn2c5ro5IupNU3TYnjsFinqdpsWJeZ6W2Y5zA8wLbTi2wPQoGwIL4HxXxDtF07zIWhkR3JhJkgep7THhv101p1keTl67ucKmA0BQdwobhrM-UqP3qOI4I+o3MBI7jiKceR8hUaxsputD3K+oKvzQ+RVEE6T3EbZQSOgZjnOUH99PC+a+i2UY+vG+mWu7SQDAIDDDPg+LtOu+9rzQeD0MR9YsfZu8hWAkW5aCmnsNXvW42i2McxAsndwYAAKQgXu7dzowFAQUBrXOxfne813Tg7dJafc2f0wWuAIEnKBY55Zk+EDpQIeXoABWx80CRw-l-aAv8yj-xBvCMG2EcSQx5KMWm8CM4txRieX4WsLyggwXwQeD58YvjfKJcQSDIKSRkDIWI9BuBMFGFA7+yV8rYIYoaX4ltgA71gNAGAEAFDZGuiQr6rDoCUJQAVUMkioDvQXgpJaysCjyI3lpLeRgdB8NjIqWAwBsCW0IM4VwHgzINlvldGydkHJOXUIo0hg15RBTwHwBQowEFiUTpVOUahArBWECiTxWC9StXsshVa6AJCVAmDE+u0hC7AKfARIiXMnHXXwlAQiiiQgLSVspfIqSN5AA
```
