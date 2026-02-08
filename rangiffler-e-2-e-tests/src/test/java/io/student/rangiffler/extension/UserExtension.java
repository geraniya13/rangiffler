package io.student.rangiffler.extension;


import io.student.rangiffler.annotation.User;
import io.student.rangiffler.model.UserJson;
import io.student.rangiffler.service.UsersClient;
import io.student.rangiffler.service.UsersDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;


public class UserExtension extends BasicExtension implements BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserExtension.class);

    private final UsersClient usersClient = new UsersDbClient();

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                User.class
        ).ifPresent(
                anno -> {
                    context.getStore(NAMESPACE).put(
                            context.getUniqueId(),
                            usersClient.createUser(USERNAME, PASSWORD)
                    );
                }
        );
    }

    @Override
    public void afterEach(ExtensionContext context) {
        UserJson user = context.getStore(NAMESPACE).get(context.getUniqueId(), UserJson.class);
        usersClient.deleteUser(user.data().user().username());
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
