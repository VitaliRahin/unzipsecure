package com.unzipsecure.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtils {

    public static byte[] readFileToBytes(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public static ZipFile receiveZipFile(String fileName) throws ZipException {
        File file = new File(fileName);
        return new ZipFile(file);
    }
}
