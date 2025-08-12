# Green Backend

## 1. 프로젝트 소개

* 온라인 쇼핑몰의 기본 기능 구현에 집중하여 REST API 개발을 진행한 프로젝트입니다.

## 2. 사용 기술

* `Java 21`, `Gradle`, `JUnit 5`, `Mockito`, `Testcontainers`, `Docker`

* `Spring Boot 3.4.5`, `Spring Web`, `Spring Data JPA`

* `MySQL`, `Querydsl`, `Redis`, `Kafka`

## 3. 실행에 필요한 환경 구성
> 해당 프로젝트를 실행 하려면 Java (Java 21 이상)와 도커 (Docker)가 설치되어 있어야 합니다!

* ① 터미널에서 아래 명령어를 입력하여 리포지토리를 로컬 환경에 다운로드 받습니다.

    ```
    git clone https://github.com/alliwannadev/green-backend.git
    ```

* ② `green-backend` 디렉토리로 이동합니다.

    ```
    cd green-backend
    ```

* ③ 이어서 `etc/docker/local` 디렉토리로 이동합니다.

    ```
    cd etc/docker/local
    ```

* ④ `reset.sh` 파일을 실행합니다.

    ```
    ./reset.sh
    ```

* ⑤ 도커 데스크톱을 확인 해보면 정상적으로 컨테이너가 구동 중인 것을 확인할 수 있습니다.

## 4. 실행 방법
> 아래 과정을 진행하기 전에 "3. 실행에 필요한 환경 구성"을 먼저 진행해야 합니다.

### 4-1. 빌드

* ① 터미널에서 `green-backend` 디렉토리로 이동합니다.

* ② 프로젝트를 빌드합니다.

  ```
  ./gradlew clean build
  ```

### 4-2. API 서버 실행

* ① 아래 디렉토리로 이동합니다.

  ```
  cd core/core-api/build/libs
  ```

* ② jar 파일을 실행합니다.

  ```
  java -jar core-api.jar
  ```

### 4-3. Consumer 실행

* ① 아래 디렉토리로 이동합니다.

  ```
  cd core/core-consumer/coupon-consumer/build/libs
  ```

* ② jar 파일을 실행합니다.

  ```
  java -jar coupon-consumer.jar
  ```
