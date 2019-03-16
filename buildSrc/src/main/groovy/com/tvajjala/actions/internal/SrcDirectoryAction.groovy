package com.tvajjala.actions.internal

import org.gradle.api.Action
import org.gradle.api.file.SourceDirectorySet

/**
 * @author ThirupathiReddy Vajjala
 *
 * Generates new source directory for test cases
 *
 */
class SrcDirectoryAction implements Action<SourceDirectorySet> {

    String directoryName


    SrcDirectoryAction(String directoryName) {
        this.directoryName = directoryName
    }

    @Override
    void execute(SourceDirectorySet files) {


        files.srcDirs("src/test/$directoryName/java","src/test/$directoryName/groovy")

    }
}
