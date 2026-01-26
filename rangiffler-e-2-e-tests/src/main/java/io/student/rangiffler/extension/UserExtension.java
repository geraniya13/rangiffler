package io.student.rangiffler.extension;


import com.github.javafaker.Faker;
import io.student.rangiffler.annotation.User;
import io.student.rangiffler.model.UserJson;
import io.student.rangiffler.service.UsersClient;
import io.student.rangiffler.service.UsersDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;


public class UserExtension implements BeforeEachCallback, ParameterResolver {
    public final static String PASSWORD = "password";

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserExtension.class);

    private final UsersClient usersClient = new UsersDbClient();

    private final Faker faker = new Faker();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                User.class
        ).ifPresent(
                anno -> {
                    context.getStore(NAMESPACE).put(
                            context.getUniqueId(),
                            usersClient.createUser(faker.name().username(), PASSWORD)
                    );
                }
        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), UserJson.class);
    }
}
