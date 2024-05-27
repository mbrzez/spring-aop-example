package pl.brzezins.logs.domain.ports;

import org.aspectj.lang.JoinPoint;
import pl.brzezins.logs.domain.exception.PointcutArgumentException;

import java.lang.annotation.Annotation;

public interface PointcutArgumentExtractor {
    Object extractAnnotatedArgument(JoinPoint joinPoint, Class<? extends Annotation> annotationType) throws PointcutArgumentException;
}
