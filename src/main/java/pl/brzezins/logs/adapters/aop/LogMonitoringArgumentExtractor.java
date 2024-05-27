package pl.brzezins.logs.adapters.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import pl.brzezins.logs.domain.exception.PointcutArgumentException;
import pl.brzezins.logs.domain.ports.PointcutArgumentExtractor;

import java.lang.annotation.Annotation;

class LogMonitoringArgumentExtractor implements PointcutArgumentExtractor {
    @Override
    public Object extractAnnotatedArgument(JoinPoint joinPoint, Class<? extends Annotation> annotationType) throws PointcutArgumentException {
        final var args = joinPoint.getArgs();

        if (args.length == 0) {
            throw new PointcutArgumentException("No args provided");
        } else if (joinPoint.getSignature() instanceof MethodSignature methodSignature) {
            final var method = methodSignature.getMethod();
            final var paramsAnnotationsArray = method.getParameterAnnotations();

            for (var i = 0; i < args.length; i++) {
                var paramAnnotations = paramsAnnotationsArray[i];

                for (var annotation : paramAnnotations) {
                    if (annotation.annotationType().equals(annotationType)) {
                        return args[i];
                    }
                }
            }
        }

        throw new PointcutArgumentException("Argument not matched with RequestBody annotation");
    }
}