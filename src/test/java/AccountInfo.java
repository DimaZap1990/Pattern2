import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AccountInfo {
    String login;
    String password;
    String status;
}
