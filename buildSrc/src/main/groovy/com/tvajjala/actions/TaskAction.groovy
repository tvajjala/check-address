package com.tvajjala.actions

import com.tvajjala.constants.TestStrategy
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.SourceSetContainer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.tvajjala.constants.Constants.GROUP_NAME
import static com.tvajjala.constants.Constants.SOURCE_SET

/**
 * @author ThirupathiReddy Vajjala
 *
 * Registers tasks to project
 *
 */
class TaskAction implements Action<Task> {

    static Logger LOG= LoggerFactory.getLogger(TaskAction.class)

    TestStrategy testStrategy

    Project project

    SourceSetContainer container

    TaskAction(TestStrategy testStrategy, Project project) {

        this.testStrategy = testStrategy
        this.project = project
        this.container = (SourceSetContainer) project.getProperties().get(SOURCE_SET)
    }


    @Override
    void execute(Task task) {

        /** Group name where task is defined */
        task.setGroup(GROUP_NAME)

        LOG.info("Adding $testStrategy.taskName to Group $GROUP_NAME")

        /** Description of the task */
        task.setDescription(testStrategy.description)

        /** Specify test classes location to run */
        task.setTestClassesDirs(container.getByName(testStrategy.sourceSetName).output.classesDirs)

        /** Specify classpath for runtime dependencies */
        task.setClasspath(container.getByName(testStrategy.sourceSetName).runtimeClasspath)

        LOG.info("Setting classpath to task")
    }
}
