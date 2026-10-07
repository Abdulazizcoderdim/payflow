package uz.payflow.account;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    void createReturns201WithAccount() throws Exception {
        Account account = new Account("20208000000000000001", "Ali Valiyev", Currency.UZS);
        when(accountService.create(anyString(), any(Currency.class))).thenReturn(account);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"ownerName": "Ali Valiyev", "currency": "UZS"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value("20208000000000000001"))
                .andExpect(jsonPath("$.currency").value("UZS"));
    }

    @Test
    void createReturns400WhenOwnerNameIsBlank() throws Exception {
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"ownerName": "", "currency": "UZS"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.ownerName").exists());

        verifyNoInteractions(accountService);
    }

    @Test
    void getByIdReturns404WhenAccountDoesNotExist() throws Exception {
        when(accountService.getById(999L)).thenThrow(new AccountNotFoundException(999L));

        mockMvc.perform(get("/api/v1/accounts/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Hisob topilmadi: id=999"));
    }
}
