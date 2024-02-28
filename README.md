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
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZkwBzKBACu2GAGI0wKgE8YAJRRakEsFEFIIaYwHcAFkjAdEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAej0VKAAdNABvLMpTAFsUABoYXCl3aBlKlBLgJAQAX0wKcNgQsLZxKKhbe18oAAoiqFKKquUJWqh6mEbmhABKDtZ2GB6BIVFxKSitFDAAVWzx7Kn13ZExSQlt0PUosgBRABk3uCSYCamYAAzXQlP7ZTC3fYPbY9Tp9FBRNB6BAIDbULY7eRQw4wECDQQoc6US7FYBlSrVOZ1G5Y+5SJ5qBRRACSADl3lZfv8ydNKfNFssWjA2Ul4mDaHCMaFIXSJFE8SgCcI9GBPCTJjyaXtZQyXsL2W9OeKNeSYMAVZ4khAANbofWis0WiG0g6PQKwzb9R2qq22tBo+Ew0JwyLey029AByhB+DIdBgKIAJgADMm8oUrjzKuafRG0O10DJNDp9IYjNBeMcYB8IHY3EYvD4-HGgsHWKG4olUhkJunuaa+XUGk0Wu0Q902+ivYM7A5if3ebN+cOVutJeIGTLXUcTkSxgutXdXbqmeRPt8uZmykCQcbndrj+7J-DEcjUevVJiHw95fjfHv1SmCkl2pe8j2hHo9TZDlL1JAcQIWFchRFMUJgDKUYC3H9cT-FBlVVQDNTA7E3WeU9oMNWCTWmHNwz9e0xVo4idSfXp2CiWjfUjD8Y3HDiLS4-1xxjfx4yTVM+yvGiBLzAs0CLbRdAMYwdBQO0ay0fRmAbbxfEwUTW16Ds+HPJI3jSdIVCkFw0DyTi8yjKAQg-KIZBQBATjwi1Rnsv0109FAQiwnFjjAfC1V89BDxIkI9QAcTeX5IrcYEIFBcLmNdJ8XLDW50PEIKXWwtyPN8cKfJkvzMoeWLTxMr4zLDQSbzSsNqqkbKAoifLAuc9soG68cnwMhMYBTZNMELYslLLQYZGrYYYDinlHh0pt9JbZgPS6KJogS7t0i0Hk7Mq7j+r6qcERgZAHGWsoJAq3Mqo-QrvxCk5yuS6LZVq15zx+Jq8xa0EmOCiROsuqJwryl7gjBncwDulAPmGQiym+rLgj1d4vgB4QPg+GAjvu2QFCfeGlp5PKwYhl9KbKGGAtp0MkcZrphs28S0wKVn5DkhSS2UoxsD0KBsA8+BcPp1Q1r0kaGT4mIEmSCziZQE6nvQdM1dZHkx3O4IcrQFB3CRx66PQSodaI2GKdCz7TrQDGaqx08cYvQH6NSkGnRpi66eh+Qete8CcQVAkzet9H2vB12WQNI0o44P3Da6omeWZPhg-90M1czxyOYCUbxu1jO+HaSb5Om0tjHMdyEAgdwYAAKQgJA3CR4wFAQUBrQ2ouFf63bYlOA61Y1i3bIKEa4AgBuoCtsv9fZ1PIZgAArNu0DN5LF7KTPKhATbZ-n-zLpDkiEYdzWnZj36z1xpLHeBtqU5ywOhGDuGivexGeVGPOfBnYdTjg-D2asYDuB8J4dO+8s5vzTrzT+tsf5yjNDIGQsR6DcCYGjaYR8i4n2gMA2OepTixD4MIRqSDgCQOgcaKYd9-bsWzqvGgA0C4hBGlzSuAsZoqScJYRAipYDAGwOLQgzhXAeF0s2Ae212G7XqolcyGR1AFzYexXEHk8B8AUKMM+8IL6ynlDoqAe5bgGLvqA5RjULFBwQWvRwegOAoLemgo+IiP7ACsTTGxpk3gwG8UwzRXpnGuKZt-dxpiRE0N8ag++tjAk0JCTlcJrDFbpKGlwzmY1Uy8MwEAA
```
