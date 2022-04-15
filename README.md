# Documentation
- OpenAPI Docs: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Execute
- Java 11
- MySQL 8

#### Build
```shell
$ ./gradlew build
```

#### Execute
```shell
$ java build/libs/backpacker-0.0.1.jar -Dspring.profiles.active=$ACTIVE_PROFILE 
```
##### Environments
- `SPRING_PROFILES_ACTIVE`
- `DB_HOSTNAME`
- `DB_USERNAME`
- `DB_PASSWORD`

## Architecture Concept.
멀티 모듈을 적용한 것과 비슷하게 패키지를 구성하였고, `api` / `core` 는 멀티 모듈로 분리되었을 때 사용할 Root Package 입니다.

### Core Module (`com.idus.backpacker.core`)
> Domain, Infrastructure Layer 가 있는 로직으로, Batch 등에서 공용으로 사용할 수 있는 비즈니스 로직이 담긴(Infra를 포함한) 모듈입니다.

1. Root Package 후에 존재하는 패키지는 Bounded Contexts(BCs) 입니다.
2. BCs 아래에는 `Domain` / `Infrastructure` 가 존재합니다. 구조와 규모에 따라 Infrastructure Layer 또한 다른 모듈로 나누기도 하나, 규모가 커진 후에 나누어도 괜찮은 부분이라 생각합니다.

### API Module (`com.idus.backpacker.api`)
> Application, Presentation(때때로, 헥사고날 아키텍처의 최종단) Layer 를 담당합니다. Spring Application 이 구동하는 곳이며, 비즈니스 로직의 Integration 을 담당하며 Endpoint, 다른 Application 간의 통신 및 구성을 이루는 모듈입니다.


### Edge Case
#### `Order` 에 `UserId` 는 Validation 이 안되어 있습니다.
1. 구매 당시 API에서 UserDetails 등을 받아와서 이를 처리할 수 있습니다.
2. BCs 가 다른 경우의 Validation 은 Saga pattern, 아니면 이벤트 기반의 검증(상태 기계 패턴을 조합하거나 하는 방식)으로 동작시켜야 합니다. 코드 레벨의 의존도를 없애도록.

#### `OrderCode` 는 중복을 허용하지 않지만 저장에 실패할 수 있습니다.
1. 커머스 규모상 12자 중복은 이슈를 발생시킬 가능성이 있습니다.
2. 실제 중복을 피하기 위해서는 Sequence 전용 TABLE을 생성하여 키를 관리하는 방법도 있지만, 적용하지 않았습니다. 규모상 불필요 하다 판단하였고, 요구에 맞추어 개선하는 것이 옳다 생각합니다.
3. 12자로는 DATETIME 사용이 어려워 DATETIME 은 사용하지 않았습니다.

- 실제로 이를 해결하기 위해서는 DATETIME + RANDOM INT(OR SEQUENCE) 를 조합하여 12자 이상을 허용하는 방안이 있을 것이며 UUID 를 사용하는 것도 좋은 방안입니다.
- 만약 DB에서 이를 Unique 하게 관리하는 경우 시퀀스 관리 테이블을 만들고, 해당 PK 에 Auto increment (Postgresql 과 같은 SEQUENCE 가 없기에 이렇게 관리한다는 가정)를 걸고, 이를 주문 번호의 일부로 이용하는 방안이 있습니다. 다만, AUTO INCREMENT LOCK이 발생합니다.
- DB에 로직을 위임하는 것보다는 `DATETIME + RANDOM INT`, 그리고 Retryable 이나 Redis 같은 인프라를 이용하여 관리하는 것이 성능으로 더 바람직한 것 같습니다.

### Q&A
#### Q. 왜 `UserOrder` 가 1:1 관계인가
#### A. 도메인 개념상 두 도메인을 한 번에 조회하는 것은 ReadModel (MongoDB, VIEW TABLE 등)을 이용하여 조회하여야 하나, 그 정도의 복잡성은 시스템 발전에 따라 적용해도 무관하다 생각
1. 현재의 요구사항은 회원의 가장 마지막 주문을 표기하는 것이니, 모든 주문 정보를 `User` Aggregate Root 의 보조 도메인 `UserOrder` 에 저장할 필요가 없습니다.
   1. 그러나 이 방법에 문제가 발생할 수 있습니다. `UserOrder` 테이블에 실제로는 Unique Index가 존재하기 때문에, UserOrder 가 없는 시점에 Unique Constraint Violation 이 발생할 것입니다.
   2. 이 경우 거의 모든 경우 최종적 일관성에 부합하게 최신 정보만을 업데이트 할 것이니, 큰 문제는 아니나 간혹 이 형상은 최신 버전을 바라보지 않을 수 있습니다.
   3. 그렇기에 `Retryable` 을 사용하여 추가 로직을 이용하지 않고 이를 보안했습니다. 실제 프로덕션 레벨의 어플리케이션이나 요청이 많은 어플리케이션은 보다 나은 방법을 선택함이 좋습니다. 예를 들자면, 애초에 MongoDB 와 같은 요소를 이용하여 ReadModel 을 분리한다던가 하는 방식입니다.
2. 모든 주문을 저장하면 `User` 의 `UserOrder` Collection 은 무한한 크기를 가질 수 있습니다.
   1. DDD 에서의 Aggregate Root 는 만들거나 불러오는 즉시 완성된 형태여야 합니다(Lazy Load 를 완성된 형태라 보지 않지만, 편의상 완성된 형태라 보겠습니다).
   2. UserOrder Collection 의 크기는 무한하니, 이를 불러오는 로직을 실행시 엄청난 부하가 발생할 수 있습니다.
   3. 이를 개발자가 예견하고 방어할 수는 없습니다. 몇 개의 컬렉션이 안에 들어있는지 Lazy Load 상태라면 알 수가 없을 것이며, Eager Load라면 애초에 엄청난 SELECT 가 실행된 이후일 테니, 소용 없습니다.
   4. 개발자는 DDD 개념에 따라 Collection 이 무한하지 않음을 보장하고 접근할 것입니다. 이 예외적인 경우를 개발자의 방어적 프로그래밍에게 책임을 위임할 수 없습니다.

#### Q. CQRS 을 적용하였음에도 ReadModel 이 같은 DB를 바라보는 이유
#### A. 복잡도의 문제입니다
굳이 비용이나 복잡도가 낮은 환경에서 ReadModel 을 위한 저장소를 따로 이용하는 것은 불필요하다 생각합니다. 계층을 분리함으로서 일차적인 의존성 분리는 완료되었다 생각합니다.

#### Q. `UserOrder` 가 왜 양방향 바인딩이 아닌지?
#### A. 양방향 바인딩으로 객체 탐색을 할 경우가 없습니다.
양방향 바인딩은 관계형 DB를 객체 지향에서 구현하기 위한 일종의 수법일 뿐, 실제 비즈니스 로직에 필요하지 않으며 더 중요한 사실은 `UserOrder` 는 Entity로서의 생명 주기가 아닌 Value Object 로의 생명 주기를 지닌다는 점입니다.

1. Entity 는 Id로서 객체를 고유하게 탐색하고 관리합니다. 이는 영속성에 관계가 있는 요소이지만 실제로 본 시스템에 사용하는 방식은 Value Object 에 가깝습니다.
2. VO 는 본디 동등성으로 객체의 고유함을 판별한다는 점에서 이 객체는 Entity 처럼 보이나 Value Object 인 것입니다.
