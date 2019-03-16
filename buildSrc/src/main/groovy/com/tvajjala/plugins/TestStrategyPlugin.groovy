package com.tvajjala.plugins

import com.tvajjala.actions.SrcSetAction
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.tvajjala.constants.Constants.SOURCE_SET
import static com.tvajjala.constants.TestStrategy.COMPONENT
import static com.tvajjala.constants.TestStrategy.CONTRACT
import static com.tvajjala.constants.TestStrategy.FUNCTIONAL
import static com.tvajjala.constants.TestStrategy.LAYER

/**
 * @author ThirupathiReddy Vajjala
 *
 * Plugin used to generate custom sourceSet for different types of tests execution
 * this allows you to write test cases in a clean way
 *
 */
class TestStrategyPlugin implements Plugin<Project> {

    Logger LOG= LoggerFactory.getLogger(TestStrategyPlugin.class)


    @Override
    void apply(Project project) {

        LOG.debug("Generating sourceSet and tasks for testing")

        SourceSetContainer container = (SourceSetContainer) project.getProperties().get(SOURCE_SET)

        container.create(COMPONENT.sourceSetName, new SrcSetAction(COMPONENT, project))

        container.create(LAYER.sourceSetName, new SrcSetAction(LAYER, project))

        container.create(CONTRACT.sourceSetName, new SrcSetAction(CONTRACT, project))

        container.create(FUNCTIONAL.sourceSetName, new SrcSetAction(FUNCTIONAL, project))


    }


}
