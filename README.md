# GraphSwimmer

A Spring Boot API for routing on waterways in the Netherlands.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Introduction

GraphSwimmer is a Spring Boot application designed to provide routing capabilities on waterways in the Netherlands. It uses a custom graph structure to represent the waterways and supports various routing algorithms.

## Features

- Routing on waterways based on CEMT classes
- Customizable graph structure
- RESTful API endpoints for routing queries
- JSON-based configuration for waterways


## Installation
Download the latest .jar from the releases page and run it using Java:

```sh
    java -jar CEMTRouting-x.x.x.jar
```

## Building from source

### Prerequisites

- Java 17 or higher
- Maven

### Steps

1. Clone the repository:
    ```sh
    git clone https://github.com/jessenagel/GraphSwimmer.git
    cd GraphSwimmer
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

Once the application is running, you can access the API endpoints to perform routing queries.

### Example

To get a route between two points, use the following endpoint:
```sh
GET /route?pointFrom=POINT_A&pointTo=POINT_B
