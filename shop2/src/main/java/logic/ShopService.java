package logic;

import dao.UserDao;

public class ShopService {
	private UserDao userdao;
	public void setUserDao(UserDao userDao) {
		this.userdao= userDao;
	}
	
	public void insertUser(User user) {
		userdao.insert(user);
	}

	public User getUser(String userid) {
		return userdao.selectOne(userid);
	}
}
