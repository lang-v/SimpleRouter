package com.sl.router.util

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class FileUtil {
    public static void copyFile(Path source, Path dest){
        Files.copy(source,dest, StandardCopyOption.REPLACE_EXISTING)
    }
}
