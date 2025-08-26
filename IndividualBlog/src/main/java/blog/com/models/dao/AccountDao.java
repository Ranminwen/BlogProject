package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
	//保存処理を更新処理　insertとupdate
	Account save(Account account);
	
	//SELECT * FROM account WHERE account_email = ?
	//同じメールアドレスがあったら登録させないように
	Account findByAccountEmail(String accountEmail);
	
	//SELECT * FROM account WHERE account_email = ? AND password
	//メールアドレスとパスワードが一致している場合ログインできるように
	Account findByAccountEmailAndPassword(String accountEmail,String password);
}
