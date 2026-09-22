package com.ddrissq.sigdosi.common.file.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileAnalyzer {

    private static final Tika TIKA = new Tika();
    private static final MimeTypes MIME_TYPES = MimeTypes.getDefaultMimeTypes();

    public static String getMimeType(MultipartFile file) throws IOException, MimeTypeException {
        MimeType mimeType = detectMimeType(file.getInputStream());
        return mimeType.getName();
    }

    public static String getMimeType(Resource resource) throws IOException, MimeTypeException {
        MimeType mimeType = detectMimeType(resource.getInputStream());
        return mimeType.getName();
    }

    public static String getExtension(MultipartFile file) throws IOException, MimeTypeException {
        MimeType mimeType = detectMimeType(file.getInputStream());
        return mimeType.getExtension();
    }

    private static MimeType detectMimeType(InputStream inputStream) throws IOException, MimeTypeException {
        String mediaType = TIKA.detect(inputStream);
        return MIME_TYPES.getRegisteredMimeType(mediaType);
    }

}
