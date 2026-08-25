package com.ddrissq.sigdosi.common.file.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileAnalyzer {

    private static final Tika TIKA = new Tika();
    private static final MimeTypes MIME_TYPES = MimeTypes.getDefaultMimeTypes();

    public static String detectContentType(MultipartFile file) throws IOException {
        return TIKA.detect(file.getInputStream());
    }

    public static String detectExtension(MultipartFile file) throws IOException, MimeTypeException {
        String contentType = detectContentType(file);
        MimeType mimeType = MIME_TYPES.getRegisteredMimeType(contentType);
        return mimeType.getExtension();
    }

}
