dp-dd-request-filter
================

A basic library for adding a Spring Boot request filter to make sure an `X-Request-Id` is present.

### Adding the logging

To expose the request-id in the logs, make sure a suitable pattern is included 
in the `logback.xml` of your project. An example [logback.xml](example/logback.xml) is provided.

```xml
<pattern>%d{HH:mm:ss.SSS} [%thread] %highlight(%-5level) %X{requestId} %logger{36} - %msg%n</pattern>

```
### Contributing

See [CONTRIBUTING](CONTRIBUTING.md) for details.

### License

Copyright © 2016-2017, Office for National Statistics (https://www.ons.gov.uk)

Released under MIT license, see [LICENSE](LICENSE.md) for details.
