package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;
import util.UserValidator;

public class UserEntryController {
	private ShopService shopService;
	private UserValidator userValidator;
	public void setShopService(ShopService shopService) {
		this.shopService =shopService;
	}
	public void setUserValidator(UserValidator uesrValidator) {
		this.userValidator = uesrValidator; 
	}
	//@RequestMapping(method=RequestMethod.GET)
	/*
	@GetMapping
	public String userEntryForm() {	//데이터필요없는 경우 String 리턴함.
		return null;	//view 이름. 기본이름으로 지정됨:userEntry.jsp 가 설정됨.
	}
	@ModelAttribute		//view폴더의 jsp 단에 연결되어있음
	public User getUser() {
		return new User();
	}
	*/
	@GetMapping		//get 방식 요청
	public ModelAndView userEntryForm() {
		ModelAndView mav= new ModelAndView();
		mav.addObject(new User());
		return mav;		//   /WEB-INF/view/userEmtry.jsp
	}
	@PostMapping	//post 방식 요청
	//User : 매개변수에 User 타입이 선언된 경우
	//파라미터값과, User의 set프로퍼티가 동일한 값을 저장.
	public ModelAndView userEntry(User user,BindingResult bindResult) {
		ModelAndView mav=new ModelAndView();
		userValidator.validate(user,bindResult);
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		
		try {
			shopService.insertUser(user);
			mav.addObject("user",user);
		}catch(DataIntegrityViolationException e) {		//중복키 오류
			e.printStackTrace();
			//중복 오류를 클라이언트한테 보여주는 곳
			bindResult.reject("error.duplicate.use");
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		mav.setViewName("userEntrySuccess");	//view 이름 설정.
		return mav;
	}
	
	@InitBinder	//파라미터 값을 형변환 기능
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true)); //true: 입력값이 선택	false:입력값이 필수
		
	}
	
}
