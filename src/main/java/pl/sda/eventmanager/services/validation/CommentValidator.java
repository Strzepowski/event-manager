package pl.sda.eventmanager.services.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.eventmanager.dto.CommentForm;

@Service
public class CommentValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CommentForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CommentForm commentForm = (CommentForm) o;



            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commentContents", "NotEmpty", "All fields are required.");

        if (commentForm.getCommentContents().length() > 500){
            errors.rejectValue("commentContents", "Size.commentForm.commentContents", "Please use less than 500 characters.");
        }

    }
}
