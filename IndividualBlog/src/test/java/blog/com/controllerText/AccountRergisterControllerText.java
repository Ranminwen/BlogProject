package blog.com.controllerText;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.com.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountRergisterControllerText {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareDate() {
		// 登録できる場合 "test5", "test5@email.com" "1234abcd"、ture
		when(accountService.createAccount("test5", "test5@email.com", "1234abcd")).thenReturn(true);
		// 登録できない場合 メールアドレスは"test2@email.com" 、名前やスワードはどんな値でもいいfalse
		when(accountService.createAccount(any(), eq("test2@email.com"), any())).thenReturn(false);
	}

	// 登録画面が正常表示できるテスト
	@Test
	public void testGetAccountRegisterPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/account/register");
		mockMvc.perform(request).andExpect(view().name("account_register.html"));
	}
	
	// 新規登録が成功するかのテスト
	@Test
	public void testAccountRegisterProcess_succed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.post("/account/register/process")
				.param("accountName", "test5")
				.param("accountEmail", "test5@email.com")
				.param("password", "1234abcd");	
		mockMvc.perform(request).andExpect(view().name("account_login.html"));
	}
	
	// 新規登録が失敗するかのテスト
	@Test
	public void testAccountRegisterProcess_Fail() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/account/register/process")
				.param("accountName", "test2")
				.param("accountEmail", "test2@email.com")
				.param("password", "abcd");	
		
		mockMvc.perform(request)
		.andExpect(view().name("account_register.html"));
		
	}
	
	

}
