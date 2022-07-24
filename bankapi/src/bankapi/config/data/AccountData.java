package bankapi.config.data;

import bankapi.Account;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

@Data
@Builder
public class AccountData {
    public HashMap<UUID, Account> accounts;
}