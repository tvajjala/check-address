package com.tvajjala.constants

/**
 * Define your testing strategies, and make sure update the same in gradle.properties
 *
 * for unitTest you need to add unitTestImplementation
 *
 * @author ThirupathiReddy Vajjala
 *
 */
enum TestStrategy {

    UNIT{

        @Override
        def getDescription() {
            return "TestSuit for unitTests"
        }

        @Override
        def getSourceSetName() {
            return getTaskName()
        }

        @Override
        def getTaskName() {
            return "unitTest"
        }

    }, LAYER{
        @Override
        def getDescription() {
            return "TestSuit for clientLayerTests"
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
            return "TestSuit for ContractTest between two micro services"
        }

        @Override
        def getSourceSetName() {
            return getTaskName()
        }

        @Override
        def getTaskName() {
            return "contractTest"
        }

    }, INTEGRATION{

        @Override
        def getDescription() {
            return "TestSuit for IntegrationTests"
        }

        @Override
        def getSourceSetName() {
            return getTaskName()
        }

        @Override
        def getTaskName() {
            return "integrationTest"
        }

    }



    abstract getSourceSetName()

    abstract getDescription()

    abstract getTaskName()

}