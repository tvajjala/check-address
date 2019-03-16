package com.tvajjala.constants

/**
 * Define your testing strategies
 *
 * @author ThirupathiReddy
 *
 */
enum TestStrategy {

    COMPONENT{

        @Override
        def getDescription() {
            return "Tests individual components"
        }

        @Override
        def getSourceSetName() {
            return getTaskName()
        }

        @Override
        def getTaskName() {
            return "componentTest"// directoryName both same
        }

    }, LAYER{
        @Override
        def getDescription() {
            return "Tests controller and client layers by mocking underlying services"
        }

        @Override
        def getSourceSetName() {
            return getTaskName()
        }

        @Override
        def getTaskName() {
            return "layerTest"
        }

    }, CONTRACT{
        @Override
        def getDescription() {
            return "Tests contract between two services"
        }

        @Override
        def getSourceSetName() {
            return getTaskName()
        }

        @Override
        def getTaskName() {
            return "contractTest"
        }

    }, FUNCTIONAL{

        @Override
        def getDescription() {
            return "Tests complete application functionality"
        }

        @Override
        def getSourceSetName() {
            return getTaskName()
        }

        @Override
        def getTaskName() {
            return "functionalTest"
        }

    }



    abstract getSourceSetName()

    abstract getDescription()

    abstract getTaskName()

}