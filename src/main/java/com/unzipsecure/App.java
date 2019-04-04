package com.unzipsecure;

import com.beust.jcommander.JCommander;
import com.unzipsecure.secure.Decrypt;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.unzipsecure.utils.FileUtils.receiveZipFile;
import static com.unzipsecure.utils.ZipUtils.unzipfile;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        CliArgs cliArgs = new CliArgs();
        JCommander.newBuilder().addObject(cliArgs).build().parse(args);

        Decrypt decrypt = Decrypt.getInstance(cliArgs.getSecretKeyPath());
        char[] pass = decrypt.decryptPassword(cliArgs.getPasswdPath());
        try {
            ZipFile zipFile = receiveZipFile(cliArgs.getArchivePath());
            unzipfile(zipFile, cliArgs.getDestination(), pass);
        } catch (ZipException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
