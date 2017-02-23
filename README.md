dp-dd-request-filter
================

A basic library for adding a Spring Boot request filter to make sure an `X-Request-Id` is present.

### Adding the logging

To expose the reqeust-id in the logs, make sure a suitable pattern is included 
in the `logback.xml` of your project. An exmple [logback.xml](example/logback.xml) is provided.

```$xml
![example_logback.xml](example/logback.xml)
```

### Contributing

See [CONTRIBUTING](CONTRIBUTING.md) for details.

### License

Copyright © 2016-2017, Office for National Statistics (https://www.ons.gov.uk)

Released under MIT license, see [LICENSE](LICENSE.md) for details.
