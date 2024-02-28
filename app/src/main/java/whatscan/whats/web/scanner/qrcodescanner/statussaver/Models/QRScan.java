package whatscan.whats.web.scanner.qrcodescanner.statussaver.Models;

import java.util.Date;

public class QRScan {
    private long id;
    private String content;
    private Date timestamp;

    public QRScan(long id, String content, Date timestamp) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
