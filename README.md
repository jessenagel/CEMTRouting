# CEMTRouting


[![DOI](https://zenodo.org/badge/905143228.svg)](https://doi.org/10.5281/zenodo.15114238)


A Spring Boot API for routing on waterways in the Netherlands.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
## Introduction

CEMTRouting is a Spring Boot package designed to provide routing capabilities on waterways in the Netherlands. It uses a custom graph structure to represent the waterways and supports various routing algorithms.

## Features

- Routing on waterways based on CEMT classes
- Customizable graph structure
- RESTful API endpoints for routing queries
- JSON-based configuration for waterways


## Installation
Download the bevaarbaarheid.json file from the repository or from https://data.overheid.nl/dataset/14509-vaarweg-netwerk-nederland--vnds----bevaarbaarheidsinformatie and place it in the project directory.
Download the latest .jar from the releases page (and place it in the same directory as bevaarbaarheid.json) and run it in using Java:
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
    git clone https://github.com/jessenagel/CEMTRouting.git
    cd CEMTRouting
    ```

2. (Optional) Convert the latest Bevaarbaarheid file from https://data.overheid.nl/dataset/14509-vaarweg-netwerk-nederland--vnds----bevaarbaarheidsinformatie to json using a program such as QGis and replace bevaarbaarheid.json in the project directory with the updated file

3. Build the project using Maven:
    ```sh
    mvn clean install
    ```
 
4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

Once the application is running, you can access the API endpoints to perform routing queries.

### Example

To get a route between two points, use the following endpoint:
```
GET /route?pointFrom=POINT_A&pointTo=POINT_B
```
In addition, you can specifiy the minimum CEMT class that a route should have using:
```
GET /route?pointFrom=POINT_A&pointTo=POINT_B&CEMTClass=V_A
```
CEMT classes are formatted as:
```
_0, I, II, III, IV, V_A, V_B, VI_A, VI_B, VI_C, VII
```
If no class is provided, all edges are allowed.

It is also possible to request a distance matrix, using

```
GET /distanceMatrix?coordinates=[[lat1,lon1],[lat2,lon2],...],CEMTClass=V_A
```

Note that square brackets are not valid http characters and have to be encoded to before sending the get request.

### Other options that can be specified

```
nearestNodeMethod='nearest' (default) or 'class'
```
Determines the way the nearest node is determined. If 'nearest' is used, the nearest node to the given coordinates is used, which might make the route infeasible. If 'class' is used, the nearest node with the at least the same CEMT class as the given coordinates is used.
The distance that must be travelled over land to reach this node is NOT included in the route length. 

