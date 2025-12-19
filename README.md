# Reactor 실습 - 리액티브 프로그래밍 완전 마스터

**Project Reactor를 통한 리액티브 프로그래밍(Reactive Programming) 완전 학습 프로젝트**입니다. **비동기 논블로킹 데이터 처리**의 핵심인 **Publisher/Subscriber 패턴**, **Flux/Mono**, **연산자(Operator)**, **스케줄링** 등을 실제 코드로 학습합니다.

---

## 🎯 프로젝트 목표

| 목표 | 설명 |
|------|------|
| **리액티브 개념** | Reactive Streams의 핵심 개념 학습 |
| **Publisher/Subscriber** | 발행자-구독자 패턴 이해 및 구현 |
| **Flux & Mono** | 비동기 데이터 시퀀스 처리 |
| **연산자 마스터** | map, filter, flatMap, concatMap 등 30+ 연산자 |
| **스케줄링** | subscribeOn, publishOn 스레드 관리 |
| **실무 패턴** | 에러 처리, 배압(Backpressure), 타이밍 제어 |

---

## 🛠 기술 스택

| 분야 | 기술 |
|------|------|
| **라이브러리** | Project Reactor (2024.0.6) |
| **Java 버전** | 17 LTS |
| **빌드 도구** | Gradle |
| **테스트** | JUnit 5 |
| **패턴** | Observer, Publisher/Subscriber, Chain of Responsibility |

---

## 📦 프로젝트 구조

```
reactor-practice/                           # 루트 프로젝트
│
├── build.gradle                            # 빌드 설정
├── settings.gradle                         # 프로젝트 설정
│
└── src/
    ├── main/
    │   └── java/com/laze/
    │       ├── Main.java                   # 진입점
    │       │
    │       ├── Publisher.java              # Flux/Mono 기초
    │       │   ├── startFlux()            # Flux 생성 (범위 기반)
    │       │   ├── startFlux2()           # Flux 생성 (컬렉션 기반)
    │       │   ├── startMono()            # Mono 생성 (값 있음)
    │       │   ├── startMono2()           # Mono 생성 (빈값)
    │       │   └── startMono3()           # Mono 에러 처리
    │       │
    │       ├── Operator.java              # 기본 연산자들
    │       │   ├── fluxMap()              # 데이터 변환
    │       │   ├── fluxFilter()           # 필터링
    │       │   ├── fluxFilterTake()       # 제한된 개수 추출
    │       │   ├── fluxFlatMap()          # 비동기 병렬 처리
    │       │   └── fluxFlatMap2()         # 구구단 예제
    │       │
    │       ├── Operator2.java             # 고급 연산자들
    │       │   ├── fluxConcatMap()        # 순서 보장 병합
    │       │   ├── monoFlatMapMany()      # Mono→Flux 변환
    │       │   ├── defaultIfEmpty()       # 기본값 설정
    │       │   ├── switchIfEmpty()        # 조건부 교체
    │       │   ├── fluxMerge()            # 여러 Publisher 병합
    │       │   ├── monoMerge()            # Mono 병합
    │       │   ├── fluxZip()              # 데이터 쌍 만들기
    │       │   └── monoZip()              # Mono 쌍 만들기
    │       │
    │       ├── Operator3.java             # 에러 처리 & 재시도
    │       │   ├── fluxOnError()          # 에러 처리
    │       │   ├── fluxRetry()            # 재시도
    │       │   ├── fluxTimeout()          # 타임아웃
    │       │   └── fluxFinally()          # 정리 작업
    │       │
    │       ├── Operator4.java             # 축약(Reduce) & 수집
    │       │   ├── fluxCollect()          # 컬렉션으로 수집
    │       │   ├── fluxReduce()           # 값 축약
    │       │   └── fluxBuffer()           # 버퍼링
    │       │
    │       └── Scheduler.java             # 스레드 관리
    │           ├── fluxMapWithSubscribeOn() # 구독 스레드 지정
    │           └── fluxMapWithPublishOn()  # 발행 스레드 지정
    │
    └── test/
        └── java/com/laze/
            ├── PublisherTest.java         # Publisher 테스트
            ├── OperatorTest.java          # 기본 연산자 테스트
            ├── Operator2Test.java         # 고급 연산자 테스트
            ├── Operator3Test.java         # 에러 처리 테스트
            ├── Operator4Test.java         # 축약 연산자 테스트
            └── SchedulerTest.java         # 스케줄링 테스트
```

---

## 🚀 빠른 시작

### 필수 요구사항

```bash
# Java 17+ 확인
java --version

# Gradle 확인
gradle --version
```

### 프로젝트 설정 및 실행

**1단계: 클론 및 빌드**
```bash
git clone https://github.com/L-a-z-e/reactor-practice.git
cd reactor-practice
gradle build
```

**2단계: Main 클래스 실행**
```bash
gradle run

# 출력 예제:
# [main] INFO reactor.Flux.Range.1 - onNext(1)
# [main] INFO reactor.Flux.Range.1 - onNext(2)
# ...
```

**3단계: 테스트 실행**
```bash
gradle test
```

---

## 💡 핵심 개념

### 1. 리액티브 프로그래밍이란?

**리액티브 프로그래밍(Reactive Programming)**은 **데이터 흐름과 변화의 전파**를 중심으로 하는 프로그래밍 방식입니다.

```
기존 방식 (❌)
요청 → 대기 → 응답 (블로킹)

리액티브 방식 (✅)
이벤트 → 처리 → 이벤트 (논블로킹)
```

### 2. Reactive Streams 인터페이스

```java
// Publisher: 데이터를 생산
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> s);
}

// Subscriber: 데이터를 소비
public interface Subscriber<T> {
    void onNext(T t);           // 다음 값 수신
    void onError(Throwable t);  // 에러 발생
    void onComplete();          // 완료
    void onSubscribe(Subscription s);
}

// Subscription: 구독 제어
public interface Subscription {
    void request(long n);       // 데이터 요청
    void cancel();              // 구독 취소
}
```

### 3. Flux vs Mono

| 특성 | Flux | Mono |
|------|------|------|
| **발행 데이터** | 0...N개 | 0 또는 1개 |
| **시퀀스** | 연속 스트림 | 단일 값 |
| **완료** | onComplete() | onComplete() 또는 onNext() |
| **에러** | 발생 가능 | 발생 가능 |
| **사용 사례** | 여러 데이터, 실시간 | 단일 응답, DB 조회 |

```java
// Flux: 0...N개 요소
Flux<Integer> flux = Flux.range(1, 10);  // 1~10 발행

// Mono: 0 또는 1개 요소
Mono<String> mono = Mono.just("Hello");  // "Hello" 발행
Mono<Void> empty = Mono.empty();         // 아무것도 발행 안함
```

### 4. Publisher 생성 방법

```java
// 방법 1: Flux.just - 고정 값
Flux<Integer> flux1 = Flux.just(1, 2, 3);

// 방법 2: Flux.range - 범위
Flux<Integer> flux2 = Flux.range(1, 10);

// 방법 3: Flux.fromIterable - 컬렉션
Flux<String> flux3 = Flux.fromIterable(List.of("a", "b", "c"));

// 방법 4: Mono.just - 단일 값
Mono<String> mono1 = Mono.just("Hello");

// 방법 5: Mono.empty - 빈값
Mono<String> mono2 = Mono.empty();

// 방법 6: Mono.error - 에러
Mono<String> mono3 = Mono.error(new Exception("Error!"));
```

---

## 🔄 핵심 연산자

### 1. 변환 연산자

```java
// map: 값 변환
Flux.range(1, 5)
    .map(i -> i * 2)           // 2, 4, 6, 8, 10
    .subscribe(System.out::println);

// flatMap: 비동기 변환 (순서 보장 안함)
Flux.range(1, 3)
    .flatMap(i -> Flux.range(1, 3)
        .map(j -> i * 10 + j))  // 11, 12, 13, 21, 22, ...
    .subscribe(System.out::println);

// concatMap: 순서 보장하며 변환
Flux.range(1, 3)
    .concatMap(i -> Flux.range(1, 3)
        .map(j -> i * 10 + j))  // 11, 12, 13, 21, 22, 23, ...
    .subscribe(System.out::println);
```

### 2. 필터링 연산자

```java
// filter: 조건에 맞는 항목만
Flux.range(1, 10)
    .filter(i -> i % 2 == 0)    // 2, 4, 6, 8, 10
    .subscribe(System.out::println);

// take: 첫 N개만 가져오기
Flux.range(1, 10)
    .take(3)                    // 1, 2, 3
    .subscribe(System.out::println);

// skip: 첫 N개 건너뛰기
Flux.range(1, 10)
    .skip(7)                    // 8, 9, 10
    .subscribe(System.out::println);

// distinct: 중복 제거
Flux.just(1, 2, 2, 3, 3, 3, 4)
    .distinct()                 // 1, 2, 3, 4
    .subscribe(System.out::println);
```

### 3. 조합 연산자

```java
// merge: 순서 없이 병합
Flux.merge(
    Flux.just("a", "b"),
    Flux.just("c", "d")
)
.subscribe(System.out::println);  // a, b, c, d (순서 보장 안함)

// concat: 순서 보장하며 병합
Flux.concat(
    Flux.just("a", "b"),
    Flux.just("c", "d")
)
.subscribe(System.out::println);  // a, b, c, d (순서 보장)

// zip: 쌍 만들기
Flux.zip(
    Flux.just("a", "b", "c"),
    Flux.just(1, 2, 3)
)
.subscribe(tuple -> System.out.println(
    tuple.getT1() + "=" + tuple.getT2()  // a=1, b=2, c=3
));
```

### 4. 에러 처리

```java
// onError: 에러 처리
Flux.error(new RuntimeException("Oops!"))
    .onErrorResume(e -> Flux.just("복구됨"))
    .subscribe(System.out::println);  // 복구됨

// retry: 재시도
Flux.range(1, 10)
    .filter(i -> i < 5)
    .onErrorMap(e -> new RuntimeException("Mapping error", e))
    .retry(3)                          // 최대 3번 재시도
    .subscribe(System.out::println);

// timeout: 타임아웃
Flux.interval(Duration.ofSeconds(2))
    .timeout(Duration.ofSeconds(1))
    .subscribe(
        System.out::println,
        e -> System.out.println("Timeout: " + e)
    );
```

### 5. 축약 연산자

```java
// reduce: 값 축약
Flux.range(1, 5)
    .reduce(0, (acc, val) -> acc + val)  // 1+2+3+4+5 = 15
    .subscribe(System.out::println);

// collect: 컬렉션으로 수집
Flux.range(1, 5)
    .collect(() -> new ArrayList<>(),
             (list, item) -> list.add(item))
    .subscribe(System.out::println);     // [1, 2, 3, 4, 5]

// buffer: 버퍼링
Flux.range(1, 10)
    .buffer(3)                           // [1,2,3], [4,5,6], [7,8,9], [10]
    .subscribe(System.out::println);
```

---

## ⚡ 스레드 관리 (Scheduler)

### subscribeOn vs publishOn

```
subscribeOn: 구독(subscribe) 시점의 스레드 지정
└── Publisher 체인 전체에 영향

publishOn: 특정 시점의 스레드 지정
└── 그 이후의 Operator부터 영향
```

### 예제

```java
public class SchedulerExample {
    
    // subscribeOn: 전체 구독 스레드 변경
    public Flux<Integer> subscribeOnExample() {
        return Flux.range(1, 10)
                .map(i -> {
                    System.out.println("map: " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(Schedulers.boundedElastic())  // 스레드 풀에서 실행
                .log();
    }
    
    // publishOn: 특정 시점부터 스레드 변경
    public Flux<Integer> publishOnExample() {
        return Flux.range(1, 10)
                .map(i -> {
                    System.out.println("map1: " + Thread.currentThread().getName());
                    return i + 1;
                })
                .publishOn(Schedulers.boundedElastic())    // 여기서 스레드 변경
                .map(i -> {
                    System.out.println("map2: " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(Schedulers.parallel())          // 다시 스레드 변경
                .map(i -> {
                    System.out.println("map3: " + Thread.currentThread().getName());
                    return i * 2;
                })
                .log();
    }
}
```

### Scheduler 종류

| Scheduler | 설명 | 사용 사례 |
|-----------|------|----------|
| `Schedulers.immediate()` | 현재 스레드에서 즉시 | 간단한 작업 |
| `Schedulers.single()` | 단일 재사용 스레드 | 순차 처리 |
| `Schedulers.boundedElastic()` | 스레드 풀 (I/O) | DB, HTTP 요청 |
| `Schedulers.parallel()` | 병렬 처리 스레드 | CPU 집약적 작업 |
| `Schedulers.newSingle()` | 새로운 단일 스레드 | 독립적인 작업 |

---

## 🎓 학습 경로

### 1주: 기초 개념
- [ ] Reactive Streams 이해
- [ ] Flux 기초 (range, just, fromIterable)
- [ ] Mono 기초 (just, empty, error)
- [ ] subscribe와 로깅
- [ ] Main.java 실행 및 이해

### 2주: 기본 연산자
- [ ] map, filter, flatMap
- [ ] take, skip, distinct
- [ ] PublisherTest 작성
- [ ] OperatorTest 작성
- [ ] Operator.java 모든 메서드 학습

### 3주: 고급 연산자
- [ ] merge, concat, zip
- [ ] defaultIfEmpty, switchIfEmpty
- [ ] flatMapMany
- [ ] Operator2.java 모든 메서드 학습
- [ ] Operator2Test 작성

### 4주: 에러 처리 & 스케줄링
- [ ] onError, retry, timeout
- [ ] subscribeOn vs publishOn
- [ ] Scheduler 종류
- [ ] Operator3.java, Operator4.java 학습
- [ ] SchedulerTest 작성

### 5주: 실무 패턴
- [ ] 배압(Backpressure) 처리
- [ ] Hot vs Cold Publisher
- [ ] Subject 사용
- [ ] 성능 튜닝
- [ ] 통합 프로젝트 만들기

---

## 📊 연산자 분류표

### 생성 연산자

```java
Flux.just(1, 2, 3)
Flux.range(1, 10)
Flux.fromIterable(list)
Flux.interval(Duration.ofSeconds(1))
Flux.never()
Flux.empty()
Flux.error(new Exception())
Mono.just(value)
Mono.empty()
Mono.defer(() -> Mono.just(value))
```

### 변환 연산자

```java
.map(x -> transform(x))
.flatMap(x -> Flux.from(x))
.concatMap(x -> Flux.from(x))
.switchMap(x -> Flux.from(x))
.flatMapMany(mono -> Flux.from(mono))
.cast(Class.class)
```

### 필터링 연산자

```java
.filter(x -> predicate(x))
.filterWhen(x -> Mono.from(x))
.take(n)
.takeLast(n)
.takeUntil(x -> condition(x))
.skip(n)
.skipLast(n)
.skipUntil(x -> condition(x))
.distinct()
.distinctUntilChanged()
```

### 조합 연산자

```java
Flux.merge(flux1, flux2)
Flux.mergeSequential(flux1, flux2)
Flux.concat(flux1, flux2)
Flux.zip(flux1, flux2)
Flux.combineLatest(flux1, flux2)
.zipWith(other)
.mergeWith(other)
.switchIfEmpty(alternative)
.switchOnNext(Flux<Flux<T>>)
```

### 에러 처리

```java
.onError(Consumer<Throwable>)
.onErrorResume(Function<Throwable, Publisher<T>>)
.onErrorMap(Function<Throwable, Throwable>)
.onErrorReturn(T)
.retry()
.retry(long n)
.retryWhen(Function<Flux<Throwable>, Publisher<?>>)
.timeout(Duration)
.doOnError(Consumer<Throwable>)
```

### 부작용(Side Effects)

```java
.doOnNext(Consumer<T>)
.doOnError(Consumer<Throwable>)
.doOnComplete(Runnable)
.doOnSubscribe(Consumer<Subscription>)
.doOnCancel(Runnable)
.doFinally(Consumer<SignalType>)
.log()
.log(String prefix)
```

### 축약 연산자

```java
.reduce(T identity, BiFunction<T, T, T>)
.collect(Supplier<A>, BiConsumer<A, T>)
.toStream()
.blockLast()
.blockFirst()
.collectList()
.collectMap(Function<T, K>)
.collectMultimap(Function<T, K>)
```

---

## 🔍 실전 예제

### 예제 1: 데이터 변환 및 필터링

```java
public class Example1 {
    public Flux<Integer> processingPipeline() {
        return Flux.range(1, 100)
                .filter(i -> i % 2 == 0)       // 짝수만
                .map(i -> i * 2)                // 2배로
                .take(10)                       // 처음 10개
                .log();
    }
}

// 출력: 2, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40
```

### 예제 2: 비동기 처리 (flatMap)

```java
public class Example2 {
    public Flux<String> getUsersWithPosts() {
        return Flux.just(1, 2, 3)                    // 사용자 ID
                .flatMap(userId -> 
                    getUserPosts(userId)             // 각 사용자의 포스트
                        .delayElements(Duration.ofMillis(100))
                )
                .log();
    }
    
    private Flux<String> getUserPosts(int userId) {
        return Flux.just(
            "Post " + userId + "-1",
            "Post " + userId + "-2"
        );
    }
}
```

### 예제 3: 병합 (merge vs concat)

```java
public class Example3 {
    // merge: 병렬 병합 (순서 보장 안함)
    public Flux<String> mergeExample() {
        return Flux.merge(
            Flux.just("A1", "A2"),
            Flux.just("B1", "B2")
        );
    }
    
    // concat: 순차 병합 (순서 보장)
    public Flux<String> concatExample() {
        return Flux.concat(
            Flux.just("A1", "A2"),
            Flux.just("B1", "B2")
        );
    }
}
```

### 예제 4: 에러 처리

```java
public class Example4 {
    public Mono<String> errorHandlingExample() {
        return Mono.just("data")
                .filter(s -> s.length() > 10)        // 조건 실패 → 에러
                .onErrorResume(e -> Mono.just("기본값"))  // 에러 처리
                .log();
    }
    
    public Mono<String> retryExample() {
        return Mono.defer(() -> callUnstableService())
                .retry(3)                             // 최대 3회 재시도
                .timeout(Duration.ofSeconds(5))      // 5초 타임아웃
                .onErrorReturn("서비스 사용 불가")    // 최종 에러 처리
                .log();
    }
    
    private Mono<String> callUnstableService() {
        return Mono.just("Success");
    }
}
```

### 예제 5: 스케줄링

```java
public class Example5 {
    public Flux<Integer> concurrentProcessing() {
        return Flux.range(1, 100)
                .map(i -> {
                    System.out.println("map: " + 
                        Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.parallel())   // 병렬 처리
                .map(i -> i * 2)
                .publishOn(Schedulers.boundedElastic()) // I/O 스레드
                .map(i -> {
                    System.out.println("second map: " + 
                        Thread.currentThread().getName());
                    return i;
                })
                .log();
    }
}
```

---

## 📝 테스트 작성

### StepVerifier를 이용한 테스트

```java
import reactor.test.StepVerifier;

public class ReactorTestExample {
    
    @Test
    public void testFlux() {
        Flux<Integer> flux = Flux.range(1, 5);
        
        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify();
    }
    
    @Test
    public void testFluxWithMap() {
        Flux<Integer> flux = Flux.range(1, 3)
                .map(i -> i * 2);
        
        StepVerifier.create(flux)
                .expectNext(2, 4, 6)
                .expectComplete()
                .verify();
    }
    
    @Test
    public void testFluxWithError() {
        Flux<Integer> flux = Flux.range(1, 5)
                .flatMap(i -> i == 3 ? 
                    Flux.error(new RuntimeException("Error at 3")) :
                    Flux.just(i)
                );
        
        StepVerifier.create(flux)
                .expectNext(1, 2)
                .expectError(RuntimeException.class)
                .verify();
    }
    
    @Test
    public void testMono() {
        Mono<String> mono = Mono.just("Hello");
        
        StepVerifier.create(mono)
                .expectNext("Hello")
                .expectComplete()
                .verify();
    }
    
    @Test
    public void testMonoEmpty() {
        Mono<String> mono = Mono.empty();
        
        StepVerifier.create(mono)
                .expectComplete()
                .verify();
    }
}
```

---

## 🎊 주요 개념 정리

### 1. 차가운 Publisher vs 뜨거운 Publisher

```java
// ❄️ 차가운 Publisher (Cold)
// - 각 구독자마다 독립적으로 데이터 발행
// - 구독하기 전까지는 데이터 발행 안함
Flux<Integer> cold = Flux.range(1, 5);
cold.subscribe(System.out::println);  // 1, 2, 3, 4, 5
cold.subscribe(System.out::println);  // 1, 2, 3, 4, 5 (다시)

// 🔥 뜨거운 Publisher (Hot)
// - 모든 구독자가 공유하는 하나의 데이터 스트림
// - 구독하기 전 데이터는 놓침
ConnectableFlux<Integer> hot = Flux.range(1, 100).publish();
hot.subscribe(System.out::println);
hot.connect();  // 구독 후 발행 시작
```

### 2. 배압(Backpressure)

```java
// Subscriber가 처리할 수 있는 만큼만 요청
Flux<Integer> flux = Flux.range(1, 1000)
        .subscribe(
            item -> System.out.println(item),
            error -> System.err.println(error),
            () -> System.out.println("Done"),
            subscription -> subscription.request(10)  // 10개씩 요청
        );
```

### 3. 구독 제어

```java
Flux<Integer> flux = Flux.range(1, 100)
        .subscribe(
            // onNext
            item -> System.out.println("Item: " + item),
            
            // onError
            error -> System.err.println("Error: " + error),
            
            // onComplete
            () -> System.out.println("Done!"),
            
            // onSubscribe (배압 처리)
            subscription -> subscription.request(Long.MAX_VALUE)
        );
```

---

## ⚙️ 의존성

```gradle
dependencyManagement {
    imports {
        mavenBom "io.projectreactor:reactor-bom:2024.0.6"
    }
}

dependencies {
    // Reactor Core
    implementation 'io.projectreactor:reactor-core'
    
    // Reactor Test
    testImplementation 'io.projectreactor:reactor-test'
    
    // JUnit 5
    testImplementation platform('org.junit:junit-bom:5.10.0')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}
```

---

## 🔗 연산자 참고표

### 빈도 순 상위 연산자

| 순위 | 연산자 | 사용 빈도 | 설명 |
|------|--------|----------|------|
| 1️⃣ | `map` | ⭐⭐⭐⭐⭐ | 가장 많이 사용 |
| 2️⃣ | `filter` | ⭐⭐⭐⭐⭐ | 데이터 필터링 |
| 3️⃣ | `flatMap` | ⭐⭐⭐⭐⭐ | 비동기 변환 |
| 4️⃣ | `subscribe` | ⭐⭐⭐⭐⭐ | 구독 필수 |
| 5️⃣ | `onError` | ⭐⭐⭐⭐ | 에러 처리 |
| 6️⃣ | `take` | ⭐⭐⭐⭐ | 제한된 개수 |
| 7️⃣ | `merge` | ⭐⭐⭐ | 병합 |
| 8️⃣ | `zip` | ⭐⭐⭐ | 쌍 생성 |
| 9️⃣ | `retry` | ⭐⭐⭐ | 재시도 |
| 🔟 | `reduce` | ⭐⭐⭐ | 축약 |

---

## 🐛 일반적인 문제 해결

### 1. 구독하지 않으면 아무것도 실행되지 않음

```java
// ❌ 문제: subscribe() 없음
Flux.range(1, 5).map(i -> i * 2);  // 아무것도 출력 안됨

// ✅ 해결: subscribe() 필수
Flux.range(1, 5)
    .map(i -> i * 2)
    .subscribe(System.out::println);  // 2, 4, 6, 8, 10
```

### 2. flatMap 순서 문제

```java
// ❌ 문제: flatMap은 순서 보장 안함
Flux.range(1, 3)
    .flatMap(i -> Flux.range(1, 3)
        .map(j -> i * 10 + j)
        .delayElements(Duration.ofMillis(100))
    )
    .subscribe(System.out::println);  // 순서 뒤섞임

// ✅ 해결: concatMap 사용
Flux.range(1, 3)
    .concatMap(i -> Flux.range(1, 3)
        .map(j -> i * 10 + j)
        .delayElements(Duration.ofMillis(100))
    )
    .subscribe(System.out::println);  // 11, 12, 13, 21, ...
```

### 3. 블로킹 연산 사용

```java
// ❌ 문제: 블로킹으로 완전히 멈춤
Integer result = Mono.just(10)
    .blockFirst();  // 위험! 스레드 블록

// ✅ 해결: 논블로킹 방식
Mono.just(10)
    .subscribe(System.out::println);  // 논블로킹
```

### 4. 메모리 누수

```java
// ❌ 문제: 구독 해제 안함
Flux.interval(Duration.ofSeconds(1))
    .subscribe(System.out::println);  // 계속 실행됨

// ✅ 해결: Disposable로 구독 해제
Disposable subscription = Flux.interval(Duration.ofSeconds(1))
    .subscribe(System.out::println);

// 나중에 해제
subscription.dispose();
```

---

## 📚 추가 학습 자료

### Reactor 공식 문서
- [Project Reactor](https://projectreactor.io/)
- [Reactor Core Reference](https://projectreactor.io/docs/core/latest/reference/)

### 주요 개념
- **Reactive Streams**: http://www.reactive-streams.org/
- **배압(Backpressure)**: 데이터 흐름 제어
- **스케줄링**: 멀티스레드 처리

### 관련 라이브러리
- **Spring WebFlux**: 리액티브 웹 프레임워크
- **R2DBC**: 리액티브 DB 접근
- **Reactor Netty**: 논블로킹 I/O
