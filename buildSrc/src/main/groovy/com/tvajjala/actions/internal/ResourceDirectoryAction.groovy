package com.tvajjala.actions.internal

import org.gradle.api.Action
import org.gradle.api.file.SourceDirectorySet

/**
 * @author ThirupathiReddy Vajjala
 *
 * Generates new resource directory for test resources
 *
 */
class ResourceDirectoryAction implements Action<SourceDirectorySet> {

    String directoryName

    ResourceDirectoryAction(String directoryName) {
        this.directoryName = directoryName
    }

    @Override
    void execute(SourceDirectorySet files) {

        files.srcDir("src/test/$directoryName/resources")

    }
}