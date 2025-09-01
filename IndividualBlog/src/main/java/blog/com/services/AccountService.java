package blog.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.AccountDao;
import blog.com.models.entity.Account;

@Service
public class AccountService {

	@Autowired
	private AccountDao accountDao;

	// Accountの保存処理（登録処理）
	// もし、登録できたメールアドレスに登録したいメールアドレスがない場合（findByAccountEmail == null）
	// save メソッドを使用してAccount登録処理をする、できたらtrue
	// そうではない場合Account登録できない、false
	public boolean createAccount(String accountName, String accountEmail, String password) {
		if (accountDao.findByAccountEmail(accountEmail) == null) {
			accountDao.save(new Account(accountName, accountEmail, password));
			return true;
		} else {
			return false;
		}
	};

	// accountログイン処理
	// もし、emailとpasswordがfindByAccountEmailAndPasswordを使用して存在しなかった場合＝＝nullの場合、
	// その場合は、存在しないnullであることをコントローラクラスに知らせる
	// そうでない場合ログインしている人に情報をコントローラクラスに知らせる
	public Account loginCheck(String accountEmail, String password) {
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		if (account == null) {
			return null;
		} else {
			return account;
		}
	}

}
