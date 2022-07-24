package bankapi.config.data;

import lombok.Data;

@Data
public class BankData {
    double startingBalance = 0;
    int autosaveMinutes = 60;
    int baltopCount = 5;
    int MAX_BAL_TOP_SEARCH = 10000;

    public long getAutosaveInTicks () { return this.autosaveMinutes * 60L * 20L; }
}