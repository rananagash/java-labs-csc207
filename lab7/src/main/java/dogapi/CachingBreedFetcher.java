package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private final BreedFetcher wrappedFetcher;
    private final Map<String, List<String>> cache;
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.wrappedFetcher = fetcher;
        this.cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // Check if breed is already in cache
        if (cache.containsKey(breed)) {
            return cache.get(breed);
        }

        // Don't catch the exception here - let it propagate naturally
        // Only increment counter and cache on successful calls
        callsMade++;
        List<String> subBreeds = wrappedFetcher.getSubBreeds(breed);

        // Only cache successful results (not exceptions)
        cache.put(breed, subBreeds);
        return subBreeds;
    }

    public int getCallsMade() {
        return callsMade;
    }
}