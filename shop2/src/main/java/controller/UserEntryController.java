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
	public String userEntryForm() {	//�������ʿ���� ��� String ������.
		return null;	//view �̸�. �⺻�̸����� ������:userEntry.jsp �� ������.
	}
	@ModelAttribute		//view������ jsp �ܿ� ����Ǿ�����
	public User getUser() {
		return new User();
	}
	*/
	@GetMapping		//get ��� ��û
	public ModelAndView userEntryForm() {
		ModelAndView mav= new ModelAndView();
		mav.addObject(new User());
		return mav;		//   /WEB-INF/view/userEmtry.jsp
	}
	@PostMapping	//post ��� ��û
	//User : �Ű������� User Ÿ���� ����� ���
	//�Ķ���Ͱ���, User�� set������Ƽ�� ������ ���� ����.
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
		}catch(DataIntegrityViolationException e) {		//�ߺ�Ű ����
			e.printStackTrace();
			//�ߺ� ������ Ŭ���̾�Ʈ���� �����ִ� ��
			bindResult.reject("error.duplicate.use");
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		mav.setViewName("userEntrySuccess");	//view �̸� ����.
		return mav;
	}
	
	@InitBinder	//�Ķ���� ���� ����ȯ ���
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true)); //true: �Է°��� ����	false:�Է°��� �ʼ�
		
	}
	
}
