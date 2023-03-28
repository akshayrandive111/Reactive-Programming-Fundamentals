package reactive.programming.demo.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ReactiveOperatorServiceTest {

    ReactiveOperatorService reactiveOperatorService = new ReactiveOperatorService();

    @Test
    void mapOperator_transformsDataFromOneFormToAnother() {
        Flux<String> transformedData = reactiveOperatorService.mapOperator();
        StepVerifier.create(transformedData)
                .expectNext("JAVA", "PYTHON", "ANGULAR", "JAVASCRIPT")
                .verifyComplete();
    }

    @Test
    void reactiveStream_notModifiedOnceCreated() {
        Flux<String> transformedData = reactiveOperatorService.reactiveStreamImmutabilty();
        StepVerifier.create(transformedData)
                .expectNext("java", "python", "angular", "javascript")
                .verifyComplete();
    }

    @Test
    void filterOperator_filters() {
        Flux<String> transformedData = reactiveOperatorService.filterOperator(6);
        StepVerifier.create(transformedData)
                .expectNext("7 - ANGULAR", "10 - JAVASCRIPT")
                .verifyComplete();
    }

    @Test
    void flatMapOperator_flattensData() {
        Flux<String> transformedData = reactiveOperatorService.flatMapOperator(6);
        StepVerifier.create(transformedData)
                .expectNext("A","N","G","U","L","A","R","J","A","V","A","S","C","R","I","P","T")
                .verifyComplete();
    }

    @Test
    void flatMapOperator_flattensDataAsynchronously() {
        Flux<String> transformedData = reactiveOperatorService.flatMapOperatorAsynchronous(6);
        StepVerifier.create(transformedData)
//                .expectNext("A","N","G","U","L","A","R","J","A","V","A","S","C","R","I","P","T")
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void concatMapOperator_preservesOrderForAsynchronousCall() {
        Flux<String> transformedData = reactiveOperatorService.concatMapOperatorAsynchronous(6);
        StepVerifier.create(transformedData)
                .expectNext("A","N","G","U","L","A","R","J","A","V","A","S","C","R","I","P","T")
                .verifyComplete();
    }

    @Test
    void flatMapManyOperator_returnsFluxForMonoPublisher() {
        Flux<String> transformedData = reactiveOperatorService.flatMapManyOperator(3);
        StepVerifier.create(transformedData)
                .expectNext("J","A","V","A")
                .verifyComplete();
    }

    @Test
    void defaultIfEmptyOperator_returnsDefaultValue() {
        Flux<String> transformedData = reactiveOperatorService.defaultIfEmptyOperator(10);
        StepVerifier.create(transformedData)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void switchIfEmptyOperator_returnsDefaultValue() {
        Flux<String> transformedData = reactiveOperatorService.switchIfEmptyOperator(10);
        StepVerifier.create(transformedData)
                .expectNext("default publisher")
                .verifyComplete();
    }
}
