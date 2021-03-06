package util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import logic.User;

public class LoginValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
		
	}

	public void validate(Object target, Errors errors) {
		User user=(User)target;
		String group="error.required";
		if(user.getUserid()==null || user.getUserid().length()==0) {
			errors.rejectValue("userid", group);	//파라미터 별  오류 등록
		}
		if(user.getPassword()==null || user.getPassword().length()==0) {
			errors.rejectValue("password", group);
		}
		if(errors.hasErrors()) {	//오류 발생
			errors.reject("error.input.user"); //글로벌 오류
		}
	}

}
