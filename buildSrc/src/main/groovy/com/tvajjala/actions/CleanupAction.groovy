package com.tvajjala.actions

import org.gradle.api.Action
import org.gradle.api.Task
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author ThirupathiReddy Vajjala
 *
 * Cleanup action
 *
 */
class CleanupAction implements Action<Task>{

    static Logger LOG= LoggerFactory.getLogger(CleanupAction.class)

    String taskName

    CleanupAction(String taskName){

        this.taskName=taskName
    }

    @Override
    void execute(Task task) {

        //TODO: cleanup move to separate task
        LOG.debug(  "Cleaning Started ...")

        File duplicateSrc = new File("src/$taskName")

        if (duplicateSrc.exists()) {

            LOG.info('Deleting duplicate directory')

            duplicateSrc.deleteDir()
        }

        duplicateSrc = new File("src/test/java")

        if (duplicateSrc.exists()) {

            LOG.info('Deleting default test directory')

            duplicateSrc.delete()
        }


        File duplicateResource = new File("src/test/resources")

        if (duplicateResource.exists()) {

            LOG.info('Deleting default resource directory')

            duplicateResource.delete()
        }

        LOG.debug( "Cleaning Completed ...")

    }
}
