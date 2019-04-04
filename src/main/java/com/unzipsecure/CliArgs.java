package com.unzipsecure;

import com.beust.jcommander.Parameter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CliArgs {
    @Parameter(
            names = "--password",
            description = "Config file path"
    )
    private String passwdPath = "passwd";
    @Parameter(
            names = "--archive",
            description = "Config file path"
    )
    private String archivePath = "archive*";
    @Parameter(
            names = "--destination",
            description = "Config file path"
    )
    private String destination = "unzip/";
    @Parameter(
            names = "--secretKey",
            description = "Config file path"
    )
    private String secretKeyPath = "private.der";



}
