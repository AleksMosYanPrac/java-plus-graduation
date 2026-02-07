package ru.practicum.ewm.stats.analyzer.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessorRunner implements CommandLineRunner {

    private final EventSimilarityProcessor eventsSimilarityProcessor;
    private final UserActionProcessor userActionProcessor;

    @Override
    public void run(String... args) throws Exception {
        Thread eventSimilarityThread = new Thread(eventsSimilarityProcessor);
        eventSimilarityThread.setName("eventSimilarityThread");
        eventSimilarityThread.start();

        userActionProcessor.run();
    }
}