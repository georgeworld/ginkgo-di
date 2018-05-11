package ginkgodi.annotation;

import java.lang.annotation.*;

/**
 *
 * @author George <GeorgeNiceWorld@gmail.com>
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface BasextCommentItem {

    public String dateTime();

    public String version();

    public String[] notes() default {""};
}
