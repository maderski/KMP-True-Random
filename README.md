# KMP-True-Random

A Kotlin Multiplatform library that generates true random numbers using the [Random.org](https://www.random.org) API, with automatic fallback to local pseudo-random generation when the network is unavailable.

## Supported Platforms

| Platform | Status |
|---|---|
| Android | ✅ |
| iOS (arm64, x64, simulatorArm64) | ✅ |

## Installation

The library uses [Ktor](https://ktor.io) for networking. Add the shared module as a dependency and include the appropriate Ktor engine for each platform.

**`settings.gradle.kts`**
```kotlin
include(":shared")
```

**`build.gradle.kts`** (shared/multiplatform module)
```kotlin
kotlin {
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))
            implementation("io.ktor:ktor-client-core:3.1.0")
        }
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:3.1.0")
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:3.1.0")
        }
    }
}
```

## Usage

### 1. Create the repository

Instantiate `DefaultRandomNumberRepository` with the platform's HTTP engine via `HttpClientFactory`.

**Android**
```kotlin
import io.ktor.client.engine.okhttp.OkHttp
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.RandomNumberApiImpl
import com.maderskitech.kmptruerandom.numbergenerator.data.remote.network.HttpClientFactory
import com.maderskitech.kmptruerandom.numbergenerator.domain.DefaultRandomNumberRepository

val httpClient = HttpClientFactory.create(OkHttp.create())
val repository = DefaultRandomNumberRepository(RandomNumberApiImpl(httpClient))
```

**iOS (Swift)**
```swift
// In MainViewController.kt (iosMain source set)
import io.ktor.client.engine.darwin.Darwin

val httpClient = HttpClientFactory.create(Darwin.create())
val repository = DefaultRandomNumberRepository(RandomNumberApiImpl(httpClient))
```

### 2. Generate a random number

`getRandomNumber` is a suspend function that returns an `Int` in the range `[min, max]` (inclusive).

```kotlin
// In a coroutine or suspend context
val number = repository.getRandomNumber(min = 1, max = 100)
println(number) // e.g. 42
```

### 3. Use with a ViewModel

```kotlin
class MyViewModel(private val repository: RandomNumberRepository) : ViewModel() {

    private val _number = MutableStateFlow<Int?>(null)
    val number: StateFlow<Int?> = _number.asStateFlow()

    fun loadRandomNumber() {
        viewModelScope.launch {
            _number.value = repository.getRandomNumber(min = 1, max = 1000)
        }
    }
}
```

## Behavior

| Scenario | Result |
|---|---|
| Random.org responds successfully | Returns the true random number from the API |
| Network unavailable or API error | Falls back to `kotlin.random.Random` in the requested range |
| `min > max` | Throws `IllegalArgumentException` |

## API Reference

### `RandomNumberRepository`

```kotlin
interface RandomNumberRepository {
    suspend fun getRandomNumber(min: Int, max: Int): Int
}
```

### `DefaultRandomNumberRepository`

The default implementation. Calls Random.org and falls back to local generation on any network failure.

```kotlin
class DefaultRandomNumberRepository(api: RandomNumberApi) : RandomNumberRepository
```

### `HttpClientFactory`

Creates a configured `HttpClient` for the given engine. Handles JSON and plain-text content negotiation.

```kotlin
object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient
}
```

### `NetworkError`

Enum of possible API failure reasons, returned via `Response.Failure`:

```kotlin
enum class NetworkError : ErrorType {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    FORBIDDEN,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNABLE_TO_PARSE_INTEGER,
    UNKNOWN
}
```
