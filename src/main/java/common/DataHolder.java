package common;

import java.io.Serializable;
import java.time.LocalDate;
//serializable class in common package to promote future reusability
public class DataHolder implements Serializable {
    private final String siteObtained;
    private final LocalDate expirationDate;

    public DataHolder(String siteObtained, LocalDate expirationDate) {
        this.siteObtained = siteObtained;
        this.expirationDate = expirationDate;
    }

    public String getSiteObtained() {
        return siteObtained;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
