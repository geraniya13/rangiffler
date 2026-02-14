package io.student.rangiffler.extension;


import io.qameta.allure.Allure;
import io.student.rangiffler.annotation.UserType;
import io.student.rangiffler.model.StaticUser;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class UsersQueueExtension implements BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    public final static String HARDCODED_PASSWORD = "123456";

    private static final Queue<StaticUser> EMPTY_USERS = new ConcurrentLinkedQueue<>(),
            WITH_FRIEND_USERS = new ConcurrentLinkedQueue<>(),
            WITH_INCOME_REQUEST_USERS = new ConcurrentLinkedQueue<>(),
            WITH_OUTCOME_REQUEST_USERS = new ConcurrentLinkedQueue<>();

    static {
        EMPTY_USERS.add(new StaticUser("foreverAlone", HARDCODED_PASSWORD, null, null, null));
        WITH_FRIEND_USERS.add(new StaticUser("charmer", HARDCODED_PASSWORD, "beer", null, null));
        WITH_INCOME_REQUEST_USERS.add(new StaticUser("onDemand", HARDCODED_PASSWORD, null, "bee", null));
        WITH_OUTCOME_REQUEST_USERS.add(new StaticUser("eager", HARDCODED_PASSWORD, null, null, "duck"));
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> AnnotationSupport.isAnnotated(parameter, UserType.class))
                .map(parameter -> parameter.getAnnotation(UserType.class))
                .forEach(
                        ut -> {
                            Optional<StaticUser> user = Optional.empty();
                            StopWatch stopWatch = StopWatch.createStarted();
                            while (user.isEmpty() && stopWatch.getTime(TimeUnit.SECONDS) < 30) {
                                user = Optional.ofNullable(getQueue(ut.value()).poll());
                            }
                            user.ifPresentOrElse(
                                    u -> {
                                        Map<UserType.Type, Queue<StaticUser>> map = ((Map<UserType.Type, Queue<StaticUser>>) context.getStore(NAMESPACE)
                                                .getOrComputeIfAbsent(
                                                        context.getUniqueId(),
                                                        key -> new HashMap<>()
                                                ));
                                        if (!map.containsKey(ut.value())) {
                                            Queue<StaticUser> queue = new ConcurrentLinkedQueue<>();
                                            queue.add(u);
                                            map.put(ut.value(), new ConcurrentLinkedQueue<>());
                                        }
                                        Queue<StaticUser> queue = map.get(ut.value());
                                        queue.add(u);
                                        map.put(ut.value(), queue);
                                    },
                                    () -> {
                                        throw new IllegalStateException("Can't find user after 30 seconds");
                                    }
                            );
                        }
                );
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Map<UserType.Type, Queue<StaticUser>> map = context.getStore(NAMESPACE).get(
                context.getUniqueId(),
                Map.class);
        for (Map.Entry<UserType.Type, Queue<StaticUser>> e : map.entrySet()) {
            Queue<StaticUser> queue = e.getValue();
            queue.forEach(u -> getQueue(e.getKey()).add(u));
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(StaticUser.class)
                && AnnotationSupport.isAnnotated(parameterContext.getParameter(), UserType.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        UserType ut = parameterContext
                .findAnnotation(UserType.class)
                .orElseThrow();

        Map<UserType.Type, Queue<StaticUser>> map = ((Map<UserType.Type, Queue<StaticUser>>) extensionContext
                .getStore(NAMESPACE)
                .get(extensionContext.getUniqueId()));

        Queue<StaticUser> queue = map.get(ut.value());

        StaticUser user = queue.poll();

        queue.add(user);

        map.put(ut.value(), queue);

        return user;
    }

    private Queue<StaticUser> getQueue(UserType.Type ut) {
        return switch (ut) {
            case EMPTY -> EMPTY_USERS;
            case WITH_FRIEND -> WITH_FRIEND_USERS;
            case WITH_INCOME_REQUEST -> WITH_INCOME_REQUEST_USERS;
            case WITH_OUTCOME_REQUEST -> WITH_OUTCOME_REQUEST_USERS;
        };
    }
}
