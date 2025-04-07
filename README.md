# 📦 Store Project
⚠️ **현재 개발 진행 중**

Spring Boot와 Kafka를 활용하여 기본적인 커머스 시스템을 구축하여 학습 프로젝트입니다.

주문(Order)처리부터 Kafka 기반 비동기 이벤트 처리까지 구현했으며, 추후 상품(Product)기능까지 확장 예정입니다.

Docker를 사용하여 Kafka 환경을 구축하고, Kafdrop으로 Kafka를 모니터링할 수 있도록 설정했습니다.



## 🛠️ 기술 스택
- **Backend**: Java 17, Spring Boot 3
- **Database**: H2 Database (개발 환경)
- **Build Tool**: Gradle
- **Message Broker**: Apache Kafka(Docker)
- **Monitoring**: Kafdrop



## 📌 주요 기능
✅ 주문(Order)

**주문 생성**	POST /api/orders

**주문 취소**	POST /api/orders/{orderId}/cancel

**주문 데이터**	Kafka Producer를 통해 메시지 발행 및 Kafka Consumer가 주문 데이터 수신 후 DB 저장



## 📝 확장 예정 기능
- [  ] 상품(Product) 도메인 추가 (상품 등록/수정/삭제, 상세 조회)
- [  ] 주문 조회 기능 (상태별 필터링)
- [  ] Vue.js 기반 상품 상세 프론트엔드
- [  ] Redis 기반 캐시 처리



## 🗂 프로젝트 구조
com.example.store

 ├── common          (공통 DTO, 유틸 등)
 
 ├── kafka           (Kafka Producer, Consumer 관련)
 
 ├── order           (주문 도메인, 서비스, 컨트롤러)
 
 ├── product         (상품 도메인 - 확장 예정)
 
 ├── payment         (결제 도메인 - 확장 예정)
