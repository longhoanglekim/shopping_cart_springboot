package com.trainings.shoppingcartdemo.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // Annotation này chỉ có thể áp dụng trên các phương thức
@Retention(RetentionPolicy.RUNTIME)  // Annotation này sẽ được giữ lại đến khi runtime (để AOP có thể sử dụng)
public @interface RequiresAuth {
}
