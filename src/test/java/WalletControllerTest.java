import com.example.wallet.WalletApplication;
import com.example.wallet.dto.OperationType;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = WalletApplication.class)
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    public void testDeposit() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(UUID.randomUUID());
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(BigDecimal.valueOf(1000));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Operation Successful"));
    }

    @Test
    public void testGetWalletBalanceSuccess() throws Exception {
        UUID walletId = UUID.fromString("a4e63d74-45c9-4a59-9a93-349a788f3d12");
        WalletResponse walletResponse = new WalletResponse(walletId.toString(), BigDecimal.valueOf(1100.00));

        Mockito.when(walletService.getWalletBalance(walletId)).thenReturn(walletResponse);

        mockMvc.perform(get("/api/v1/wallet/" + walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(1100.00));
    }
}