# 비관적 잠금

트랜잭션끼리의 충돌이 발생한다고 가정하고 충돌을 방지하기 위해 락을 거는 방법이다. 

## 잠금 전 : 
<img width="605" alt="image" src="https://github.com/ori0o0p/Pessimistic-Lock/assets/107746917/d297387a-4e0e-4a70-8cfd-df971bf1bbbf">
<img width="458" alt="image" src="https://github.com/ori0o0p/Pessimistic-Lock/assets/107746917/6e1671b6-97d4-46d0-a8ce-949fbe04a967">
<img width="474" alt="image" src="https://github.com/ori0o0p/Pessimistic-Lock/assets/107746917/2b69370e-f65b-4f44-a2b3-bb959018bf1a">

상품의 기본값은 10000원이다. 

락을 걸지 않은 위의 프로젝트에 API 동시 요청을 보내보면? 
- 상품의 가격에서 -1000을 더하는 API
<img width="1319" alt="image" src="https://github.com/ori0o0p/Pessimistic-Lock/assets/107746917/6f0c50c7-f7cf-4c45-8dd9-e8dd6fd94945">
5번의 API 요청을 보냈으니 현재 상품의 값은 5000원이라 예상된다.

그러나 요청을 보낸 후 상품을 조회하면 
```json
{
    "id": 1,
    "title": "상품",
    "price": 9000
}
```
기본값 10000원에서 1000원이 깎인 9000원이 반환되는 것을 볼 수 있다. 
5000원이 반환되어야하는데 데이터가 일치하지 않는다. 

이유는 모든 트랜잭션이 동시에 10000원으로 데이터를 읽어서 1000을 뺀 9000원이 업데이트가 된 것이다. 

이러한 상황을 해결하려면 일반적으로 낙관적 잠금, 비관적 잠금과 같은 동시성 제어 형식을 구현해야한다. 

## 잠금 추가 : 
<img width="617" alt="image" src="https://github.com/ori0o0p/Pessimistic-Lock/assets/107746917/dec0e1ce-aac3-4174-8a59-85aaa7a590ec">

레포지토리에 `@Lock(LockModeType.PESSIMISTIC_WRITE)` 어노테이션을 추가하면 된다.
- 잠금이 해제될 때까지 다른 트랜잭션이 동일한 레코드에 동시에 쓰는 것을 방지하는 어노테이션

이제 락을 건 상태에서 API 동시 요청을 보내보면?
<img width="1329" alt="image" src="https://github.com/ori0o0p/Pessimistic-Lock/assets/107746917/2a302ff1-1147-420d-8ebd-dfd310a4de03">
```json
{
    "id": 1,
    "title": "상품",
    "price": 4000
}
```
9000원에서 5000원이 절감된 값을 반환하는 걸 볼 수 있다!

하지만 이런 비관적 잠금에는 단점이 존재한다.
- 동시성 제한 : 레코드 자체에 잠금을 걸어버리므로 동시성이 떨어진다.
- 데드락(교착상태) : 각 트랜잭션이 다른 트랜잭션의 자원을 기다리는 상태가 되어, 시스템 전체의 작동에 영향을 끼칠 수 있다.
- 성능 손실 : 동시 처리 제한으로 성능 손실을 감수해야한다.
- 롤백의 복잡성 : 복잡함 
