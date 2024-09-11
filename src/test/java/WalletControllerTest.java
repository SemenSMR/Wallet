import com.example.wallet.dto.OperationType;
import com.example.wallet.dto.WalletOperationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDeposit() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(UUID.randomUUID());
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(BigDecimal.valueOf(1000));

        mockMvc.perform(post("/api/v1 /wallet")
                 .contentType(MediaType.APPLICATION_JSON) .content(new ObjectMapper().writeValueAsString(request))) .andExpect(status().isOk()) .andExpect(content().string("Operation successful")); }

        @Test
        public void testGetWalletBalance() throws Exception {
            UUID walletId = UUID.randomUUID();

            mockMvc.perform(get("/api/v1/wallets/" + walletId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(walletId.toString()))
                    .andExpect(jsonPath("$.balance").isNumber());
        }
    }