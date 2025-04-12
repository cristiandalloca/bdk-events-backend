package br.com.bdk.eventsmanager.core.util;


import br.com.bdk.eventsmanager.core.storage.FileDownloadException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@UtilityClass
public class MultipartFileUtil {

    @NonNull
    public static byte[] toByte(MultipartFile multipartFile) throws FileDownloadException {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileDownloadException(e);
        }
    }
}
