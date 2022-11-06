# Cron Parser

A simple cron parser which displays scheduling time for the provided cron expression.


### Input format:
```
 min hour day_of_month month day_of_week command
```

### Output format:
``` 
minute        * * * 
hour          *
day of month  *
month         *
day of week   *
command       *
```

### Example:
```
input: 
  */15 0 1,15 * 1-5 /usr/bin/find

output:
  minute        0 15 30 45
  hour          0
  day of month  1 15
  month         1 2 3 4 5 6 7 8 9 10 11 12
  day of week   1 2 3 4 5
  command       /usr/bin/find
```

### Cron Supported values:

| Component    | Required | Allowed Values     | Allowed Special Chars |
|--------------|----------|--------------------|-----------------------|
| minute       | Y        | 0-59               | ,-*/                  |
| hour         | Y        | 0-23               | ,-*/                  |
| day of month | Y        | 1-31               | ,-*/?                 |
| month        | Y        | (1-12) / (JAN-DEC) | ,-*/                  |
| day of week  | Y        | (1-7) / (SUN-SAT)  | ,-*/?                 |
| command      | Y        |                    |                       |


## Local Setup
Install JDK-17

### Mac OS
```shell
brew install openjdk@17
```

### Ubuntu
```shell
sudo apt install openjdk-17-jdk
```

### Windows
```shell
choco install openjdk17
```

Or download and install from [here](https://www.oracle.com/in/java/technologies/downloads/#java17) 

## Build and Execution
```shell
./gradlew build
java -jar build/libs/cron_parser-1.0-SNAPSHOT.jar "cron_exp"
```

### Run test cases

```shell
./gradlew test
```

