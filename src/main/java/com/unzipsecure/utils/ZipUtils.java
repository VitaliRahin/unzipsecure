package com.unzipsecure.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.unzipsecure.errors.ErrorMessages.EXCEPTION_ZIPFILE;

public final class ZipUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);

    public static void unzipfile(ZipFile zfile, String dest, char[] passwd) throws ZipException {
        if (zfile == null || !zfile.isValidZipFile()) {
            LOGGER.error(EXCEPTION_ZIPFILE);
            throw new ZipException(EXCEPTION_ZIPFILE);
        }
        File file = new File(dest);
        if (file.isDirectory() && !file.exists()) {
            file.mkdirs();
        }
        if (zfile.isEncrypted()) {
            zfile.setPassword(passwd);
        }
        zfile.extractAll(dest);
    }
}
