package ginkgodi.annotation;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 *
 * @author George <GeorgeNiceWorld@gmail.com>
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface BasextComments {

    public BasextCommentItem[] value();
}
