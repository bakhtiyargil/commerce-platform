# kafka-streams-demo

Dynamic Routing with Kafka Streams
Overview

A Spring Cloud Stream with Kafka Streams with dynamic message routing based on Http Header(X-Event-Type). 
Routes messages to consumer functions at runtime and supports retries and DLQs.

Features:
1. Dynamic Routing: Routes messages to functions based on X-Event-Type header.
2. Single Topic Binding: Multiple event types share one Kafka topic binding.
3. Retry & DLQ Support: Configurable retries, backoff, and dead-letter queues per event type.
4. Automatic Function Registration: Routers auto-registered at startup via BeanFactory.
5. Spring Cloud Stream Integration: Leverages Kafka Streams for scalable and fault-tolerant streaming.