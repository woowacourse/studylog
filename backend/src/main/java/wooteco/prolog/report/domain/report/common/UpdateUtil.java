package wooteco.prolog.report.domain.report.common;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UpdateUtil {

    private UpdateUtil() {
    }

    public static <T extends Updatable<T>> void execute(List<T> origin, List<T> source) {
        List<UpdatableProxy<T>> originProxies = toProxy(origin);
        List<UpdatableProxy<T>> sourceProxies = toProxy(source);

        delete(originProxies, sourceProxies);
        update(originProxies, sourceProxies);
        create(originProxies, sourceProxies);

        resetOrigin(origin, originProxies);
    }

    private static <T extends Updatable<T>> List<UpdatableProxy<T>> toProxy(
        List<T> origin) {
        return origin.stream()
            .map(UpdatableProxy::new)
            .collect(toList());
    }

    private static <T extends Updatable<T>> void update(List<UpdatableProxy<T>> origin,
                                                        List<UpdatableProxy<T>> source) {
        Map<UpdatableProxy<T>, UpdatableProxy<T>> origins = origin.stream()
            .collect(toMap(Function.identity(), Function.identity()));

        List<UpdatableProxy<T>> updatableSources = source.stream()
            .filter(origins::containsKey)
            .collect(toList());

        updatableSources.forEach(sourceProxy -> origins.get(sourceProxy).update(sourceProxy));

        source.removeAll(updatableSources);
    }

    private static <T extends Updatable<T>> void delete(List<UpdatableProxy<T>> origin,
                                                        List<UpdatableProxy<T>> source) {
        origin.removeIf(o -> !source.contains(o));
    }

    private static <T extends Updatable<T>> void create(List<UpdatableProxy<T>> origin,
                                                        List<UpdatableProxy<T>> source) {
        origin.addAll(source);
    }

    private static <T extends Updatable<T>> void resetOrigin(List<T> origin,
                                                             List<UpdatableProxy<T>> originProxies) {
        origin.clear();
        List<T> updatedOrigin = extractTarget(originProxies);
        origin.addAll(updatedOrigin);
    }

    private static <T extends Updatable<T>> List<T> extractTarget(
        List<UpdatableProxy<T>> originProxies) {
        return originProxies.stream()
            .map(UpdatableProxy::getTarget)
            .collect(toList());
    }
}
