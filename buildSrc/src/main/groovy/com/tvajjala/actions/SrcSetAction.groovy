package com.tvajjala.actions

import com.tvajjala.actions.internal.ResourceDirectoryAction
import com.tvajjala.actions.internal.SrcDirectoryAction
import com.tvajjala.constants.TestStrategy
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.testing.Test

import static com.tvajjala.constants.Constants.SOURCE_SET

/**
 * @author ThirupathiReddy Vajjala
 *
 * Creates SourceSet and add required classpath to that directory
 *
 */
class SrcSetAction implements Action<SourceSet> {

    TestStrategy testStrategy

    Project project

    SourceSetContainer container

    SrcSetAction(TestStrategy testStrategy, Project project) {
        this.testStrategy = testStrategy
        this.project = project
        this.container = (SourceSetContainer) project.getProperties().get(SOURCE_SET)
    }


    @Override
    void execute(SourceSet sourceSet) {

        def javaSrcSet = sourceSet.java(new SrcDirectoryAction(testStrategy.taskName))
        /** Setting classpath to avoid compilation errors */
        javaSrcSet.setCompileClasspath(javaSrcSet.compileClasspath + container.getByName("main").output.classesDirs+
                container.getByName("main").compileClasspath)

        /** Setting runtime classpath to execute tests */
        javaSrcSet.setRuntimeClasspath(javaSrcSet.runtimeClasspath + container.getByName("main").output.classesDirs+
                container.getByName("main").runtimeClasspath)

        /** Defining resource folder for yml or properties  */
        sourceSet.resources(new ResourceDirectoryAction(testStrategy.taskName))



        TaskContainer taskContainer = project.getTasks()
        /** Define task to execute specific testCases */
        taskContainer.create(testStrategy.taskName, Test.class, new TaskAction(testStrategy, project))
        taskContainer.getByName("clean", new CleanupAction(testStrategy.taskName))

    }


}
