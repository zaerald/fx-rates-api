<h1 align="center">FX Rates API</h1>

Please check the [REQUIREMENTS.md](./REQUIREMENTS.md) to review the goal of this project.

# Getting Started

## Open API / Swagger UI

- API Docs: `{baseUrl}/v3/api-docs`
- Swagger UI: `{baseUrl}/swagger-ui/index.html`

## Sample Requests

```shell
BASE_URL=http://localhost:8080/api
```

View all available currency symbols
```shell
curl "$BASE_URL/symbols"
```

Exchange 10 USD to PHP
```shell
curl "$BASE_URL/exchange?base=USD&target=PHP&amount=10"
```

Exchange 1.5 AED to JPY
```shell
curl "$BASE_URL/exchange?base=AED&target=JPY&amount=1.5"
```

## Prerequisites

- Java 11
- API key from https://exchangeratesapi.io
  - Use `fake` provider if you do not want to sign up. See "Changing Providers" section.
- Docker engine - optional if you want to run the application via Gradle

## Development

### Build

```shell
# unix
./gradlew build

# win
gradlew.bat build
```

### Running

**Docker**

Please ensure that the application is already built. See "Build" section for more details.

```shell
# perform build first
docker compose up
```
Please provide your access token in the [.env](./.env) file. Or run it with

```shell
# unix
FX_API_KEY=<YOUR_TOKEN_HERE> docker compose up

# win
SET FX_API_KEY=<YOUR_TOKEN_HERE>
docker compose up
```

If you encounter ` => ERROR [...] COPY build/libs/fx-rates-api*.jar fx-rates-api.jar`, then that means the application is not yet built.
Please check the "Build" section.

**Gradle**

You can run the application without performing the Gradle `build` task.

```shell
# unix
FX_API_KEY=<YOUR_TOKEN_HERE> ./gradlew bootRun

# win
SET FX_API_KEY=<YOUR_TOKEN_HERE>
gradlew.bat bootRun
```

# Providers

There are two providers for both the rate and symbols. The default provider is `exchangeratesapi.io`.

## Provider Limitations

### exchangeratesapi.io

- FREE plan
  - There’s a limit to the number of requests per month
  - For the full list of limitations, please check their [documentation](https://exchangeratesapi.io/documentation/).
  - We can't change the base currency, but we can use the list of rates with one base. The implication is that we have to derive our own rate. If the base currency of the provider has changed, the application will adapt. `EUR` is the base of the provider at the time of the development.

### Fake Provider

- Only supports limited rates. See the table below for the mappings.

| Base | Target | Rate  |
|------|--------|-------|
| USD  | PHP    | 52.17 |
| PHP  | USD    | 0.019 |
| AED  | JPY    | 93.49 |
| JPY  | AED    | 0.011 |
| EUR  | GBP    | 0.83  |
| GBP  | EUR    | 1.21  |

The rates provided from the table is just a fake data, and not necessarily precise, this fake provider is only useful
for testing the behavior of our service. They are just for demonstration purposes, and querying `USD` to `AED` for
instance will not work, as there is no mapping between them. Please use the [exchangeratesapi.io](http://exchangeratesapi.io)
provider if you want to test with multiple currencies.

Check "Changing Providers" section on how to change a provider.

## Changing Providers

### exchangeratesapi.io Provider

This is the default provider. Check the "Running" section.

### Fake Provider

You can change it to a `fake` provider by changing the active profile to `local`.

**Docker**

```shell
docker compose --env-file ./config/.env.local up
```

**Gradle**

```shell
# unix
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun

# win
SET SPRING_PROFILES_ACTIVE=local
gradlew.bat bootRun
```

### Checking Current Provider

To verify the current provider in use. Call the `/api/provider` endpoint.

```shell
BASE_URL=localhost:8080/api
curl "$BASE_URL/provider"
```

# Notes

- Buying and selling currencies will both use the `/api/exchange` endpoint. Both of them will provide `base`, `target`, and the `amount`, just in different context.
  - For instance, when a user wants to BUY `USD` to another party, and they want to spend 10 `PHP`, they will have `/api/exchange?base=PHP&target=USD&amount=10`.
  - If they are going to SELL 100 `PHP` to another party with `AED` currency, they will have `/api/exchange?base=PHP&target=AED&amount=100`.
- We are only exposing three endpoints.
  - `GET /api/exchange` - the only responsibility of this endpoint is to provide the value based from the derived `rate` of `base` and `target` currency. Selling and buying foreign currency can both use this endpoint, they just need to switch out the `base` and `target`.
  - `GET /api/symbols` - lists supported currency symbols
  - `GET /api/provider` - to easily verify what provider is in use.
    - exchange rates - default provider
    - fake provider - provides limited symbols and rates
- Here are some improvements/considerations for future development
  - More logs and control
  - Cached data are stored in memory, this can be improved by using a Redis or a similar service.
  - Better handling of Access Token, we can set up a config server for the application bootstrap
  - Ability to configure API timeouts
  - Rate limiting and rate throttling
  - Better API client design for future work if required, but right now, we only want to build URLs based on a path, it limits building complex queries.
