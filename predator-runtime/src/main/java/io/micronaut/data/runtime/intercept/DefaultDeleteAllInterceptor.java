package io.micronaut.data.runtime.intercept;

import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.util.ArrayUtils;
import io.micronaut.data.intercept.DeleteAllInterceptor;
import io.micronaut.data.store.Datastore;

import javax.annotation.Nonnull;

public class DefaultDeleteAllInterceptor<T> extends AbstractQueryInterceptor<T, Void> implements DeleteAllInterceptor<T> {

    public DefaultDeleteAllInterceptor(@Nonnull Datastore datastore) {
        super(datastore);
    }

    @Override
    public Void intercept(MethodInvocationContext<T, Void> context) {
        Object[] parameterValues = context.getParameterValues();
        Class rootEntity = getRequiredRootEntity(context);
        if (parameterValues.length == 1 && parameterValues[0] instanceof Iterable) {
            datastore.deleteAll(rootEntity, (Iterable) parameterValues[0]);
        } else if (parameterValues.length == 0) {
            datastore.deleteAll(rootEntity);
        } else {
            throw new IllegalArgumentException("Unexpected argument types received to deleteAll method");
        }
        return null;
    }
}