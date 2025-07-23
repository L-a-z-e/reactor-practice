package com.laze;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    private Scheduler scheduler = new Scheduler();

    @Test
    void fluxMapWithSubscribeOn() {
        StepVerifier.create(scheduler.fluxMapWithSubscribeOn())
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void fluxMapWithPublishOn() {
        StepVerifier.create(scheduler.fluxMapWithPublishOn())
                .expectNextCount(10)
                .verifyComplete();
    }
}