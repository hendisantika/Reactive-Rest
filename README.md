# Reactive REST Services with Spring Boot

A demonstration project showcasing different approaches to building reactive REST services using Spring Boot. This
project compares synchronous, asynchronous blocking, and asynchronous non-blocking REST endpoints.

## Overview

This application demonstrates three different approaches to handling HTTP requests:

1. **Synchronous** - Traditional blocking request handling
2. **Asynchronous Blocking** - Async with thread pool but still blocks threads
3. **Asynchronous Non-Blocking** - True async without blocking threads

## Technology Stack

- Java 21
- Spring Boot 3.5.7
- Gradle 8.9
- Spring Web

## Prerequisites

- Java 21 or higher
- Gradle (wrapper included)

## Getting Started

### Build the Project

```bash
./gradlew clean build
```

### Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Synchronous Endpoint

```bash
curl http://localhost:8080/sync
```

**Description**: Traditional synchronous endpoint that blocks the request thread for 100ms before responding.

**Response**: `sync`

### 2. Asynchronous Blocking Endpoint

```bash
curl http://localhost:8080/async-blocking
```

**Description**: Uses `DeferredResult` with an `ExecutorService` thread pool (100 threads). The request thread is freed,
but work is delegated to a worker thread that blocks.

**Response**: `async-blocking`

### 3. Asynchronous Non-Blocking Endpoint

```bash
curl http://localhost:8080/async-nonblocking
```

**Description**: Uses `DeferredResult` with a `Timer` to schedule the response. This is truly non-blocking as it doesn't
tie up any threads during the delay.

**Response**: `async-nonblocking`

## Configuration

- **Thread Pool Size**: 100 threads for async-blocking endpoint
- **Delay**: 100 milliseconds for all endpoints

These values can be modified in `ReactiveRestApplication.java:22-24`

## Running Tests

```bash
./gradlew test
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/hendisantika/
│   │       └── ReactiveRestApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/hendisantika/
            └── ReactiveRestApplicationTests.java
```

## Performance Comparison

- **Sync**: Blocks the request thread during processing
- **Async-Blocking**: Frees the request thread but uses worker threads from the pool
- **Async-NonBlocking**: Most efficient - doesn't block any threads during the delay

## Reference

Based on the
article: [Reactive REST Services with Spring Boot](https://www.linkedin.com/pulse/reactive-rest-services-spring-boot-aliaksandr-liakh)

## Author

hendisantika@gmail.com

## License

This project is available for educational purposes.