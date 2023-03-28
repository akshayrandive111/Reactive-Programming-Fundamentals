package reactive.programming.demo.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class ReactiveOperatorService {

    public Flux<String> mapOperator() {
        return Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"))
                .map(String::toUpperCase);
    }

    public Flux<String> reactiveStreamImmutabilty() {
        Flux<String> languages = Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"));
        languages.map(String::toUpperCase);
        return languages;
    }

    public Flux<String> filterOperator(int stringLength) {
        return Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"))
                .filter(language -> language.length() > stringLength)
                .map(language -> language.length() + " - " + language.toUpperCase());
    }

    public Flux<String> flatMapOperator(int stringLength) {
        return Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"))
                .filter(language -> language.length() > stringLength)
                .map(String::toUpperCase)
                .flatMap(splitString);

    }

    Function<String, Flux<String>> splitString = (language) -> {
        String[] characters = language.split("");
        return Flux.fromArray(characters);
    };

    public Flux<String> flatMapOperatorAsynchronous(int stringLength) {
        return Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"))
                .filter(language -> language.length() > stringLength)
                .map(String::toUpperCase)
                .flatMap(splitStringWithDelays).log();
    }

    Function<String, Flux<String>> splitStringWithDelays = (language) -> {
        String[] characters = language.split("");
        return Flux.fromArray(characters)
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    };

    public Flux<String> concatMapOperatorAsynchronous(int stringLength) {
        return Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"))
                .filter(language -> language.length() > stringLength)
                .map(String::toUpperCase)
                .concatMap(splitStringWithDelays).log();
    }

    public Flux<String> flatMapManyOperator(int stringLength) {
        return Mono.just("java")
                .filter(language -> language.length() > stringLength)
                .map(String::toUpperCase)
                .flatMapMany(splitString);
    }

    public Flux<String> defaultIfEmptyOperator(int stringLength) {
        return Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"))
                .filter(language -> language.length() > stringLength)
                .map(String::toUpperCase)
                .flatMap(splitString)
                .defaultIfEmpty("default");
    }

    public Flux<String> switchIfEmptyOperator(int stringLength) {
        return Flux.fromIterable(Arrays.asList("java", "python", "angular", "javascript"))
                .filter(language -> language.length() > stringLength)
                .map(String::toUpperCase)
                .flatMap(splitString)
                .switchIfEmpty(Flux.just("default publisher"));
    }

}
