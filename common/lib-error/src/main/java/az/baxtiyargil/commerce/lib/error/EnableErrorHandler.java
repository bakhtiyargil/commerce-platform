package az.baxtiyargil.commerce.lib.error;

import az.baxtiyargil.commerce.lib.error.component.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Import(GlobalExceptionHandler.class)
public @interface EnableErrorHandler {
}
