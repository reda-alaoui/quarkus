package io.quarkus.avro.runtime.graal;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

import io.quarkus.runtime.graal.JDK17OrLater;

@TargetClass(className = "org.apache.avro.reflect.ReflectionUtil", onlyWith = JDK17OrLater.class)
final class Target_org_apache_avro_reflect_ReflectionUtil {

    @Substitute
    public static <V, R> Function<V, R> getConstructorAsFunction(Class<V> parameterClass, Class<R> clazz) {
        // Cannot use the method handle approach as it uses ProtectionDomain which got removed.
        try {
            Constructor<R> constructor = clazz.getConstructor(parameterClass);
            return new Function<V, R>() {
                @Override
                public R apply(V v) {
                    try {
                        return constructor.newInstance(v);
                    } catch (Exception e) {
                        throw new IllegalStateException("Unable to create new instance for " + clazz, e);
                    }
                }
            };
        } catch (Throwable t) {
            // if something goes wrong, do not provide a Function instance
            return null;
        }
    }
}

class AvroSubstitutions {
}
