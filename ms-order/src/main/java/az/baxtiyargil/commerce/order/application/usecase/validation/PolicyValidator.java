package az.baxtiyargil.commerce.order.application.usecase.validation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class PolicyValidator<T> {

    private List<Policy<T>> policies = new LinkedList<>();
    private Mode mode = Mode.FAIL_FAST_SEQUENTIAL;

    public static <T> PolicyValidator<T> with(List<Policy<T>> policies) {
        var validator = new PolicyValidator<T>();
        validator.policies = policies;
        return validator;
    }

    public static <T> PolicyValidator<T> withMode(Mode mode) {
        var validator = new PolicyValidator<T>();
        validator.mode = mode;
        return validator;
    }

    public static <T> PolicyValidator<T> with(Policy<T> policy) {
        var validator = new PolicyValidator<T>();
        validator.policies.add(policy);
        return validator;
    }

    private PolicyValidator(){
    }

    public PolicyValidator<T> addAll(List<Policy<T>> policies) {
        this.policies.addAll(policies);
        return this;
    }

    public PolicyValidator<T> add(Policy<T> policy) {
        policies.add(policy);
        return this;
    }

    public void executeAllFor(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        if (policies.isEmpty()) {
            return;
        }

        switch (mode) {
            case FAIL_FAST_SEQUENTIAL:
                sequentialFailFast(item);
                break;
            case FAIL_FAST_PARALLEL:
                parallelFailFast(item);
                break;
        }
    }

    private void sequentialFailFast(T item) {
        for (Policy<T> policy : policies) {
            policy.validate(item);
        }
    }

    @SuppressWarnings("preview")
    private void parallelFailFast(T item) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (Policy<T> policy : policies) {
                scope.fork(() -> policy.validate(item));
            }

            scope.join();
            scope.throwIfFailed();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public enum Mode {
        FAIL_FAST_PARALLEL,
        FAIL_FAST_SEQUENTIAL,
    }

}
