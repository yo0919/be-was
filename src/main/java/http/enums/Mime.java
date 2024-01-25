package http.enums;

import java.util.Arrays;

public enum Mime {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    ICO("ico", "image/x-icon"),
    NONE("none", "");

    private final String extension;
    private final String mimeType;

    Mime(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static Mime convertMime(String ext) {
        return Arrays.stream(Mime.values())
                .filter(m -> m.extension.equalsIgnoreCase(ext))
                .findFirst()
                .orElse(Mime.NONE);
    }
}
