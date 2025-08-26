package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.services.AccountService;

@Controller
public class AccountRergisterController {
	@Autowired
	private AccountService accountService;

	// account登録画面の表示
	@GetMapping("/account/register")
	public String getAccountRegisterPage() {
		return "account_register.html";
	}

	// account登録処理
	@PostMapping("/account/register/process")
	public String accountRegisterProcess(@RequestParam String accountName, @RequestParam String accountEmail,
			@RequestParam String password) {
		// もし、creatAccountがtrueだたら、ログイン(account_login.html)へ
		// そうではない場合、account_register.htmlへ
		if (accountService.createAccount(accountName, accountEmail, password)) {
			return "account_login.html";
		} else {
			return "account_register.html";
		}

	}

}
