package controller;

import javax.servlet.http.HttpSession;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;
import util.LoginValidator;

public class LoginController {
	private ShopService shopService;
	private Validator loginValidator;
	
	public void setShopService(ShopService shopService) {
		this.shopService=shopService;
	}
	public void setLoginValidator(Validator Validator) {
		this.loginValidator = Validator;
	}
	
//	@GetMapping		//get ��� ��û
//	public ModelAndView userEntryForm() {
//		ModelAndView mav= new ModelAndView();
//		mav.addObject(new User());
//		return mav;		//   /WEB-INF/view/userEntry.jsp
//	}
	@GetMapping
	//Model : view�� ������ ������ ��ü
	public String loginForm(Model model) {  //String �� view
		model.addAttribute(new User());
		return "login";	//view �̸�
	}
	
	@PostMapping
	 public ModelAndView login(User user, BindingResult bresult, HttpSession session) {
		ModelAndView mav= new ModelAndView();
		System.out.println(mav);
		loginValidator.validate(user, bresult);
			if(bresult.hasErrors()) {
				mav.getModel().putAll(bresult.getModel());
				return mav;
			}
			/*
			 * 1.db���� userid �� �ش��ϰ� �������� �о� User ��ü�� ����
			 * 2.�Էµ� ��й�ȣ�� db�� ��й�ȣ ���Ͽ� ���ϴ� ���
			 * 	session.Attribute("loginUser",dbuser) ����
			 * 3.�Էµ� ��й�ȣ�� db�� ��й�ȣ ���Ͽ� ����ġ�ϴ� ���
			 * 	��ȿ�� �������� "��й�ȣ�� Ȯ���ϼ���." �޼����� login.jsp �������� ����
			 * 4.�α����� �������� ���  loginsuccess.jsp �������� �̵�.
			 * 
			 * 
			 */
			try {		//��ȿ�� ���� �κ�
			//1
			User dbuser = shopService.getUser(user.getUserid());
			//2
			//user.getPassword() : �Էµ� ��й�ȣ
			//dbuser.getPassword() : db�� ����� ��й�ȣ
			if(user.getPassword().equals(dbuser.getPassword())) {
				session.setAttribute("loginUser", dbuser);		//session�� ��ü
				mav.setViewName("loginSuccess");
				return mav;
				
			}else {//3 : ��й�ȣ Ʋ�����
				bresult.reject("error.login.password");
				mav.getModel().putAll(bresult.getModel());
				return mav;
			}
			}catch(EmptyResultDataAccessException e) { //��ȿ�� ���� �κ� spring jdbc������ ����
				//dbuser�� ������ ���� ���
				bresult.reject("error.login.id");
				mav.getModel().putAll(bresult.getModel());
				return mav;
			}
			
			
	}
	
}
